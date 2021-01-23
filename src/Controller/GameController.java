package Controller;

import Model.Input.ConsoleInput;
import Model.Player.HumanPlayer;
import Model.Player.VirtualPlayer;
import Model.Ship.Ship;
import View.GameView;
import View.GameViewImp;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.Timer;
import java.util.TimerTask;


public class GameController {
    private String  playerName;
    private String winnerName ;
    private Scanner scanner ;
    private GameView gameView;
    private HumanPlayer humanPlayer;
    private VirtualPlayer virtualPlayer;
    private static int lastHitPosX = -1;
    private static int lastHitPosY = -1;
    private boolean isGameOver;
    private Timer timer ;
    private long gameStartingTime;

    public GameController(String playerName)
    {
        this.playerName = playerName;
        this.gameView = new GameViewImp();
        this.humanPlayer = new HumanPlayer(playerName);
        this.virtualPlayer = new VirtualPlayer();

    }

    public void initializeGame()
    {


        gameView.printBoardInitializationMessage();
        humanPlayer.initializeGameBoard();
        virtualPlayer.initializeGameBoard();
        humanPlayer.loadShips();
        virtualPlayer.loadShips();
        gameView.showOwnBoard(humanPlayer.getCurrentBoard());
        delay(2);


        gameView.printHumanPlayerShipDeploymentMessage();
        delay(2);
        humanPlayer.deployShips();
        gameView.showOwnBoard(humanPlayer.getCurrentBoard());

        delay(2);
        gameView.printVirtualPlayerShipDeploymentMessage();
        virtualPlayer.deployShips();
        gameView.showOwnBoard(virtualPlayer.getCurrentBoard());

        gameView.printGameStartingMessage();
        gameView.showEnemyBoard(virtualPlayer.getCurrentBoard());

        gameStartingTime = System.currentTimeMillis();



    }

    public void executePlayerTurns() throws InterruptedException
    {
        long gameStartingTime = System.currentTimeMillis();
        int turnFlag = 0;
        scanner = new Scanner(System.in);
        Random random = new Random();
        int posX,posY;
        while (!isAllShipSunk())
        {
            int timeElapse = (int) ((System.currentTimeMillis() -gameStartingTime) / 1000);

            if( timeElapse >= 300 ){
                gameView.showTimeOverMessage();
                gameView.showWinnerName(winnerName);
                break;
            }
            posX=0;
            posY=0;
            if(turnFlag == 0) // human Player's Turn
            {

                  gameView.printHumanPlayerTurnMessage();
                  gameView.propmtInputMessageForRow();

                  int posx = 0,posy = 0;


//                  posX = scanner.nextInt();

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

//                System.out.println("Done. Your input was: " + posX);

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


                          if (virtualPlayer.getCurrentBoard().isHit(posX , posY )) {
                              turnFlag = 0;

                          } else {

                              turnFlag = 1;
                          }

                          humanPlayer.performPlayerTurn(virtualPlayer, posX, posY);
                          gameView.showEnemyBoard(virtualPlayer.getCurrentBoard());


                      } else {
                          turnFlag = 1;
                      }
                  }
                  else {
                      turnFlag = 1;
                  }










            }

            else {
                // Virtual Player's Turn
                    gameView.printVirtualPlayerTurnMessage();

                if(!wasHit())
                {
                    posX = random.nextInt(10);
                    while (posX < 1) {
                        posX = random.nextInt(10);
                    }

                    posY = random.nextInt(15);
                    while (posY < 1) {
                        posY = random.nextInt(15);
                    }
                }
                else {

                    if(isRightMovePossible())
                    {
                        // Right move
                        posX=lastHitPosX;
                        posY=lastHitPosY+1;
                    }
                    else {
                        if(isUpMovePossible())
                        {
                            // Up move
                            posX=lastHitPosX+1;
                            posY=lastHitPosY;
                        }
                        else {
                                // Down move
                                posX=lastHitPosX-1;
                                posY=lastHitPosY;

                        }

                    }
                }

                if(humanPlayer.getCurrentBoard().isHit(posX,posY))
                {
                    lastHitPosX = posX;
                    lastHitPosY = posY;
                    turnFlag = 1;
                }
                else{
                    lastHitPosX = -1;
                    lastHitPosY = -1;
                    turnFlag = 0;
                }


                    virtualPlayer.performPlayerTurn(humanPlayer,posX,posY);




            }


            gameView.printPoint(humanPlayer.getPlayerType(), humanPlayer.getPoints());
            gameView.printPoint(virtualPlayer.getPlayerType(), virtualPlayer.getPoints());

        }


        if(isAllShipSunk())
        {

            gameView.showAllShipSunkMessage();
            gameView.showWinnerName(winnerName);
        }

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
        ArrayList<Ship> shipsOfHumanPlayer = humanPlayer.getListOfShips();
        ArrayList<Ship> shipsOfVirtualPlayer = virtualPlayer.getListOfShips();

        for(int i=0; i<shipsOfHumanPlayer.size(); i++)
        {
            if(shipsOfHumanPlayer.get(i).isSunk())
            {
                sunk_count++;
            }
        }

        if(sunk_count == 28)
        {
            winnerName = virtualPlayer.getPlayerName();
            allSunk = true;
            return allSunk;
        }

        sunk_count = 0;
        for(int i=0; i<shipsOfVirtualPlayer.size(); i++)
        {
            if(shipsOfVirtualPlayer.get(i).isSunk())
            {
                sunk_count++;
            }
        }

        if(sunk_count == 28)
        {
            winnerName = humanPlayer.getPlayerName();
            allSunk = true;
            return allSunk;
        }

        return allSunk;

    }

    public boolean wasHit()
    {
        return lastHitPosX != -1 && lastHitPosY != -1;
    }

    public boolean isRightMovePossible()
    {
        return lastHitPosY <14;
    }

    public boolean isUpMovePossible()
    {
        return lastHitPosX < 10;
    }

}
