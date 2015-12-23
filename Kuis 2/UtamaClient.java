import java.io.IOException;

import java.util.Scanner;

public class UtamaClient {
    public static void main(String[] args) {
        try {
            Scanner keluaran = new Scanner(System.in);

            Client client = new Client("localhost", 33333);

            System.out.print("Perintah: ");
            String perintah = keluaran.nextLine();
            perintah = perintah.trim().toUpperCase();

            String balasan = client.perintah(perintah);                
            if (balasan != null) {
                System.out.print("Server: ");
                System.out.println(balasan);
            }

            System.out.println();
        }
        catch(IOException salah) {
            System.out.println(salah);
        }
    }
}