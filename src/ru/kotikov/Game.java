package ru.kotikov;

import java.util.Objects;
import java.util.Scanner;

public class Game {

    static final String[][] PLAYING_FIELD = new String[][]{
            {"|-|", "|-|", "|-|"},
            {"|-|", "|-|", "|-|"},
            {"|-|", "|-|", "|-|"}
    };
    private static byte count = 1;
    private static boolean gameOver = false;
    public static final Player FIRST = new Player();
    public static final Player SECOND = new Player();

    // Основной игровой процесс
    public static void game() {

        {
            System.out.println("Привет! Добро пожаловать в игру крестики-нолики.");
        }
        // Игровой процесс:
        while (!gameOver) {
            if (FIRST.getName() == null) {
                Scanner namesScanner = new Scanner(System.in);
                System.out.println("Первый игрок (ходит крестиками), введи своё имя:");
                FIRST.setName(namesScanner.nextLine());
                System.out.println("Второй игрок (ходит ноликами), введи своё имя:");
                SECOND.setName(namesScanner.nextLine());
                System.out.println("Вот как выглядит игровое поле:");
                showPlayingField();
            }

            while (!gameOver) {
                move(PLAYING_FIELD);
                if (count > 9) {
                    gameOver = true;
                }
                showPlayingField();
            }
            Game.results(count);
            Game.refreshPlayingField(PLAYING_FIELD);
            count = 1;
        }
    }

    // Ход игрока
    public static void move(String[][] playingField) {

        if (count % 2 == 1) {
            System.out.println(FIRST.getName() + " (крестики), твой ход! Введи координаты хода (например: \"21\", где 2-номер по вертикали, " +
                    "1-номер по горизонталли): ");
        } else {
            System.out.println(SECOND.getName() + " (нолики), твой ход! Введи координаты хода (например: \"33\", где 3-номер по вертикали, " +
                    "3-номер по горизонталли): ");
        }
        Scanner scannerMove = new Scanner(System.in);

        String coordinates;
        coordinates = scannerMove.nextLine();

        if (coordinates.length() != 2) {
            System.out.println("ай-ай:) ввел какую-то ерунду! Введи нормальные координаты (например - 13)");
            return;
        }

        char coordinateX, coordinateY;
        coordinateX = coordinates.charAt(0);
        coordinateY = coordinates.charAt(1);

        int x, y;
        switch (coordinateX) {
            case '1' -> x = 0;
            case '2' -> x = 1;
            case '3' -> x = 2;
            default -> {
                System.out.println("ай-ай:) ввел какую-то ерунду! Введи нормальные координаты (например - 32)");
                return;
            }
        }
        switch (coordinateY) {
            case '1' -> y = 0;
            case '2' -> y = 1;
            case '3' -> y = 2;
            default -> {
                System.out.println("ай-ай:) ввел какую-то ерунду! Введи нормальные координаты (например - 33)");
                return;
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
    }

    // Поиск возможного победителя
    public static void winnerSearch(String[][] playingField) {
        gameOver = (playingField[0][0].equals(playingField[0][1]))
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
    }


    // Подведение результатов игры
    public static void results(byte numbersOfMoves) {
        Scanner scanner = new Scanner(System.in);
        if (gameOver) {
            if (count > 9) System.out.println("Ничья! Не удивительно %)");
            else if (numbersOfMoves % 2 == 1) {
                System.out.println(SECOND.getName() + " (нолик) победил(-а)! А " + FIRST.getName() +
                        " проиграл(-а) :)");
                SECOND.setNumberOfWins(SECOND.getNumberOfWins() + 1);
                FIRST.setNumberOfLoses(FIRST.getNumberOfLoses() + 1);
            } else {
                System.out.println(FIRST.getName() + " (крестик) победил(-а)! А " + SECOND.getName() +
                        " проиграл(-а) :)");
                FIRST.setNumberOfWins(FIRST.getNumberOfWins() + 1);
                SECOND.setNumberOfLoses(SECOND.getNumberOfLoses() + 1);
            }
        }
        System.out.println("Игра окончена! Общий счёт: \n" + "У игрока " + FIRST.getName() + " " + FIRST.getNumberOfWins() +
                " побед и " + FIRST.getNumberOfLoses() + " поражений; \n" + "У игрока " + SECOND.getName() + " " +
                SECOND.getNumberOfWins() + " побед и " + SECOND.getNumberOfLoses() + " поражений");
        System.out.println("Хотите сыграть ещё разок?;) Введи на клавиатуре ответ \"Да\" или \"Нет\":");

        while (true) {
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase("да")) {
                Game.gameOver = false;
                break;
            } else if (answer.equalsIgnoreCase("нет")) {
                System.out.println("Спасибо за игру! Заходите поиграть ещё:)");
                break;
            } else System.out.println("Вы ввели неверный ответ! Введите \"да\" или \"нет\"");
        }
    }

    // Обновление игрового поля (очистка)
    public static void refreshPlayingField(String[][] playingField) {

        for (int i = 0; i < playingField.length; i++) {
            for (int j = 0; j < playingField.length; j++) {
                playingField[i][j] = "|-|";
            }
        }
    }

    // Отображение игрового поля в текущем состоянии
    public static void showPlayingField() {
        for (String[] charsX : PLAYING_FIELD) {
            for (String charsY : charsX) {
                if (count > 5 && count < 10) {
                    Game.winnerSearch(PLAYING_FIELD);
                } else if (count > 9) gameOver = true;
                System.out.print(charsY + " ");
            }
            System.out.println();
        }
    }
}

