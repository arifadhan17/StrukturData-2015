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
    /**
     * 1. Buka socket
     * 2. Ambil Input dan Output stream-nya
     * 3. Ubah Stream-nya ke Reader dan Writer
     * 4. Kirim satu perintah ke server dan 
     * 5. Tampung balasannya.
     * 6. Tutup koneksi
     */
    public String perintah(String ipStr, int port, String perintah) 
           throws IOException {
        String balasan = null;
        
        // Buka socket ke server
        InetAddress ip = InetAddress.getByName(ipStr);
        Socket koneksi = new Socket(ip, port);

        // Minta stream untuk tulis
        OutputStream keluaran = koneksi.getOutputStream();
        BufferedWriter keluaranWriter = new BufferedWriter(new OutputStreamWriter(keluaran)); 
        
        // Minta stream untuk baca
        InputStream masukan = koneksi.getInputStream();
        BufferedReader masukanReader = new BufferedReader(new InputStreamReader(masukan)); 

        // Kirim perintah ke server
        keluaranWriter.write(perintah, 0, perintah.length());               
        keluaranWriter.newLine();
        keluaranWriter.flush();
        
        // Baca balasan dari Server
        balasan = masukanReader.readLine();

        // Tutup koneksi
        koneksi.close();
        
        // Kembalikan balasan
        return balasan;
    }
}
