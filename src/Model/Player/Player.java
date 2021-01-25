package Model.Player;

import java.io.Serializable;
import java.util.ArrayList;

import Model.Board.Board;
import Model.Board.BoardImp;
import Model.Ship.*;
import View.GameView;
import View.GameViewImp;

public abstract class Player implements Serializable {

    protected int playerType;
    protected BoardImp board;
    protected String playerName;
    protected ArrayList<Ship> listOfShips;
    protected int points;

    public Player()
    {
        this.listOfShips = new ArrayList<Ship>();
        this.board= new BoardImp();
        this.points = 0;
    }

    public int getTotalShipNumbers () {
        return listOfShips.size();
    }

    public void initializeGameBoard()
    {
        board.initializeBoard();
    }

    public int getPoints()
    {
        return points;
    }


    public BoardImp getCurrentBoard()
    {
        return board;
    }

    public ArrayList<Ship> getListOfShips(){
        return listOfShips;
    }

    public void loadShips()
    {
        int[] shipQuantity= {2,3,5,8,10};

        for(int i=0; i<5; i++)
        {
            
            for(int j=1; j <= shipQuantity[i]; j++ )
            {
                     
                if(i==0)
                {
                    listOfShips.add(new Carrier(board, j));

                }
                else if(i==1)
                {
                    listOfShips.add(new BattleShip(board, j));

                }
                else if(i==2)
                {
                    listOfShips.add(new Destroyer(board, j));

                }
                else if(i==3)
                {
                    listOfShips.add(new SuperPatrol(board, j));

                }
                else if(i==4)
                {
                    listOfShips.add(new PatrolBoat(board, j));

                }

            }

        }

    }

    public void deployShips()
    {
        for(int i=0; i< listOfShips.size(); i++) {
            listOfShips.get(i).deployShip();
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


    public void performPlayerTurn (Player enemyPlayer,int posX, int posY) {

        GameView gameView = new GameViewImp();
        Board enemyBoard ;
        ArrayList<Ship> enemyShipList ;
        enemyBoard = enemyPlayer.getCurrentBoard();
        enemyShipList = enemyPlayer.getListOfShips();

        if(enemyBoard.isHit(posX, posY)) {
            gameView.printHitMessage(playerType);
            int cellValue = enemyBoard.getCellValue(posX, posY);
            int shipType = cellValueToType(cellValue);
            int shipInstanceNumber = cellValue - shipType;

            for (Ship ship: enemyShipList ) {
                if( ship.getShipType() == shipType && shipInstanceNumber == ship.getShipInstance()) {
                    ship.hitShip();
                    points++;
                    if(ship.isSunk()) {
                        gameView.printSunkMessage(playerType);
                        points++;
                    }

                }
            }

            enemyBoard.fire(posX,posY);


        } else {


            if(enemyBoard.getCellValue(posX, posY) == 0 || enemyBoard.getCellValue(posX, posY) == -5)
            {
                gameView.printAlreadyFiredMessage(playerType);
            }
            else{
                gameView.printMissMessage(playerType);
            }

            enemyBoard.fire(posX,posY);

        }
    }

    public void increasePoint()
    {
        points++;
    }

    public String getPlayerName()
    {
        return playerName;
    }

    public int getPlayerType()
    {
        return playerType;
    }

    public boolean isAllSunk() {
        boolean allSunk = true;
        for ( Ship ship: listOfShips ) {
            if (!ship.isSunk()) {
                allSunk = false;
                break;
            }
        }
        return  allSunk;
    }
}
