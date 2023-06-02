package softwareEngineeringProject;

public class Board {
    char [][] board;

    Board(){
        board = new char[9][9];
        populateBoard();
    }

    public char[][] getBoard() {
        return board;
    }

    private void populateBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board [i][j] = '~';
                if(i == 0 && j == 0)
                    board[i][j] = ' ';
                else if(i == 0)
                    board[i][j] = (char) (j + '0');
                else if(j == 0)
                    board[i][j] = (char) (i + '0');
            }
        }
    }

    public char getChar(int row, int column){
        try {
            return board[row][column];
        }catch (Exception ignored){
            System.out.println("Coordinates do not exist. Index out of bound exception");
        }

        return 'e';
    }

    public char setChar(int row, int column, char res){
        try {
            board[row][column] = res;
            return res;
        }catch (Exception e){
            System.out.println("Index out of bounds, Exception. Nothing was added");
        }

        return 'e';
    }

    public void print() {
        for (int i = 0; i < board.length; i++) {
            System.out.println();
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board [i][j] + "  ");
            }
        }
        System.out.println();
        System.out.println();
    }
}
