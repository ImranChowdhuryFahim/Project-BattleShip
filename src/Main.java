import Controller.GameController;


public class Main {
    public static void main(String[] args) throws InterruptedException{


        GameController gameController = new GameController("imran");

        gameController.initializeGame();
        gameController.playGame();

        
    }
}

