package ru.kotikov;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        // Запук игры
        Game.game();

        // Создаем список и добавляем туда игроков прошедшей игры
        ArrayList<Player> rating = new ArrayList<>();
        rating.add(Game.FIRST_PLAYER);
        rating.add(Game.SECOND_PLAYER);

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
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // функция чтения истории игр
        try {
            // Создается построитель документа
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            // Создается дерево DOM документа из файла
            Document document = documentBuilder.parse("GamesHistory.xml");

            // Получаем корневой элемент
            Node root = document.getDocumentElement();

            System.out.println("Список сыгранных игр по ходам:");
            System.out.println();
            // Просматриваем все подэлементы корневого - т.е. игры
            NodeList games = root.getChildNodes();
            for (int i = 0; i < games.getLength(); i++) {
                Node game = games.item(i);
                // Если нода не текст, то это игра - заходим внутрь
                if (game.getNodeType() != Node.TEXT_NODE) {
                    NodeList gameProps = game.getChildNodes();
                    for(int j = 0; j < gameProps.getLength(); j++) {
                        Node gameProp = gameProps.item(j);
                        // Если нода не текст, то это один из параметров игры - печатаем
                        if (gameProp.getNodeType() != Node.TEXT_NODE) {
                            System.out.println(gameProp.getNodeName() + ":" + gameProp.getChildNodes().item(0).getTextContent());
                        }
                    }
                    System.out.println("===========>>>>");
                }
            }

        } catch (
                ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace(System.out);
        }


        // добавление новой истории игры
        try {
            // Создается построитель документа
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            // Создается дерево DOM документа из файла
            Document document = documentBuilder.parse("BookCatalog.xml");

            // Вызываем метод для добавления новой истории игры
            addGameplay(document);

        } catch (ParserConfigurationException ex) {
            ex.printStackTrace(System.out);
        } catch (SAXException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

    // Функция добавления новой истории игры и записи в файл
    private static void addGameplay(Document document) throws TransformerFactoryConfigurationError, DOMException {
        // Получаем корневой элемент
        Node root = document.getDocumentElement();

        // Создаем новую книгу по элементам
        // Сама игра <Gameplay>
        Element gameplay = document.createElement("Gameplay");
        // <Player>
        Element player = document.createElement("Player");
        // Устанавливаем атрибуты
        player.setAttribute("id", "???????");
        player.setAttribute("name", "?????????");
        player.setAttribute("symbol", "?????????");
        // Game
        Element game = document.createElement("Game");
        // Step
        Element step = document.createElement("Step");
        step.setAttribute("num", "??????");
        step.setAttribute("playerId", "??????");
        step.setTextContent("??????????????");
        // GameResult
        Element gameResult = document.createElement("GameResult");



        // Добавляем внутренние элементы игры в элемент <Game>
        gameplay.appendChild(player);
        gameplay.appendChild(game);
        game.appendChild(step);

        // Добавляем игру в корневой элемент
        root.appendChild(game);

        // Записываем XML в файл
        writeDocument(document);
    }

    // Функция для сохранения DOM в файл
    private static void writeDocument(Document document) throws TransformerFactoryConfigurationError {
        try {
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(document);
            FileOutputStream fos = new FileOutputStream("other.xml");
            StreamResult result = new StreamResult(fos);
            tr.transform(source, result);
        } catch (TransformerException | IOException e) {
            e.printStackTrace(System.out);
        }
    }
}

