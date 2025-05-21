/*
* Name: Sang Luong
* Class: CSCI 3810
* Assignment: Class Project
* Description: This is a recreation of the classic Tic-Tac-Toe for my class project that includes some new
* features in which to adds to the gameplay. The main features that are added to the game are, bots, music,
* and being able to view game history. There are five main fragments, that being mainactvity.java which handles
* the core game logic, and handling where the game results get saved. Historyactivity.java which is where game results
* will be displayed. Startingscreen.java which acts as the main menu for the app and provides a menu bar/button which
* was a requirement for the project. Then aboutpage.java which displays useful information for the app.
* Lastly we have AddPlayers.java which handles the initial building of the main Tic-Tac-Toe game.*/
package com.luong.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.luong.tictactoe.GameHistory;
import com.luong.tictactoe.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


// MainActivity class to handle the game logic and user interactions
public class MainActivity extends AppCompatActivity {

    // Binding variable to interact with the views defined in the XML layout files using view binding
    ActivityMainBinding binding;

    // GameHistory object to store or manage the history of the game's plays
    GameHistory gameHistory;

    // A list to store all possible winning combinations for the game
    private final List<int[]> combinationList = new ArrayList<>();

    // An array representing the state of each box in the game (0 means unselected, 1 or 2 for selected by a player)
    private int[] boxPositions = {0,0,0,0,0,0,0,0,0}; // Initialize with 9 zeros, representing 9 boxes

    // Variable to keep track of which player's turn it is (player 1 or 2)
    private int playerTurn = 1;

    // Counter for how many boxes have been selected so far in the game
    private int totalSelectedBoxes = 0;

    // Variables to keep track of the scores of player one and player two
    private int playerOneScore = 0;
    private int playerTwoScore = 0;

    // Flag to determine whether the user is playing against regular bot mode
    private boolean playWithBot = false;

    // Flag to determine whether the bot difficulty is set to easy bot mode
    private boolean isEasyMode = false;


    // onCreate is the first method called when the activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setting up view binding for the activity
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize the gameHistory object
        gameHistory = new GameHistory(MainActivity.this);

        // Adding all winning combinations to the combinationList
        // Each winning combination is an array of 3 indices which correspond to a winning line in the game grid
        combinationList.add(new int[] {0,1,2});
        combinationList.add(new int[] {3,4,5});
        combinationList.add(new int[] {6,7,8});
        combinationList.add(new int[] {0,3,6});
        combinationList.add(new int[] {1,4,7});
        combinationList.add(new int[] {2,5,8});
        combinationList.add(new int[] {2,4,6});
        combinationList.add(new int[] {0,4,8});

        // Retrieving the names of the players and whether a bot is being played against from the Intent
        String getPlayerOneName = getIntent().getStringExtra("playerOne");
        String getPlayerTwoName = getIntent().getStringExtra("playerTwo");
        playWithBot = getIntent().getBooleanExtra("playWithBot", false);
        isEasyMode = getIntent().getBooleanExtra("isEasyMode", false);

        // Setting the retrieved player names to the TextViews in the layout
        binding.playerOneName.setText(getPlayerOneName);
        binding.playerTwoName.setText(getPlayerTwoName);

