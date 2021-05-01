package tictactoe;

import java.util.Random;
import java.util.Scanner;

class Game {
    private final Scanner sc = new Scanner(System.in);
    private final Random random = new Random();
    private boolean isEnd = false;
    private int Xs = 0;
    private int Os = 0;


    void StartGame(GameField field) {
        System.out.print("Input command: ");
        String[] command = sc.nextLine().split(" ");
        if (command[0].equals("start") && command.length == 3) {
            field.ShowField();
            GamingInTicTacToe(field, command[1], command[2]);

        } else if (command[0].equals("exit") && command.length == 1) {
        } else {
            System.out.println("Bad parameters!");
            StartGame(field);
        }
    }
        private void GamingInTicTacToe(GameField field, String player1, String player2) {
            while (!isEnd) {
                if (Xs < Os || Xs == Os) {
                    while (true) {
                        int[] coordinatesPlayer1 = new int[2];
                        int yPlayer1;
                        int xPlayer1;
                        int player_1 = 1;
                        switch (player1) {
                            case "user":
                                coordinatesPlayer1 = User();
                                break;
                            case "easy":
                                coordinatesPlayer1 = EasyAI();
                                break;
                            case "medium":
                                coordinatesPlayer1 = MediumAI(field.getField());
                                break;
                            case "hard":
                                coordinatesPlayer1 = HardAI(field.getField(), player_1);
                                break;
                        }
                        xPlayer1 = coordinatesPlayer1[1];
                        yPlayer1 = coordinatesPlayer1[0];
                        if (CheckingCoordinates(xPlayer1, yPlayer1, field)) {
                            field.setField(xPlayer1, yPlayer1, 'X');
                            Xs++;
                            field.ShowField();
                            break;
                        }
                    }

                } else {
                    while (true) {
                        int xPlayer2;
                        int yPlayer2;
                        int player_2 = 2;
                        int[] coordinatesPlayer2 = new int[2];
                        switch (player2) {
                            case "user":
                                coordinatesPlayer2 = User();
                                break;
                            case "easy":
                                coordinatesPlayer2 = EasyAI();
                                break;
                            case "medium":
                                coordinatesPlayer2 = MediumAI(field.getField());
                                break;
                            case "hard":
                                coordinatesPlayer2 = HardAI(field.getField(), player_2);
                                break;
                        }
                        xPlayer2 = coordinatesPlayer2[1];
                        yPlayer2 = coordinatesPlayer2[0];
                        if (CheckingCoordinates(xPlayer2, yPlayer2, field)) {
                            field.setField(xPlayer2, yPlayer2, 'O');
                            Os++;
                            field.ShowField();
                            break;
                        }
                    }
                }
                isWins(field.getField());
            }
            Xs = 0;
            Os = 0;
            isEnd = false;
            field.ClearField();
            StartGame(field);
        }


    private int[] User() {
        int[] coor = new int[2];
        while (true) {
            System.out.print("Enter the coordinates: ");
            String coordinates = sc.nextLine();
            if (isNumber(coordinates)) {
                int y = Integer.parseInt(String.valueOf(coordinates.charAt(0))) - 1;
                int x = Integer.parseInt(String.valueOf(coordinates.charAt(2))) - 1;
                coor[0] = y;
                coor[1] = x;
                break;
            } else {
                System.out.println("You should enter numbers!");
            }
        }
        return coor;
    }

    private int[] EasyAI() {
        System.out.println("Making move level \"easy\"");
        return new int[]{random.nextInt(3), random.nextInt(3)};
    }

    private int[] MediumAI(char[][] field) {
        System.out.println("Making move level \"medium\"");
        int row = FinePositionForMediumAI(field);
        for (int i = 0; i < field.length; i++) {
            if (row == 1 && field[i][i] == ' ') {
                return new int[] {i, i};
            } else if (row == 2 && field[field.length - 1 - i][i] == ' ') {
                return new int[] {field.length - 1 - i, i};
            }
            for (int j = 0; j < field[i].length; j++) {
                if (row == 3 && field[i][j] == ' ') {
                    return new int[] {i, j};
                } else if (row == 4 && field[j][i] == ' ') {
                    return new int[] {j, i};
                }
            }
        }
        return new int[]{random.nextInt(3), random.nextInt(3)};
    }

    private int[] HardAI(char[][] field, int numPlayer) {
        System.out.println("Making move level \"hard\"");
        return findBestMove(field, numPlayer);
//        return new int[]{random.nextInt(3), random.nextInt(3)};
    }

    private int FinePositionForMediumAI(char[][] field) {
        int sumDL = 0;
        int sumDR = 0;
        for (int i = 0; i < field.length; i++) {
            int sumG = 0;
            int sumV = 0;
            sumDL += field[i][i];
            sumDR += field[field.length - 1 - i][i];
            if (sumDL == 208 || sumDL == 190) {
                return 1;
            } else if (sumDR == 208 || sumDR == 190) {
                return  2;
            }
            for (int j = 0; j < field[i].length; j++) {
                sumG += field[i][j];
                sumV += field[j][i];
                if (sumG == 208 || sumG == 190) {
                    return  3;
                } else if (sumV == 208 || sumV == 190) {
                    return  4;
                }
            }
        }
        return 0;
    }

   private boolean CheckingCoordinates(int x, int y, GameField field) {
        boolean check = true;
        if (y < 0 || y > 2 || x < 0 || x > 2) {
            System.out.println("Coordinates should be from 1 to 3!");
            check = false;
        } else if (field.getField(x, y) != ' ') {
            check = false;
            System.out.println("This cell is occupied! Choose another one!");
        }
        return check;
    }

