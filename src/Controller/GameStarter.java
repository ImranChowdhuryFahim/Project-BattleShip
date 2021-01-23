package Controller;

import View.GameView;
import View.GameViewImp;

import java.util.Scanner;

public class GameStarter {
    public void starter () {
        GameView gameView = new GameViewImp();
        gameView.printWelcomeMessage();
        gameView.printInstructionsMessage();
        Scanner scanner = new Scanner(System.in);

        int option = scanner.nextInt();

        while (!(option == 1) && !(option == 2)) {
            gameView.printWarning();
            option = scanner.nextInt();
        }

        if (  option == 1 ) {

        }
        else if ( option == 2) {

        }
    }
}
