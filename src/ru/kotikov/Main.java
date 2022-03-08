package ru.kotikov;

import java.io.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        GameXO.game();

        ArrayList<Player> rating = new ArrayList<>();
        rating.add(GameXO.FIRST);
        rating.add(GameXO.SECOND);
        File file = new File("rating.txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

