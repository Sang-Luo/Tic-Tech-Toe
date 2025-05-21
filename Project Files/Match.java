package com.luong.tictactoe;

import java.io.Serializable;

// Definition of the Match class which represents a single game match with its details.
public class Match {
    // Member variables to hold the details of the match.
    private String winnerName;      // Name of the winner of the match
    private String playerOneName;   // Name of player one
    private String playerTwoName;   // Name of player two
    private String result;          // The result of the match (e.g., "Match Draw", "X is Winner!")

    // Constructor for creating a new Match instance with the provided details.
    public Match(String winnerName, String playerOneName, String playerTwoName, String result) {
        this.winnerName = winnerName;        // Assigns the winner's name to the member variable
        this.playerOneName = playerOneName;  // Assigns player one's name
        this.playerTwoName = playerTwoName;  // Assigns player two's name
        this.result = result;                // Assigns the match result
    }

    // Getter method for the winner's name.
    public String getWinnerName() {
        return winnerName; // Returns the name of the winner
    }

    // Setter method for the winner's name.
    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName; // Sets the winner's name to the specified value
    }

    // Getter method for player one's name.
    public String getPlayerOneName() {
        return playerOneName; // Returns the name of player one
    }

    // Setter method for player one's name.
    public void setPlayerOneName(String playerOneName) {
        this.playerOneName = playerOneName; // Sets player one's name to the specified value
    }

    // Getter method for player two's name.
    public String getPlayerTwoName() {
        return playerTwoName; // Returns the name of player two
    }

    // Setter method for player two's name.
    public void setPlayerTwoName(String playerTwoName) {
        this.playerTwoName = playerTwoName; // Sets player two's name to the specified value
    }

    // Getter method for the result of the match.
    public String getResult() {
        return result; // Returns the result of the match
    }

    // Setter method for the result of the match.
    public void setResult(String result) {
        this.result = result; // Sets the result of the match to the specified value
    }

    // Add additional methods or logic here if needed for the Match class...
}

