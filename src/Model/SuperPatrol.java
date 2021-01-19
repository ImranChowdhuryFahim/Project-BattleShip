package Model;

import java.util.Random;

public class SuperPatrol extends ShipImp {

    @Override
    public String shipType() {
        return ShipImp.typeSuperPatrol;
    }

    @Override
    public int shipSize() {
        return ShipImp.sizeSuperPatrol;
    }

    @Override
    public int shipQuantity() {
        return ShipImp.quantitySuperPatrol;
    }

    @Override
    public void deployShip() {
        Random random= new Random();
        int[][] board= BoardImp.board;

        boolean deployed= false;
        int shipSize = shipSize(); 
        int shipDenoterInt = 18;
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
                    
                        System.out.print(baal(BoardImp.board[row][column]));
                    
                    
    
                }
                System.out.println("\n");
            }
                System.out.println();

    }

    public String baal(int n)
    {
        if(n==-1)
        {
            return "\t"+"~"; 
        }
        else if(n>=3 && n<13){

             return "\t"+"p"+n;
        }
        else if(n>=13 && n < 18)
        {
            return "\t"+"d"+n;
        }
        else if (n > 18) {
            return "\t"+"s"+n;
        }
        else {
            return "\t"+"c"+n;
        }
        
        
    }
    
    
}