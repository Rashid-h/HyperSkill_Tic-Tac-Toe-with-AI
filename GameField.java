package tictactoe;

import java.util.Arrays;

class GameField {
    private final char[][] field;

    GameField() {
        field = new char[3][3];
        CreateField();
    }

    void setField(int x, int y, char cell) {
        field[y][x] = cell;
    }

    char getField(int x, int y) {
        return field[y][x];
    }

    char[][] getField() {
        return field;
    }

    private void CreateField() {
        for (char[] ch : field) {
            Arrays.fill(ch, ' ');
        }
    }

    void ClearField() {
        for (char[] ch : field) {
            Arrays.fill(ch, ' ');
        }
    }

    void ShowField() {
        System.out.println("---------");
        for (char[] chars : field) {
            System.out.print("| ");
            for (char ch : chars) {
                System.out.print(ch + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }
}




