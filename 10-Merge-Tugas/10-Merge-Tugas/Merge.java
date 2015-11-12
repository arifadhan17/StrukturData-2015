import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class Merge {

    public void merge (String file1, String file2, String file3, String sasaran)throws IOException {
         FileInputStream gabung1 = null;
         FileInputStream gabung2 = null;
         FileInputStream gabung3 = null;
         FileOutputStream keluaran = null;
        
         try { 
            gabung1 = new FileInputStream(file1);
            gabung2 = new FileInputStream(file2);
            gabung3 = new FileInputStream(file3);
            keluaran = new FileOutputStream(sasaran);
            
            int karakter = gabung1.read();
            
            while (karakter != -1) {
                keluaran.write(karakter);
                karakter = gabung1.read();
            }
            
            keluaran = new FileOutputStream(sasaran,true);
            karakter = gabung2.read();
            
            while (karakter != -1) {
                keluaran.write(karakter);
                karakter = gabung2.read();
            }
            
            keluaran = new FileOutputStream(sasaran,true);
            karakter = gabung3.read();
            
            while (karakter != -1) {
                keluaran.write(karakter);
                karakter = gabung3.read();
            }
            
            keluaran.flush();
        } 
        finally {
            if (gabung1 !=null)
                gabung1.close();
            if (gabung2 != null)
                gabung2.close();
            if (gabung3 != null)
                gabung3.close(); 
                
        
           if (keluaran != null)
            keluaran.close();
        
        }
    }
}