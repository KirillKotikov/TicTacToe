package ru.kotikov;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class TicTacToeDomParser {

    public static void main(String[] args) {

        Player winner = new Player();

        String[][] playingField = new String[][]{
                {"|1|", "|2|", "|3|"},
                {"|4|", "|5|", "|6|"},
                {"|7|", "|8|", "|9|"}
        };

        try {
            File inputFile = new File("GamesHistory.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            String result = "";

            NodeList playerList = doc.getElementsByTagName("Player");
            for (int temp = 0; temp < playerList.getLength(); temp++) {
                Node playerNode = playerList.item(temp);

                if (playerNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element playerElement = (Element) playerNode;
                    if (temp == 2) {
                        winner.setId(playerElement.getAttribute("id"));
                        winner.setName(playerElement.getAttribute("name"));
                        winner.setSymbol(playerElement.getAttribute("symbol"));
                        result = ("Player " + winner.getId() + " -> " +
                                winner.getName() + " is winner as \'"
                                + winner.getSymbol() + "\'!");
                    }
                }
            }
            if (winner.getName() == null) {
                NodeList gameResultList = doc.getElementsByTagName("GameResult");
                Node gameResultNode = gameResultList.item(0);
                Element gameResultElement = (Element) gameResultNode;
                result = gameResultElement.getTextContent();
            }

            NodeList stepList = doc.getElementsByTagName("Step");
            for (int i = 0; i < stepList.getLength(); i++) {
                Node stepListNode = stepList.item(i);
                Element stepElement = (Element) stepListNode;
                 String id = stepElement.getAttribute("playerId");
                 String playerChar;
                 if (id.equals("1")) playerChar = "x";
                 else playerChar = "o";
                int[] coordinates = Game.fieldCoordinates(stepElement.getTextContent());
                playingField[coordinates[0]][coordinates[1]] = "|" + playerChar + "|";
                for (String[] charsX : playingField) {
                    for (String charsY : charsX) {
                        System.out.print(charsY + " ");
                    }
                    System.out.println();
                }
                System.out.println();

            }
            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}