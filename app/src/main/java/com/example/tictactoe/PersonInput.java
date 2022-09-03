package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PersonInput extends AppCompatActivity {
    EditText p1,p2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_input);
        p1 = (EditText) findViewById(R.id.playerx);
        p2 = (EditText) findViewById(R.id.playero);
        p1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        p2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

    }
    public void game(View view){

        if(TextUtils.isEmpty(p1.getText().toString()) || TextUtils.isEmpty(p2.getText().toString()) ){
            Toast.makeText(getApplicationContext(),"Enter the all fields",Toast.LENGTH_SHORT).show();
            return;
        }
        String s1 = p1.getText().toString();
        String s2 = p2.getText().toString();

        Intent intent = new Intent(PersonInput.this,Game.class);
        intent.putExtra("Player_Names", new String[]{ s1,s2});
        startActivity(intent);
    }
}