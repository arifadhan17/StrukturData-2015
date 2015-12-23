import java.io.IOException;

import java.net.Socket;
import java.net.ServerSocket;

public class Server {
    public Server() throws IOException {
        serverSocket = new ServerSocket(33333);
    }
    
    public void dengar() throws IOException {
        while (true) {
            Socket koneksi = serverSocket.accept();
            ProcessServerThread satuProcess = new ProcessServerThread(koneksi);
            Thread satuProcessThread = new Thread(satuProcess);
            satuProcessThread.start();                
        }
    }
    
    private ServerSocket serverSocket = null;
}