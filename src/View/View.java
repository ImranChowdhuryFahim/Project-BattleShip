package View;

import Model.Board.BoardImp;

public interface View {

    public void printWelcomeMessage();

    public void clearConsole();

    public void resetConsole();

    public void printWinMessage();

    public void printTurnMessage();

    public void printBoard(BoardImp board);

    public void printInstructionsMessage();

}
