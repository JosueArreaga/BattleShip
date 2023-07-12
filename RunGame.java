import java.util.Scanner;

public class RunGame {

    //This function facilitates the game. A do-while loop is used to give each player
    //a turn. At the end of each turn, the function checks to see if the player's point
    //total has hit the necessary amount needed to win the game. If not, the game continues.

    public static void runSolo (Player player, AI computerPlayer, Scanner input) {
        int turnCounter = 1;
        int playerIndicator = 1;
        do {
            System.out.print("Turn: " + turnCounter);
            System.out.print(" Humanity's turn to fight back!\n");
            TakeTurn(player, computerPlayer, input);
            if (computerPlayer.boats.size() <= 0) {
                System.out.println("You've defeated the AI menace! ChatPTG is no more!");
                DisplayFireworks();
                System.exit(0);
            }
            else {
                turnCounter++;
                playerIndicator++;

                computerPlayer.AIatkManager(player);

                turnCounter++;
                playerIndicator--;
            }
            if (player.boats.size() <= 0) {
                System.out.println("AI has taken over! You lost!");
                DisplayAIVictory();
                System.exit(0);
            }

        } while (player.boats.size() > 0 && computerPlayer.boats.size() > 0);
    }


    public static void runMultiPlayer (Player player, Player opponent, Scanner input) {
        int turnCounter = 1;
        int playerIndicator = 1;
        do {
            System.out.println("Player 1, it's your turn!");
            System.out.print("Turn: " + turnCounter);
            System.out.print("  Player: " + playerIndicator +"\n");
            TakeTurn(player, opponent, input);
            if (opponent.boats.size() <= 0) {
                System.out.println("Player" + " 1 " + "Wins!");
                DisplayFireworks();
                System.exit(0);
            }
            else {
                turnCounter++;
                playerIndicator++;

                System.out.println("Player 2, it's your turn!");
                System.out.print("Turn: " + turnCounter);
                System.out.print("  Player: " + playerIndicator +"\n");
                TakeTurn(opponent, player, input);

                turnCounter++;
                playerIndicator--;
            }
            if (player.boats.size() <= 0) {
                System.out.println("Player" + " 2 " + "Wins!");
                DisplayFireworks();
                System.exit(0);
            }

        } while (player.boats.size() > 0 && opponent.boats.size() > 0);
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
    public static void selectMode(Scanner choice) {
        int difficulty = -1;
        int gameMode = -1;
        char placementStyle = 'x';
        System.out.println("Welcome Captain! Choose your game mode: Solo(1) or Multiplayer(2)");

        while(!choice.hasNext("[12]")) {
            System.out.println("Please enter (1) for Solo, or (2) for Multiplayer.");
            choice.next();
        }

        gameMode = choice.nextInt();

        System.out.println("Captain do you want to QuickStart (q) or a StrategicStart (s)");

        while(!choice.hasNext("[qs]")){
            System.out.println("Please enter (q) for QuickStart or (s) for a StrategicStart");
            choice.next();
        }

        placementStyle = choice.next().charAt(0);

        if(gameMode == 1){
            System.out.println("You've chosen to fight the AI menace of ChatPTG, what level of opponent will you face?");
            System.out.println("Level (0) Easy - Commander R2D2");
            System.out.println("Level (1) Hard - Commander Hal9000");
            System.out.println("Level (2) Unfair - Commander SkyNet");
            while(!choice.hasNext("[012]")){
                System.out.println("Please enter (0) for Easy, (1) for Hard, (2) for Unfair");
                choice.next();
            }

            difficulty = choice.nextInt();
        }

        GameSelect(choice, gameMode, placementStyle, difficulty);
    }

    //This is a basic function containing the turn actions for each player.
    //When called by another function, swapping the player objects as 1st
    //and 2nd parameters behaves like swapping turns would.
    public static void TakeTurn(Player player, Player opponent, Scanner input) {
        player.combinedBoard();
        if (player.superAttackActive == true) {
            System.out.println("Do you want to use a super Attack (y for Yes, n for No) (1) left");

            while(!input.hasNext("[yn]")) {
                System.out.println("Please enter y for yes, or n for no.");
                input.next();
            }

            char keyPress = input.next().charAt(0);

            if(keyPress == 'y'){
                player.SuperAttack(opponent, input);
            }
            else
                player.Attack(opponent, input);
        }
        else
            player.Attack(opponent, input);
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

    public static void DisplayFireworks() {
        System.out.println(
                "                                 .''.\n" +
                        "       .''.             *''*    :_\\/_:     .\n" +
                        "      :_\\/_:   .    .:.*_\\/_*   : /\\ :  .'.:.'.\n" +
                        "  .''.: /\\ : _\\(/_  ':'* /\\ *  : '..'.  -=:o:=-\n" +
                        " :_\\/_:'.:::. /)\\*''*  .|.* '.\\'/.'_\\(/_'.':'.'\n" +
                        " : /\\ : :::::  '*_\\/_* | |  -= o =- /)\\    '  *\n" +
                        "  '..'  ':::'   * /\\ * |'|  .'/.\\'.  '._____\n" +
                        "      *        __*..* |  |     :      |.   |' .---\"|\n" +
                        "       _*   .-'   '-. |  |     .--'|  ||   | _|    |\n" +
                        "    .-'|  _.|  |    ||   '-__  |   |  |    ||      |\n" +
                        "    |' | |.    |    ||       | |   |  |    ||      |\n" +
                        " ___|  '-'     '    \"\"       '-'   '-.'    '`      |____\n" +
                        "jgs~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
    }

    public static void DisplayAIVictory() {
        System.out.println("Put a robot ASCII art here.");
    }

    public static void GameSelect(Scanner input, int gameMode, char placementStyle, int difficulty) {

        if(gameMode == 2){
            //Multiplayer
            Player player1 = new Player();
            Player player2 = new Player();
            if(placementStyle == 'q'){
                player1.SetBoardRandomly();
                player2.SetBoardRandomly();
            }else{
                player1.SetBoard(input);
                player2.SetBoard(input);
            }
            runMultiPlayer(player1, player2, input);
        }else if(gameMode == 1){
            //Solo
            Player player1 = new Player();
            //Sets AI to selected difficulty
            AI computerPlayer = new AI(difficulty);

            if(placementStyle == 'q'){
                player1.SetBoardRandomly();
                computerPlayer.SetBoardRandomlyAI();
            }else{
                player1.SetBoard(input);
                computerPlayer.RandomSizeBoardSet();
            }
            runSolo(player1, computerPlayer, input);

        }
    }
}
