package Controller;

import View.View;
import  View.ViewImp;

import java.util.Scanner;

public class GameStarter {
    public void starter () {
        View view = new ViewImp();
        view.printWelcomeMessage();
        view.printInstructionsMessage();
        Scanner scanner = new Scanner(System.in);

        int option = scanner.nextInt();

        while (!(option == 1) && !(option == 2)) {
            view.printWarning();
            option = scanner.nextInt();
        }

        if (  option == 1 ) {

        }
        else if ( option == 2) {

        }
    }
}
