package com.example.thaddeus.csci4100proj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void startGame(View view) {
        Intent intent = new Intent(this, PlayGameActivity.class);
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
}
