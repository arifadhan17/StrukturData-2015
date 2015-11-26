import java.io.FileWriter;
import java.io.IOException;

public class CariPrima {
    public static void main() throws IOException {
        // Buat berkas untuk menulis hasil. Pakai WRITER karena yang ditulis 
        // berkas text.
        FileWriter berkas = new FileWriter(NAMA_BERKAS);
        
        // Buat array dari thread
        BenarPrima[] benarPrima = new BenarPrima[JUMLAH_THREAD];
        Thread [] thread = new Thread[JUMLAH_THREAD];
      // Thread thread = new Thread ();
        // Mulai hitung dari angka 2, karena 1 otomatis bukan prima
        int angka = 2;
        // Loop sampai batas atas yang diminta
        while (angka<=ANGKA_TERBESAR) {
          
          for(int a=0; a<JUMLAH_THREAD; a++){
             benarPrima[a] = new BenarPrima(angka);
             thread[a] = new Thread(benarPrima[a]);
             angka++;
            }
          
          for (int cnt = 0; cnt < JUMLAH_THREAD; ++cnt)
                thread[cnt].start();
            
          
          for (int counter = 0; counter < JUMLAH_THREAD; ++counter)
            while (benarPrima[counter].selesai() == false) { }
          

          for(int a=0; a<JUMLAH_THREAD; a++){
            if(benarPrima[a].selesai()){
                if(benarPrima[a].prima()){
                    
                  synchronized(berkas) {
                     try {
                          berkas.write(benarPrima[a].angka()+"\n");
                       }
                       catch (IOException kesalahan) {
                          System.out.printf("Terjadi kesalahan: %s", kesalahan);
                     }
                  }
                  
                }
            }   
          }
        }
        
            berkas.close();
      }
  
           
        // Tunggu sampai semua thread selesai     
        
        // Tutup berkas untuk menulis hasil
        
     private final static String NAMA_BERKAS = "prima.log";
     private final static int JUMLAH_THREAD = 10;
     private final static int ANGKA_TERBESAR = 1000;
    }