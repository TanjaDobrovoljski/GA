package genAlgoritam;

import java.util.Comparator;
import java.util.Random;

public class Main {
    public static  Populacija p=new Populacija();

    public static void main(String args[]) {
        p.brojBita();

        Jedinka j=new Jedinka();

        int i=0;
         int k=0;

        while (i<100) {
            System.out.println("Iteracija "+(i+1)+" :\n");
            if (k>0)
                p.ponavljanje();
            else
                j.naj();
            p.selekcija();
            p.rekombinacija();
            p.mutacija();
            k++;

            System.out.println("================================================\nBroj rekombinacija: " + p.br_rekomb + " Broj mutacija: " + p.br_mut);
        i++;

        }

        System.out.println("Ukupno je kroz 100 generacija bilo "+p.br_rekombinacija+" rekombinacija, i "+p.br_mutacija+" mutacija\n");
    }
}
