package Controller.Client;

import Model.Board.Board;
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
    int posX=0,posY=0,enemyPoint=0;
    int turnFlag=0;

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

        InitializeGame();

        long gameStartingTime = System.currentTimeMillis();

        while (!isAllShipSunk())
        {
            int timeElapse = (int) ((System.currentTimeMillis() -gameStartingTime) / 1000);

            if( timeElapse >= 300 ){
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
        }

        sendWinnerNameToServer();

        gameView.showWinnerName(winnerName);



    }

    private void sendWinnerNameToServer() throws IOException {
        dataOutputStreamToServer.writeUTF(winnerName);
    }


    private void clientsTurn() throws IOException, InterruptedException {

        int cellValue=-50,point=0;
        boolean sunk=false;
        boolean hit= false;

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
                    ,restTime
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
                    turnFlag =1;
                }
                else {

                    turnFlag = 0;
                }
                sunk = performPlayerTurn(enemy,posX,posY);
                cellValue = enemy.getCurrentBoard().getCellValue(posX,posY);
                point = playerClient.getPoints();





            } else {
                turnFlag = 0;
            }
        }
        else {
            turnFlag = 0;
        }

        System.out.println(playerClient.getPlayerName()+"'s point: "+playerClient.getPoints());
        System.out.println(enemy.getPlayerName()+"'s point: "+enemyPoint);

        if(posX>0 && posY>0)
        {
            gameView.showEnemyBoard(enemy.getCurrentBoard());
        }
        dataOutputStreamToServer.writeInt(posX);
        dataOutputStreamToServer.writeInt(posY);
        dataOutputStreamToServer.writeInt(cellValue);
        dataOutputStreamToServer.writeBoolean(sunk);
        dataOutputStreamToServer.writeBoolean(hit);
        dataOutputStreamToServer.writeInt(point);

    }








    private void serversTurn() throws IOException {

        System.out.println(enemy.getPlayerName()+"'s turn");
        int x,y,cell,point;
        boolean sunk,hit;
        x=dataInputStreamFromServer.readInt();
        y=dataInputStreamFromServer.readInt();
        cell = dataInputStreamFromServer.readInt();
        sunk = dataInputStreamFromServer.readBoolean();
        hit = dataInputStreamFromServer.readBoolean();
        point = dataInputStreamFromServer.readInt();

        if(x==0 || y==0)
        {
            System.out.println("Opponent didn't respond");
            turnFlag =1;
        }
        else if(hit)
        {
            System.out.println("Oops! opponent has hit your ship");
            if(sunk)
            {
                System.out.println("Oops! opponent has sunk your ship");
            }
            enemyPoint=point;
            System.out.println(playerClient.getPlayerName()+"'s point: "+playerClient.getPoints());
            System.out.println(enemy.getPlayerName()+"'s point: "+enemyPoint);
            turnFlag = 0;
        }
        else{
            System.out.println("Opponent has missed");
            System.out.println(playerClient.getPlayerName()+"'s point: "+playerClient.getPoints());
            System.out.println(enemy.getPlayerName()+"'s point: "+enemyPoint);
            turnFlag = 1;
        }

    }








    private void InitializeGame() throws IOException, ClassNotFoundException {

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
                    playerClient.increasePoint();
                    if(ship.isSunk()) {
                        System.out.println("Congrats! you have sunk opponent's ship");
                        playerClient.increasePoint();
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
