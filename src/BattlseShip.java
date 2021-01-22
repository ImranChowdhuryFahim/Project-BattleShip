import Controller.Game;
import Controller.GameStarter;
import Model.Player.HumanPlayer;
import Model.Player.TestPlayer;
import Model.Player.VirtualPlayer;
import View.View;
import View.ViewImp;

import java.util.Scanner;


public class BattlseShip {
    public static void main(String[] args) {
////        SuperPatrol superPatrol=new SuperPatrol();
//
//        Board b = new BoardImp();
//
//        b.initializeBoard();
//
//

//        HumanPlayer t = new HumanPlayer("imran");
//        HumanPlayer t1 = new HumanPlayer("motin");
//        View v = new ViewImp();
//        t.initializeGameBoard();
//        t.loadShips();
//        t.deployShips();
//        v.printBoard(t.getCurrentBoard());
//        Scanner scanner= new Scanner(System.in);
//        int r,c;
//        r=scanner.nextInt();
//        c=scanner.nextInt();
//        t1.performPlayerTurn(t,r,c);
//        t.performPlayerTurn();

        Game game = new Game("imran");

        game.initializeGame();
        game.executePlayerTurns();

//        VirtualPlayer v = new VirtualPlayer();
//        v.initializeGameBoard();
//        v.loadShips();
//        v.deployShips();
//        View view = new ViewImp();
//        view.printBoard(v.getCurrentBoard());



//
//
//        GameStarter menu = new GameStarter();
//
//        menu.starter();
//



        
    }
}
