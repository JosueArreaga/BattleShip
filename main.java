package softwareEngineeringProject;
import java.util.Scanner;

public class main {

	public static int POINTS_FOR_VICTORY = 7; 
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	        Scanner input = new Scanner(System.in);
			Player player1 = new Player();
	        player1.setBoard(input);
	        Player player2 = new Player();
	        player2.setBoard(input);
	        RunGame(player1, player2, input);
	}
	
	public static void TakeTurn(Player player, Player opponent, Scanner input) {
		player.Attack(opponent, input);
		player.opponentBoard.print();
	}
	
	public static void RunGame(Player player, Player opponent, Scanner input) {
        do {
        TakeTurn(player, opponent, input);
        if (player.points >= POINTS_FOR_VICTORY) {
			System.out.println("Player" + "1" + "Wins!");
			System.exit(0);
        }
        else TakeTurn(opponent, player, input);
        if (opponent.points >= POINTS_FOR_VICTORY) {
			System.out.println("Player" + "2" + "Wins!");
			System.exit(0);
        }
        
        } while (player.points < POINTS_FOR_VICTORY || opponent.points < POINTS_FOR_VICTORY);
	}
	
	
	
	public void ship_placement(char[][] board, int size){
        Scanner coor_input = new Scanner(System.in);
        System.out.println("Input a row for the head of your " + size + " unit large ship.");
        int head_row = coor_input.nextInt;
        System.out.println("Input a column for the head of your " + size + " unit large ship.");
        int head_col = coor_input.nextInt;

        if (valid_coordinate(board, head_row, head_col) == False){
            System.out.println("That coordinate is not within the limits of the board, or is ontop of another ship, try again.");
            ship_placement(board, size);
            break;
        }

        int[] head = {head_row, head_col};
        System.out.println("Your head coordinates are valid, input a tail coordinate that shares either a row or column with your head, it can't be diagonal!");
        
        System.out.println("Input a row for the tail of your " + size + " unit large ship.");
        int tail_row = coor_input.nextInt;
        System.out.println("Input a column for the tail of your " + size + " unit large ship.");
        int tail_col = coor_input.nextInt;

        if (valid_coordinate(board, tail_row, tail_col) == False){
            System.out.println("That coordinate is not within the limits of the board, or is ontop of another ship, try again.");
            ship_placement(board, size);
            break;
        }
        int[] tail = {tail_row, tail_col};


        if (valid_ship(board, size, head, tail) == True){

        }else{
            System.out.println("Your ship placement is invalid.");
            ship_placement(board, size);
            break;
        }

    }
	
	public boolean valid_coordinate(char[][] board, int row, int col){
        boolean result = true;
        if (row < 1 || row > 9) {
            System.out.println("Row is invalid.");
            result = false;
        }
        if (col < 1 || row > 9){
            System.out.println("Col is invalid.");
            result = false;
        }

        if (board[row][col] != '~'){
            System.out.println("Ships cannot be on top of eachother.");
            result = false;
        }

        return result;
    }

    public boolean valid_ship(char[][] board, int size, int[] head, int[] tail){
        String aligned = "None";
        if(head[0] == tail[0]){
            if(head[1] > tail[1]){
                aligned = "horizontal h->t";
            }else if(head[1] < tail[1]){
                aligned = "horizontal t->h";
            } else{
                return false;
            }
            } else if(head[1] == tail[1]) {
                if(head[0] > tail[0]){
                    aligned = "vertical h->t";
                }else if (head[0] < tail[0]){
                    aligned = "vertical t->h";
                }
            }
        

        if (size > 2){
            mid_pieces =  (size - 2);
            if (aligned == "horizontal h->t"){
                while (mid_pieces > 0){
                    if (valid_coordinate(board, head[0], head[1] + mid_pieces) == false){
                        return false;
                    }
                    mid_pieces--;
                }
            } else if (aligned == "horizontal t->h"){
                while (mid_pieces > 0){
                    if (valid_coordinate(board, head[0], head[1] - mid_pieces) == false){
                        return false;
                    }
                    mid_pieces--;
                }
            } else if (aligned == "vertical h->t"){
                while (mid_pieces > 0){
                    if (valid_coordinate(board, head[0]  + mid_pieces, head[1]) == false){
                        return false;
                    }
                    mid_pieces--;
                }
            } else if (aligned == "vertical t->h"){
                while (mid_pieces > 0){
                    if (valid_coordinate(board, head[0] - mid_pieces, head[1]) == false){
                        return false;
                    }
                    mid_pieces--;
                }
            }
            
        }

    }
}
