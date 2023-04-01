import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.management.RuntimeErrorException;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket ssoc = new ServerSocket(8000)) {// создание серв сокета на порту
            System.out.println("[Server UP]");
            while (true) {
                try (
                        Socket soc = ssoc.accept(); // при подключении к серв сокету создается сокет для общения
                        PrintWriter out = new PrintWriter(
                                new BufferedWriter(
                                        new OutputStreamWriter(
                                                soc.getOutputStream())),
                                true);
                        BufferedReader input = new BufferedReader(
                                new InputStreamReader(
                                        soc.getInputStream()));) {
                    String request = "";
                    out.println("[SERVER] >> U connect to server!");
                    while (!request.equals("END")) {
                        request = input.readLine();
                        System.out.println(">> " + request);
                        if (request.equals("...datasending closing...")) {
                            out.println("[SERVER] >> Ok, next");
                            continue;
                        }
                        if (request.equals("...datasending in progress..."))
                            continue;
                        if (request.equals("close")) {
                            out.println("close");
                            break;
                        }
                        if (request.split(" ").length != 4) {
                            out.println("[SERVER] >> int +str +str");
                            continue;
                        }
                        /*
                         * / ==========================================
                         * if (request="hi"))
                         * out.println("[SERVER] >> hi too");
                         * else if (request.equals("fuck"))
                         * out.println("[SERVER] >> NO, fuck you !!!");
                         * else
                         * out.println("[SERVER] >> " + request);
                         * // ==========================================
                         * 
                         * switch (request) {
                         * case ("hi"):
                         * out.println("[SERVER] >> SWICH IS WORK!!!");
                         * break;
                         * case ("fuck"):
                         * out.println("[SERVER] >> NO, fuck you !!!");
                         * break;
                         * default:
                         * out.println("[server]");
                         * }
                         * // ==========================================
                         */ // int [] to []
                        String[] word = request.split(" ");
                        System.out.println("[ANALYSING] >> count is: " + word[0]);
                        System.out.println("[ANALYSING] >> input is: " + word[1]);
                        System.out.println("[ANALYSING] >> outpt is: " + word[3]);
                        out.println("[SERVER] >> Analysing is ok!");
                    }
                    out.println("[SERVER] >> Break connection! Bye Bye");
                } catch (Exception ignored) {
                    // throw new RuntimeException(e);
                    // soc.close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
