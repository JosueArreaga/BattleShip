import java.util.ArrayList;
import java.util.Random;

public class AI extends Player{

    int level;
    String name = "";
    int prevBoatHit = -1;
    int prevShotCoord = -1;
    int[] directions = {-10, 10, 1, -1};
    int currDirection = 0;
    ArrayList<Integer> attackCoordinates = new ArrayList<>();
    ArrayList<Integer> hitCoordinates = new ArrayList<>();
    ArrayList<Integer> missCoordinates = new ArrayList<>();

    public AI(int difficulty){
        level = difficulty;
        if(level == 0){
            name = "R2D2";
        } else if (level == 1){
            name = "Hal9000";
        } else {
            name = "SkyNet";
        }
        //Filling out attack coordinates
        for(int i = 11; i <= 88; i++) {
            attackCoordinates.add(i);
            if(i%10 == 8){
                //Adds 2 to go from x8 to (x+1)0, which is still out of bounds
                //The for loop i++ takes it from (x+1)0 to (x+1)1
                i+=2;
            }
        }
    }

    public void SetBoardRandomlyAI() {
        RandomShipPlacement(2);
        RandomShipPlacement(3);
        RandomShipPlacement(3);
        RandomShipPlacement(4);
        if(level == 2){
            RandomShipPlacement(2);
            RandomShipPlacement(4);
        }
    }


    public void RandomSizeBoardSet(){
        Random rng = new Random();
        RandomShipPlacement(rng.nextInt(6) + 1);
        RandomShipPlacement(rng.nextInt(6) + 1);
        RandomShipPlacement(rng.nextInt(6) + 1);
        RandomShipPlacement(rng.nextInt(6) + 1);
        if(level == 2){
            RandomShipPlacement(rng.nextInt(6) + 1);
            RandomShipPlacement(rng.nextInt(6) + 1);
        }
    }

    public boolean CoordCheck(Integer coord){
        if(attackCoordinates.contains(coord) && !missCoordinates.contains(coord) && !hitCoordinates.contains(coord)){
            return true;
        }
        return false;
    }

    public void AIatkManager(Player opponent){
        if(level == 0){
            System.out.println("Easy mode");
            RandomAtk(opponent);
        }else if(level >= 1){
            System.out.println("Hard mode");
            if(prevBoatHit < 0){
                //if there is no new boat hit recently, do random attack
                RandomAtk(opponent);
            }else if(currDirection == 0){
                //if there is a recently hit boat, but the AI doesn't know which way it faces,
                //start direction discovery phase.
                DirectionDiscovery(opponent);
            } else{
                //if there is a recently hit boat and the AI knows which way it faces, start firing down that way
                DirectFound(opponent);
            }
        }
    }

    public void DirectFound(Player opponent){
        System.out.println("Direct found start");
        Integer newShot = prevShotCoord + currDirection;
        char[] results;
        if(CoordCheck(newShot)){
            if(superAttackActive){
                 results = AIsuperAtk(opponent, currDirection, prevBoatHit);
            }else{
                results = firing(opponent, newShot);
                prevShotCoord = newShot;
            }
            if(results[1] == 't'){
                //Direction is reset and prevBoatHit is reset
                currDirection = 0;
                prevBoatHit = -1;
                hitCoordinates.add(newShot);
                System.out.println(name + " sunk one of your fighting boats!");
                //System.out.println("AI sunk boat direct found");
            } else if(results[0] == 'b'){
                hitCoordinates.add(newShot);
                System.out.println(name + " got a hit at " + newShot);
                //System.out.println("AI hit boat direct found");
            } else if(results[0] == '~'){
                //Direction is reverted to 0 if there is a miss going down one direction, that means the rest of the boat is down the opposite direction
                //from where we initially hit the boat, so we reverse the current direction and set the prevshot to the prevboathit
                currDirection = -1*currDirection;
                prevShotCoord = prevBoatHit;
                missCoordinates.add(newShot);
                //System.out.println("AI miss direct found, reversing direction.");
            }
        } else{
            //If the newShot is invalid, then look in another direction.
            currDirection = 0;
            //System.out.println("Direct found fail, reverting to direct discovery phase");
            DirectionDiscovery(opponent);
        }
    }

