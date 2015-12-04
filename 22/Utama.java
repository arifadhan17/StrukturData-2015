import java.util.Scanner;
import java.io.IOException;
import java.net.UnknownHostException;

public class Utama {
    public static void main(String[] args) {
        try {
            Scanner input = new Scanner ( System.in);
            DomainSiapa tanya = new DomainSiapa();
            System.out.print("Pesan: ");
            String in = input.next();
            tanya.whois(in);
        }
        catch (UnknownHostException ex) {
            System.err.println(ex);
        }
        catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
