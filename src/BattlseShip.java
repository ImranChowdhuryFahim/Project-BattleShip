import Controller.GameStarter;
import Model.Player.HumanPlayer;
import Model.Player.TestPlayer;
import Model.Player.VirtualPlayer;
import View.View;
import View.ViewImp;


public class BattlseShip {
    public static void main(String[] args) {
////        SuperPatrol superPatrol=new SuperPatrol();
//
//        Board b = new BoardImp();
//
//        b.initializeBoard();
//
//

        VirtualPlayer t = new VirtualPlayer();
        View v = new ViewImp();
        t.initializeGameBoard();
        t.loadShips();
        t.deployShips();
        v.printBoard(t.getCurrentBoard());
        t.performPlayerTurn();



//
//
//        GameStarter menu = new GameStarter();
//
//        menu.starter();
//


        
    }
}
