import java.net.BindException;
import java.io.IOException;

public class UtamaServer {
    public static void main(String args[]) {
        try {            
            Server server = new Server();
            server.dengar();
        }
        catch(BindException err) {
            System.out.println(err);
        }
        catch(IOException err) {
            System.out.println(err);
        }
    }
}
