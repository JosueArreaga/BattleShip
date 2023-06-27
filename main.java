import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        RunGame game = new RunGame();
        game.displayLogo();

        System.out.println("Press enter to start!");
        String line = input.nextLine();
        game.gameSelect(input);
        input.close();
    }

}