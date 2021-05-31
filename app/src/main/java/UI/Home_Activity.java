package UI;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wallpaper_application.R;

public class Home_Activity extends AppCompatActivity implements View.OnClickListener {
    private TextView playerOneScore, playerTwoScore, playerStatus;
    private Button[] buttons = new Button[9];
    private Button resetGame;

    private int playerOneScoreCount, playerTwoScoreCount, roundCount;
    boolean activePlayer;

    //p1=>0
    //p2=>1
    //empty=>2
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    int[][] winningPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},  //rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},  //columns
            {0, 4, 8}, {2, 4, 6}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_);

        playerOneScore = findViewById(R.id.PlayerOneScore);
        playerTwoScore = findViewById(R.id.PlayerTwoScore);
        playerStatus = findViewById(R.id.PlayerStatus);

        resetGame = findViewById(R.id.ResetGame);

        for (int i = 0; i < buttons.length; i++) {
            String buttonID = "btn_" + i;
            int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }
        roundCount = 0;
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
        activePlayer = true;
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        String buttonID = v.getResources().getResourceEntryName(v.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length() - 1, buttonID.length()));

        if (activePlayer) {
            ((Button) v).setText("X");
            ((Button) v).setTextColor(Color.parseColor("#FFC34A"));
            gameState[gameStatePointer] = 0;
        } else {
            ((Button) v).setText("O");
            ((Button) v).setTextColor(Color.parseColor("#70FFEA"));
            gameState[gameStatePointer] = 1;
        }
        roundCount++;

        if (checkWinner()) {
            if (activePlayer) {
                playerOneScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "Player One Won!", Toast.LENGTH_LONG).show();

            } else {
                playerTwoScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "Player Two Won!", Toast.LENGTH_LONG).show();

            }
        }else if (roundCount == 9) {
            playAgain();
            Toast.makeText(this, "No Winner!", Toast.LENGTH_LONG).show();

        } else {
            activePlayer = !activePlayer;
        }
        if(playerOneScoreCount > playerTwoScoreCount){
            playerStatus.setText("Player One is Winning!");
        }else if(playerTwoScoreCount > playerOneScoreCount){
            playerStatus.setText("Player two is winning!");
        }else{
            playerStatus.setText("");
        }

        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain();
                playerOneScoreCount = 0;
                playerTwoScoreCount = 0;
                playerStatus.setText("");
                updatePlayerScore();
            }
        });
    }




    public boolean checkWinner() {
        boolean winnerResult = false;
        for (int[] winningPosition : winningPositions) {
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                    gameState[winningPosition[0]] != 2) {
                winnerResult = true;
            }
        }
        return winnerResult;

    }
    private void updatePlayerScore() {
        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));

    }
    private void playAgain() {
        roundCount = 0;
        activePlayer = true;

        for (int i = 0; i < buttons.length; i++) {
            gameState[i] = 2;
            buttons[i].setText("");

        }


    }
}
