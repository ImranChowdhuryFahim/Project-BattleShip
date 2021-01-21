package Model.Player;

import java.util.ArrayList;
import java.util.Scanner;

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

    public void getPlayerName()
    {
        System.out.println(playerName);
    }

    public BoardImp getCurrentBoard()
    {
        return board;
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
            return ShipInfo.battleShipSize;
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

    public void performPlayerTurn () {
        // Child classes will implement this method
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