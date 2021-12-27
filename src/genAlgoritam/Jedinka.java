package genAlgoritam;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Jedinka {

    public Jedinka(){}

    public Jedinka(Jedinka j)
    {
        this.vrijednost=j.vrijednost;
        this.bd=j.bd;
        this.binarniKod=j.binarniKod;
        this.fitnesFunkcija=j.fitnesFunkcija;
    }
    public  double getVrijednost() {
        return vrijednost;
    }

    public  void setVrijednost(double vrijednost) {
        this.vrijednost = vrijednost;
    }

    public  double vrijednost;
    public  int bd;
    public  String binarniKod;
    public double p;
    public double q;

    public void racunanjeP()
    {
        p=fitnesFunkcija/Populacija.getOcjena_populacije();

    }

    public void racunanjeQ()
    {
        int ind=(Main.p.lista).indexOf(this);
        if(ind-1>=0)
            q=((LinkedList<Jedinka>)Main.p.lista).get(ind-1).getQ()+((LinkedList<Jedinka>)Main.p.lista).get(ind).getP();
        else
            q=((LinkedList<Jedinka>)Main.p.lista).get(ind).getP();

    }

    public  double getP() {
        racunanjeP();
        return p;
    }

    public void setP(double p) {
        this.p = p;
    }

    public double getQ() {
        racunanjeQ();
        return q;
    }

    public void setQ(double q) {
        this.q = q;
    }

    public  double getFitnesFunkcija() {
        return fitnesFunkcija;
    }

    public   void setFitnesFunkcija(double fitnesFunkcija) {
        this.fitnesFunkcija = fitnesFunkcija;
    }

    public  double getVrijednost_funkcije() {
        return vrijednost_funkcije;
    }

    public  void setVrijednost_funkcije(double vrijednost_funkcije) {
        this.vrijednost_funkcije = vrijednost_funkcije;
    }

    public  double fitnesFunkcija;
    public  double vrijednost_funkcije;

    public void izracunavanjeBd()
    {
         bd=(int)Math.floor(((vrijednost-Populacija.donjaG)/(Populacija.gornjaG-Populacija.donjaG))*(Math.pow(2, Populacija.brojBita)-1)); //Math.floor donja granica vrijednosti

    }

    public void izracunavanje_vrijednosti()
    {
        vrijednost=Populacija.donjaG+(((Populacija.gornjaG-Populacija.donjaG)/(double)(Math.pow(2,Populacija.brojBita)-1))*Integer.parseInt(binarniKod, 2));

    }

    public  int getBd() {
        return bd;
    }

    public  void setBd(int bd) {
        this.bd = bd;
    }

    public  String getBinarniKod() {
        return binarniKod;
    }

    public  void setBinarniKod(String binarniKod) {
        this.binarniKod = binarniKod;
    }

    public void binarnoKodovanje()
    {
        String tmp=Integer.toBinaryString(bd);
        binarniKod = String.format("%10s", tmp).replace(' ', '0');
    }

    public   void funkcija(double x)
    {
        vrijednost_funkcije=0.015*Math.pow(x,3)-5*Math.pow(x,2)+10*x+1500*Math.sin(0.25*x);

    }



    public  void fitnes()
    {
        double min=trazenjeMin();
        for (int i=0;i<Main.p.lista.size();i++)
        {
            Main.p.lista.get(i).setFitnesFunkcija(Main.p.lista.get(i).getVrijednost_funkcije()-min);

        }



    }

    public void naj()
    {
        List<Jedinka> list=Main.p.lista;
        fitnes();
        double max=list.get(0).getFitnesFunkcija();

        int id=0;
        for (int i=0;i<Main.p.lista.size();i++)
        {
            if(Main.p.lista.get(i).getFitnesFunkcija()==0)
                System.out.println("\nNajlosije prilagodjena jedinka je "+(i+1));

            if ((double)Main.p.lista.get(i).getFitnesFunkcija()>max)
                max=Main.p.lista.get(i).getFitnesFunkcija();
        }

        for (int i=0;i<Main.p.lista.size();i++)
        {
            if ((double)Main.p.lista.get(i).getFitnesFunkcija()==max)
                id=i;
        }

        System.out.println("\nNajbolje prilagodjena jedinka je "+(id+1));

        double sum=0.0;
        for (int i=0;i<Main.p.lista.size();i++)
        {
            sum+=Main.p.lista.get(i).fitnesFunkcija;

        }

        System.out.println("\nSrednja vrijednost fitnes funkcije je: "+(sum/Main.p.lista.size()));
    }

    public  double trazenjeMin()
    {
        double tmp=(double)Main.p.lista.get(0).getVrijednost_funkcije();

        for (int i=1;i<Main.p.lista.size();i++)
        {
            if ((double)Main.p.lista.get(i).getVrijednost_funkcije()<tmp)
                tmp=Main.p.lista.get(i).getVrijednost_funkcije();

        }

        return tmp;
    }



}


