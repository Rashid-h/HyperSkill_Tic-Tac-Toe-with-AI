package tictactoe;

public class Main {


    public static void main(String[] args) {
        GameField field = new GameField();
        Game game = new Game();
        game.StartGame(field);
    }
}
