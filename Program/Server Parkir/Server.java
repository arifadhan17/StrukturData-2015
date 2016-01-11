import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import java.io.IOException;

public class Server {
    public Server() throws BindException, IOException {
        serverSocket = new ServerSocket(33333);
    }
    
    public void dengar() {
        while (true) {
            try  {
                // Tunggu sampai ada koneksi dari client
                Socket koneksi = serverSocket.accept();
                
                // Buat thread untuk proses
                ClientProcess oneProcess = new ClientProcess(koneksi);
                Thread oneProcessThread = new Thread(oneProcess);
                oneProcessThread.start(); 
            }
            catch(IOException err) {
                System.out.println(err);
            }
        }
    }

    private ServerSocket serverSocket = null;
}
