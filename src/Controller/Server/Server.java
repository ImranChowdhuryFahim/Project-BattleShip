package Controller.Server;

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

public class Server {
    String name;
    ServerSocket server;
    DataInputStream dataInputStreamFromClient;
    DataOutputStream dataOutputStreamToClient;
    ObjectInputStream objectInputStreamFromClient;
    ObjectOutputStream objectOutputStreamToClient;
    String winnerName;
    int posX=0,posY=0;


    Scanner scanner =new Scanner(System.in);
    HumanPlayer serverPlayer;
    HumanPlayer enemy;
    GameView gameView;

    public Server(String name)
    {
        this.name = name;
        serverPlayer = new HumanPlayer(name);
        gameView = new GameViewImp();
    }

    public void initializeServer() throws IOException {
        server = new ServerSocket(3000);
        System.out.println("Hi "+name+"!\n"+ "Server has started successfully");
        System.out.println("Waiting for another player to join");
        Socket new_connection =  server.accept();
        System.out.println("Player has joined ");
        dataInputStreamFromClient = new DataInputStream(new_connection.getInputStream());
        dataOutputStreamToClient = new DataOutputStream(new_connection.getOutputStream());
        objectOutputStreamToClient = new ObjectOutputStream(new_connection.getOutputStream());
        objectOutputStreamToClient.flush();
        objectInputStreamFromClient = new ObjectInputStream(new_connection.getInputStream());
    }



    public void gamePlay() throws IOException, ClassNotFoundException, InterruptedException {
        gameView.printBoardInitializationMessage();
        serverPlayer.initializeGameBoard();
        serverPlayer.loadShips();
        gameView.showOwnBoard(serverPlayer.getCurrentBoard());
        delay(2);

        gameView.printHumanPlayerShipDeploymentMessage();
        delay(2);
        serverPlayer.deployShips();
        gameView.showOwnBoard(serverPlayer.getCurrentBoard());


        objectOutputStreamToClient.writeObject(serverPlayer);
        enemy= (HumanPlayer) objectInputStreamFromClient.readObject();

        gameView.printGameStartingMessage();
        gameView.showEnemyBoard(enemy.getCurrentBoard());

        gameView.printGameStartingMessage();
        gameView.showEnemyBoard(enemy.getCurrentBoard());

        int turnFlag=0;

//        while (!isAllShipSunk())
//        {
//            if(turnFlag == 0)   // Server turn
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
//                            turnFlag = 0;
//
//                        } else {
//
//                            turnFlag = 1;
//                        }
//
//                        serverPlayer.performPlayerTurn(enemy, posX, posY);
//                        gameView.showEnemyBoard(enemy.getCurrentBoard());
//
//
//                    } else {
//                        turnFlag = 1;
//                    }
//                    dataOutputStreamToClient.writeUTF("turn");
//                }
//                else {
//                    String turn = dataInputStreamFromClient.readUTF();
//                    System.out.println("Opponent's Turn");
//                }
//            }
//            else { // client turn
//
//            }
//        }
//
//        while (!isAllShipSunk())
//        {
//            if(turnFlag ==0)
//            {
//                System.out.println("Input dicci");
//                dataOutputStreamToClient.writeInt(scanner.nextInt());
//                dataOutputStreamToClient.writeInt(scanner.nextInt());
//                turnFlag = 1;
//            }
//            else{
//                System.out.println("input nicci");
//                System.out.println(dataInputStreamFromClient.readInt());
//                System.out.println(dataInputStreamFromClient.readInt());
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
        ArrayList<Ship> myShips = serverPlayer.getListOfShips();
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
            winnerName = serverPlayer.getPlayerName();
            allSunk = true;
            return allSunk;
        }

        return allSunk;

    }

    public static void main(String args[]) throws IOException, ClassNotFoundException, InterruptedException {
        Server s = new Server("Imran");
        s.initializeServer();
        s.gamePlay();

    }






}