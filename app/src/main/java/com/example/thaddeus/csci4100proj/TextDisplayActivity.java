package com.example.thaddeus.csci4100proj;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TextDisplayActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_display);

        displayText();
    }

    private void displayText() {
        EditText textBox = (EditText)findViewById(R.id.textView);
        StringBuilder text = new StringBuilder();
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(getResources().openRawResource(
                        getIntent().getIntExtra("file", -1))))){
            String line;
            while ((line = br.readLine()) != null){
                text.append(line);
                text.append('\n');
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        textBox.setText(text.toString());
    }

    public void returnToMain(View view){
        finish();
    }
}
