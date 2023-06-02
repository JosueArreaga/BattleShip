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


}
