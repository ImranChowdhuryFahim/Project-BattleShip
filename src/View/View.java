package View;

public interface View {

    public void printWelcomeMessage();

    public void clearConsole();

    public void resetConsole();

    public void printWinMessage();

    public void printTurnMessage();

    public void printBoard(int[][] board);

    public void printInstructionsMessage();

}
