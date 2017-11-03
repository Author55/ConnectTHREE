package com.anshit.connectthree;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //yellow=0, red=1
    int activePlayer=0;

    boolean gameIsActive=true;

    //2 means unplayed
    int[] gameState = {2,2,2,2,2,2,2,2,2};

    int[][] winningPositions={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    public void about(View view){
        Intent intent= new Intent(MainActivity.this,Main2Activity.class);
        startActivity(intent);
    }

    public void dropIn(View view){
        ImageView counter= (ImageView) view;
        System.out.println(counter.getTag().toString());
        int tappedCounter = Integer.parseInt(counter.getTag().toString());
        if(gameState[tappedCounter]==2 && gameIsActive) {
            gameState[tappedCounter]=activePlayer;
            counter.setTranslationY(-1000f);
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }
            counter.animate().translationYBy(1000f).rotation(3600f).setDuration(300);
            for(int[] winningPosition:winningPositions){
                if(gameState[winningPosition[0]]==gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]]==gameState[winningPosition[2]]&&
                        gameState[winningPosition[0]]!=2){

                    //Someone has won!
                    gameIsActive=false;

                    String winner= "Red";

                    if(gameState[winningPosition[0]]==0) {
                        winner="Yellow";
                    }

                    TextView winnerMsg = (TextView)findViewById(R.id.winnerMsg);
                    winnerMsg.setText(winner+" has won!");

                    LinearLayout layout=(LinearLayout)findViewById(R.id.playAgainLayout);
                    LinearLayout layout1=(LinearLayout)findViewById(R.id.playagainbuttonLayout);
                    layout.animate().rotation(720f).setDuration(500);
                    layout.setVisibility(View.VISIBLE);
                    layout1.setVisibility(View.VISIBLE);
                    break;

                }
                else {
                    boolean gameIsOver= true;
                    for(int counterState: gameState) {
                        if (counterState == 2) {
                            gameIsOver = false;
                        }
                    }
                        if((gameIsOver== true) ) {
                            TextView winnerMsg = (TextView)findViewById(R.id.winnerMsg);
                            winnerMsg.setText("It's a draw!");

                            LinearLayout layout=(LinearLayout)findViewById(R.id.playAgainLayout);
                            LinearLayout layout1=(LinearLayout)findViewById(R.id.playagainbuttonLayout);
                            layout.animate().rotation(720f).setDuration(500);
                            layout.setVisibility(View.VISIBLE);
                            layout1.setVisibility(View.VISIBLE);
                        }
                    }
            }
        }
    }

    public void playAgain(View view){
        gameIsActive=true;
        LinearLayout layout=(LinearLayout)findViewById(R.id.playAgainLayout);
        LinearLayout layout1=(LinearLayout)findViewById(R.id.playagainbuttonLayout);
        layout.animate().rotation(-720f).setDuration(500);
        layout.setVisibility(View.INVISIBLE);
        layout1.setVisibility(View.INVISIBLE);

        activePlayer=0;

        for(int i=0;i<gameState.length;i++){
            gameState[i]=2;
        }
        GridLayout gridLayout= (GridLayout) findViewById(R.id.gridLayout);
        for(int i=0; i<gridLayout.getChildCount();i++){

            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
