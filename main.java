package softwareEngineeringProject;
import java.util.Scanner;

public class main {

	public static int POINTS_FOR_VICTORY = 9; 
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	        Scanner input = new Scanner(System.in);
			Player player1 = new Player();
			System.out.println("Player 1, let's position your ships!");
	        player1.SetBoard(input);
	        Player player2 = new Player();
	        System.out.println("Player 2, let's position your ships!");
	        player2.SetBoard(input);
	        RunGame(player1, player2, input);
	}
	
	public static void TakeTurn(Player player, Player opponent, Scanner input) {
		player.Attack(opponent, input);
		player.opponentBoard.print();
	}
	
	public static void RunGame(Player player, Player opponent, Scanner input) {
		int turnCounter = 1;
		int playerIndicator = 1;
        do {
        System.out.println("Player 1, it's your turn!");
        System.out.print("Turn: " + turnCounter);
        System.out.print("  Player: " + playerIndicator);
        System.out.print("  Points:" + player.points);
        TakeTurn(player, opponent, input);
        if (player.points >= POINTS_FOR_VICTORY) {
			System.out.println("Player" + " 1 " + "Wins!");
			System.exit(0);
        }
        else {
        	turnCounter++;
        	playerIndicator++;
        	System.out.println("Player 2, it's your turn!");
            System.out.print("Turn: " + turnCounter);
            System.out.print("  Player: " + playerIndicator);
            System.out.print("  Points:" + opponent.points);
        	TakeTurn(opponent, player, input);
        	turnCounter++;
        	playerIndicator--;
        }
        if (opponent.points >= POINTS_FOR_VICTORY) {
			System.out.println("Player" + " 2 " + "Wins!");
			System.exit(0);
        }
        
        } while (player.points < POINTS_FOR_VICTORY || opponent.points < POINTS_FOR_VICTORY);
	}


}
