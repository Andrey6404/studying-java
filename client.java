import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.net.Socket;
import java.util.Scanner;

public class client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.0.1", 8000);
                PrintWriter out = new PrintWriter(
                        new BufferedWriter(
                                new OutputStreamWriter(
                                        socket.getOutputStream())),
                        true);
                BufferedReader input = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));) {
            System.out.println("[We connected to server sucsess!]");

            // out.println("");
            System.out.println(input.readLine());
            Scanner scanner = new Scanner(System.in);
            String iokeybord = "";
            String servstream;
            while (!iokeybord.equals("END")) {
                iokeybord = scanner.nextLine();
                out.println(iokeybord);
                servstream = input.readLine();
                System.out.println(servstream);
            }
            servstream = input.readLine();
            System.out.println(servstream);
            // out.write("Close connection");
            scanner.close();
        }

        catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }
}