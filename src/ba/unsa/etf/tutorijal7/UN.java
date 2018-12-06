package ba.unsa.etf.tutorijal7;

import java.util.ArrayList;

public class UN {
    private ArrayList<Drzava> drzave;
    public UN(){
        setDrzave(new ArrayList<Drzava>());
    }
    public UN(ArrayList<Drzava> drzava){
        this.setDrzave(drzava);
    }
    public ArrayList<Drzava> getDrzave() {
        return drzave;
    }
    public void setDrzave(ArrayList<Drzava> drzava) {
        this.drzave = drzava;
    }
}
