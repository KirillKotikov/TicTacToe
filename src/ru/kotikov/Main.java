package ru.kotikov;

import java.io.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        GameXO.game();

        ArrayList<Player> rating = new ArrayList<>();
        rating.add(GameXO.FIRST);
        rating.add(GameXO.SECOND);
        try (BufferedReader reader = new BufferedReader(new FileReader(
                "/Users/kirillkotikov/IdeaProjects/XO/src/ru/kotikov/rating.txt"))) {
            while (reader.ready()) {
                String playerIn = reader.readLine();
                String[] parametrs = playerIn.split(" ");
                Player forAdding = new Player();
                forAdding.setName(parametrs[2]);
                forAdding.setNumberOfWins(Integer.parseInt(parametrs[4]));
                forAdding.setNumberOfLoses(Integer.parseInt(parametrs[6]));
                rating.add(forAdding);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (
                BufferedWriter writer = new BufferedWriter(new FileWriter(
                        "/Users/kirillkotikov/IdeaProjects/XO/src/ru/kotikov/rating.txt", false))
        ) {

            rating.sort((o1, o2) -> o2.getNumberOfWins() - o1.getNumberOfWins());

            for (Player playerOut : rating) {
                writer.write("Имя игрока: " + playerOut.getName() + " побед " + playerOut.getNumberOfWins() +
                        " поражений " + playerOut.getNumberOfLoses());
                writer.newLine();
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}

