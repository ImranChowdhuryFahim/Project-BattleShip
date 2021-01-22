package Model.Player;

import java.util.ArrayList;
import java.util.Scanner;

import Model.Board.Board;
import Model.Board.BoardImp;
import Model.Ship.*;
import View.View;
import View.ViewImp;

public abstract class Player {

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

        for(int i=1; i<2; i++)
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

    public int shipTypeToQuantity(int type) {
        if (type == 0) {
            return ShipInfo.carrierQuantity;
        }  else if (type == 2) {
            return ShipInfo.battleShipQuantity;
        }  else if (type == 5) {
            return ShipInfo.destroyerQuantity;
        }  else if (type == 10) {
            return ShipInfo.superPatrolQuantity;
        }  else {
            return ShipInfo.patrolBoatQuantity;
        }
    }

    public void performPlayerTurn (Player enemyPlayer,int posX, int posY) {

        View view = new ViewImp();
        Board enemyBoard =new BoardImp();
        ArrayList<Ship> enemyShipList = new ArrayList<Ship>();
        enemyBoard = enemyPlayer.getCurrentBoard();
        enemyShipList = enemyPlayer.getListOfShips();

        if(enemyBoard.isHit(posX - 1, posY - 1)) {
            view.printHitMessage(playerType,playerName);
            int cellValue = enemyBoard.getCellValue(posX - 1, posY - 1);
            int shipType = cellValueToType(cellValue);
            int shipQuantity = shipTypeToQuantity(shipType);
            int shipInstanceNumber = cellValue - shipType;

            for (Ship ship: enemyShipList ) {
                if( ship.getShipType() == shipType && shipInstanceNumber == ship.getShipInstance()) {
                    ship.hitShip();
                    points++;
                    if(ship.isSunk()) {
                        view.printSunkMessage(playerType,playerName);
                        points++;
                    }

                }
            }

            enemyBoard.fire(posX,posY);


        } else {

            if(enemyBoard.getCellValue(posX - 1, posY - 1) == 0)
            {
                view.printAlreadyHitMessage();
            }
            else{
                view.printMissMessage(playerType,playerName);
            }

        }
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
