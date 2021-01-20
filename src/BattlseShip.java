import Model.Board.Board;
import Model.Board.BoardImp;
import Model.Player.TestPlayer;
import View.View;
import View.ViewImp;

public class BattlseShip {
    public static void main(String[] args) {
//        SuperPatrol superPatrol=new SuperPatrol();

        Board b = new BoardImp();

        b.initializeBoard();


        TestPlayer t = new TestPlayer();
        View v = new ViewImp();
        t.initializeGameBoard();
        t.loadShips();
        t.deployShips();

        v.printBoard(t.getCurrentBoard());









        
    }
}
