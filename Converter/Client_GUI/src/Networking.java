import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Networking {
    public PrintWriter output;
    public BufferedReader input;
    public BufferedReader userdata;

    private String datafromserver = "";
    private String nickname = "username";
    private Socket socket;

    public Networking(String hostIP, int port) {
        try {
            this.socket = new Socket(hostIP, port);
        } catch (IOException e) {
            System.err.println("Socket failed");
            System.err.println(e);
        }
        try {
            output = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(
                                    socket.getOutputStream())),
                    true);
            input = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()));
            output.println("im a GUI user");
            this.datafromserver = input.readLine();
            System.out.println("[SERVER] >> " + datafromserver);
            // userdata = new BufferedReader(new StringReader("[EMPTY]"));
            // this.pressNickname();

            // new ReadingThread().start();
            // new WritingThread().start();
        } catch (IOException e) {
            // Сокет должен быть закрыт при любой ошибке, кроме ошибки конструктора сокета:
            Networking.this.CloseSocket();
        }
    }

    private void pressNickname() {
        System.out.print("Press your nick: \n");
        // nickname = userdata.toString();// readLine();
        // почему тут не нужен this.nickname?
        output.write("Hello " + nickname + "\n");
        output.flush();
    }

    public void CloseSocket() {
        this.SocketSending("close");
        try {
            if (!socket.isClosed()) {
                socket.close();
                input.close();
                output.close();
            }
        } catch (IOException i) {
            System.err.println(i);
        }
    }

    private class ReadingThread extends Thread {
        @Override
        public void run() {

            String str;
            try {
                while (true) {
                    str = input.readLine(); // ждем сообщения с сервера
                    if (str.equals("stop")) {
                        Networking.this.CloseSocket(); // харакири
                        break; // выходим из цикла если пришло "stop"
                    }
                    System.out.println(str); // пишем сообщение с сервера на консоль
                    datafromserver = str;

                }
            } catch (IOException e) {
                Networking.this.CloseSocket();
            }
        }
    }

    public class WritingThread extends Thread {

        @Override
        public void run() {
            String userWord;
            while (true) {
                // time = new Date(); // текущая дата
                // dt1 = new SimpleDateFormat("HH:mm:ss"); // берем только время до секунд
                // dtime = dt1.format(time); // время
                userWord = "nulling";// userdata.readLine(); // сообщения с консоли
                if (userWord.equals("stop")) {
                    output.write("stop" + "\n");
                    Networking.this.CloseSocket(); // харакири
                    break; // выходим из цикла если пришло "stop"
                } else {
                    output.write("[" + nickname + "] >>> " + userWord + "\n"); // отправляем на сервер
                }
                output.flush(); // чистим
            }
        }
    }

    public void SocketSending(String a) {
        // output.println("...datasending in progress...");
        // for (int i = 0; i < elements.length; i++) {
        // output.write("[" + nickname + "] >>> " + elements[i] + "\n");
        // }
        output.println(a);
        // output.write(b + "\n");
        // output.println("...datasending closing...");

        try {
            this.datafromserver = input.readLine();
            System.out.println(datafromserver);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // if (datafromserver.equals("close"))
        // Networking.this.CloseSocket();

    }

    public String getmessage() {
        return this.datafromserver;
    }

    public String getArrayMeasure() throws IOException {
        // String local;
        output.println("getMeasure");
        return input.readLine();

    }

}
