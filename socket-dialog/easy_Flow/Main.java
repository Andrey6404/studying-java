import java.io.BufferedReader;
import java.io.StringReader;

class two extends Thread {
    public two() {
    }

    @Override
    public void run() {

    }

}

public class Main {
    public static BufferedReader userdata;
    static two mAnotherOpinion; // Побочный поток

    public static void main(String[] args) {
        mAnotherOpinion = new two(); // Создание потока
        userdata = new BufferedReader(new StringReader("[EMPTY]"));
        System.out.println(userdata.readLine());
        userdata = new BufferedReader(new StringReader("FULL"));
        System.out.println(userdata.lines());
        userdata = new BufferedReader(new StringReader("[EMPTY]"));
        System.out.println(userdata.lines());
        // mAnotherOpinion.start(); // Запуск потока

    }
}