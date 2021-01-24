package View;

import Model.Board.BoardImp;

public interface GameView {

    public void printWelcomeMessage();

    public void clearConsole();

    public void resetConsole();

    public void printWinMessage();

    public void showGameOverMessage();

    public void showTimeOverMessage();

    public void showWinnerName(String winner);

    public void showAllShipSunkMessage();

    public void printAlreadyFiredMessage(int playerType,String playerName);

    public void printHumanPlayerTurnMessage();
    public void printVirtualPlayerTurnMessage();

    public void printHitMessage(int playerType, String playerName);
    public void printMissMessage(int playerType, String playerName);
    public  void printSunkMessage(int playerType, String playerName);
    public void printPoint(int playerType, int point);

    public void showOwnBoard(BoardImp board);

    public void printInstructionsMessage();

    public void printWarning();
    public void propmtInputMessageForRow();
    public void propmtInputMessageForColumn();
    public void invalidRowWarning();
    public void invalidColWarning();

    public void printBoardInitializationMessage();
    public void printHumanPlayerShipDeploymentMessage();
    public void printVirtualPlayerShipDeploymentMessage();
    public void showEnemyBoard(BoardImp board);
    public void printGameStartingMessage();

    public void showServerStartedMessage(String name);
    public void showJoinedToServerMessage(String name);
    public void showServerWaitingMessage();
    public void showPlayerJoinedMessage();

    public void showOpponentsHitMessage();
    public void showOpponentsMissMessage();
    public void showOpponentsAlreadyHitMessage();
    public void showPointsWithName(String name,int point);
    public void showHumanOpponentPlayerSunkMessage();

}
