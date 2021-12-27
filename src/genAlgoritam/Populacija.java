package genAlgoritam;

import java.util.*;

import static java.util.Comparator.comparing;

public class Populacija {

    public    List<Jedinka> lista=new LinkedList<>();
    private static double vjerovatnoca_rekombinacije= (float) 0.15;
    private static double vjerovatnoca_mutacije=(float)0.05;
    public static int velicina_populacije=8;
    public static int donjaG=-30;
    public static int gornjaG=30;
    public static int preciznost=1;
    public static  int brojBita=10;
    public static double ocjena_populacije=0.0;
    public static  int br_rekombinacija=0;
    public static  int br_mutacija=0;

    public   int br_rekomb=0;
    public   int br_mut=0;

    public static void izracunavanjeOcjene()
    {
        double sum=0.0;
        for (Jedinka j:Main.p.lista
             ) {

            sum+=j.fitnesFunkcija;
        }
        ocjena_populacije=sum;
    }

    public static double getOcjena_populacije() {
        izracunavanjeOcjene();
        return ocjena_populacije;
    }

    public void setOcjena_populacije(double ocjena_populacije) {
        this.ocjena_populacije = ocjena_populacije;
    }

    public Populacija(float r, float m, int velicina)
    {
        this.vjerovatnoca_rekombinacije=r;
        this.vjerovatnoca_mutacije=m;

        this.velicina_populacije=velicina;
        popunjavanje();
    }
    public Populacija()
    {
        popunjavanje();
    }

    public static void brojBita() //broj bita za jedinku
    {
        double tmp= (int) (Math.log(((gornjaG-donjaG)*Math.pow(10,preciznost))+1)/Math.log(2));
        brojBita=(int)tmp+1;

    }
    private  void popunjavanje()
    {
        Random r=new Random();
        for(int i=0;i<velicina_populacije;i++)
        {
            Jedinka nova=new Jedinka();
            double v=r.nextDouble();
            nova.setVrijednost(((2*gornjaG)*v-gornjaG));
            nova.izracunavanjeBd();
            nova.binarnoKodovanje();
            nova.funkcija(nova.getVrijednost());


            lista.add(nova);
            System.out.println((i+1)+" jedinka: "+nova.getVrijednost()+"\n    Bd: "+nova.getBd()+"             Binarni kod: "+nova.getBinarniKod()+"\n----------------------------------------------------------------------");
        }



    }

    public   void ponavljanje()
    {

        for(int i=0;i<lista.size();i++)
        {
            lista.get(i).izracunavanjeBd();
            lista.get(i).binarnoKodovanje();
            lista.get(i).funkcija(lista.get(i).getVrijednost());

            System.out.println((i+1)+" jedinka: "+lista.get(i).getVrijednost()+"\n    Bd: "+lista.get(i).getBd()+"             Binarni kod: "+lista.get(i).getBinarniKod()+"\n----------------------------------------------------------------------");
        }

    }

    public void sortiranje()
    {

        Collections.sort(lista, new Comparator<Jedinka>() {
            @Override
            public int compare(Jedinka o1, Jedinka o2) {
                return Double.compare(o1.getP(), o2.getP());
            }

            
        });
        ispis();
        System.out.println("---------------------------------------------------------------------------------------------------------");
    }





    public void selekcija() {

        System.out.println("Selekcija: ");
        List<Jedinka> l = new LinkedList<>();
        Random r = new Random();
        while (l.size() < velicina_populacije) {
            Jedinka prev = null, curr = null;
            Iterator<Jedinka> it = lista.iterator();
            if (it.hasNext())
                prev = it.next();
            if (it.hasNext())
                curr = it.next();
            double tmp = r.nextDouble();
            while (curr.getQ() <= tmp) {
                prev = curr;
                if (it.hasNext())
                    curr = it.next();
                else
                    break;
            }
            l.add(new Jedinka(curr));
        }
        lista=l;
        ispis();
    }

    public void ispis()
    {
        int i=0;
        for (Jedinka j: lista
             )
        {
            System.out.println((++i)+" jedinka: "+j.getVrijednost()+"\n    Bd: "+j.getBd()+"             Binarni kod: "+j.getBinarniKod()+"  Fitnes f-ja: "+j.getFitnesFunkcija()+" P: "+j.getP()+" Q: "+j.getQ());

        }
        System.out.println("------------------------------------------------------------------------");

    }

    public void rekombinacija()
    {
        br_rekomb=0;
        System.out.println("Rekombinacija: ");

        Random r=new Random();
        for(int i=0;i<5;i++)
        {
            int h1=(int)Math.floor(velicina_populacije*r.nextDouble());
            int h2=(int)Math.floor(velicina_populacije*r.nextDouble());

            while(h1==h2)
            {
                h1=(int)Math.floor(velicina_populacije*r.nextDouble());
                h2=(int)Math.floor(velicina_populacije*r.nextDouble());
            }
            Collections.swap(lista,h1,h2);

        }
        ispis();

        Iterator<Jedinka> it=lista.iterator();
        while(it.hasNext())
        {
            Jedinka prva=it.next(),druga=null;
            if(it.hasNext())
                druga=it.next();
            if(prva != null && druga != null) {
                rekomb(prva, druga);

            }
        }
        System.out.println("=============================================================");
        ispis();

    }

    public void rekomb(Jedinka j1,Jedinka j2)
    {
        Random r=new Random();

        double m=r.nextDouble();
        if(m<vjerovatnoca_rekombinacije)
        {
            br_rekombinacija++;
            br_rekomb++;


            int tacka=(int)Math.floor((brojBita-1)*r.nextDouble());

            String prva=j1.getBinarniKod();
            String druga=j2.getBinarniKod();

            String prva1 = prva.substring(0,tacka);
            String prva2 = prva.substring(tacka);

            String druga1=druga.substring(0,tacka);
            String druga2= druga.substring(tacka);



            String nova1=prva1+druga2;
            String nova2=druga1+prva2;
            j1.setBinarniKod(nova1);
            j2.setBinarniKod(nova2);

            j1.izracunavanje_vrijednosti();
            j2.izracunavanje_vrijednosti();
            j1.izracunavanjeBd();
            j2.izracunavanjeBd();


        }
    }

    public void mutacija()
    {
        br_mut=0;
        System.out.println("Mutacija:");
        Random r=new Random();

        for(int i=0;i<lista.size();i++)
        {
            if(r.nextDouble()<vjerovatnoca_mutacije)
            {
                br_mutacija++;
                br_mut++;
                String binKod=lista.get(i).getBinarniKod();
                int bit=(int) Math.floor(brojBita*r.nextDouble());

                System.out.println(binKod+"  "+lista.get(i).getVrijednost()+"  bit: "+bit);
                String tmp="";

                if(binKod.charAt(binKod.length()-bit-1)=='0')
                    tmp=replace(binKod,binKod.length()-bit-1,'1');

                 else
                    tmp=replace(binKod,binKod.length()-bit-1,'0');
                System.out.println(tmp);

                lista.get(i).setBinarniKod(tmp);
                lista.get(i).izracunavanje_vrijednosti();
                lista.get(i).izracunavanjeBd();

            }

        }

        ispis();
    }

    public String replace(String str, int index, char replace){
        if(str==null){
            return str;
        }else if(index<0 || index>=str.length()){
            return str;
        }
        char[] chars = str.toCharArray();
        chars[index] = replace;
        return String.valueOf(chars);
    }


}
