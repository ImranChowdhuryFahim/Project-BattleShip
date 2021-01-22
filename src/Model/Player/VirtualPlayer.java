package Model.Player;

import Model.Board.Board;
import Model.Board.BoardImp;
import Model.Ship.Ship;
import View.View;
import View.ViewImp;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class VirtualPlayer extends Player{
    public VirtualPlayer() {
        this.playerType = 2;
        this.playerName = "Computer";
    }

//    @Override
//    public void performPlayerTurn (Player enemyPlayer,int posX, int posY){
//
//        View view = new ViewImp();
//        Scanner scanner = new Scanner(System.in);
//        Board enemyBoard =new BoardImp();
//        ArrayList<Ship> enemyShipList = new ArrayList<Ship>();
//        enemyBoard = enemyPlayer.getCurrentBoard();
//        enemyShipList = enemyPlayer.getListOfShips();
////        Random random = new Random();
////        int row = random.nextInt(10);
////        while (row < 1) {
////            row = random.nextInt(10);
////        }
////
////        int col = random.nextInt(15);
////        while (col < 1) {
////            col = random.nextInt(15);
////        }
//
//
//        if(enemyBoard.isHit(posX - 1, posY - 1)) {
//            view.printHitMessage();
//
//            int cellValue = enemyBoard.getCellValue(posX - 1, posY - 1);
//            int shipType = cellValueToType(cellValue);
//            int shipQuantity = shipTypeToQuantity(shipType);
//            int shipInstanceNumber = cellValue - shipType;
//            for (Ship ship: enemyShipList ) {
//                if( ship.getShipType() == shipType && shipInstanceNumber == ship.getShipInstance()) {
//                    ship.hitShip();
//                    points++;
//                    if(ship.isSunk()) {
//                        view.printSunkMessage();
//                        points++;
//                    }
//
//                }
//            }
//
//        } else {
//            view.printMissMessage();
//        }
//
//    }

}
