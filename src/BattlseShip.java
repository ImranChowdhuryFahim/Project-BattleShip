import Controller.GameController;


public class BattlseShip {
    public static void main(String[] args) throws InterruptedException{


        GameController gameController = new GameController("imran");

        gameController.initializeGame();
        gameController.executePlayerTurns();

        
    }
}

