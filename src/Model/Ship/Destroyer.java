package Model.Ship;

import Model.Board.Board;

public class Destroyer extends Ship{

    public Destroyer(Board board, int instanceNo)
    {
        this.shipHealth=ShipInfo.carrierSize;
        this.shipSize=ShipInfo.carrierSize;
        this.shipType=ShipInfo.carrierType;
        this.shipInstance=instanceNo;
        this.board=board.getBoard();
    }




}
