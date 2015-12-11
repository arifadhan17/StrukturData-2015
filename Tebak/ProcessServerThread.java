import java.net.Socket;
import java.net.InetAddress;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;

public class ProcessServerThread implements Runnable {
    private Socket koneksi;
    private String simpan;

    public ProcessServerThread(Socket koneksiKiriman, int simpan) {
        koneksi = koneksiKiriman;
        this.simpan=""+simpan;
    }

    public void run()
    {
        try{
            if (koneksi != null)
                prosesPermintaanClient();
        }catch(IOException err) {
            System.out.println(err);
        }
    }

    private void prosesPermintaanClient() throws IOException 
    {
        String ip = koneksi.getInetAddress().getHostAddress();
        System.out.println("Dari: " + ip);
        int a =0;
        String pesanServer=null;
        OutputStream keluaran=null;
        BufferedWriter keluaranBuf=null;

        for(; a<3; a++)
        {
            // Ambil dan tampilkan masukan
            InputStream masukan = koneksi.getInputStream();
            BufferedReader masukanReader = new BufferedReader(new InputStreamReader(masukan)); 
            String baris = masukanReader.readLine();
            System.out.println(baris);

            if(simpan.equalsIgnoreCase(baris))
                pesanServer="Benar";
            else
                pesanServer="Salah";

            // Kirim ke client
            keluaran = koneksi.getOutputStream();
            keluaranBuf = new BufferedWriter(new OutputStreamWriter(keluaran)); 
            keluaranBuf.write(pesanServer);
            keluaranBuf.newLine();
            keluaranBuf.flush();

            if(pesanServer.equalsIgnoreCase("Benar"))
                break;
        }
        if(a==3){
            pesanServer="Kalah, simpan = "+simpan;
            keluaran = koneksi.getOutputStream();
            keluaranBuf = new BufferedWriter(new OutputStreamWriter(keluaran)); 
            keluaranBuf.write(pesanServer);
            keluaranBuf.newLine();
            keluaranBuf.flush();
        }
    }
}