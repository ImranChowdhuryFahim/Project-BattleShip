package Model.Ship;

import Model.Board.Board;

public class BattleShip extends Ship {

    public BattleShip(Board board, int instanceNo)
    {
        this.shipHealth=ShipInfo.carrierSize;
        this.shipSize=ShipInfo.carrierSize;
        this.shipType=ShipInfo.carrierType;
        this.shipInstance=instanceNo;
        this.board=board.getBoard();
    }


}
