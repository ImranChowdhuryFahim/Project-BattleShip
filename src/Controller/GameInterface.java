package Controller;

public interface GameInterface {
    public void initializeAPlayer();
    public void play();
    public int whoseTurn ();
    public boolean isGameOver ();
    public void printFinalVerdict();

}
