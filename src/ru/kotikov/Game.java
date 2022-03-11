package ru.kotikov;

import java.util.Objects;
import java.util.Scanner;

public class Game {

    static final String[][] PLAYING_FIELD = new String[][]{
            {"|1|", "|2|", "|3|"},
            {"|4|", "|5|", "|6|"},
            {"|7|", "|8|", "|9|"}
    };
    private static byte count = 1;
    private static boolean gameOver = false;
    public static final Player FIRST_PLAYER = new Player();
    public static final Player SECOND_PLAYER = new Player();

    // Основной игровой процесс
    public static void game() {

        {
            System.out.println("Привет! Добро пожаловать в игру крестики-нолики.");
        }
        // Игровой процесс:
        while (!gameOver) {
            if (FIRST_PLAYER.getName() == null) {
                Scanner namesScanner = new Scanner(System.in);
                System.out.println("Первый игрок (ходит крестиками), введи своё имя:");
                FIRST_PLAYER.setName(namesScanner.nextLine());
                System.out.println("Второй игрок (ходит ноликами), введи своё имя:");
                SECOND_PLAYER.setName(namesScanner.nextLine());
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
            System.out.println(FIRST_PLAYER.getName() + " (крестики), твой ход! Введи координаты хода (число от 1 до 9 включительно): ");
        } else {
            System.out.println(SECOND_PLAYER.getName() + " (нолики), твой ход! Введи координаты хода (число от 1 до 9 включительно): ");
        }
        Scanner scannerMove = new Scanner(System.in);

        String coordinates;
        coordinates = scannerMove.next();

        if (coordinates.length() != 1) {
            System.out.println("ай-ай:) ввел какую-то ерунду! Введи нормальные координаты (число от 1 до 9 включительно)");
            return;
        }

        char coordinate = coordinates.charAt(0);

        int x, y;
        switch (coordinate) {
            case '1' -> {
                x = 0;
                y = 0;
            }
            case '2' -> {
                x = 0;
                y = 1;
            }
            case '3' -> {
                x = 0;
                y = 2;
            }
            case '4' -> {
                x = 1;
                y = 0;
            }
            case '5' -> {
                x = 1;
                y = 1;
            }
            case '6' -> {
                x = 1;
                y = 2;
            }
            case '7' -> {
                x = 2;
                y = 0;
            }
            case '8' -> {
                x = 2;
                y = 1;
            }
            case '9' -> {
                x = 2;
                y = 2;
            }
            default -> {
                System.out.println("ай-ай:) ввел какую-то ерунду! Введи нормальные координаты (число от 1 до 9 включительно)");
                return;
            }
        }

        if (Objects.equals(playingField[x][y], "|1|") || Objects.equals(playingField[x][y], "|2|") || Objects.equals(playingField[x][y], "|3|") ||
                Objects.equals(playingField[x][y], "|4|") || Objects.equals(playingField[x][y], "|5|") || Objects.equals(playingField[x][y], "|6|") ||
                Objects.equals(playingField[x][y], "|7|") || Objects.equals(playingField[x][y], "|8|") || Objects.equals(playingField[x][y], "|9|")
        ) {
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
                && (!playingField[0][0].equals("|\\d{1-9}|"));

        if ((playingField[1][0].equals(playingField[1][1]))
                && (playingField[1][1].equals(playingField[1][2]))
                && (!playingField[1][1].equals("|\\d{1-9}|"))) {
            gameOver = true;
        }
        if ((playingField[2][0].equals(playingField[2][1]))
                && (playingField[2][1].equals(playingField[2][2]))
                && (!playingField[2][2].equals("|\\d{1-9}|"))) {
            gameOver = true;
        }

        if ((playingField[0][0].equals(playingField[1][0]))
                && (playingField[1][0].equals(playingField[2][0]))
                && (!playingField[0][0].equals("|\\d{1-9}|"))) {
            gameOver = true;
        }
        if ((playingField[0][1].equals(playingField[1][1]))
                && (playingField[1][1].equals(playingField[2][1]))
                && (!playingField[1][1].equals("|\\d{1-9}|"))) {
            gameOver = true;
        }
        if ((playingField[0][2].equals(playingField[1][2]))
                && (playingField[1][2].equals(playingField[2][2]))
                && (!playingField[2][2].equals("|\\d{1-9}|"))) {
            gameOver = true;
        }

        if ((playingField[0][0].equals(playingField[1][1]))
                && (playingField[1][1].equals(playingField[2][2]))
                && (!playingField[2][2].equals("|\\d{1-9}|"))) {
            gameOver = true;
        }
        if ((playingField[0][2].equals(playingField[1][1]))
                && (playingField[1][1].equals(playingField[2][0]))
                && (!playingField[2][0].equals("|\\d{1-9}|"))) {
            gameOver = true;
        }
    }


    // Подведение результатов игры
    public static void results(byte numbersOfMoves) {
        Scanner scanner = new Scanner(System.in);
        if (gameOver) {
            if (count > 9) {
                System.out.println("Ничья! Не удивительно %)");
                FIRST_PLAYER.setNumberOfDraws(FIRST_PLAYER.getNumberOfDraws() + 1);
                SECOND_PLAYER.setNumberOfDraws(SECOND_PLAYER.getNumberOfDraws() + 1);
            } else if (numbersOfMoves % 2 == 1) {
                System.out.println(SECOND_PLAYER.getName() + " (нолик) победил(-а)! :)");
                SECOND_PLAYER.setNumberOfWins(SECOND_PLAYER.getNumberOfWins() + 1);
                FIRST_PLAYER.setNumberOfLoses(FIRST_PLAYER.getNumberOfLoses() + 1);
            } else {
                System.out.println(FIRST_PLAYER.getName() + " (крестик) победил(-а)! :)");
                FIRST_PLAYER.setNumberOfWins(FIRST_PLAYER.getNumberOfWins() + 1);
                SECOND_PLAYER.setNumberOfLoses(SECOND_PLAYER.getNumberOfLoses() + 1);
            }
        }
        System.out.println("Игра окончена! Общий счёт: \n" + "У игрока " + FIRST_PLAYER.getName() + " " + FIRST_PLAYER.getNumberOfWins() +
                " побед, " + FIRST_PLAYER.getNumberOfLoses() + " поражений, " + FIRST_PLAYER.getNumberOfDraws() + " игр сыграно в ничью; \n" +
                "У игрока " + SECOND_PLAYER.getName() + " " + SECOND_PLAYER.getNumberOfWins() + " побед, " + SECOND_PLAYER.getNumberOfLoses() + " поражений, " +
                SECOND_PLAYER.getNumberOfDraws() + " игр сыграно в ничью.");
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

        int fieldCount = 1;
        for (int i = 0; i < playingField.length; i++) {
            for (int j = 0; j < playingField.length; j++) {
                playingField[i][j] = "|" + fieldCount + "|";
                fieldCount++;
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

