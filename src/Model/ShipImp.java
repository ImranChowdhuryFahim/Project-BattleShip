package Model;

public class ShipImp implements Ship{
    //Size of Ships

    public static int sizeCarrier=5;
    public static int sizeBattleShip=4;
    public static int sizeDestroyer=3;
    public static int sizeSuperPatrol=2;
    public static int sizePatrolBoat=1;

    //Quantity of Ships

    public static int quantityCarrier=2;
    public static int quantityBattleShip=3;
    public static int quantityDestroyer=5;
    public static int quantitySuperPatrol=8; 
    public static int quantityPatrolBoat=10;

    //Type of Ships

    public static String typeCarrier="Carrier";
    public static String typeBattleShip="Battleship";
    public static String typeDestroyer="Destroyer";
    public static String typeSuperPatrol="Super Patrol";
    public static String typePatrolBoat="Patrol Boat";
   

    @Override
    public String shipType() {
        return null;
    }

    @Override
    public int shipSize() {
        return 0;
    }

    @Override
    public int shipQuantity() {
        return 0;
    }

    @Override
    public void deployShip() {

    }
}