        // Setting onClickListeners for each image in the grid
        // Each listener calls a method to perform an action if the box at that position is selectable
        binding.image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(0)){
                    performAction((ImageView) view, 0);
                }
            }
        });
        binding.image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(1)){
                    performAction((ImageView) view, 1);
                }
            }
        });
        binding.image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(2)){
                    performAction((ImageView) view, 2);
                }
            }
        });
        binding.image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(3)){
                    performAction((ImageView) view, 3);
                }
            }
        });
        binding.image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(4)){
                    performAction((ImageView) view, 4);
                }
            }
        });
        binding.image6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(5)){
                    performAction((ImageView) view, 5);
                }
            }
        });
        binding.image7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(6)){
                    performAction((ImageView) view, 6);
                }
            }
        });
        binding.image8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(7)){
                    performAction((ImageView) view, 7);
                }
            }
        });
        binding.image9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(8)){
                    performAction((ImageView) view, 8);
                }
            }
        });

    }

    // This method is triggered to perform an action when a player selects a box on the board.
    private void performAction(ImageView imageView, int selectedBoxPosition) {
        // Sets the current player's number (1 or 2) in the array that keeps track of the game state.
        boxPositions[selectedBoxPosition] = playerTurn;
        // Increments the count of total selected boxes.
        totalSelectedBoxes++;

        // Sets the image resource to 'X' or 'O' depending on the player's turn.
        if (playerTurn == 1) {
            imageView.setImageResource(R.drawable.ximage);
        } else {
            imageView.setImageResource(R.drawable.oimage);
        }

        // If checkResults() returns true, it means someone has won the game.
        if (checkResults()) {
            // Increment the winner's score.
            if (playerTurn == 1) {
                playerOneScore++;
                // Update the UI with the new score.
                binding.playerOneScore.setText("Score: " + playerOneScore);
            } else {
                playerTwoScore++;
                binding.playerTwoScore.setText("Score: " + playerTwoScore);
            }

            // Check if a player has reached the score of 3 and thus wins the match.
            if ((playerTurn == 1 && playerOneScore == 3) || (playerTurn == 2 && playerTwoScore == 3)) {
                // Get the winner's name.
                String winnerName = playerTurn == 1 ? binding.playerOneName.getText().toString() : binding.playerTwoName.getText().toString();
                // Add the match to the history with the winner's name.
                gameHistory.addMatch(new Match(winnerName,
                        binding.playerOneName.getText().toString(),
                        binding.playerTwoName.getText().toString(),
                        "Winner"));
                // Show the win dialog.
                ResultDialog resultDialog = new ResultDialog(MainActivity.this, winnerName + " wins the match!", MainActivity.this);
                resultDialog.setCancelable(false);
                resultDialog.show();
                // Reset the game to start over.
                resetGame();
            } else {
                // Restart the match as no player has reached the winning score yet.
                restartMatch();
            }
        } else if (totalSelectedBoxes == 9) {
            // If all boxes are selected and no winner, declare a draw.
            ResultDialog resultDialog = new ResultDialog(MainActivity.this, "Match Draw", MainActivity.this);
            resultDialog.setCancelable(false);
            resultDialog.show();
            // Add a draw match to the history. (Optional: if you want  to save every draw match)
            // gameHistory.addMatch(new Match("Draw",
                    //binding.playerOneName.getText().toString(),
                    //binding.playerTwoName.getText().toString(),
                    //"Draw"));
        } else {
            // Change the turn to the other player.
            changePlayerTurn(playerTurn == 1 ? 2 : 1);
            // If it's the bot's turn and the game is in easy mode, calculate a random move.
            if (playerTurn == 2 && isEasyMode) {
                int bestMove = generateRandomMove(boxPositions);
                ImageView botMoveImageView = getImageViewForPosition(bestMove);
                performAction(botMoveImageView, bestMove);
                // If playing with a bot and not in easy mode, calculate the best move using minimax algorithm.
            } else if (playerTurn == 2 && playWithBot) {
                int bestMove = minimax(boxPositions, playerTurn).position;
                ImageView botMoveImageView = getImageViewForPosition(bestMove);
                performAction(botMoveImageView, bestMove);
            }
        }
    }

    // This method changes the visual indication of whose turn it is.
    public void changePlayerTurn(int currentPlayerTurn) {
        playerTurn = currentPlayerTurn;
        // Highlight the current player's layout with a black border.
        if (playerTurn == 1) {
            binding.playerOneLayout.setBackgroundResource(R.drawable.black_border);
            binding.playerTwoLayout.setBackgroundResource(R.drawable.white_box);
        } else {
            binding.playerTwoLayout.setBackgroundResource(R.drawable.black_border);
            binding.playerOneLayout.setBackgroundResource(R.drawable.white_box);
        }
    }


    // Checks if any player has won the game.
    public boolean checkResults() {
        for (int[] combination : combinationList) {
            // If a winning combination is found for the current player, return true.
            if (boxPositions[combination[0]] == playerTurn && boxPositions[combination[1]] == playerTurn &&
                    boxPositions[combination[2]] == playerTurn) {
                return true;
            }
        }
        return false;
    }


    // Easy Mode Method: generates a random move from available positions.
    public int generateRandomMove(int[] board) {
        List<Integer> availableMoves = new ArrayList<>();
        // Adds all available positions to the list.
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 0) {
                availableMoves.add(i);
            }
        }
        Random rand = new Random(System.currentTimeMillis());
        // Returns a random position from the available moves.
        return availableMoves.get(rand.nextInt(availableMoves.size()));
    }

    // getImageViewForPosition is a helper function to retrieve the correct ImageView based on the position
    private ImageView getImageViewForPosition(int position) {
        switch (position) {
            case 0:
                return binding.image1;
            case 1:
                return binding.image2;
            case 2:
                return binding.image3;
            case 3:
                return binding.image4;
            case 4:
                return binding.image5;
            case 5:
                return binding.image6;
            case 6:
                return binding.image7;
            case 7:
                return binding.image8;
            case 8:
                return binding.image9;
            default:
                return null;
        }
    }

    private class Move {
        int position;
        int score;

        Move(int position, int score) {
            this.position = position;
            this.score = score;
        }
    }

    // Minimax algorithm implementation to calculate the best move.
    private Move minimax(int[] board, int currentPlayer) {
        // Check for game end conditions and assign scores accordingly.
        int winner = checkWinner(board);
        if (winner == 1) return new Move(-1, -10);
        if (winner == 2) return new Move(-1, 10);
        if (isFull(board)) return new Move(-1, 0);

        // Initialize the best score based on the player.
        int bestMove = -1;
        int bestScore = (currentPlayer == 1) ? Integer.MAX_VALUE : Integer.MIN_VALUE;

        for (int i = 0; i < board.length; i++) {
            // Go through all possible moves.
            if (board[i] == 0) {
                board[i] = currentPlayer;
                // Recursively call minimax for the next player.
                int currentScore = minimax(board, 3 - currentPlayer).score;
                board[i] = 0; // Undo the move

                // Update the best score and move for the current player.
                if (currentPlayer == 1 && currentScore < bestScore) {
                    bestScore = currentScore;
                    bestMove = i;
                } else if (currentPlayer == 2 && currentScore > bestScore) {
                    bestScore = currentScore;
                    bestMove = i;
                }
            }
        }

        // Return the best move found along with its score.
        return new Move(bestMove, bestScore);
    }

    // Checks for a winner and returns the player's number if found.
    private int checkWinner(int[] board) {
        for (int[] combination : combinationList) {
            if (board[combination[0]] == board[combination[1]] && board[combination[1]] == board[combination[2]] && board[combination[0]] != 0) {
                return board[combination[0]];
            }
        }
        return 0; // No winner
    }

    // Checks if the board is full.
    private boolean isFull(int[] board) {
        for (int i : board) {
            if (i == 0) return false;
        }
        return true; // Board is full
    }


    // End of Minimax Recursion Method

    //______________________________________________

    // Check if Box Position is Not Selected Already
    private boolean isBoxSelectable(int boxPosition) {
        boolean response = false;
        if (boxPositions[boxPosition] == 0) {
            response = true;
        }
        return response;
    }

    // Reset New Game
    public void resetGame() {
        restartMatch();
        playerOneScore = 0;
        playerTwoScore = 0;
        updateScores();
    }

    // Update the scores on the UI
    private void updateScores() {
        binding.playerOneScore.setText("Score: " + playerOneScore);
        binding.playerTwoScore.setText("Score: " + playerTwoScore);
    }

    // Restart Board
    public void restartMatch(){
        boxPositions = new int[] {0,0,0,0,0,0,0,0,0}; //9 zero
        playerTurn = 1;
        totalSelectedBoxes = 0;

        binding.image1.setImageResource(R.drawable.white_box);
        binding.image2.setImageResource(R.drawable.white_box);
        binding.image3.setImageResource(R.drawable.white_box);
        binding.image4.setImageResource(R.drawable.white_box);
        binding.image5.setImageResource(R.drawable.white_box);
        binding.image6.setImageResource(R.drawable.white_box);
        binding.image7.setImageResource(R.drawable.white_box);
        binding.image8.setImageResource(R.drawable.white_box);
        binding.image9.setImageResource(R.drawable.white_box);
    }
}