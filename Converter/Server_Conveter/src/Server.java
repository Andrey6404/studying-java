import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static String encoding = System.getProperty("console.encoding", "utf-8");

    private static Socket clientSocket; // сокет для общения
    private static ServerSocket server; // серверсокет
    private static BufferedReader in; // поток чтения из сокета
    private static BufferedWriter out; // поток записи в сокет

    public static void main(String[] args) {
        try {
            try {
                server = new ServerSocket(4004); // серверсокет прослушивает порт 4004
                System.out.println("Start server"); // хорошо бы серверу объявить о своем запуске
                while (true) {
                    clientSocket = server.accept(); // accept() будет ждать пока кто-нибудь не захочет подключиться
                    try { // установив связь и воссоздав сокет для общения с клиентом можно перейти
                          // к созданию потоков ввода/вывода. теперь мы можем принимать сообщения
                        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), encoding));
                        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), encoding));

                        // инициализация необходимых переменных
                        String word = null;
                        boolean status = true;
                        String errstr = "";
                        Float inputValue = null;
                        Float outputValue = null;// переменая для хранения конвертированной величины
                        Converter converter = new Converter();

                        // Первое рукопожатие
                        try {
                            word = in.readLine();
                            System.out.println("[CLIENT] >> " + word);
                            out.write("ok, lets calc\n");
                            out.flush();
                        } catch (IOException err) {
                            System.err.println("First handshake was broken" + word);
                        }

                        while (status) {
                            word = in.readLine();
                            word.toLowerCase(); // не работатет!!!!
                            String[] words = word.split(" ");
                            System.out.println("words.length = " + words.length);

                            // проверка на валидность переданных данных
                            if (words.length != 4) {
                                // System.out.println("length not correct");
                                switch (word) {
                                    case "getMeasure":
                                        System.out.println("getMeasure tray");
                                        out.write(converter.pushMeasures() + "\n");
                                        out.flush();
                                        break;
                                    case "faq":
                                        System.out.println("faq tray");
                                        out.write(
                                                "This server converting length, to use it enter [float] [measure] to [measure] If you have a questions enter \"FAQ\" to exit enter \"[stop|end|exit|break]\"\n");
                                        out.flush();
                                        break;
                                    case "close":
                                        status = false;
                                        System.out.println("close tray");
                                        out.write("we closing connection\n");
                                        out.flush();
                                        break;
                                    default:
                                        System.out.println("def tray");
                                        out.write("i dont understand this message\n");
                                        out.flush();
                                        break;
                                }
                            } else {
                                try {
                                    System.out.println("Convert str to float: " + Float.valueOf(words[0]));
                                    converter.setData(Float.valueOf(words[0]), words[1], words[3]);
                                    errstr = converter.MeasureCheck(words[1], words[3]);
                                    if (errstr.length() > 2) {
                                        out.write(errstr + "\n");
                                        out.flush();
                                    } else {
                                        outputValue = converter.Conversion();
                                        out.write(outputValue + " " + words[3] + "\n");
                                        out.flush();
                                    }
                                } catch (IllegalArgumentException ill) {
                                    out.write("Please write a normal float!!!\n");
                                    out.flush();
                                }
                            }
                        }
                    } catch (NullPointerException ignore) {

                    } finally { // в любом случае сокет будет закрыт
                        clientSocket.close();// потоки тоже хорошо бы закрыть
                        in.close();
                        out.close();
                    }
                }
            } catch (IOException ServerSocketException) {
                System.err.println(
                        "Excaption wits server socket\n[system data to check]\n\n" + ServerSocketException + "\n\n");
                System.out.println("Server is close!");
                server.close();
            }
        } catch (IOException e) {
            System.err.println("unknown error\n[system data to check]\n\n" + e + "\n\n");
        }
    }
}