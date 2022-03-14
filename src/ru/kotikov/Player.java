package ru.kotikov;

public class Player {

    private String name;
    private int numberOfWins = 0;
    private int numberOfLoses = 0;
    private int numberOfDraws = 0;
    private String id;
    private String symbol;

    public Player() {
    }

    public Player(String id, String symbol) {
        this.id = id;
        this.symbol = symbol;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfWins() {
        return numberOfWins;
    }

    public void setNumberOfWins(int numberOfWins) {
        this.numberOfWins = numberOfWins;
    }

    public int getNumberOfLoses() {
        return numberOfLoses;
    }

    public void setNumberOfLoses(int numberOfLoses) {
        this.numberOfLoses = numberOfLoses;
    }

    public int getNumberOfDraws() {
        return numberOfDraws;
    }

    public void setNumberOfDraws(int numberOfDraws) {
        this.numberOfDraws = numberOfDraws;
    }



    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", numberOfWins=" + numberOfWins +
                ", numberOfLoses=" + numberOfLoses +
                ", numberOfDraws=" + numberOfDraws +
                ", id='" + id + '\'' +
                ", symbol='" + symbol + '\'' +
                '}';
    }
}
