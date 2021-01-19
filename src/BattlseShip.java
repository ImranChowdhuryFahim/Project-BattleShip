import Controller.Client.Client;
import Controller.Server.Server;

public class BattlseShip {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        Server s= new Server();

        Client c=new Client();

        Thread t1= new Thread(){
            @Override
            public void run() {
                s.run();
            }
        };
        t1.start();
        Thread t2= new Thread(){
            @Override
            public void run()
            {
                c.run();
            }
        };


    }
}
