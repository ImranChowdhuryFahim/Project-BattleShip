package Model;


public interface Board {

    public void initializeBoard();

    public boolean isHit();

    public boolean isSunk();

    public void deployShips();

    public void fire();
    
}
