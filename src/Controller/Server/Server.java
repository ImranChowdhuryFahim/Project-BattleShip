package Controller.Server;

import Model.Board.Board;
import Model.Board.BoardImp;
import Model.Input.ConsoleInput;
import Model.Player.HumanPlayer;
import Model.Player.Player;
import Model.Ship.Ship;
import Model.Ship.ShipInfo;
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
    Socket new_connection;
    DataInputStream dataInputStreamFromClient;
    DataOutputStream dataOutputStreamToClient;
    ObjectInputStream objectInputStreamFromClient;
    ObjectOutputStream objectOutputStreamToClient;
    String winnerName;
    int posX=0,posY=0,enemyPoint=0;
    int turnFlag=0;
    int cellValue=-50,point=0;
    boolean sunk=false;
    boolean hit= false;


    // save life
    public  static  boolean allSunk = false;

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
        new_connection =  server.accept();
        System.out.println("Player has joined ");
        dataInputStreamFromClient = new DataInputStream(new_connection.getInputStream());
        dataOutputStreamToClient = new DataOutputStream(new_connection.getOutputStream());
        objectOutputStreamToClient = new ObjectOutputStream(new_connection.getOutputStream());
        objectOutputStreamToClient.flush();
        objectInputStreamFromClient = new ObjectInputStream(new_connection.getInputStream());
    }





    public void gamePlay() throws IOException, ClassNotFoundException, InterruptedException {


        InitializeGame();

        long gameStartingTime = System.currentTimeMillis();

        while (!isAllShipSunk())
        {
            int timeElapse = (int) ((System.currentTimeMillis() -gameStartingTime) / 1000);

            if( timeElapse >= 300 ){
                if(enemyPoint > serverPlayer.getPoints()) {
                    System.out.println(enemy.getPlayerName() + " wins");
                }
                else if (enemyPoint == serverPlayer.getPoints()) {
                    delay(2);
                    System.out.println(getWinnerName() + " wins");
                }
                else {
                    System.out.println("You won");
                }
                gameView.showTimeOverMessage();
                break;


            }
            posX=0;
            posY=0;
            if(turnFlag ==0)
            {
                serversTurn();
            }
            else{

                clientsTurn();

            }


        }






        if(isAllShipSunk())
        {
            gameView.showAllShipSunkMessage();
            gameView.showGameOverMessage();
            System.out.println("You won");
            dataOutputStreamToClient.writeInt(-101);
            dataOutputStreamToClient.writeInt(posY);
            dataOutputStreamToClient.writeInt(cellValue);
            dataOutputStreamToClient.writeBoolean(sunk);
            dataOutputStreamToClient.writeBoolean(hit);
            dataOutputStreamToClient.writeInt(point);
        }


    }

    private void recieveWinnerNameFromCleint() throws IOException {
//        winnerName = dataInputStreamFromClient.readUTF();
    }


    private void InitializeGame() throws IOException, ClassNotFoundException {
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
    }







    private void clientsTurn() throws IOException {
        System.out.println(enemy.getPlayerName()+"'s turn");
        int x,y,cell,point;
        boolean sunk,hit;
        x=dataInputStreamFromClient.readInt();
        y=dataInputStreamFromClient.readInt();
        cell=dataInputStreamFromClient.readInt();
        sunk = dataInputStreamFromClient.readBoolean();
        hit = dataInputStreamFromClient.readBoolean();
        point = dataInputStreamFromClient.readInt();
        //no input so its my turn
        if(x != -101) {

            if(x==0 || y==0)
            {
                System.out.println("Opponent didn't respond");
                turnFlag =0;
            }
            else if(hit)
            {
                System.out.println("Oops! opponent has hit your ship");
                if(sunk)
                {
                    System.out.println("Oops! opponent has sunk your ship");
                }
                enemyPoint=point;
                System.out.println(serverPlayer.getPlayerName()+"'s point: "+serverPlayer.getPoints());
                System.out.println(enemy.getPlayerName()+"'s point: "+enemyPoint);

                turnFlag =1;
            }
            else
            {

                System.out.println("Opponent has missed");
                System.out.println(serverPlayer.getPlayerName()+"'s point: "+serverPlayer.getPoints());
                System.out.println(enemy.getPlayerName()+"'s point: "+enemyPoint);


                turnFlag =0;
            }
        }
        else  {
            System.out.println(enemy.getPlayerName() + " wins");
            System.exit(0);
        }

    }






    private void serversTurn() throws IOException, InterruptedException {

        cellValue=-50; point=0;
        sunk=false;
        hit= false;

        System.out.println("It's your turn");
        gameView.propmtInputMessageForRow();

        ConsoleInput con = new ConsoleInput(
                1
                ,
                30,
                TimeUnit.SECONDS
        );
        long startingTime = System.currentTimeMillis();

        String inputLine = con.readLine();

        if(inputLine != null) {
            posX = Integer.parseInt(inputLine);
        }



        while ((posX > 10 || posX < 1) && posX != 0) {

            gameView.invalidRowWarning();
            inputLine = con.readLine();
            if(inputLine != null) {
                posX = Integer.parseInt(inputLine);
            }
        }

        if(posX != 0) {
            int restTime = (int)(System.currentTimeMillis() - startingTime) / 1000;
            ConsoleInput con1 = new ConsoleInput(
                    1
                    ,30
                    ,
                    TimeUnit.SECONDS
            );

            gameView.propmtInputMessageForColumn();
            inputLine = con1.readLine();
            if(inputLine != null) {
                posY = Integer.parseInt(inputLine);
            }

            while ((posY > 15 || posY < 1) && posY != 0 ) {

                gameView.invalidColWarning();
                inputLine = con1.readLine();
                if(inputLine != null) {
                    posY = Integer.parseInt(inputLine);
                }
            }
            if(posX > 0 && posY > 0) {



                if(enemy.getCurrentBoard().isHit(posX,posY))
                {
                    hit=true;
                    turnFlag =0;
                }
                else {

                    turnFlag = 1;
                }
                sunk = performPlayerTurn(enemy,posX,posY);
                cellValue = enemy.getCurrentBoard().getCellValue(posX,posY);
                point = serverPlayer.getPoints();

            } else {

                turnFlag = 1;
            }
        }
        else {

            turnFlag = 1;
        }
        System.out.println(serverPlayer.getPlayerName()+"'s point: "+serverPlayer.getPoints());
        System.out.println(enemy.getPlayerName()+"'s point: "+enemyPoint);
        if(posX>0 && posY>0)
        {
            gameView.showEnemyBoard(enemy.getCurrentBoard());
        }
        dataOutputStreamToClient.writeInt(posX);
        dataOutputStreamToClient.writeInt(posY);
        dataOutputStreamToClient.writeInt(cellValue);
        dataOutputStreamToClient.writeBoolean(sunk);
        dataOutputStreamToClient.writeBoolean(hit);
        dataOutputStreamToClient.writeInt(point);

    }





    public void delay(int time)  {

        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }






    public int cellValueToType (int cellValue) {
        if (  cellValue <= 2 ) {
            return ShipInfo.carrierType;
        } else if(cellValue <= 5 ) {
            return ShipInfo.battleShipType;
        } else if (cellValue <= 10) {
            return ShipInfo.destroyerType;
        } else if (cellValue <= 18) {
            return ShipInfo.superPatrolType;
        } else {
            return ShipInfo.patrolBoatType;
        }
    }







    public boolean performPlayerTurn(Player enemyPlayer,int posX,int posY)
    {
        boolean sunk =false;
        GameView gameView = new GameViewImp();
        Board enemyBoard ;
        ArrayList<Ship> enemyShipList ;
        enemyBoard = enemyPlayer.getCurrentBoard();
        enemyShipList = enemyPlayer.getListOfShips();

        if(enemyBoard.isHit(posX, posY)) {
            System.out.println("Congrats! you have hit opponent's ship");
            int cellValue = enemyBoard.getCellValue(posX, posY);
            int shipType = cellValueToType(cellValue);
            int shipInstanceNumber = cellValue - shipType;

            for (Ship ship: enemyShipList ) {
                if( ship.getShipType() == shipType && shipInstanceNumber == ship.getShipInstance()) {
                    ship.hitShip();
                    serverPlayer.increasePoint();
                    if(ship.isSunk()) {
                        System.out.println("Congrats! you have sunk opponent's ship");
                        serverPlayer.increasePoint();
                        sunk=true;
                    }

                }
            }

            enemyBoard.fire(posX,posY);


        } else {


            if(enemyBoard.getCellValue(posX, posY) == 0 || enemyBoard.getCellValue(posX, posY) == -5)
            {
                System.out.println("You have already fired");
            }
            else{
                System.out.println("You have missed");
            }

            enemyBoard.fire(posX,posY);

        }

        return sunk;

    }








    public boolean isAllShipSunk()
    {
        allSunk = false;
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

        if(sunk_count == 2)
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

        if(sunk_count == 2)
        {
            winnerName = serverPlayer.getPlayerName();
            allSunk = true;
            return allSunk;
        }

        return allSunk;

    }

    public  String getWinnerName() {
        boolean allSunk = false;
        int sunk_count1 =0;
        int sunk_count2 =0;
        ArrayList<Ship> myShips = serverPlayer.getListOfShips();
        ArrayList<Ship> enemyShips = enemy.getListOfShips();

        for(int i=0; i<myShips.size(); i++)
        {
            if(myShips.get(i).isSunk())
            {
                sunk_count1++;
            }
        }




        for(int i=0; i<enemyShips.size(); i++)
        {
            if(enemyShips.get(i).isSunk())
            {
                sunk_count2++;
            }
        }

        if(sunk_count1 > sunk_count2) {
            return enemy.getPlayerName();
        } else if(sunk_count1 < sunk_count2) {
            return serverPlayer.getPlayerName();
        } else {
            return "No one";
        }
    }






    public static void main(String args[]) throws IOException, ClassNotFoundException, InterruptedException {
        Server s = new Server("Imran");
        s.initializeServer();
        s.gamePlay();

    }






}

