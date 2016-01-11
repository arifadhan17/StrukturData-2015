import java.net.Socket;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientProcess implements Runnable {
    private static CopyOnWriteArrayList<String> mobil = new CopyOnWriteArrayList<String>();
    private static CopyOnWriteArrayList<String> sepmor = new CopyOnWriteArrayList<String>();
    private static CopyOnWriteArrayList<String> sepeda = new CopyOnWriteArrayList<String>();
    private String jenis,plat;
    private int jmlMobil=100,jmlSepmor=100,jmlSepeda=100;
    
    public ClientProcess(Socket koneksi) {
        this.koneksi = koneksi;
    }

    public void run() {        
        if (koneksi != null) {
            // Ambil IP dari koneksi
            ipStr = koneksi.getInetAddress().getHostAddress();
            
            try {
                // Ambil InputStream
                masukan = koneksi.getInputStream();
                masukanReader = new BufferedReader(new InputStreamReader(masukan)); 
                // Ambil OutputStream
                keluaran = koneksi.getOutputStream();
                keluaranWriter = new BufferedWriter(new OutputStreamWriter(keluaran)); 

                // Selama masih terhubung dengan client tangani
                tangani();
            }
            catch(IOException salah) { 
                System.out.println(salah);
            }
            finally {
                try { 
                    // Tutup koneksi
                    koneksi.close();
                }
                catch(IOException salah) {
                    System.out.println(salah);
                }                
            }
        }
    }
    
    private void tangani() throws IOException {
        // Baca perintah dari socket
        String perintah = masukanReader.readLine();
        // Jika tidak ada perintah keluar saja
        if (perintah == null)
            return;            
        // Ada perintah, hilangkan spasi depan & belakang serta ubah ke huruf besar semua
        perintah = perintah.trim().toUpperCase();
        
        // Ambil parameter-nya
        String[] parameter = perintah.split(" ");
        String command = parameter[0];
        
        // Tangani perintahnya
        if (command.compareTo("MASUK")==0) {
            if(parameter.length==3) {
                jenis    = parameter[1];
                jenis    = jenis.trim().toUpperCase();
                plat     = parameter[2]; 
                plat     = plat.trim().toUpperCase();
                
                if(jenis.compareTo("MOBIL") == 0) {
                    if(mobil.size()<jmlMobil) {
                        if(mobil.contains(plat)==true) {
                            keluaranWriter.write(PERINTAH_TIDAK, 0, PERINTAH_TIDAK.length());
                            keluaranWriter.newLine();
                            keluaranWriter.flush();
                        }
                        else {
                            mobil.add(plat);
                            keluaranWriter.write(PERINTAH_YA, 0, PERINTAH_YA.length());
                            keluaranWriter.newLine();
                            keluaranWriter.flush();
                        }
                    }
                    else {
                            keluaranWriter.write(PERINTAH_TIDAK, 0, PERINTAH_TIDAK.length());
                            keluaranWriter.newLine();
                            keluaranWriter.flush();
                    }
                }
                        
                else if(jenis.compareTo("SEPEDAMOTOR") == 0) {
                    if(sepmor.size()<jmlSepmor) {
                        if(sepmor.contains(plat)==true) {
                            keluaranWriter.write(PERINTAH_TIDAK, 0, PERINTAH_TIDAK.length());
                            keluaranWriter.newLine();
                            keluaranWriter.flush();
                        }
                        else {
                            sepmor.add(plat);
                            keluaranWriter.write(PERINTAH_YA, 0, PERINTAH_YA.length());
                            keluaranWriter.newLine();
                            keluaranWriter.flush();
                        }
                    }
                    else {
                        keluaranWriter.write(PERINTAH_TIDAK, 0, PERINTAH_TIDAK.length());
                        keluaranWriter.newLine();
                        keluaranWriter.flush();
                    }
                }
                        
                else if(jenis.compareTo("SEPEDA") == 0) {
                    if(sepeda.size()<jmlSepeda) {
                        if(sepeda.contains(plat)==true) {
                            keluaranWriter.write(PERINTAH_TIDAK, 0, PERINTAH_TIDAK.length());
                            keluaranWriter.newLine();
                            keluaranWriter.flush();
                        }
                        else {
                            sepeda.add(plat);
                            keluaranWriter.write(PERINTAH_YA, 0, PERINTAH_YA.length());
                            keluaranWriter.newLine();
                            keluaranWriter.flush();
                        }
                    }
                    else {
                        keluaranWriter.write(PERINTAH_TIDAK, 0, PERINTAH_TIDAK.length());
                        keluaranWriter.newLine();
                        keluaranWriter.flush();
                    }
                }
                else {
                    keluaranWriter.write(PERINTAH_TIDAK_DIKENAL, 0, PERINTAH_TIDAK_DIKENAL.length());
                    keluaranWriter.newLine();
                    keluaranWriter.flush();
                }   
            }
            else {
                keluaranWriter.write(PERINTAH_TIDAK_DIKENAL, 0, PERINTAH_TIDAK_DIKENAL.length());
                keluaranWriter.newLine();
                keluaranWriter.flush();
            }
        }
                
        else if(command.compareTo("STATUS") == 0) {
            if(parameter.length==1) {
                if(mobil.size()<jmlMobil || sepmor.size()<jmlSepmor || sepeda.size()<jmlSepeda) {
                    String PERINTAH_ADA = "ADA" +" " +(jmlMobil-mobil.size()) +" " +(jmlSepmor-sepmor.size()) +" " +(jmlSepeda-sepeda.size());
                    keluaranWriter.write(PERINTAH_ADA, 0, PERINTAH_ADA.length());
                    keluaranWriter.newLine();
                    keluaranWriter.flush();
                }
                else {
                    keluaranWriter.write(PERINTAH_TIDAK, 0, PERINTAH_TIDAK.length());
                    keluaranWriter.newLine();
                    keluaranWriter.flush();
                }
            }
            else {
                keluaranWriter.write(PERINTAH_TIDAK_DIKENAL, 0, PERINTAH_TIDAK_DIKENAL.length());
                keluaranWriter.newLine();
                keluaranWriter.flush();
            }
        }
                
        else if(command.compareTo("KELUAR") == 0) {
            if(parameter.length==3) {
                jenis    = parameter[1];
                jenis    = jenis.trim().toUpperCase();
                plat     = parameter[2]; 
                plat     = plat.trim().toUpperCase();
                if(jenis.compareTo("MOBIL")== 0) {
                    if(mobil.contains(plat)==true) {
                        mobil.remove(plat);
                        keluaranWriter.write(PERINTAH_YA, 0, PERINTAH_YA.length());
                        keluaranWriter.newLine();
                        keluaranWriter.flush();
                    }
                    else {
                        keluaranWriter.write(PERINTAH_TIDAK, 0, PERINTAH_TIDAK.length());
                        keluaranWriter.newLine();
                        keluaranWriter.flush();
                    }
                }
                else if(jenis.compareTo("SEPEDAMOTOR")== 0) {
                    if(sepmor.contains(plat)==true) {
                        sepmor.remove(plat);
                        keluaranWriter.write(PERINTAH_YA, 0, PERINTAH_YA.length());
                        keluaranWriter.newLine();
                        keluaranWriter.flush();
                    }
                    else {
                        keluaranWriter.write(PERINTAH_TIDAK, 0, PERINTAH_TIDAK.length());
                        keluaranWriter.newLine();
                        keluaranWriter.flush();
                    }
                }
                else if(jenis.compareTo("SEPEDA")== 0) {
                    if(sepeda.contains(plat)==true) {
                        sepeda.remove(plat);
                        keluaranWriter.write(PERINTAH_YA, 0, PERINTAH_YA.length());
                        keluaranWriter.newLine();
                        keluaranWriter.flush();
                    }
                    else {
                        keluaranWriter.write(PERINTAH_TIDAK, 0, PERINTAH_TIDAK.length());
                        keluaranWriter.newLine();
                        keluaranWriter.flush();
                    }
                }
                else {
                    keluaranWriter.write(PERINTAH_TIDAK_DIKENAL, 0, PERINTAH_TIDAK_DIKENAL.length());
                    keluaranWriter.newLine();
                    keluaranWriter.flush();
                }
            }
            else {
                keluaranWriter.write(PERINTAH_TIDAK_DIKENAL, 0, PERINTAH_TIDAK_DIKENAL.length());
                keluaranWriter.newLine();
                keluaranWriter.flush();
            }
        }
                        
        else {
            keluaranWriter.write(PERINTAH_TIDAK_DIKENAL, 0, PERINTAH_TIDAK_DIKENAL.length());
            keluaranWriter.newLine();
            keluaranWriter.flush();
        }
                
        // Tampilkan perintahnya
        System.out.println("Dari: " + ipStr);
        System.out.println("perintah: " + perintah);
        System.out.println();
    }
    
    // Koneksi ke Client
    private Socket koneksi; 
    // IP address dari client
    private String ipStr; 
    
    // InputStream untuk baca perintah
    private InputStream masukan = null;
    // Reader untuk InputStream, pakai yang buffer
    private BufferedReader masukanReader = null;
    // OutputStream untuk tulis balasan
    private OutputStream keluaran = null;
    // Writer untuk OutputStream, pakai yang buffer
    private BufferedWriter keluaranWriter = null;
    
    private final static String PERINTAH_TIDAK_DIKENAL = "Perintah tidak dikenal!";
    
    private final static String PERINTAH_YA = "YA";
    
    private final static String PERINTAH_TIDAK = "TIDAK";
}
