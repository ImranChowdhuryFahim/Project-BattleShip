package Model;


public interface Board {

    public void initializeBoard();

    public int[][] getBoard();

    public boolean isHit(int posX, int posY);

    public void fire(int posX, int posY);
    
}
