import java.util.Scanner;

public class RunGame {

    //This function facilitates the game. A do-while loop is used to give each player
    //a turn. At the end of each turn, the function checks to see if the player's point
    //total has hit the necessary amount needed to win the game. If not, the game continues.

    public static void runSolo (Player player, AI opponent, Scanner input, boolean isAI) {
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

                if(isAI)
                    TakeTurnAI(opponent,player);
                else{
                    System.out.println("Player 2, it's your turn!");
                    System.out.print("Turn: " + turnCounter);
                    System.out.print("  Player: " + playerIndicator);
                    System.out.println("  Points:" + opponent.points +"\n");
                    TakeTurn(opponent, player, input);
                }


                turnCounter++;
                playerIndicator--;
            }
            if (opponent.points >= Player.POINTS_FOR_VICTORY) {
                System.out.println("Player" + " 2 " + "Wins!");
                System.exit(0);
            }

        } while (player.points < Player.POINTS_FOR_VICTORY || opponent.points < Player.POINTS_FOR_VICTORY);
    }


    public static void runMultiPlayer (Player player, Player opponent, Scanner input) {
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

        } while (player.points < Player.POINTS_FOR_VICTORY || opponent.points < Player.POINTS_FOR_VICTORY);
    }

    /*
    This function asks the user which game mode and difficulty he would like to play and returns a number
    corresponding to its difficulty.

    Game Mode:
        1 = Single Player
        2 = Multi-Player

    Game Modes:
        0 = QuickStart
        1 = Strategic Start

     Difficulty Levels (Solo Only):
        0 = Easy
        1 = Hard

    Return Value Examples:
        100 = Single-Player, QuickStart, Easy
        110 = Single-Player, Strategic, Easy
        200 = Multi-Player, QuickStart
        210 = Multi-Player, Strategic
 */
    public static int selectMode(Scanner choice) {
        System.out.println("Welcome Captain! Choose your game mode: Solo(1) or Multiplayer(2)");
        int gameMode = choice.nextInt();
        int result = 0;

        while (gameMode != 1 && gameMode != 2) {
            System.out.println("Invalid Value Choose your game mode: Solo(1) or Multiplayer(2)");
            gameMode = choice.nextInt();
        }
        result += gameMode * 100;


        System.out.println("Captain do you want to QuickStart (0) or a StrategicStart (1)");
        gameMode = choice.nextInt();
        while (gameMode != 0 && gameMode != 1) {
            System.out.println("Invalid value! Choose your game mode: QuickStart (0) or a StrategicStart (1)");
            gameMode = choice.nextInt();
        }

        result += gameMode * 10;

        return result;
    }

    //This is a basic function containing the turn actions for each player.
    //When called by another function, swapping the player objects as 1st
    //and 2nd parameters behaves like swapping turns would.
    public static void TakeTurn(Player player, Player opponent, Scanner input) {
        player.combinedBoard();
        player.Attack(opponent, input);
    }

    public static void TakeTurnAI(AI computerPlayer, Player opponent) {
        computerPlayer.RandomAttack(opponent);
    }

    public void displayLogo() {
        String[] logo;
        logo = new String[10];
        logo[0] = "                                     # #  ( )";
        logo[1] = "                                  ___#_#___|__";
        logo[2] = "                              _  |____________|  _";
        logo[3] = "                       _=====| | |            | | |==== _";
        logo[4] = "                 =====| |.---------------------------. | |====";
        logo[5] = "   <--------------------'   .  .  .  .  .  .  .  .   '--------------/";
        logo[6] = "     \\                                                            /";
        logo[7] = "      \\__________________________________________________________/";
        logo[8] = "                                    BOAT-FIGHT                      ";
        logo[9] = "ASCII artist: Matthew Bace";
        for(int i = 0; i < 9; i++){
            System.out.println(logo[i]);
        }
    }

    public void gameSelect(Scanner input) {
        int gameSelected = selectMode(input);

        if(gameSelected == 100){
            Player player1 = new Player();
            player1.SetBoardRandomly();
            AI player2 = new AI();
            player2.SetBoardRandomly();
            runSolo(player1, player2, input, true);
        }

        if(gameSelected == 110){
            Player player1 = new Player();
            System.out.println("Player 1, let's position your ships!");
            player1.SetBoard(input);
            AI player2 = new AI();
            player2.SetBoardRandomly();
            runSolo(player1, player2, input, true);
        }


        if(gameSelected == 200){
            Player player1 = new Player();
            player1.SetBoardRandomly();
            Player player2 = new Player();
            player2.SetBoardRandomly();
            runMultiPlayer(player1, player2, input);
        }

        if(gameSelected == 210){
            Player player1 = new Player();
            System.out.println("Player 1, let's position your ships!");
            player1.SetBoard(input);
            Player player2 = new Player();
            System.out.println("Player 2, let's position your ships!");
            player2.SetBoard(input);
            runMultiPlayer(player1, player2, input);
        }
    }
}