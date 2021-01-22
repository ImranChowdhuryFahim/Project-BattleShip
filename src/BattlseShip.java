import Controller.Game;
import Controller.GameStarter;
import Model.Player.HumanPlayer;
import Model.Player.TestPlayer;
import Model.Player.VirtualPlayer;
import View.View;
import View.ViewImp;

import java.util.Scanner;


public class BattlseShip {
    public static void main(String[] args) throws InterruptedException{
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

/*
1-> 2 6 7 8 12 13 14
2-> 6 7 8 9 11 12
3-> 2 3 4 5 6 7  8 14 15
4-> 1 2 5 6 9  13
5-> 1 2 3 4 8 9 10 11 12 13
6-> 1 8 9 10 11
7-> 3 4 5 6 7 8 10 11 12
8-> 2 3 5 6 7 8 9
9-> 7 11 12 13
 */