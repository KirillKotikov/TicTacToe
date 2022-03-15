package ru.kotikov;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MainWithStaxWriter {

    public static void main(String[] args) {

        // Запук игры
        {
            System.out.println("Привет! Добро пожаловать в игру крестики-нолики.");
        }

        // Игровой процесс:
        while (!Game.gameOver) {
            int result = 9;
            if (Game.FIRST_PLAYER.getName() == null) {
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
            }

                // Номер файла с историей игры
                int fileNumber = 1;
                // Имя файла с историей игры
                String fileName = "GamesHistory_" + fileNumber + ".xml";
                // Иницилизация файла с историей игры
                File file = new File(fileName);
                // Цикл для определения наличия файла с таким именем и при наличии, создает файл со следующим порядковым номером
                while (file.exists()) {
                    fileNumber++;
                    fileName = "GamesHistory_" + fileNumber + ".xml";
                    file = new File(fileName);
                }
                try {
                    // Создание писателя истории игры
                    XMLStreamWriter writer = XMLOutputFactory.newInstance()
                            .createXMLStreamWriter(new FileOutputStream(fileName), "windows-1251");

                    boolean notFirstRound = false;
                    boolean newRound = true;
                    // Цикл для ходов пока игра не окончена
                    while (!Game.gameOver) {

                        if (newRound) {
                            if (notFirstRound) {
                                while (file.exists()) {
                                    fileNumber++;
                                    fileName = "GamesHistory_" + fileNumber + ".xml";
                                    file = new File(fileName);
                                }
                                writer = XMLOutputFactory.newInstance()
                                        .createXMLStreamWriter(new FileOutputStream(fileName), "windows-1251");
                            }
                            notFirstRound = true;
                            // Вносим параметры файла
                            writer.writeStartDocument("windows-1251", "1.0");
                            writer.writeCharacters("\n");
                            // Создаем корневой элемент <Gameplay>
                            writer.writeStartElement("Gameplay");
                            writer.writeCharacters("\n  ");

                            // добавляем игрока X в историю <Player>
                            writer.writeEmptyElement("Player");
                            // Устанавливаем атрибуты
                            writer.writeAttribute("id", Game.FIRST_PLAYER.getId());
                            writer.writeAttribute("name", Game.FIRST_PLAYER.getName());
                            writer.writeAttribute("symbol", Game.FIRST_PLAYER.getSymbol());
                            writer.writeCharacters("\n  ");
                            // добавляем игрока O в историю <Player>
                            writer.writeEmptyElement("Player");
                            // Устанавливаем атрибуты
                            writer.writeAttribute("id", Game.SECOND_PLAYER.getId());
                            writer.writeAttribute("name", Game.SECOND_PLAYER.getName());
                            writer.writeAttribute("symbol", Game.SECOND_PLAYER.getSymbol());
                            writer.writeCharacters("\n  ");

                            // Добавляем ход игры <Game> в историю
                            writer.writeStartElement("Game");
                            writer.writeCharacters("\n    ");

                            // Отображение игрового поля перед началом новой игры
                            System.out.println("Вот как выглядит игровое поле:");
                            Game.showPlayingField();
                            newRound = false;
                        }
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
                        // флаг - занята ли ячейка поля
                        boolean occupied = false;
                        // Проверка на уже заполненное поле
                        if (Game.occupiedField(x, y)
                        ) {
                            if ((Game.movesCounter % 2 == 1)) {
                                Game.PLAYING_FIELD[x][y] = "|x|";
                                // добавление хода игрока в историю <Step>
                                writer.writeStartElement("Step");
                                writer.writeAttribute("num", "" + Game.movesCounter);
                                writer.writeAttribute("playerId", "1");
                                writer.writeCharacters(coordinateFromPlayer);
                                writer.writeEndElement();
                            } else {
                                Game.PLAYING_FIELD[x][y] = "|o|";
                                // добавление хода игрока в историю <Step
                                writer.writeStartElement("Step");
                                writer.writeAttribute("num", "" + Game.movesCounter);
                                writer.writeAttribute("playerId", "2");
                                writer.writeCharacters(coordinateFromPlayer);
                                writer.writeEndElement();
                            }
                            // Инкрементация счетчика ходов
                            Game.movesCounter++;
                        } else {
                            System.out.println("Здесь уже занято:)");
                            occupied = true;
                        }
                        // Проверка на победителя или ничью
                        if (Game.movesCounter > 5 && Game.movesCounter < 10) {
                            Game.winnerSearch(Game.PLAYING_FIELD);
                        } else if (Game.movesCounter > 9) {
                            Game.gameOver = true;
                        }
                        Game.showPlayingField();
                        if (!occupied && Game.gameOver)
                            writer.writeCharacters("\n  ");
                        else writer.writeCharacters("\n    ");

                        if (Game.gameOver) {
                            result = Game.results(Game.movesCounter, Game.PLAYING_FIELD);
                            writer.writeEndElement();
                            writer.writeCharacters("\n  ");
                            // создаем <GameResult> в истории
                            writer.writeStartElement("GameResult");
                            if (result == 1) {
                                writer.writeStartElement("Player");
                                writer.writeAttribute("id", Game.FIRST_PLAYER.getId());
                                writer.writeAttribute("name", Game.FIRST_PLAYER.getName());
                                writer.writeAttribute("symbol", Game.FIRST_PLAYER.getSymbol());
                                writer.writeEndElement();
                            } else if (result == 2) {
                                writer.writeStartElement("Player");
                                writer.writeAttribute("id", Game.SECOND_PLAYER.getId());
                                writer.writeAttribute("name", Game.SECOND_PLAYER.getName());
                                writer.writeAttribute("symbol", Game.SECOND_PLAYER.getSymbol());
                                writer.writeEndElement();
                            } else if (result == 0) writer.writeCharacters("Draw!");
                            writer.writeEndElement();
                            writer.writeCharacters("\n");
                            writer.writeEndDocument();

                            writer.flush();
                            writer.close();

                            Game.refreshPlayingField(Game.PLAYING_FIELD);
                            Game.movesCounter = 1;
                            newRound = true;
                        }
                    }

                } catch (XMLStreamException | FileNotFoundException e) {
                    e.printStackTrace();
                }


            // Создаем список и добавляем туда игроков прошедшей игры
            ArrayList<Player> rating = new ArrayList<>();
            rating.add(Game.FIRST_PLAYER);
            rating.add(Game.SECOND_PLAYER);

            // Создаем, если не существует, или подключаем файл "rating.txt" для записи рейтинга
            File fileRating = new File("rating.txt");
            try {
                fileRating.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        /* Создаем поток для чтения из файла информации об игроках из предыдущих игр
           и сохраняем игроков в вышесозданный список */
            try (BufferedReader reader = new BufferedReader(new FileReader(
                    "rating.txt"))) {
                // счетчик для пропуска первых двух строк
                int count = 0;
                //
                while (reader.ready()) {
                    String playerIn = reader.readLine();
                    if (count < 2) {
                        count++;
                        continue;
                    }
                    String[] parameters = playerIn.split(" ");
                    Player forAdding = new Player();
                    forAdding.setName(parameters[1]);
                    forAdding.setNumberOfWins(Integer.parseInt(parameters[2]));
                    rating.add(forAdding);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        /* Создаем поток для записи в файл с рейтингом и записываем туда отсортированный по количеству побед список
        игроков.
        */
            try (
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(
                            "rating.txt", false))
            ) {

                rating.sort((o1, o2) -> o2.getNumberOfWins() - o1.getNumberOfWins());
                bufferedWriter.write("№ Имя Количество побед:\n\n");
                int number = 1;
                for (Player playerOut : rating) {
                    bufferedWriter.write(number + " " + playerOut.getName() + " " + playerOut.getNumberOfWins());
                    bufferedWriter.newLine();
                    number++;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}

