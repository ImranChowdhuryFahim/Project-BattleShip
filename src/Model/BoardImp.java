 package Model;

public class BoardImp implements Board {
    static int boardRow = 10;
    static int boardColumn = 15;
    public static int[][] board = new int[boardRow][boardColumn];




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
                System.out.print("\t"+"~");


            }
            System.out.println("\n");
        }
            System.out.println();

    }

    @Override
    public int[][] getBoard() {
        return board;
    }

    @Override
    public boolean isHit(int posX, int posY) {
        return board[posX][posY] != -1;
    }

    @Override
    public void fire(int posX, int posY) {



    }
}
