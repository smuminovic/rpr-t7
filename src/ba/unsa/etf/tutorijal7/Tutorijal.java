package ba.unsa.etf.tutorijal7;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Tutorijal {

    public static ArrayList<Grad> ucitajGradove() {
        ArrayList<Grad> gradovi = new ArrayList<>();
        Scanner ulaz;
        String zarez = ",";
        try {
            ulaz = new Scanner(new FileReader("mjerenja.txt.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("Datoteka se ne može otvoriti.");
            return gradovi;
        }
        try {
            while (ulaz.hasNextLine()) {
                int br=0;
                String imeGrada = "";
                String linija = ulaz.nextLine();
                int i;
                for (i = 0; i < linija.length(); i++) {
                    if (linija.charAt(i) != zarez.charAt(0)) {
                        imeGrada+=linija.charAt(i);
                    }
                    else {
                        int j;
                        double[] temperature = new double[1000];
                        String temp = "";
                        for (j = i; j < linija.length(); j++) {
                            if (linija.charAt(j) != zarez.charAt(0)) {
                                temp += linija.charAt(j);
                                temperature[br] = Double.parseDouble(temp);
                                br++;
                            }
                            Grad g = new Grad(imeGrada, 0, temperature);
                            gradovi.add(g);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            System.out.println("Greška pri čitanju datoteke.");
            System.out.println("Greška: " + e);
        }
        finally {
            ulaz.close();
        }
        return gradovi;
    }

    public static UN ucitajXml(ArrayList<Grad> gradovi) {
        int i;
        UN un = new UN();
        ArrayList<Drzava> drzave = new ArrayList<Drzava>();
        Drzava drzava = new Drzava();
        Grad grad = new Grad();
        Document xmldoc = null;
        try {
            DocumentBuilder docReader = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            xmldoc = docReader.parse(new File("drzave.xml"));
        } catch (Exception e) {
            System.out.println("drzave.xml nije validan XML dokument");
        }
        Element korijen = xmldoc.getDocumentElement();
        NodeList nodeList = korijen.getChildNodes();
        for (i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if(node instanceof Element) {
                Element element = (Element) node;
                String naziv = element.getElementsByTagName("naziv").item(0).getTextContent();
                int brojStanovnika = Integer.parseInt(element.getAttribute("brojStanovnika"));
                String jedinica = element.getElementsByTagName("povrsina").item(0).getAttributes().item(0).getTextContent();
                int povrsina = Integer.parseInt(element.getElementsByTagName("povrsina").item(0).getTextContent());
                Element glavniGrad = (Element) element.getElementsByTagName("glavnigrad").item(0);
                String glavniGradIme = glavniGrad.getElementsByTagName("naziv").item(0).getTextContent();
                int glavniStanovnika = Integer.parseInt(glavniGrad.getAttribute("stanovnika"));

                for(int j = 0; j < gradovi.size(); j++) {
                    if(glavniGradIme.equals(gradovi.get(j).getNaziv())) {
                        Grad noviGrad = new Grad();
                        noviGrad.setNaziv(glavniGradIme);
                        noviGrad.setBrojStanovnika(glavniStanovnika);
                        noviGrad.setTemperature(gradovi.get(j).getTemperature());
                        Drzava novaDrzava = new Drzava();
                        novaDrzava.setGlavniGrad(noviGrad);
                        novaDrzava.setBrojStanovnika(brojStanovnika);
                        novaDrzava.setNaziv(naziv);
                        novaDrzava.setJedinica(jedinica);
                        novaDrzava.setPovrsina(povrsina);
                        drzave.add(novaDrzava);
                    }
                }
            }
        }
        un.setDrzave(drzave);
        return un;
    }



    public static void main(String[] args) {
    }
}