    private boolean isNumber(String str) {
        try {
           Integer.parseInt(String.valueOf(str.charAt(0)));
           Integer.parseInt(String.valueOf(str.charAt(2)));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private void isWins(char[][] field) {

        int sumDL = 0;
        int sumDR = 0;
        int xWin = 0;
        int oWin = 0;
        for (int i = 0; i < field.length; i++) {
            int sumG = 0;
            int sumV = 0;
            sumDL += field[i][i];
            sumDR += field[field.length - 1 - i][i];
            for (int j = 0; j < field[i].length; j++) {
                sumG += field[i][j];
                sumV += field[j][i];
            }
            if (sumG == 264 || sumV == 264 || sumDL == 264 || sumDR == 264) {
                xWin = 1;
            }
            if (sumG == 237 || sumV == 237 || sumDL == 237 || sumDR == 237) {
                oWin = 1;
            }
        }
        if (xWin == 1 && oWin != 1) {
            System.out.println("X wins");
            isEnd = true;
        } else if (oWin == 1 && xWin != 1) {
            System.out.println("O wins");
            isEnd = true;
        } else if ((Math.abs(Xs - Os) > 1 || Math.abs(Os - Xs) > 1) || xWin == 1 && oWin == 1) {
            System.out.println("Impossible");
            isEnd = true;
        } else if (Xs + Os == 9) {
            System.out.println("Draw");
            isEnd = true;
        }
    }

    int evaluate(char b[][], int numPlayer) {
        char player;
        char opponent;
        if (numPlayer == 1) {
            player = 'X';
            opponent = 'O';
        } else {
            player = 'O';
            opponent = 'X';
        }
        // Checking for Rows for X or O victory.
        for (int row = 0; row < 3; row++)
        {
            if (b[row][0] == b[row][1] && b[row][1] == b[row][2]) {
                if (b[row][0] == player)
                    return +10;
                else if (b[row][0] == opponent)
                    return -10;
            }
        }

        // Checking for Columns for X or O victory.
        for (int col = 0; col < 3; col++)
        {
            if (b[0][col] == b[1][col] && b[1][col] == b[2][col]) {

                if (b[0][col] == player)
                    return +10;

                else if (b[0][col] == opponent)
                    return -10;
            }
        }

        // Checking for Diagonals for X or O victory.
        if (b[0][0] == b[1][1] && b[1][1] == b[2][2])
        {
            if (b[0][0] == player)
                return +10;
            else if (b[0][0] == opponent)
                return -10;
        }

        if (b[0][2] == b[1][1] && b[1][1] == b[2][0])
        {
            if (b[0][2] == player)
                return +10;
            else if (b[0][2] == opponent)
                return -10;
        }

        // Else if none of them have won then return 0
        return 0;
    }


    int minimax(char field[][], int depth, Boolean isMax, int numPlayer) {
        int score = evaluate(field, numPlayer);
        char player;
        char opponent;
        if (numPlayer == 1) {
            player = 'X';
            opponent = 'O';
        } else {
            player = 'O';
            opponent = 'X';
        }


        // If Maximizer has won the game
        // return his/her evaluated score
        if (score == 10) {
            return score;
        }
        // If Minimizer has won the game
        // return his/her evaluated score
        if (score == -10) {
            return score;
        }
        // If there are no more moves and
        // no winner then it is a tie
        if (!isMovesLeft(field)) {
            return 0;
        }

        // If this maximizer's move
        int best;
        if (isMax) {

            best = -1000;

            // Traverse all cells
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    // Check if cell is empty
                    if (field[i][j] == ' ')
                    {
                        // Make the move
                        field[i][j] = player;

                        // Call minimax recursively and choose
                        int minimax = minimax(field, depth++, !isMax, numPlayer);
                        best = Math.max(best, minimax);

                        // Undo the move
                        field[i][j] = ' ';
                    }
                }
            }
        }

        // If this minimizer's move
        else
        {
            best = 1000;

            // Traverse all cells
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    // Check if cell is empty
                    if (field[i][j] == ' ')
                    {
                        // Make the move
                        field[i][j] = opponent;

                        // Call minimax recursively and choose

                        best = Math.min(best, minimax(field, depth++, !isMax, numPlayer));

                        // Undo the move
                        field[i][j] = ' ';
                    }
                }
            }
        }
        return best;
    }

    int[] findBestMove(char board[][], int numPlayer) {
        int bestVal = -1000;
        int[] bestMove = new int[2];
        char player = numPlayer == 1 ? 'X' : 'O';

        // Traverse all cells, evaluate minimax function
        // for all empty cells. And return the cell
        // with optimal value.
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                // Check if cell is empty
                if (board[i][j] == ' ')
                {
                    // Make the move
                    board[i][j] = player;

                    // compute evaluation function for this
                    // move.
                    int moveVal = minimax(board, 0, false, numPlayer);

                    // Undo the move
                    board[i][j] = ' ';

                    // If the value of the current move is
                    // more than the best value, then update
                    // best/
                    if (moveVal > bestVal)
                    {
                        bestMove[0] = i;
                        bestMove[1] = j;
                        bestVal = moveVal;
                    }
                }
            }
        }

        return bestMove;
    }

    Boolean isMovesLeft(char board[][])
    {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == ' ')
                    return true;
        return false;
    }
}
