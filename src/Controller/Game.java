package Controller;

import Controller.TakingInput.ConsoleInput;
import Model.Board.Board;
import Model.Board.BoardImp;
import Model.Player.HumanPlayer;
import Model.Player.Player;
import Model.Player.VirtualPlayer;
import Model.Ship.Ship;
import View.View;
import View.ViewImp;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Game {
    private String  playerName;
    private String winnerName ;
    private Scanner scanner ;
    private View view ;
    private Board playBoard ;
    private HumanPlayer humanPlayer;
    private VirtualPlayer virtualPlayer;
    public Game(String playerName)
    {
        this.playerName = playerName;
        this.view = new ViewImp();
        this.playBoard = new BoardImp();
        this.humanPlayer = new HumanPlayer(playerName);
        this.virtualPlayer = new VirtualPlayer();
    }

    public void initializeGame()
    {


        view.printBoardInitializationMessage();
        humanPlayer.initializeGameBoard();
        virtualPlayer.initializeGameBoard();
        humanPlayer.loadShips();
        virtualPlayer.loadShips();
        view.printBoard(humanPlayer.getCurrentBoard());
        delay(2);


        view.printHumanPlayerShipDeploymentMessage();
        delay(2);
        humanPlayer.deployShips();
        view.printBoard(humanPlayer.getCurrentBoard());

        delay(2);
        view.printVirtualPlayerShipDeploymentMessage();
        virtualPlayer.deployShips();
        view.printBoard(virtualPlayer.getCurrentBoard());

        playBoard.initializeBoard();

        view.printGameStartingMessage();
        view.showBoard(playBoard.getBoard());





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
                System.out.println("TIME IS OVER");
                break;
            }
            posX=0;
            posY=0;
            if(turnFlag == 0) // human Player's Turn
            {

                  view.printHumanPlayerTurnMessage();
                  view.propmtInputMessageForRow();


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

                        view.invalidRowWarning();
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

                      view.propmtInputMessageForColumn();
                      inputLine = con1.readLine();
                      if(inputLine != null) {
                          posY = Integer.parseInt(inputLine);
                      }

                      while ((posY > 15 || posY < 1) && posY != 0 ) {

                          view.invalidColWarning();
                          inputLine = con1.readLine();
                          if(inputLine != null) {
                              posY = Integer.parseInt(inputLine);
                          }
                      }
                      if(posX > 0 && posY > 0) {
                          playBoard.fire(posX, posY);


                          view.showBoard(playBoard.getBoard());

                          if (virtualPlayer.getCurrentBoard().isHit(posX - 1, posY - 1)) {
                              turnFlag = 0;

                          } else {

                              turnFlag = 1;
                          }

                          humanPlayer.performPlayerTurn(virtualPlayer, posX, posY);
                      } else {
                          turnFlag = 1;
                      }
                  }
                  else {
                      turnFlag = 1;
                  }










            }

            else {    // Virtual Player's Turn
                    view.printVirtualPlayerTurnMessage();

                    posX = random.nextInt(10);
                    while (posX < 1) {
                        posX = random.nextInt(10);
                    }

                    posY = random.nextInt(15);
                    while (posY < 1) {
                        posY = random.nextInt(15);
                    }

                if(humanPlayer.getCurrentBoard().isHit(posX-1,posY-1))
                {
                    turnFlag = 1;
                }
                else{
                    turnFlag = 0;
                }


                    virtualPlayer.performPlayerTurn(humanPlayer,posX,posY);




            }


            view.printPoint(humanPlayer.getPlayerType(), humanPlayer.getPoints());
            view.printPoint(virtualPlayer.getPlayerType(), virtualPlayer.getPoints());

        }

        if(isAllShipSunk())
        {
            System.out.println("All ship sunk");
            System.out.println(winnerName);
        }
        else {
            System.out.println("Game time over!");
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
            winnerName = humanPlayer.getPlayerName();
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
            winnerName = virtualPlayer.getPlayerName();
            allSunk = true;
            return allSunk;
        }

        return allSunk;

    }

}
