package Controller.Client;

import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String args[])
    {
        try {
            Socket Client = new Socket("localhost",3000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void run()
    {
        try {
            Socket Client = new Socket("localhost",3000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
