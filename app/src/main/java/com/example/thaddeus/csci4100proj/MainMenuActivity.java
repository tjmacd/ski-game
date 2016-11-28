package com.example.thaddeus.csci4100proj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                TextView textView = (TextView)findViewById(R.id.textView4);
                textView.setText("Your Score: \n" + data.getStringExtra("result"));
            }
        }
    }
}
