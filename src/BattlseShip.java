import Controller.Client.Client;
import Controller.Server.Server;
import Model.*;

public class BattlseShip {
    public static void main(String[] args) {
        SuperPatrol superPatrol=new SuperPatrol();

        Board b = new BoardImp();
        Carrier c= new Carrier();
        PatrolBoat p= new PatrolBoat();
        Destroyer d= new Destroyer();
        SuperPatrol s= new SuperPatrol();
        BattleShip bt = new BattleShip();

        b.initializeBoard();
        c.deployShip();
        p.deployShip();
        d.deployShip();
        s.deployShip();
        bt.deployShip();

        
    }
}
