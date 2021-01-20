package Model.Player;

import Model.Ship.Ship;
import View.View;
import  View.ViewImp;

import java.util.Scanner;

public class HumanPlayer extends Player {
    public HumanPlayer(String name) {

        this.playerType = 2;
        this.playerName = name;

    }

    @Override
    public void performPlayerTurn (){

        View view = new ViewImp();
        Scanner scanner = new Scanner(System.in);
        view.propmtInputMessageForRow();
        int row = scanner.nextInt();
        while (row > 10 || row < 1) {
            view.invalidRowWarning();
            row = scanner.nextInt();
        }
        view.propmtInputMessageForColumn();
        int col = scanner.nextInt();

        while (col > 15 || col < 1) {
            view.invalidColWarning();
            col = scanner.nextInt();
        }

        if(board.isHit(row - 1, col - 1)) {
            view.printHitMessage();
            int cellValue = board.getCellValue(row - 1, col - 1);
            int shipType = cellValueToType(cellValue);
            int shipQuantity = shipTypeToQuantity(shipType);
            int shipInstanceNumber = cellValue - shipType;
            for (Ship ship: listOfShips ) {
                if( ship.getShipType() == shipType && shipInstanceNumber == ship.getShipInstance()) {
                    ship.hitShip();
                    points++;
                    if(ship.isSunk()) {
                        view.printSunkMessage();
                        points++;
                    }

                }
            }

        } else {
            view.printMissMessage();
        }

    }

}
