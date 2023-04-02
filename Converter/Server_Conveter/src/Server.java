import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static Socket clientSocket; //сокет для общения
    private static ServerSocket server; // серверсокет
    private static BufferedReader in; // поток чтения из сокета
    private static BufferedWriter out; // поток записи в сокет

    public static void main(String[] args) {
        try {
            try {
                server = new ServerSocket(4004); // серверсокет прослушивает порт 4004
                System.out.println("Сервер запущен!"); // хорошо бы серверу
                //   объявить о своем запуске
                clientSocket = server.accept(); // accept() будет ждать пока
                //кто-нибудь не захочет подключиться
                try { // установив связь и воссоздав сокет для общения с клиентом можно перейти
                    // к созданию потоков ввода/вывода.
                    // теперь мы можем принимать сообщения
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    // и отправлять
                    out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                    String word = in.readLine(); // ждём пока клиент что-нибудь нам напишет
                    System.out.println(word);

                    String[] words = word.split(" ");

                    System.out.println("words.length = " + words.length);

                    // переменая для хранения конвертированной величины
                    Float outputValue = null;

                    // проверка на валидность переданных данных
                    if (words.length != 4)
                    {
                        System.out.println("Дынные от клиента не могут быть использованы для конвертации!!!");
                    }
                    else
                    {
                        System.out.println(Float.valueOf(words[0]));
                        System.out.println(words[1]);
                        System.out.println(words[2]);
                        System.out.println(words[3]);
                        Converter converter = new Converter(Float.valueOf(words[0]), words[1], words[3]);
                        outputValue = converter.Conversion();
                    }

                    // ответ клиенту
                    if (outputValue == null) {
                        out.write("Привет, это Сервер! Ваше конвертированное значение : не удалось конвертировать величину, т.к. была передана неправильная размерность");
                        out.flush(); // выталкиваем все из буфера
                    }
                    else {
                        out.write("Привет, это Сервер! Ваше конвертированное значение : " + outputValue + " " + words[3]);
                        out.flush(); // выталкиваем все из буфера
                    }
                } finally { // в любом случае сокет будет закрыт
                    clientSocket.close();
                    // потоки тоже хорошо бы закрыть
                    in.close();
                    out.close();
                }
            } finally {
                System.out.println("Сервер закрыт!");
                server.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}




//        System.out.println("Hello world!");
//        String inputDim = "mile";
//        String outputDim = "NM";
//        Float value = 1.0f;
//        Converter conv = new Converter(value, inputDim, outputDim);
//        System.out.print(value + " " + inputDim + " = ");
//        System.out.println(conv.Conversion() +  " " + outputDim);
