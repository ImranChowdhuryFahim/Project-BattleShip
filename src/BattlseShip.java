import Model.*;

public class BattlseShip {
    public static void main(String[] args) {
//        SuperPatrol superPatrol=new SuperPatrol();

        Board b = new BoardImp();

        b.initializeBoard();
        Carrier c = new Carrier(b,1);

        c.deployShip();







        
    }
}
