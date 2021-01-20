package Model;

import java.util.Random;


public class PatrolBoat extends Ship{

    public PatrolBoat(Board board,int instanceNo)
    {
        this.shipHealth=ShipInfo.carrierSize;
        this.shipSize=ShipInfo.carrierSize;
        this.shipType=ShipInfo.carrierType;
        this.shipInstance=instanceNo;
        this.board=board.getBoard();
    }



}
