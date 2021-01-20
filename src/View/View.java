package View;

import Model.Board.BoardImp;

public interface View {

    public void printWelcomeMessage();

    public void clearConsole();

    public void resetConsole();

    public void printWinMessage();

    public void printTurnMessage();

    public void printHitMessage();
    public void printMissMessage();
    public  void printSunkMessage();
    public void printPoint();

    public void printBoard(BoardImp board);

    public void printInstructionsMessage();

    public void printWarning();
    public void propmtInputMessageForRow();
    public void propmtInputMessageForColumn();
    public void invalidRowWarning();
    public void invalidColWarning();
}
