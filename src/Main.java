import utils.Constants;

public class Main {

    public static void main(String[] args){
        StartGame game = StartGame.getInstance(Constants.WINDOW_SIZE);
        game.run();
    }

}
