package View;

import Model.Board.BoardImp;

public interface View {

    public void printWelcomeMessage();

    public void clearConsole();

    public void resetConsole();

    public void printWinMessage();

    public void printHumanPlayerTurnMessage();
    public void printVirtualPlayerTurnMessage();

    public void printHitMessage(int playerType, String playerName);
    public void printMissMessage(int playerType, String playerName);
    public  void printSunkMessage(int playerType, String playerName);
    public void printPoint(int playerType, int point);

    public void printBoard(BoardImp board);

    public void printInstructionsMessage();

    public void printWarning();
    public void propmtInputMessageForRow();
    public void propmtInputMessageForColumn();
    public void invalidRowWarning();
    public void invalidColWarning();

    public void printBoardInitializationMessage();
    public void printHumanPlayerShipDeploymentMessage();
    public void printVirtualPlayerShipDeploymentMessage();
    public void showBoard(int[][] board);
    public void printGameStartingMessage();
}
