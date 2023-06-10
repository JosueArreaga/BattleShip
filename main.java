import java.util.Scanner;

public class main {

    //Note: We should probably move this into the Player class and have it equal the ship totals.
    //Note: I went ahead and did it, but I'm leaving this here in case I broke something.
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

    //This is a basic function containing the turn actions for each player.
    //When called by another function, swapping the player objects as 1st
    //and 2nd parameters behaves like swapping turns would.
    public static void TakeTurn(Player player, Player opponent, Scanner input) {
        player.combinedBoard();
        player.Attack(opponent, input);
        //player.opponentBoard.print();
    }

    //This function facilitates the game. A do-while loop is used to give each player
    //a turn. At the end of each turn, the function checks to see if the player's point
    //total has hit the necessary amount needed to win the game. If not, the game continues.
    public static void RunGame(Player player, Player opponent, Scanner input) {
        int turnCounter = 1;
        int playerIndicator = 1;
        do {
            System.out.println("Player 1, it's your turn!");
            System.out.print("Turn: " + turnCounter);
            System.out.print("  Player: " + playerIndicator);
            System.out.println("  Points:" + player.points + "\n");
            TakeTurn(player, opponent, input);
            if (player.points >= Player.POINTS_FOR_VICTORY) {
                System.out.println("Player" + " 1 " + "Wins!");
                System.exit(0);
            }
            else {
                turnCounter++;
                playerIndicator++;
                System.out.println("Player 2, it's your turn!");
                System.out.print("Turn: " + turnCounter);
                System.out.print("  Player: " + playerIndicator);
                System.out.println("  Points:" + opponent.points +"\n");

                TakeTurn(opponent, player, input);
                turnCounter++;
                playerIndicator--;
            }
            if (opponent.points >= Player.POINTS_FOR_VICTORY) {
                System.out.println("Player" + " 2 " + "Wins!");
                System.exit(0);
            }

        } while (player.points < POINTS_FOR_VICTORY || opponent.points < POINTS_FOR_VICTORY);
    }
}