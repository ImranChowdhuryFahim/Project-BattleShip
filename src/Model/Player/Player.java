package Model.Player;

import java.util.ArrayList;

import Model.Board.BoardImp;
import Model.Ship.BattleShip;
import Model.Ship.Carrier;
import Model.Ship.Destroyer;
import Model.Ship.PatrolBoat;
import Model.Ship.Ship;
import Model.Ship.SuperPatrol;

public abstract class Player {

    protected int playerType;
    protected BoardImp board;
    protected String playerName;
    protected ArrayList<Ship> listOfShips;

    public Player()
    {
        this.listOfShips = new ArrayList<Ship>();
        this.board= new BoardImp();
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
            
            for(int j=0; j<shipQuantity[i]; j++ )
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
        for(int i=0; i< listOfShips.size(); i++)
        {
            listOfShips.get(i).deployShip();
            System.out.println(i+1);
        }
    }


}
