 package Model;

public class BoardImp implements Board {
    static int boardRow = 10;
    static int boardColumn = 15;
    static int[][] board = new int[boardRow][boardColumn];
    



    @Override
    public void initializeBoard() {
        
        //Initializing game board

        for(int i=0; i<boardRow; i++)
        {
            

            for(int j=0; j<boardColumn; j++)
            {

                board[i][j]=-1;  // -1 deonotes empty cell

            }

        }

        System.out.println("\tA \tB \tC \tD \tE \tF \tG \tH \tI \tJ \tK \tL \tM \tN \tO");
        System.out.println();

        for(int row=0 ; row < 10 ; row++ ) {
            System.out.print((row+1) + "");
            for (int column = 0; column < 15; column++) {
                if(board[row][column]==-1)
                {
                    System.out.print("\t"+"~");
                }
                

            }
            System.out.println("\n");
        }
            System.out.println();

    }

    @Override
    public boolean isHit() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isSunk() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void deployShips() {
        // TODO Auto-generated method stub

    }

    @Override
    public void fire() {
        // TODO Auto-generated method stub

    }
}