    public void DirectionDiscovery(Player opponent){
        System.out.println("Direct discovery start");
        Random rng = new Random();
        int randomDirection = 0;
        int newShot = -1;
        while(newShot < 0){
            randomDirection = directions[rng.nextInt(4)];
            if(CoordCheck(prevBoatHit + randomDirection)){
                newShot = prevBoatHit + randomDirection;
                break;
            }else if(hitCoordinates.contains(prevBoatHit + randomDirection)){
                int i = 1;
                while(hitCoordinates.contains(prevBoatHit + randomDirection * i)){
                    i++;
                }
                if(attackCoordinates.contains(prevBoatHit + randomDirection * i) && !missCoordinates.contains(prevBoatHit + randomDirection * i)){
                    newShot = prevBoatHit+(randomDirection*i);
                }
            }
        }

        char[] results = firing(opponent, newShot);
        prevShotCoord = newShot;

        if(results[1] == 't'){
            //If it sinks a ship this way, no need to keep looking. Reverts to random attack phase.
            currDirection = 0;
            prevBoatHit = -1;
            System.out.println(name + " sunk one of your fighting boats!");
            //System.out.println("AI sunk boat direct discovery");
            hitCoordinates.add(newShot);
        } else if(results[0] == 'b'){
            //If it hits a ship but doesn't sink, switch to DirectFound phase.
            currDirection = randomDirection;
            System.out.println(name + " got a hit at " + newShot);
            //System.out.println("AI hit boat direct discovery");
            hitCoordinates.add(newShot);
        }else{
            //If it misses, keep looking for the direction its facing.
            System.out.println(name + " missed at " + newShot);
            //System.out.println("AI missed direct discovery");
            missCoordinates.add(newShot);
        }
    }

    public void RandomAtk(Player opponent){
        Random rnd = new Random();
        boolean isValid = false;
        Integer randCoord = 0;
        while(!isValid){
            randCoord = attackCoordinates.get(rnd.nextInt(attackCoordinates.size()));
            isValid = CoordCheck(randCoord);
        }

        char[] results = firing(opponent, randCoord);
        prevShotCoord = randCoord;

        if(results[0] == 'b'){
            if(results[1] == 't'){
                System.out.println(name + " sunk one of your fighting boats!");
            }else {
                System.out.println(name + " got a hit at " + randCoord);
            }
            //System.out.println("Random AI hit: " + randCoord);
            hitCoordinates.add(randCoord);
            prevBoatHit = randCoord;
        }else{
            System.out.println(name + " missed at " + randCoord);
            //System.out.println("Random AI miss: " + randCoord);
            missCoordinates.add(randCoord);
        }

    }

    public char[] AIsuperAtk(Player opponent, int direction, int target){
        superAttackActive = false;
        int xcoordinate = target/10;
        int ycoordinate = target%10;
        int hitTotal = 0;
        int sinkTotal = 0;
        char[] fullResult = {'a','b'};

        if(currDirection == -10 || currDirection == 10){
            xcoordinate = 1;
            while(xcoordinate < 9){
                char[] results = firing(opponent, xcoordinate*10 + ycoordinate);
                if(results[0] == 'b') {
                    hitTotal++;
                    if(results[1] == 't'){
                        sinkTotal++;
                    }
                }
                xcoordinate++;
            }
        }else{
            ycoordinate = 1;
            while(ycoordinate < 9){
                char[] results = firing(opponent, xcoordinate*10 + ycoordinate);
                if(results[0] == 'b') {
                    hitTotal++;
                    if(results[1] == 't'){
                        sinkTotal++;
                    }
                }
                ycoordinate++;
            }
        }

        if(hitTotal > 1){
            fullResult[0] = 'b';
        }else if(hitTotal > 0){
            fullResult[0] = 'b';
        }else{
            fullResult[0] = '~';
        }

        if(sinkTotal > 0){
            fullResult[1] = 't';
        }else {
            fullResult[1] = 'f';
        }

        return fullResult;
    }

    /*
        This method is similar to ShipPlacement with a few differences. Firstly, there is no need for user input, nor a need for
        coordinate verification. The reason for this, is that the random class automatically produces values within the valid range.
        It then iterates randomly through the board until all the ships are placed.
     */



}
