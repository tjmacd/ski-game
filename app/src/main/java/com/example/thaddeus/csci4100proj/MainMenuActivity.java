package com.example.thaddeus.csci4100proj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.ByteBuffer;

public class MainMenuActivity extends AppCompatActivity {
    private String scoreFile = "bestScore.txt";
    private int bestScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    @Override
    protected void onStart(){
        super.onStart();
        bestScore = loadScore();
        TextView textView = (TextView)findViewById(R.id.textView4);
        textView.setText(String.format(getResources().getString(R.string.best_score),
                bestScore));

    }

    public void startGame(View view) {
        Intent intent = new Intent(this, PlayGameActivity.class);
        startActivityForResult(intent, 1);
    }

    public void highscores(View view){
        Intent intent = new Intent(this, LeaderboardActivity.class);
        intent.putExtra("file", R.raw.highscores);
        startActivity(intent);
    }

    public void showInstructions(View view){
        Intent intent = new Intent(this, TextDisplayActivity.class);
        intent.putExtra("file", R.raw.instructions);
        startActivity(intent);
    }

    public void showCredits(View view){
        Intent intent = new Intent(this, TextDisplayActivity.class);
        intent.putExtra("file", R.raw.credits);
        startActivity(intent);
    }

    private void saveScore(int score){
        try(PrintWriter writer = new PrintWriter(openFileOutput(scoreFile, 0))){
            writer.print(score);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private int loadScore(){
        int score = 0;
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(openFileInput(scoreFile)))){
            String line = br.readLine();
            score = Integer.parseInt(line);
        } catch(IOException e){
            e.printStackTrace();
        }
        return score;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                int newScore = data.getIntExtra("result", 0);
                if(newScore > bestScore){
                    saveScore(newScore);
                }
            }
        }
    }
}
