package Controller.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        Integer playerCount=0;
        try{
            ServerSocket server= new ServerSocket(3000);
            System.out.println("The server has started successfully");
            while (playerCount<2)
            {
                playerCount++;
                Socket new_connection= server.accept();
                System.out.println("Player "+playerCount+" has connected to the server");
            }
            System.out.println("The Server is full.Not accepting new connections...");

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void run()
    {
        Integer playerCount=0;
        try{
            ServerSocket server= new ServerSocket(3000);
            System.out.println("The server has started successfully");
            while (playerCount<2)
            {
                playerCount++;
                Socket new_connection= server.accept();
                System.out.println("Player "+playerCount+" has connected to the server");
            }
            System.out.println("The Server is full.Not accepting new connections...");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
