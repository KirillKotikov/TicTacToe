package ru.kotikov;

import java.util.Objects;
import java.util.Scanner;

public class Game {

    // Массив игрового поля
    static final String[][] PLAYING_FIELD = new String[][]{
            {"|1|", "|2|", "|3|"},
            {"|4|", "|5|", "|6|"},
            {"|7|", "|8|", "|9|"}
    };
    // Счетчик ходов
    static byte movesCounter = 1;
    // Определяет игра продолжается или окончена (есть победитель или ничья)
    static boolean gameOver = false;
    //Первый игрок - х
    public static final Player FIRST_PLAYER = new Player("1", "x");
    //Второй игрок - о
    public static final Player SECOND_PLAYER = new Player("2", "o");

    // Игровой процесс
    public static void game() {
        // Приветствие
        System.out.println("Привет! Добро пожаловать в игру крестики-нолики:)");

        // Игра идет пока не найден победитель или нет ничьи
        while (!gameOver) {

            // При начальном запуске игры вводятся имена игроков
            if (FIRST_PLAYER.getName() == null) {
                // Сканнер для считывания имен из консоли
                Scanner namesScanner = new Scanner(System.in);
                String name;
                // Ввод и проверка имени первого игрока
                while (true) {
                    System.out.println("Первый игрок (ходит крестиками), введи своё имя:");
                    name = namesScanner.nextLine();
                    if (name.trim().isEmpty()) {
                        System.out.println("Ты ввёл пустую строку:(");
                    } else {
                        Game.FIRST_PLAYER.setName(name);
                        break;
                    }
                }
                // Ввод и проверка имени второго игрока
                while (true) {
                    System.out.println("Второй игрок (ходит ноликами), введи своё имя:");
                    name = namesScanner.nextLine();
                    if (name.trim().isEmpty()) {
                        System.out.println("Ты ввёл пустую строку:(");
                    } else {
                        Game.SECOND_PLAYER.setName(name);
                        break;
                    }
                }
                System.out.println("Вот как выглядит игровое поле:");
                Game.showPlayingField();
            }

            // Цикл для ходов пока игра не окончена
            while (!gameOver) {
                // Оповещение об очередности хода
                if (Game.movesCounter % 2 == 1) {
                    System.out.println(Game.FIRST_PLAYER.getName() + " (крестики), твой ход! Введи координаты хода (число от 1 до 9 включительно): ");
                } else {
                    System.out.println(Game.SECOND_PLAYER.getName() + " (нолики), твой ход! Введи координаты хода (число от 1 до 9 включительно): ");
                }
                // Считывает ход игрока
                Scanner scannerMove = new Scanner(System.in);
                // Сохраняем введенные координаты
                String coordinateFromPlayer = scannerMove.next();
                // Проверка координат на длину
                if (coordinateFromPlayer.length() != 1) {
                    System.out.println("ай-ай:) ввел какую-то ерунду! Введи нормальные координаты (число от 1 до 9 включительно)");
                    continue;
                }
                // Переменные для координат хода на поле
                int x, y;
                // Проверка введенных координат на соответствие возможным
                switch (coordinateFromPlayer) {
                    case "1" -> {
                        x = 0;
                        y = 0;
                    }
                    case "2" -> {
                        x = 0;
                        y = 1;
                    }
                    case "3" -> {
                        x = 0;
                        y = 2;
                    }
                    case "4" -> {
                        x = 1;
                        y = 0;
                    }
                    case "5" -> {
                        x = 1;
                        y = 1;
                    }
                    case "6" -> {
                        x = 1;
                        y = 2;
                    }
                    case "7" -> {
                        x = 2;
                        y = 0;
                    }
                    case "8" -> {
                        x = 2;
                        y = 1;
                    }
                    case "9" -> {
                        x = 2;
                        y = 2;
                    }
                    default -> {
                        System.out.println("ай-ай:) ввел какую-то ерунду! Введи нормальные координаты (число от 1 до 9 включительно)");
                        continue;
                    }
                }
                // Проверка на уже заполненное поле
                if (occupiedField(x, y)
                ) {
                    // В зависимости от счетчика ходов, определяется каким символом заполнять свободное поле
                    if ((Game.movesCounter % 2 == 1)) {
                        Game.PLAYING_FIELD[x][y] = "|x|";
                    } else {
                        Game.PLAYING_FIELD[x][y] = "|o|";

                    }
                    // Инкрементация счетчика ходов
                    Game.movesCounter++;
                } else {
                    System.out.println("Здесь уже занято:)");
                }
                // Проверка на победителя или ничью
                if (movesCounter > 5 && movesCounter < 10) {
                    Game.winnerSearch(PLAYING_FIELD);
                } else if (movesCounter > 9) gameOver = true;
                // отображает вид игрового поля в насто]щий момент
                Game.showPlayingField();
                //При завершении раунда выводится результат и спрашивает о новом раунде
                if (gameOver) {
                    results(movesCounter, PLAYING_FIELD);
                    Game.refreshPlayingField(Game.PLAYING_FIELD);
                    Game.movesCounter = 1;
                }
            }

        }

    }

