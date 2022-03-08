package ru.kotikov;

import java.util.Objects;
import java.util.Scanner;

public class GameXO {

    final String[][] PLAYING_FIELD = new String[][]{
            {"|-|", "|-|", "|-|"},
            {"|-|", "|-|", "|-|"},
            {"|-|", "|-|", "|-|"}
    };
    String coordinates;
    char coordinateX, coordinateY;
    byte count = 1;
    static boolean gameOver = false;
    public static final Player FIRST = new Player();
    public static final Player SECOND = new Player();


    public byte getCount() {
        return count;
    }

    public void setCount(byte count) {
        this.count = count;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public static void game() {

        GameXO game = new GameXO();
        boolean gameOn = true;

        while (gameOn) {
            if (FIRST.getName() == null) {
                Scanner namesScanner = new Scanner(System.in);
                System.out.println("Первый игрок (ходит крестиками), введи своё имя!");
                FIRST.setName(namesScanner.nextLine());
                System.out.println("Второй игрок (ходит ноликами), введи своё имя!");
                SECOND.setName(namesScanner.nextLine());
            }

            while (!game.getGameOver()) {
                game.getPlayingField(game.PLAYING_FIELD);
                if (game.getCount() > 9) {
                    game.setGameOver(true);
                }

                for (String[] charsX : game.PLAYING_FIELD) {
                    for (String charsY : charsX) {
                        if (game.getCount() > 5) {
                            game.setGameOver(GameXO.winnerSearch(game.PLAYING_FIELD));
                        }
                        System.out.print(charsY + " ");
                    }
                    System.out.println();
                }

            }
            gameOn = GameXO.results(game.getGameOver(), game.getCount());
            GameXO.refreshPlayingFied(game.PLAYING_FIELD);
            game.setCount((byte) 1);
        }
    }

    public String[][] getPlayingField(String playingField[][]) {

        if (count % 2 == 1) {
            System.out.println(FIRST.getName() + " (крестики), твой ход! Введи координаты хода (варианты: а1-3, б1-3, в1-3. Пример - b2): ");
        } else {
            System.out.println(SECOND.getName() + " (нолики), твой ход! Введи координаты хода (варианты: а1-3, б1-3, в1-3. Пример - b2): ");
        }
        Scanner scannerMove = new Scanner(System.in);


        coordinates = scannerMove.nextLine();

        if (coordinates.length() != 2) {
            System.out.println("ай-ай:) ввел какую-то ерунду! Введи нормальные координаты (например - a2)");
            return playingField;
        }

        coordinateX = coordinates.charAt(0);
        coordinateY = coordinates.charAt(1);

        int x, y;
        switch (coordinateX) {
            case 'а' -> x = 0;
            case 'б' -> x = 1;
            case 'в' -> x = 2;
            default -> {
                System.out.println("ай-ай:) ввел какую-то ерунду! Введи нормальные координаты (например - в2)");
                return playingField;
            }
        }
        switch (coordinateY) {
            case '1' -> y = 0;
            case '2' -> y = 1;
            case '3' -> y = 2;
            default -> {
                System.out.println("ай-ай:) ввел какую-то ерунду! Введи нормальные координаты (например - в2)");
                return playingField;
            }
        }


        if (Objects.equals(playingField[x][y], "|-|")) {
            if ((count % 2 == 1)) {
                playingField[x][y] = "|X|";
            } else {
                playingField[x][y] = "|O|";
            }
            count++;
        } else {
            System.out.println("Здесь уже занято:)");
        }
        return playingField;
    }

    public static boolean winnerSearch(String[][] playingField) {
        boolean gameOver = (playingField[0][0].equals(playingField[0][1]))
                && (playingField[0][1].equals(playingField[0][2]))
                && (!playingField[0][0].equals("|-|"));

        if ((playingField[1][0].equals(playingField[1][1]))
                && (playingField[1][1].equals(playingField[1][2]))
                && (!playingField[1][1].equals("|-|"))) {
            gameOver = true;
        }
        if ((playingField[2][0].equals(playingField[2][1]))
                && (playingField[2][1].equals(playingField[2][2]))
                && (!playingField[2][2].equals("|-|"))) {
            gameOver = true;
        }

        if ((playingField[0][0].equals(playingField[1][0]))
                && (playingField[1][0].equals(playingField[2][0]))
                && (!playingField[0][0].equals("|-|"))) {
            gameOver = true;
        }
        if ((playingField[0][1].equals(playingField[1][1]))
                && (playingField[1][1].equals(playingField[2][1]))
                && (!playingField[1][1].equals("|-|"))) {
            gameOver = true;
        }
        if ((playingField[0][2].equals(playingField[1][2]))
                && (playingField[1][2].equals(playingField[2][2]))
                && (!playingField[2][2].equals("|-|"))) {
            gameOver = true;
        }

        if ((playingField[0][0].equals(playingField[1][1]))
                && (playingField[1][1].equals(playingField[2][2]))
                && (!playingField[2][2].equals("|-|"))) {
            gameOver = true;
        }
        if ((playingField[0][2].equals(playingField[1][1]))
                && (playingField[1][1].equals(playingField[2][0]))
                && (!playingField[2][0].equals("|-|"))) {
            gameOver = true;
        }
        return gameOver;
    }

    public static boolean results(boolean gameOver, byte numbersOfMoves) {
        Scanner scanner = new Scanner(System.in);
        if (gameOver) {
            if (numbersOfMoves % 2 == 1) {
                System.out.println(SECOND.getName() + " (нолик) победил(-а)!:) А " + FIRST.getName() +
                        " проиграл(-а) :Д");
                SECOND.setNumberOfWins(SECOND.getNumberOfWins() + 1);
                FIRST.setNumberOfLoses(FIRST.getNumberOfLoses() + 1);
            } else {
                System.out.println(FIRST.getName() + " (крестик) победил(-а)!:) А " + SECOND.getName() +
                        " проиграл(-а) :Д");
                FIRST.setNumberOfWins(FIRST.getNumberOfWins() + 1);
                SECOND.setNumberOfLoses(SECOND.getNumberOfLoses() + 1);
            }
        } else {
            System.out.println("Ничья! Не удивительно %)");
        }
        System.out.println("Игра окончена! Общий счёт: \n" + "У игрока " + FIRST.getName() + " " + FIRST.getNumberOfWins() +
                " побед и " + FIRST.getNumberOfLoses() + " поражений; \n" + "У игрока " + SECOND.getName() + " " +
                SECOND.getNumberOfWins() + " побед и " + SECOND.getNumberOfLoses() + " поражений");
        System.out.println("Хотите сыграть ещё разок?;) Введи на клавиатуре ответ Да или Нет");

        boolean validAnswer = false;
        while (!validAnswer) {
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase("да")) {
                validAnswer = true;
                GameXO.gameOver = false;
                return true;
            } else if (answer.equalsIgnoreCase("нет")) {
                validAnswer = true;
                System.out.println("Спасибо за игру! Заходите поиграть ещё:)");
                return false;
            } else System.out.println("Вы ввели неверный ответ! Введите \"да\" или \"нет\"");
        }
        return false;
    }

    public static String[][] refreshPlayingFied (String [][] playingField) {

        for (int i = 0; i < playingField.length; i++) {
            for (int j = 0; j < playingField.length; j++) {
                playingField [i] [j] = "|-|";
            }
        }
        return playingField;
    }
}

