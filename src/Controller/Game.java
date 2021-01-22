package Controller;

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

    public void executePlayerTurns()
    {
        int turnFlag = 0;
        scanner = new Scanner(System.in);
        Random random = new Random();
        int posX,posY;
        while (!isAllShipSunk())
        {
            posX=0;
            posY=0;
            if(turnFlag == 0) // human Player's Turn
            {

                  view.printHumanPlayerTurnMessage();
                  view.propmtInputMessageForRow();


                  posX = scanner.nextInt();

                  while (posX > 10 || posX < 1) {

                        view.invalidRowWarning();
                      posX = scanner.nextInt();
                  }

                  view.propmtInputMessageForColumn();
                  posY = scanner.nextInt();

                  while (posY > 15 || posY < 1) {

                       view.invalidColWarning();
                      posY = scanner.nextInt();
                  }

                  playBoard.fire(posX,posY);

                  view.showBoard(playBoard.getBoard());


                  humanPlayer.performPlayerTurn(virtualPlayer,posX,posY);






                  turnFlag = 1;

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

                    virtualPlayer.performPlayerTurn(humanPlayer,posX,posY);

                    turnFlag = 0;
            }


            view.printPoint(humanPlayer.getPlayerType(), humanPlayer.getPoints());
            view.printPoint(virtualPlayer.getPlayerType(), virtualPlayer.getPoints());
        }

        if(isAllShipSunk())
        {
            System.out.println("All ship sunk");
            System.out.println(winnerName);
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
