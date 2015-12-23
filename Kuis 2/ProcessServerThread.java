import java.net.Socket;
import java.net.InetAddress;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.util.Calendar;

public class ProcessServerThread implements Runnable {
    public ProcessServerThread(Socket koneksi) {
        this.koneksi = koneksi;
    }

    public void run() {        
        if (koneksi != null) {
            ipStr = koneksi.getInetAddress().getHostAddress();

            try {
                masukan = koneksi.getInputStream();
                masukanReader = new BufferedReader(new InputStreamReader(masukan)); 
                keluaran = koneksi.getOutputStream();
                keluaranWriter = new BufferedWriter(new OutputStreamWriter(keluaran)); 

                while (koneksi != null) {
                    tangani();
                }           
            }
            catch(IOException salah) { 
                System.out.println(salah);
            }
            finally {
                try { 
                    System.out.println("Tutup: " + ipStr);
                    System.out.println();

                    koneksi.close();
                }
                catch(IOException salah) {
                    System.out.println(salah);
                }                
            }
        }
    }

    private void tangani() throws IOException {
        String perintah = masukanReader.readLine();
        String[] hasil = perintah.split(" ");
        
        if (perintah == null)
            return;            
        
        perintah = perintah.trim().toUpperCase();

        if (perintah.compareTo("SIAPA") == 0)
            synchronized(this) {
                keluaranWriter.write(ipStr);
                keluaranWriter.newLine();
                keluaranWriter.flush();
            }
        else if (perintah.compareTo("WAKTU") == 0)
            synchronized(this) {
                Calendar kalender = Calendar.getInstance();
                String waktu = kalender.getTime().toString();
                keluaranWriter.write(waktu);
                keluaranWriter.newLine();
                keluaranWriter.flush();
            }
        else if (hasil[0].compareTo("WAKTU") == 0)
            synchronized(this) {
                try{
                    int n = Integer.parseInt(hasil[1]);
                    Calendar kalender = Calendar.getInstance();
                    kalender.add(Calendar.HOUR_OF_DAY, n-7);
                    String waktuStr = kalender.getTime().toString();
                    keluaranWriter.write(waktuStr);
                    keluaranWriter.newLine();
                    keluaranWriter.flush();
                }catch (NumberFormatException ex) {
                    keluaranWriter.write("Perintah tidak dikenal !");
                    keluaranWriter.newLine();
                    keluaranWriter.flush();
                }
            }
        else {
            keluaranWriter.write("Perintah tidak dikenal !");
            keluaranWriter.newLine();
            keluaranWriter.flush();
        }

        System.out.println("Dari: " + ipStr);
        System.out.println("perintah: " + perintah);
        System.out.println();
    }

    private Socket koneksi; 
    private String ipStr; 
    private InputStream masukan = null;
    private BufferedReader masukanReader = null;
    private OutputStream keluaran = null;
    private BufferedWriter keluaranWriter = null;
}