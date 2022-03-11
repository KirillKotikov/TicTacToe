package ru.kotikov;

import java.io.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        // Запук игры
        Game.game();

        // Создаем список и добавляем туда игроков прошедшей игры
        ArrayList<Player> rating = new ArrayList<>();
        rating.add(Game.FIRST);
        rating.add(Game.SECOND);

        // Создаем, если не существует, или подключаем файл "rating.txt" для записи рейтинга
        File file = new File("rating.txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /* Создаем поток для чтения из файла информации об игроках из предыдущих игр
           и сохраняем игроков в вышесозданный список */
        try (BufferedReader reader = new BufferedReader(new FileReader(
                "rating.txt"))) {
            int count = 0;
            while (reader.ready()) {
                String playerIn = reader.readLine();
                if (count < 2) {
                    count++;
                    continue;
                }
                String[] parametrs = playerIn.split(" ");
                Player forAdding = new Player();
                forAdding.setName(parametrs[1]);
                forAdding.setNumberOfWins(Integer.parseInt(parametrs[2]));
                rating.add(forAdding);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /* Создаем поток для записи в файл с рейтингом и записываем туда отсортированный по количеству побед список
        игроков.
        */
        try (
                BufferedWriter writer = new BufferedWriter(new FileWriter(
                        "rating.txt", false))
        ) {

            rating.sort((o1, o2) -> o2.getNumberOfWins() - o1.getNumberOfWins());
            writer.write("№ Имя Количество побед:\n\n");
            int number = 1;
            for (Player playerOut : rating) {
                writer.write(number + " " + playerOut.getName() + " " + playerOut.getNumberOfWins());
                writer.newLine();
                number++;
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}