    // Поиск возможного победителя
    public static boolean winnerSearch(String[][] playingField) {
        gameOver = (playingField[0][0].equals(playingField[0][1]))
                && (playingField[0][1].equals(playingField[0][2]));

        if ((playingField[1][0].equals(playingField[1][1]))
                && (playingField[1][1].equals(playingField[1][2]))) {
            gameOver = true;
        }
        if ((playingField[2][0].equals(playingField[2][1]))
                && (playingField[2][1].equals(playingField[2][2]))) {
            gameOver = true;
        }

        if ((playingField[0][0].equals(playingField[1][0]))
                && (playingField[1][0].equals(playingField[2][0]))) {
            gameOver = true;
        }
        if ((playingField[0][1].equals(playingField[1][1]))
                && (playingField[1][1].equals(playingField[2][1]))) {
            gameOver = true;
        }
        if ((playingField[0][2].equals(playingField[1][2]))
                && (playingField[1][2].equals(playingField[2][2]))) {
            gameOver = true;
        }

        if ((playingField[0][0].equals(playingField[1][1]))
                && (playingField[1][1].equals(playingField[2][2]))) {
            gameOver = true;
        }
        if ((playingField[0][2].equals(playingField[1][1]))
                && (playingField[1][1].equals(playingField[2][0]))) {
            gameOver = true;
        }
        return gameOver;
    }


    // Подведение результатов игры. Возвращает 0 - ничья, 1 - выиграл первый игрок, 2 - выиграл второй игрок.
    public static int results(byte numbersOfMoves, String [][] playingField) {
        boolean draw = !winnerSearch(playingField);
        int result = 0;
        Scanner scanner = new Scanner(System.in);
        if (gameOver) {
            if (movesCounter > 9 && draw) {
                System.out.println("Ничья! Не удивительно %)");
                FIRST_PLAYER.setNumberOfDraws(FIRST_PLAYER.getNumberOfDraws() + 1);
                SECOND_PLAYER.setNumberOfDraws(SECOND_PLAYER.getNumberOfDraws() + 1);
            } else if (numbersOfMoves % 2 == 1) {
                result = 2;
                System.out.println(SECOND_PLAYER.getName() + " (нолик) победил(-а)! :)");
                SECOND_PLAYER.setNumberOfWins(SECOND_PLAYER.getNumberOfWins() + 1);
                FIRST_PLAYER.setNumberOfLoses(FIRST_PLAYER.getNumberOfLoses() + 1);
            } else {
                result = 1;
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
        // Определение начала нового раунда или окончания игры.
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
        return result;
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
                System.out.print(charsY + " ");
            }
            System.out.println();
        }
    }

    // Возвращает массив координат x, y игрового поля в зависимости от введенной координаты игроком
    public static int[] fieldCoordinates(String coordinate) {
        int[] coordinates = new int[2];

        switch (coordinate) {
            case "1" -> {
            }
            case "2" -> coordinates[1] = 1;
            case "3" -> coordinates[1] = 2;
            case "4" -> coordinates[0] = 1;
            case "5" -> {
                coordinates[0] = 1;
                coordinates[1] = 1;
            }
            case "6" -> {
                coordinates[0] = 1;
                coordinates[1] = 2;
            }
            case "7" -> coordinates[0] = 2;
            case "8" -> {
                coordinates[0] = 2;
                coordinates[1] = 1;
            }
            case "9" -> {
                coordinates[0] = 2;
                coordinates[1] = 2;
            }
        }
        return coordinates;
    }

    public static boolean occupiedField(int x, int y) {
        return Objects.equals(Game.PLAYING_FIELD[x][y], "|1|") || Objects.equals(Game.PLAYING_FIELD[x][y], "|2|")
                || Objects.equals(Game.PLAYING_FIELD[x][y], "|3|") || Objects.equals(Game.PLAYING_FIELD[x][y], "|4|")
                || Objects.equals(Game.PLAYING_FIELD[x][y], "|5|") || Objects.equals(Game.PLAYING_FIELD[x][y], "|6|")
                || Objects.equals(Game.PLAYING_FIELD[x][y], "|7|") || Objects.equals(Game.PLAYING_FIELD[x][y], "|8|")
                || Objects.equals(Game.PLAYING_FIELD[x][y], "|9|");
    }
}

