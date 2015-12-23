import java.net.Socket;
import java.net.InetAddress;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;

public class Client {    
    public Client(String ipStr, int port) throws IOException {
        InetAddress ip = InetAddress.getByName(ipStr);
        koneksi = new Socket(ip, port);

        keluaran = koneksi.getOutputStream();
        keluaranWriter = new BufferedWriter(new OutputStreamWriter(keluaran)); 

        masukan = koneksi.getInputStream();
        masukanReader = new BufferedReader(new InputStreamReader(masukan)); 
    }

    public String perintah(String perintah) throws IOException {
        String balas = null;

        keluaranWriter.write(perintah, 0, perintah.length());               
        keluaranWriter.newLine();
        keluaranWriter.flush();

        balas = masukanReader.readLine();
        while (balas == null) {
            balas = masukanReader.readLine();
        }

        return balas;
    }

    public void tutup() throws IOException {
        koneksi.close();
    }

    private Socket koneksi;
    private OutputStream keluaran;
    private BufferedWriter keluaranWriter;
    private InputStream masukan;
    private BufferedReader masukanReader;        
}