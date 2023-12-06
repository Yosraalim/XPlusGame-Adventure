/*
 * CS2210 - Assignment 2
 * Yosra Alim
 * Oct, 18th 2023
 * 
 */



public class Data {
    private String configuration;
    private int score;


    // constructor to create a Data object with configuration and score
    public Data(String config, int score) {
        this.configuration = config;
        this.score = score;
    }

    
    // get the configuration associated with this Data object
    public String getConfiguration() {
        return configuration;
    }

    
    // get the score associated with this Data object
    public int getScore() {
        return score;
    }

}



