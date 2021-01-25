package Model.Ship;

import java.io.Serializable;
import java.util.Random;

public abstract class Ship implements Serializable {

    protected int shipSize;
    protected int shipType;
    protected int shipInstance;
    protected int shipHealth;
    protected int[][] board;

    public boolean isSunk()
    {
        return shipHealth==0;
    }

    public void hitShip()
    {
        --shipHealth; // decreasing ship health by one if hit once

    }

    public int getShipType()
    {
        return shipType;
    }

    public int getShipInstance()
    {
        return shipInstance;
    }

    public void deployShip()  // randomly deploying ship horizontally on user game board
    {
        Random random= new Random();
        boolean deployed= false;
        int boardRow=10;
        int boardColumn=15;
        int shipDenotedInt = shipType+shipInstance;

        while(!deployed)
            {

                // Randomly calculating starting coordinates for ship placement
                int posX= random.nextInt(boardRow-1);
                int posY= random.nextInt(boardColumn-1);

                // checking if the starting cell is valid for the ship placement
                boolean slotFound = true;

                for(int i=0; i<shipSize; i++)
                {

                    if(posY + i > 14)
                    {
                        slotFound = false;
                        break;
                    }
                    if( board[posX][posY + i] != -1)
                    {
                        slotFound = false;
                        break;
                    }

                }


                //Deploying the ship if found valid starting cell

                if(slotFound)
                {
                    for(int i=0; i<shipSize; i++)

                    {
                        board[posX][posY + i] = shipDenotedInt;
                    }

                    deployed=true;


                }
            }
    }

}
