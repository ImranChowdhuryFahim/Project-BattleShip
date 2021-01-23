package Controller.Client;

import Model.Input.ConsoleInput;
import Model.Player.HumanPlayer;
import Model.Ship.Ship;
import View.GameView;
import View.GameViewImp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Client {
    String name;
    Socket client;
    DataInputStream dataInputStreamFromServer;
    DataOutputStream dataOutputStreamToServer;
    ObjectInputStream objectInputStreamFromServer;
    ObjectOutputStream objectOutputStreamToServer;
    Scanner scanner =new Scanner(System.in);
    HumanPlayer playerClient;
    HumanPlayer enemy;
    GameView gameView;
    int posX=0,posY=0;

    String winnerName;

    public Client(String name)
    {
        this.name = name;
        playerClient = new HumanPlayer(name);
        gameView = new GameViewImp();
    }

    public void initialize() throws IOException {
        client = new Socket("localhost",3000);
        System.out.println("Hi "+name+"!\n"+ "Connected to the game server");
        dataInputStreamFromServer = new DataInputStream(client.getInputStream());
        dataOutputStreamToServer = new DataOutputStream(client.getOutputStream());
        objectOutputStreamToServer = new ObjectOutputStream(client.getOutputStream());
        objectOutputStreamToServer.flush();
        objectInputStreamFromServer = new ObjectInputStream(client.getInputStream());


    }


    public void gamePlay() throws IOException, ClassNotFoundException, InterruptedException {
        gameView.printBoardInitializationMessage();
        playerClient.initializeGameBoard();
        playerClient.loadShips();
        gameView.showOwnBoard(playerClient.getCurrentBoard());
        delay(2);

        gameView.printHumanPlayerShipDeploymentMessage();
        delay(2);
        playerClient.deployShips();
        gameView.showOwnBoard(playerClient.getCurrentBoard());
        delay(2);

        enemy= (HumanPlayer) objectInputStreamFromServer.readObject();
        objectOutputStreamToServer.writeObject(playerClient);


        gameView.printGameStartingMessage();
        gameView.showEnemyBoard(enemy.getCurrentBoard());

        int turnFlag=0;

//        while (!isAllShipSunk())
//        {
//            if(turnFlag == 1)   // Server turn
//            {
//                gameView.printHumanPlayerTurnMessage();
//                gameView.propmtInputMessageForRow();
//
//
//                ConsoleInput con = new ConsoleInput(
//                        1
//                        ,
//                        30,
//                        TimeUnit.SECONDS
//                );
//                long startingTime = System.currentTimeMillis();
//
//                String inputLine = con.readLine();
//
//                if(inputLine != null) {
//                    posX = Integer.parseInt(inputLine);
//                }
//
////                System.out.println("Done. Your input was: " + posX);
//
//                while ((posX > 10 || posX < 1) && posX != 0) {
//
//                    gameView.invalidRowWarning();
//                    inputLine = con.readLine();
//                    if(inputLine != null) {
//                        posX = Integer.parseInt(inputLine);
//                    }
//                }
//
//                if(posX != 0) {
//                    int restTime = (int)(System.currentTimeMillis() - startingTime) / 1000;
//                    ConsoleInput con1 = new ConsoleInput(
//                            1
//                            ,restTime
//                            ,
//                            TimeUnit.SECONDS
//                    );
//
//                    gameView.propmtInputMessageForColumn();
//                    inputLine = con1.readLine();
//                    if(inputLine != null) {
//                        posY = Integer.parseInt(inputLine);
//                    }
//
//                    while ((posY > 15 || posY < 1) && posY != 0 ) {
//
//                        gameView.invalidColWarning();
//                        inputLine = con1.readLine();
//                        if(inputLine != null) {
//                            posY = Integer.parseInt(inputLine);
//                        }
//                    }
//
//
//                    if(posX > 0 && posY > 0) {
//
//
//
//                        if (enemy.getCurrentBoard().isHit(posX , posY )) {
//                            turnFlag = 1;
//
//                        } else {
//
//                            turnFlag = 0;
//                        }
//
//                        playerClient.performPlayerTurn(enemy, posX, posY);
//                        gameView.showEnemyBoard(enemy.getCurrentBoard());
//
//
//                    } else {
//                        turnFlag = 0;
//                    }
//                    dataOutputStreamToServer.writeUTF("turn");
//                }
//                else {
//                    String turn = dataInputStreamFromServer.readUTF();
//                    System.out.println("Oppo");
//                }
//            }
//            else { // client turn
//
//            }
//        }


//        while (!isAllShipSunk())
//        {
//            if(turnFlag ==0)
//            {
//                System.out.println("Input nicchi");
//                System.out.println(dataInputStreamFromServer.readInt());
//                System.out.println(dataInputStreamFromServer.readInt());
//                turnFlag = 1;
//
//            }
//            else{
//                System.out.println("Input dicchi");
//                dataOutputStreamToServer.writeInt(scanner.nextInt());
//                dataOutputStreamToServer.writeInt(scanner.nextInt());
//                turnFlag = 0;
//            }
//        }





    }

    public void delay(int time)  {

        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isAllShipSunk()
    {
        boolean allSunk = false;
        int sunk_count =0;
        ArrayList<Ship> myShips = playerClient.getListOfShips();
        ArrayList<Ship> enemyShips = enemy.getListOfShips();

        for(int i=0; i<myShips.size(); i++)
        {
            if(myShips.get(i).isSunk())
            {
                sunk_count++;
            }
        }

        if(sunk_count == 28)
        {
            winnerName = enemy.getPlayerName();
            allSunk = true;
            return allSunk;
        }

        sunk_count = 0;
        for(int i=0; i<enemyShips.size(); i++)
        {
            if(enemyShips.get(i).isSunk())
            {
                sunk_count++;
            }
        }

        if(sunk_count == 28)
        {
            winnerName = playerClient.getPlayerName();
            allSunk = true;
            return allSunk;
        }

        return allSunk;

    }

    public static void main(String args[]) throws IOException, ClassNotFoundException, InterruptedException {
       Client client = new Client("Matin");
       client.initialize();
       client.gamePlay();
    }

}
