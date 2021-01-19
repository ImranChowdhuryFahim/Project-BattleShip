package Model;

import java.util.Random;

public class Carrier extends ShipImp{

    @Override
    public String shipType() {
        return ShipImp.typeCarrier;
    }

    @Override
    public int shipSize() {
        return ShipImp.sizeCarrier;
    }

    @Override
    public int shipQuantity() {
        return ShipImp.quantityCarrier;
    }

    @Override
    public void deployShip() {
        
        Random random= new Random();
        int[][] board= BoardImp.board;

        boolean deployed= false;
        int shipSize = shipSize(); 
        int shipDenoterInt = 1;
        int shipQuantity = shipQuantity();
        
        for (int carrierShipNo = 0; carrierShipNo < shipQuantity; carrierShipNo++){

            deployed = false;     

            while(!deployed)
            {

                // Randomly calculating starting coordinates for ship placement
                int posX= random.nextInt(9);
                int posY= random.nextInt(14);
                
                // checking if the starting cell is valid for the ship placement
                boolean slotFound = true; 
                
                for(int i=0; i<shipSize; i++)
                {

                    if(posY + i > 14)
                    {
                        slotFound = false;
                        break;
                    }
                    if( board[posX][posY + i] != -1)
                    {
                        slotFound = false;
                        break;
                    }
                    
                }


                //Deploying the ship if found valid starting cell

                if(slotFound)   
                {
                    for(int i=0; i<shipSize; i++)

                    {
                        BoardImp.board[posX][posY + i] = shipDenoterInt;
                    }

                    deployed=true;
                    shipDenoterInt++;
                    
                }
            }
            
        }

        
        System.out.println("\tA \tB \tC \tD \tE \tF \tG \tH \tI \tJ \tK \tL \tM \tN \tO");
            System.out.println();
    
            for(int row=0 ; row < 10 ; row++ ) {
                System.out.print((row+1) + "");
                for (int column = 0; column < 15; column++) {
                    if(BoardImp.board[row][column]==1)
                    {
                        System.out.print("\t"+"c1");
                    }
                    else if(BoardImp.board[row][column]==2)
                    {
                        System.out.print("\t"+"c2");
                    }

                    else{
                        System.out.print("\t"+"~");
                    }
                    
    
                }
                System.out.println("\n");
            }
                System.out.println();

    }
    
    
}
