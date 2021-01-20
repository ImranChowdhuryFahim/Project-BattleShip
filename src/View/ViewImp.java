package View;

import Model.Board.BoardImp;
import Model.Ship.ShipInfo;

public class ViewImp implements View {
    private  String welcomeText = "Welcome to Battle Ship Game ...";
    private  String menuText = "1. Play with virtual opponent\n 2. Play with human opponent\n Choose option: ";
    private  String warningMessage = "Please choose a valid option";
    private String inputMessageForRow = "Input Row(1 to 10)";
    private String inputMessageForCol = "Input Column(1 to 15)";
    private  String invalidRowMessage = "Given row is out of the board. Please input a valid one: ";
    private  String invalidColMessage = "Given column is out of the board. Please input a valid one: ";
    private String hitMessage = "Congrats! You have hit opponent's ship";
    private  String missMessage = "Oops!, you have missed";
    private  String sunkMessage = "Ya hoo!, you have sunk one opponent's ship";
    @Override
    public void printWelcomeMessage() {
        System.out.println( welcomeText );
    }

    @Override
    public void clearConsole() {

    }

    @Override
    public void resetConsole() {

    }

    @Override
    public void printWinMessage() {

    }

    @Override
    public void printTurnMessage() {

    }

    @Override
    public void printHitMessage() {
        System.out.println(hitMessage);
    }

    @Override
    public void printMissMessage() {
        System.out.println(missMessage);
    }

    @Override
    public void printSunkMessage() {
        System.out.println(sunkMessage);
    }

    @Override
    public void printPoint() {

    }

    @Override
    public void printBoard(BoardImp board1) {

        int[][] board= board1.getBoard();
        System.out.println("\tA \tB \tC \tD \tE \tF \tG \tH \tI \tJ \tK \tL \tM \tN \tO");
        System.out.println();


        for (int i = 0; i < 10; i++) {
            System.out.print((i+1) + "");
            for (int j = 0; j < 15; j++) {
                if( board[i][j] == -1)
                {
                    System.out.print("\t~");
                }
                else if ( board[i][j] <= 2 ) {
                    System.out.print( "\tc" + (board[i][j] - ShipInfo.carrierType ));
                }
                else if ( board[i][j] <= 5 ) {
                    System.out.print( "\tb" + (board[i][j] - ShipInfo.battleShipType));
                }
                else if ( board[i][j] <= 10 ) {
                    System.out.print( "\td" + (board[i][j] - ShipInfo.destroyerType));
                }
                else if ( board[i][j] <= 18 ) {
                    System.out.print( "\ts" + (board[i][j] - ShipInfo.superPatrolType));
                }
                else {
                    System.out.print( "\tp" + (board[i][j] - ShipInfo.patrolBoatType));
                }
                
            }
            System.out.println("\n");
        }
        System.out.println();

        // System.out.println("\tA \tB \tC \tD \tE \tF \tG \tH \tI \tJ \tK \tL \tM \tN \tO");
        // System.out.println();

        // for(int row=0 ; row < 10 ; row++ ) {
        //     System.out.print((row+1) + "");
        //     for (int column = 0; column < 15; column++) {
        //         if(board[row][column]!=-1)
        //         {
        //             System.out.print("\t"+"c"+shipInstance);
        //         }
        //         else{
        //             System.out.print("\t"+"~");
        //         }


        //     }
        //     System.out.println("\n");
        // }
        //     System.out.println();
    }

    @Override
    public void printInstructionsMessage() {
        System.out.print(menuText);
    }

    @Override
    public void printWarning() {
        System.out.println(warningMessage);
        printInstructionsMessage();
    }

    @Override
    public void propmtInputMessageForRow() {
        System.out.print(inputMessageForRow);
    }

    @Override
    public void propmtInputMessageForColumn() {
        System.out.print(inputMessageForCol);
    }

    @Override
    public void invalidRowWarning() {
        System.out.println(invalidRowMessage);
    }

    @Override
    public void invalidColWarning() {
        System.out.println(invalidColMessage);
    }


}
