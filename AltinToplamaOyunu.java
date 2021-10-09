import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class AltinToplamaOyunu {
    static double oklidMesafe(int x1,int y1,int x2,int y2){
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

    public static void main(String[] args) throws IOException {
        Oyun test = new Oyun();
        test.Oyna();
        int satir,sutun,baslangicAltini,adimSayisi,hamleMaliyeti,hedefMaliyeti;
        double altinOrani,gizliAltinOrani;
        Scanner sc = new Scanner(System.in);
        System.out.println("Tahta satır boyutunu giriniz:");
        satir=sc.nextInt();
        System.out.println("Tahta sütun boyutunu giriniz:");
        sutun=sc.nextInt();
        System.out.println("Tahtada bulunacak altın oranını giriniz: Ör(0.20 = %20)");
        altinOrani=sc.nextDouble();
        System.out.println("Tahtada bulunacak gizli altın oranını giriniz: Ör(0.20 = %20)");
        gizliAltinOrani=sc.nextDouble();
        System.out.println("Başlangıç altınını giriniz:");
        baslangicAltini=sc.nextInt();
        System.out.println("Adım Sayısı giriniz: ");
        adimSayisi=sc.nextInt();
        System.out.println("Hamle Maliyetini giriniz:");
        hamleMaliyeti=sc.nextInt();
        System.out.println("Hedef arama Maliyeti giriniz:");
        hedefMaliyeti=sc.nextInt();
        Oyun oyun1 = new Oyun(satir,sutun,altinOrani,gizliAltinOrani,baslangicAltini,adimSayisi,hamleMaliyeti,hedefMaliyeti);
        oyun1.Oyna();
        System.out.println("Oyun bitti, oyuncu hareketleri ve gizli altın olayları metin dosyalarına kaydedildi.\n İstatistikler yazdırıldı.");
    }
    static class Oyun{
Tahta oyunTahtasi;
int baslangicAltini;
int adimSayisi;
    Oyuncu oyuncuA;
    Oyuncu oyuncuB;
    Oyuncu oyuncuC;
    Oyuncu oyuncuD;

        public Oyun() throws IOException {
            oyunTahtasi=new Tahta();
            baslangicAltini=200;
            adimSayisi=3;
            oyuncuA=new Oyuncu(5,5,'A',baslangicAltini,0,this);
            oyuncuB=new Oyuncu(5,10,'B',baslangicAltini,1,this);
            oyuncuC=new Oyuncu(5,15,'C',baslangicAltini,2,this);
            oyuncuD=new Oyuncu(5,20,'D',baslangicAltini,3,this);
            oyuncuA.konum=new Hucre(0,0);//sol ust
            oyuncuB.konum=new Hucre(oyunTahtasi.satir-1,0); //sol alt
            oyuncuC.konum=new Hucre(oyunTahtasi.satir-1,oyunTahtasi.sutun-1); //sag alt
            oyuncuD.konum=new Hucre(0,oyunTahtasi.sutun-1); //sag ust
            oyuncuA.adimSayisi=adimSayisi;
            oyuncuB.adimSayisi=adimSayisi;
            oyuncuC.adimSayisi=adimSayisi;
            oyuncuD.adimSayisi=adimSayisi;
        }
        public Oyun(int satir,int sutun,double altinOrani,double gizliAltinOrani,int baslangicAltini,int adimSayisi,int hamleMaliyeti,int hedefMaliyeti) throws IOException {
            oyunTahtasi=new Tahta(satir,sutun,altinOrani,gizliAltinOrani);
            this.baslangicAltini=baslangicAltini;
            this.adimSayisi=adimSayisi;
            oyuncuA=new Oyuncu(hamleMaliyeti,hedefMaliyeti,'A',baslangicAltini,0,this);
            oyuncuB=new Oyuncu(hamleMaliyeti*2,hedefMaliyeti*2,'B',baslangicAltini,1,this);
            oyuncuC=new Oyuncu(hamleMaliyeti*3,hedefMaliyeti*3,'C',baslangicAltini,2,this);
            oyuncuD=new Oyuncu(hamleMaliyeti*4,hedefMaliyeti*4,'D',baslangicAltini,3,this);
            oyuncuA.konum=new Hucre(0,0);//sol ust
            oyuncuB.konum=new Hucre(oyunTahtasi.satir-1,0); //sol alt
            oyuncuC.konum=new Hucre(oyunTahtasi.satir-1,oyunTahtasi.sutun-1); //sag alt
            oyuncuD.konum=new Hucre(0,oyunTahtasi.sutun-1); //sag ust
            oyuncuA.adimSayisi=adimSayisi;
            oyuncuB.adimSayisi=adimSayisi;
            oyuncuC.adimSayisi=adimSayisi;
            oyuncuD.adimSayisi=adimSayisi;
        }
    public void Oyna() throws IOException {
        oyunTahtasi.tahtaHazirla();
        oyunTahtasi.tahtaGoruntule();
        do{
        if(oyuncuA.altinSayisi>0){
            oyuncuA.hedefeIlerle();
            tahtaGuncelle();
            oyunTahtasi.tahtaGoruntule();
        }
        if(oyuncuB.altinSayisi>0){
            oyuncuB.hedefeIlerle();
            tahtaGuncelle();
            oyunTahtasi.tahtaGoruntule();
        }
        if(oyuncuC.altinSayisi>0){
            oyuncuC.hedefeIlerle();
            tahtaGuncelle();
            oyunTahtasi.tahtaGoruntule();
        }
        if(oyuncuD.altinSayisi>0){
            oyuncuD.hedefeIlerle();
            tahtaGuncelle();
            oyunTahtasi.tahtaGoruntule();
        }
        }while(!oyunBittiMi());
        System.out.println("Oyun bitti.");
        oyuncuA.dosyayaYaz.close();
        oyuncuB.dosyayaYaz.close();
        oyuncuC.dosyayaYaz.close();
        oyuncuD.dosyayaYaz.close();
        istatistikYazdir();
    }

        private void istatistikYazdir() {
            System.out.println("Oyuncu A\nToplanan Altın Miktarı: "+oyuncuA.toplananAltin+"\nToplam Adım Sayısı:"+oyuncuA.gidilenAdim+"\nHarcanan Altın Miktarı: "+oyuncuA.harcananAltin+"\nKalan Altın Miktarı: "+oyuncuA.altinSayisi);
            System.out.println("Oyuncu B\nToplanan Altın Miktarı: "+oyuncuB.toplananAltin+"\nToplam Adım Sayısı:"+oyuncuB.gidilenAdim+"\nHarcanan Altın Miktarı: "+oyuncuB.harcananAltin+"\nKalan Altın Miktarı: "+oyuncuB.altinSayisi);
            System.out.println("Oyuncu C\nToplanan Altın Miktarı: "+oyuncuC.toplananAltin+"\nToplam Adım Sayısı:"+oyuncuC.gidilenAdim+"\nHarcanan Altın Miktarı: "+oyuncuC.harcananAltin+"\nKalan Altın Miktarı: "+oyuncuC.altinSayisi);
            System.out.println("Oyuncu D\nToplanan Altın Miktarı: "+oyuncuD.toplananAltin+"\nToplam Adım Sayısı:"+oyuncuD.gidilenAdim+"\nHarcanan Altın Miktarı: "+oyuncuD.harcananAltin+"\nKalan Altın Miktarı: "+oyuncuD.altinSayisi);
            System.out.println("Kasada Kalan Altın: "+oyunTahtasi.kasa);
        }

        public boolean oyunBittiMi(){
    if(!oyunTahtasi.altinKaldimi()){
        System.out.println("Altın kalmadı");
        return true;
    }
    else{
        if (oyuncuA.altinSayisi<=0&&oyuncuB.altinSayisi<=0&&oyuncuC.altinSayisi<=0&&oyuncuD.altinSayisi<=0){
            System.out.println("Oyuncuların altını kalmadı.");
            return true;
        }
    }
    return false;
}

    public void tahtaGuncelle() {
        for(int i=0;i<oyunTahtasi.satir;i++){
            for(int j=0;j<oyunTahtasi.sutun;j++) {
                oyunTahtasi.oyunTahtasi[i][j].Harf = '*';
                for (Hucre altinHucresi : oyunTahtasi.altinHucreleri) {
                    if (oyunTahtasi.oyunTahtasi[i][j].ustundeMi(altinHucresi) && !altinHucresi.gizliMi && oyunTahtasi.oyunTahtasi[i][j].altinMiktari > 0) {
                        oyunTahtasi.oyunTahtasi[i][j].Harf = 'G';
                        oyunTahtasi.oyunTahtasi[i][j].bulunduMu = false;
                    }
                }
                if (oyuncuA.altinSayisi > 0) {
                    if (oyuncuA.konum.ustundeMi(oyunTahtasi.oyunTahtasi[i][j]))
                        oyunTahtasi.oyunTahtasi[i][j].Harf = oyuncuA.oyuncuHarfi;
                }
                if (oyuncuB.altinSayisi > 0) {
                    if (oyuncuB.konum.ustundeMi(oyunTahtasi.oyunTahtasi[i][j]))
                        oyunTahtasi.oyunTahtasi[i][j].Harf = oyuncuB.oyuncuHarfi;
                }
                if (oyuncuC.altinSayisi > 0) {
                    if (oyuncuC.konum.ustundeMi(oyunTahtasi.oyunTahtasi[i][j]))
                        oyunTahtasi.oyunTahtasi[i][j].Harf = oyuncuC.oyuncuHarfi;
                }
                if (oyuncuD.altinSayisi>0) {
                    if (oyuncuD.konum.ustundeMi(oyunTahtasi.oyunTahtasi[i][j]))
                        oyunTahtasi.oyunTahtasi[i][j].Harf = oyuncuD.oyuncuHarfi;
                }

            }
        }
    }
}
static class Hucre{
    int x,y;
    char Harf;
    boolean gizliMi;
    boolean bulunduMu;
    int altinMiktari=0;
    public Hucre(int x,int y,char harf,boolean gizliMi){
        this.x=x;
        this.y=y;
        this.Harf=harf;
        this.gizliMi=gizliMi;
    }
    public Hucre(int x,int y){
        this.x=x;
        this.y=y;
    }
    public boolean ustundeMi(Hucre diger){
        if (diger.x==this.x&&diger.y==this.y)return true;
        else return false;
    }
}
static class Tahta{

    public int kasa;
    Hucre oyunTahtasi[][];
    ArrayList<Hucre> altinHucreleri;
    int satir;
    int sutun;
    double altinOrani;
    double gizliAltinOrani;
    Random rnd;
    public Tahta(){
        rnd = new Random();
        satir=20;
        sutun=20;
        altinOrani=0.20;
        gizliAltinOrani=0.10;
        oyunTahtasi = new Hucre[satir][sutun];
        altinHucreleri = new ArrayList<Hucre>();
        kasa=0;
    }
    public Tahta(int satir,int sutun,double altinOrani,double gizliAltinOrani){
        rnd = new Random();
        this.satir=satir;
        this.sutun=sutun;
        this.altinOrani=altinOrani;
        this.gizliAltinOrani=gizliAltinOrani;
        oyunTahtasi = new Hucre[satir][sutun];
        altinHucreleri = new ArrayList<Hucre>();
        kasa=0;
    }
    int rastgeleAltin(){
        int altin = rnd.nextInt(15)+5;
        while(altin%5!=0){
            altin=rnd.nextInt(15)+5;
        }
        return altin;
    }
    public boolean altinKaldimi(){
    for(Hucre altinHucre:altinHucreleri){
        if(!altinHucre.bulunduMu)return true;
    }
    return false;
    }
    private boolean konumBosmu(Hucre hucre){
        for(Hucre altinHucresi:altinHucreleri){
            if(altinHucresi.ustundeMi(hucre))return false;
        }
        return true;
    }
    private void altinlariBelirle(){
        int altinSayisi=(int)((satir*sutun)*altinOrani);
        int gizliAltinSayisi=(int)(altinSayisi*gizliAltinOrani);
        int randomx,randomy;
        for (int i=0;i<altinSayisi;i++){
            do{
                randomx=rnd.nextInt(satir);
                randomy=rnd.nextInt(sutun);
            }while(!konumBosmu(new Hucre(randomx,randomy)));
            Hucre temp = new Hucre(randomx,randomy,'G',false);
            temp.altinMiktari=rastgeleAltin();
            kasa+=temp.altinMiktari;
            altinHucreleri.add(temp);
        }
        for (int i=0;i<gizliAltinSayisi;i++){
            altinHucreleri.get(i).gizliMi=true;
        }
    }
    private void tahtaHazirla(){
        altinlariBelirle();
    for(int i=0;i<satir;i++){
        for(int j=0;j<sutun;j++){
            oyunTahtasi[i][j]= new Hucre(i,j,'*',false);
        }
    }
    for (Hucre altinH:altinHucreleri){
        oyunTahtasi[altinH.x][altinH.y]=altinH;
    }
    }
    public void tahtaGoruntule(){
        System.out.print(String.format("%-3s"," "));
        for(int i=0;i<sutun;i++)
            System.out.print(String.format("%-3d",i));
        System.out.println();
        for(int i=0;i<satir;i++){
            System.out.print(String.format("%-3d",i));
            for(int j=0;j<sutun;j++){
                if(oyunTahtasi[i][j].gizliMi){
                    System.out.print(String.format("%-3s","*"));
                }
                else{
                    System.out.print(String.format("%-3s",oyunTahtasi[i][j].Harf));
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
static class Oyuncu {
    int hamleMaliyeti;
    int hedefMaliyeti;
    char oyuncuHarfi;
    int altinSayisi;
    int yontem;
    Hucre hedef;
    public Hucre konum;
    public int adimSayisi;
    public int gidilenAdim;
    public int harcananAltin;
    public int toplananAltin;
    Oyun oyun;
    FileWriter dosyayaYaz;

    public Oyuncu(int hamleMaliyeti, int hedefMaliyeti, char oyuncuHarfi, int altinSayisi, int yontem, Oyun oyun) throws IOException {
        this.hamleMaliyeti = hamleMaliyeti;
        this.hedefMaliyeti = hedefMaliyeti;
        this.oyuncuHarfi = oyuncuHarfi;
        this.altinSayisi = altinSayisi;
        this.yontem = yontem;
        this.oyun = oyun;
        gidilenAdim=0;
        harcananAltin=0;
        toplananAltin=0;
        dosyayaYaz = new FileWriter (oyuncuHarfi+"_hamleler.txt");
    }

    public void hedefBelirle() {
        ArrayList<Hucre> digerOyuncuHedefleri = new ArrayList<Hucre>();
        if (oyun.oyuncuA.hedef != null) digerOyuncuHedefleri.add(oyun.oyuncuA.hedef);
        if (oyun.oyuncuB.hedef != null) digerOyuncuHedefleri.add(oyun.oyuncuC.hedef);
        if (oyun.oyuncuC.hedef != null) digerOyuncuHedefleri.add(oyun.oyuncuB.hedef);
        if (yontem == 0) {
            double mesafe;
            double enKisaMesafe = 0;
            for (int i = 0; i < oyun.oyunTahtasi.satir; i++) {
                for (int j = 0; j < oyun.oyunTahtasi.sutun; j++) {
                    if (oyun.oyunTahtasi.oyunTahtasi[i][j].altinMiktari > 0&&!oyun.oyunTahtasi.oyunTahtasi[i][j].gizliMi) {
                        int tahtaX = oyun.oyunTahtasi.oyunTahtasi[i][j].x;
                        int tahtaY = oyun.oyunTahtasi.oyunTahtasi[i][j].y;
                        mesafe = oklidMesafe(tahtaX, tahtaY, konum.x, konum.y);
                        if (enKisaMesafe == 0) {
                            enKisaMesafe = mesafe;
                            hedef=oyun.oyunTahtasi.oyunTahtasi[i][j];
                        } else {
                            if (mesafe < enKisaMesafe) {
                                enKisaMesafe = mesafe;
                                hedef = oyun.oyunTahtasi.oyunTahtasi[i][j];
                            }
                        }
                    }
                }
            }
            altinSayisi -= hedefMaliyeti;
            System.out.println("A oyuncusunun yeni hedefi: " + hedef.x + " x " + hedef.y);
        } else if (yontem == 1) {
            double mesafe;
            double enKisaMesafe = 0;
            Hucre enKarliHucre = null;
            for (int i = 0; i < oyun.oyunTahtasi.satir; i++) {
                for (int j = 0; j < oyun.oyunTahtasi.sutun; j++) {
                    if (oyun.oyunTahtasi.oyunTahtasi[i][j].altinMiktari > 0) {
                        int tahtaX = oyun.oyunTahtasi.oyunTahtasi[i][j].x;
                        int tahtaY = oyun.oyunTahtasi.oyunTahtasi[i][j].y;
                        mesafe = oklidMesafe(tahtaX, tahtaY, konum.x, konum.y);
                        if (enKisaMesafe == 0) {
                            enKisaMesafe = mesafe;
                            hedef=oyun.oyunTahtasi.oyunTahtasi[i][j];
                            enKarliHucre = oyun.oyunTahtasi.oyunTahtasi[i][j];
                        } else {
                            if (mesafe < enKisaMesafe && oyun.oyunTahtasi.oyunTahtasi[i][j].altinMiktari > enKarliHucre.altinMiktari&&!oyun.oyunTahtasi.oyunTahtasi[i][j].gizliMi) {
                                enKisaMesafe = mesafe;
                                enKarliHucre = oyun.oyunTahtasi.oyunTahtasi[i][j];
                                hedef = oyun.oyunTahtasi.oyunTahtasi[i][j];
                            }
                        }
                    }
                }
            }
            altinSayisi -= hedefMaliyeti;
            System.out.println("B oyuncusunun yeni hedefi: " + hedef.x + " x " + hedef.y);
        } else if (yontem == 2) {
            double mesafe;
            double enKisaMesafe = 0;
            Hucre enKarliHucre = null;
            for (int i = 0; i < oyun.oyunTahtasi.satir; i++) {
                for (int j = 0; j < oyun.oyunTahtasi.sutun; j++) {
                    if (oyun.oyunTahtasi.oyunTahtasi[i][j].altinMiktari > 0) {
                        int tahtaX = oyun.oyunTahtasi.oyunTahtasi[i][j].x;
                        int tahtaY = oyun.oyunTahtasi.oyunTahtasi[i][j].y;
                        mesafe = oklidMesafe(tahtaX, tahtaY, konum.x, konum.y);
                        if (enKisaMesafe == 0) {
                            enKisaMesafe = mesafe;
                            hedef=oyun.oyunTahtasi.oyunTahtasi[i][j];
                            enKarliHucre = oyun.oyunTahtasi.oyunTahtasi[i][j];
                        } else {
                            if (mesafe < enKisaMesafe && oyun.oyunTahtasi.oyunTahtasi[i][j].altinMiktari > enKarliHucre.altinMiktari
                                    && oyun.oyunTahtasi.oyunTahtasi[i][j].gizliMi) {
                                enKisaMesafe = mesafe;
                                enKarliHucre = oyun.oyunTahtasi.oyunTahtasi[i][j];
                                hedef = oyun.oyunTahtasi.oyunTahtasi[i][j];
                            }
                        }
                    }
                }
            }
            altinSayisi -= hedefMaliyeti;
            System.out.println("C oyuncusunun yeni hedefi: " + hedef.x + " x " + hedef.y);
        } else if (yontem == 3) {
            double mesafe;
            double enKisaMesafe = 0;
            Hucre enKarliHucre = null;
            for (Hucre h : digerOyuncuHedefleri) {
                mesafe = oklidMesafe(konum.x, konum.y, h.x, h.y);
                if (mesafe == 0) {
                    enKisaMesafe = mesafe;
                    hedef=h;
                } else {
                    if (mesafe < enKisaMesafe) {
                        enKisaMesafe = mesafe;
                        enKarliHucre = h;
                    }
                }
            }
            if (enKarliHucre!=null) {
                if(ulasabilirMi(enKarliHucre)){
                    hedef = enKarliHucre;
                }
            } else {
                enKisaMesafe = 0;
                for (int i = 0; i < oyun.oyunTahtasi.satir; i++) {
                    for (int j = 0; j < oyun.oyunTahtasi.sutun; j++) {
                        if (oyun.oyunTahtasi.oyunTahtasi[i][j].altinMiktari > 0) {
                            int tahtaX = oyun.oyunTahtasi.oyunTahtasi[i][j].x;
                            int tahtaY = oyun.oyunTahtasi.oyunTahtasi[i][j].y;
                            mesafe = oklidMesafe(tahtaX, tahtaY, konum.x, konum.y);
                            if (enKisaMesafe == 0) {
                                enKisaMesafe = mesafe;
                                hedef=oyun.oyunTahtasi.oyunTahtasi[i][j];
                                enKarliHucre = oyun.oyunTahtasi.oyunTahtasi[i][j];
                            } else {
                                if (mesafe < enKisaMesafe && oyun.oyunTahtasi.oyunTahtasi[i][j].altinMiktari > enKarliHucre.altinMiktari&&!oyun.oyunTahtasi.oyunTahtasi[i][j].gizliMi) {
                                    enKisaMesafe = mesafe;
                                    enKarliHucre = oyun.oyunTahtasi.oyunTahtasi[i][j];
                                    hedef = oyun.oyunTahtasi.oyunTahtasi[i][j];
                                }
                            }
                        }
                    }
                }
            }
            if(altinSayisi>=hedefMaliyeti){
                altinSayisi -= hedefMaliyeti;
                harcananAltin+=hedefMaliyeti;
            }
            else {
                harcananAltin+=altinSayisi;
                altinSayisi = 0;
            }
            System.out.println("D oyuncusunun yeni hedefi: " + hedef.x + " x " + hedef.y);
        } else {
            System.exit(0);
        }
    }

    public boolean ulasabilirMi(Hucre hedef) {
        double gidilebilecekMesafe = oklidMesafe(konum.x, konum.y, konum.x + adimSayisi, konum.y);
        if (oklidMesafe(konum.x, konum.y, hedef.x, hedef.y) > gidilebilecekMesafe) return false;
        else return true;
    }

    public void hedefiKontrolEt() {
        if (hedef == null || hedef.altinMiktari <= 0 || hedef.ustundeMi(konum)) {
            hedefBelirle();
        }
    }
    public void hedefeIlerle() throws IOException {
       if(altinSayisi>0){
           hedefiKontrolEt();
           dosyayaYaz.write(oyuncuHarfi+" hedefine ilerliyor.\n");
           for (int i = 0; i < adimSayisi; i++) {
               if (konum.x > hedef.x && konum.x-1>=0&&(oyun.oyunTahtasi.oyunTahtasi[konum.x-1][konum.y].Harf=='G'||oyun.oyunTahtasi.oyunTahtasi[konum.x-1][konum.y].Harf=='*')) {//sol
                   konum.x--;
               } else if (konum.x < hedef.x&&konum.x+1<oyun.oyunTahtasi.satir&&(oyun.oyunTahtasi.oyunTahtasi[konum.x+1][konum.y].Harf=='G'||oyun.oyunTahtasi.oyunTahtasi[konum.x+1][konum.y].Harf=='*')) {//sag
                   konum.x++;
               } else if (konum.y > hedef.y&&konum.y-1>=0&&(oyun.oyunTahtasi.oyunTahtasi[konum.x][konum.y-1].Harf=='G'||oyun.oyunTahtasi.oyunTahtasi[konum.x][konum.y-1].Harf=='*')) {//yukari
                   konum.y--;
               } else {//asagi
                   if(konum.y+1<oyun.oyunTahtasi.sutun&&(oyun.oyunTahtasi.oyunTahtasi[konum.x][konum.y+1].Harf=='G'||oyun.oyunTahtasi.oyunTahtasi[konum.x][konum.y+1].Harf=='*')){
                       konum.y++;
                   }
               }
               if (oyun.oyunTahtasi.oyunTahtasi[konum.x][konum.y].gizliMi) {
                   oyun.oyunTahtasi.oyunTahtasi[konum.x][konum.y].gizliMi = false;
                   oyun.oyunTahtasi.altinHucreleri.get(oyun.oyunTahtasi.altinHucreleri.indexOf(oyun.oyunTahtasi.oyunTahtasi[konum.x][konum.y])).gizliMi=false;
                   dosyayaYaz.write(konum.x+" x "+konum.y+" konumundaki gizli altın ortaya çıktı!\n");
               } else if (oyun.oyunTahtasi.oyunTahtasi[konum.x][konum.y].altinMiktari > 0) {
                   oyun.oyunTahtasi.oyunTahtasi[konum.x][konum.y].bulunduMu=true;
                   altinSayisi += oyun.oyunTahtasi.oyunTahtasi[konum.x][konum.y].altinMiktari;
                   toplananAltin+= oyun.oyunTahtasi.oyunTahtasi[konum.x][konum.y].altinMiktari;
                   oyun.oyunTahtasi.kasa-=oyun.oyunTahtasi.oyunTahtasi[konum.x][konum.y].altinMiktari;
                   oyun.oyunTahtasi.oyunTahtasi[konum.x][konum.y].altinMiktari = 0;
                   oyun.oyunTahtasi.altinHucreleri.get(oyun.oyunTahtasi.altinHucreleri.indexOf(oyun.oyunTahtasi.oyunTahtasi[konum.x][konum.y])).bulunduMu=true;

               }
               gidilenAdim++;
           }
           if(altinSayisi>=hamleMaliyeti){
               harcananAltin+=hamleMaliyeti;
               altinSayisi-=hamleMaliyeti;
           }
           else{
               harcananAltin+=altinSayisi;
               altinSayisi=0;
           }
           dosyayaYaz.write("Oyuncu "+oyuncuHarfi+" yeni konumu: "+konum.x+" x "+konum.y+ " Mevcut altını: "+altinSayisi+"\n");
       }
    }
}


}

