package Controller.Server;

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

public class Server {
    private String name;
    private ServerSocket server;
    private Socket new_connection;
    private DataInputStream dataInputStreamFromClient;
    private DataOutputStream dataOutputStreamToClient;
    private ObjectInputStream objectInputStreamFromClient;
    private ObjectOutputStream objectOutputStreamToClient;
    private String winnerName;
    private int posX=0,posY=0,enemyPoint=0;
    private int turnFlag=0;
    private int cellValue=-50,point=0;
    private boolean sunk=false;
    private boolean hit= false;



    private  boolean allSunk = false;

    private Scanner scanner;
    private HumanPlayer serverPlayer;
    private HumanPlayer enemy;
    private GameView gameView;

    public Server(String name)
    {
        this.name = name;
        this.serverPlayer = new HumanPlayer(name);
        this.gameView = new GameViewImp();
        this.scanner =new Scanner(System.in);
    }






    public void initialize() throws IOException {
        server = new ServerSocket(3000);
        gameView.showServerStartedMessage(name);
        gameView.showServerWaitingMessage();
        new_connection =  server.accept();
        gameView.showPlayerJoinedMessage();
        dataInputStreamFromClient = new DataInputStream(new_connection.getInputStream());
        dataOutputStreamToClient = new DataOutputStream(new_connection.getOutputStream());
        objectOutputStreamToClient = new ObjectOutputStream(new_connection.getOutputStream());
        objectOutputStreamToClient.flush();
        objectInputStreamFromClient = new ObjectInputStream(new_connection.getInputStream());
    }





    public void playGame() throws IOException, ClassNotFoundException, InterruptedException {


        InitializeGame();

        long gameStartingTime = System.currentTimeMillis();

        while (!isAllShipSunk())
        {
            int timeElapse = (int) ((System.currentTimeMillis() -gameStartingTime) / 1000);

            if( timeElapse >= 300 ){ // game ends on 5th minute
                gameView.showTimeOverMessage();
                if(enemyPoint > serverPlayer.getPoints()) {

                    gameView.printWinMessageWithPlayerName(enemy.getPlayerName());
                }
                else if (enemyPoint == serverPlayer.getPoints()) {
                    delay(2);
                    gameView.printWinMessageWithPlayerName(getWinnerName());
                }
                else {

                    gameView.printYouWonMessage();
                }

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
            gameView.printYouWonMessage();
            dataOutputStreamToClient.writeInt(-101);
            dataOutputStreamToClient.writeInt(posY);
            dataOutputStreamToClient.writeInt(cellValue);
            dataOutputStreamToClient.writeBoolean(sunk);
            dataOutputStreamToClient.writeBoolean(hit);
            dataOutputStreamToClient.writeInt(point);
        }


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

        gameView.printTurnMessageWithName(enemy.getPlayerName());
        int x, y, cell, point;
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
                gameView.printOpponentDidNotResponseMessage();
                turnFlag =0;
            }
            else if(hit)
            {
                gameView.showOpponentsHitMessage();
                if(sunk)
                {
                    gameView.showHumanOpponentPlayerSunkMessage();
                }
                enemyPoint=point;

                gameView.showPointsWithName(serverPlayer.getPlayerName(), serverPlayer.getPoints());
                gameView.showPointsWithName(enemy.getPlayerName(), enemyPoint);
                turnFlag =1;
            }
            else
            {

                gameView.showOpponentsMissMessage();
                gameView.showPointsWithName(serverPlayer.getPlayerName(),serverPlayer.getPoints());
                gameView.showPointsWithName(enemy.getPlayerName(),enemyPoint);


                turnFlag =0;
            }
        }
        else  {

            gameView.printWinMessageWithPlayerName(enemy.getPlayerName());
            terminateGame();
        }

    }






    private void serversTurn() throws IOException, InterruptedException {

        cellValue=-50; point=0;
        sunk=false;
        hit= false;


        gameView.printHumanPlayerTurnMessage();

        gameView.promptInputMessageForRow();

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
            int restTime = (int) (30 - (System.currentTimeMillis() - startingTime) / 1000);
            ConsoleInput con1 = new ConsoleInput(
                    1
                    ,restTime
                    ,
                    TimeUnit.SECONDS
            );

            gameView.promptInputMessageForColumn();
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

        gameView.showPointsWithName(serverPlayer.getPlayerName(),serverPlayer.getPoints());
        gameView.showPointsWithName(enemy.getPlayerName(),enemyPoint);
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

            gameView.printHitMessage(serverPlayer.getPlayerType());
            int cellValue = enemyBoard.getCellValue(posX, posY);
            int shipType = cellValueToType(cellValue);
            int shipInstanceNumber = cellValue - shipType;

            for (Ship ship: enemyShipList ) {
                if( ship.getShipType() == shipType && shipInstanceNumber == ship.getShipInstance()) {
                    ship.hitShip();
                    serverPlayer.increasePoint();
                    if(ship.isSunk()) {

                        gameView.printSunkMessage(serverPlayer.getPlayerType());
                        serverPlayer.increasePoint();
                        sunk=true;
                    }

                }
            }

            enemyBoard.fire(posX,posY);


        } else {


            if(enemyBoard.getCellValue(posX, posY) == 0 || enemyBoard.getCellValue(posX, posY) == -5)
            {

                gameView.printAlreadyFiredMessage(serverPlayer.getPlayerType());
            }
            else{

                gameView.printMissMessage(serverPlayer.getPlayerType());
            }

            enemyBoard.fire(posX,posY);

        }

        return sunk;

    }








    public boolean isAllShipSunk()
    {
        allSunk = false;
        int sunk_count =0;
        int totalShipCount= ShipInfo.getTotalShipCount();
        ArrayList<Ship> myShips = serverPlayer.getListOfShips();
        ArrayList<Ship> enemyShips = enemy.getListOfShips();

        for(int i=0; i<myShips.size(); i++)
        {
            if(myShips.get(i).isSunk())
            {
                sunk_count++;
            }
        }

        if(sunk_count == totalShipCount)
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

        if(sunk_count == totalShipCount)
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



    public  void terminateGame () {
        System.exit(0);
    }


    public static void main(String args[]) throws IOException, ClassNotFoundException, InterruptedException {
        GameView gameView = new GameViewImp();
        Scanner scanner = new Scanner(System.in);
        gameView.printWelcomeMessage();
        gameView.enterYourNameMessage();
        String name = scanner.nextLine();
        Server s = new Server(name);
        s.initialize();
        s.playGame();
    }

}

