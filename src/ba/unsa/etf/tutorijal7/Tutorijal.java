package ba.unsa.etf.tutorijal7;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Tutorijal {

    public static ArrayList<Grad> ucitajGradove() {
        ArrayList<Grad> gradovi = new ArrayList<>();
        Scanner ulaz;
        String zarez = ",";
        try {
            ulaz = new Scanner(new FileReader("mjerenja.txt"));
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
        }
        return gradovi;
    }

    public static void main(String[] args) {
    }
}
