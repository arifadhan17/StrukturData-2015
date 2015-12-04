import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.io.OutputStreamWriter;

public class Utama
{
    public static void main (String[] args) throws UnknownHostException, IOException
    {
        String a = "Muhammad Arif Ramadhan (1408107010065)";
        
        Socket koneksi = new Socket("192.168.43.139", 33333);
        OutputStream keluaran = koneksi.getOutputStream();
        Writer keluaranWriter = new OutputStreamWriter(keluaran); 
        keluaranWriter.write(a);
        keluaranWriter.write("\r\n");
        keluaranWriter.flush();
        koneksi.close();
    }
    
}
