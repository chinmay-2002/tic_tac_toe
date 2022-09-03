package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Game extends AppCompatActivity {

    private TIctactoeBoard tt ;
    Button playagainn, home;
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        playagainn = (Button) findViewById(R.id.play_again);
        home = (Button) findViewById(R.id.home_button);
        txt = (TextView) findViewById(R.id.playerInfo);
        tt = (TIctactoeBoard) findViewById(R.id.TIctactoeBoard);
        String [] names = getIntent().getStringArrayExtra("Player_Names");
        playagainn.setVisibility(View.GONE);
        home.setVisibility(View.GONE);
        if(names!=null){
            txt.setText(names[0]+"'s Turn");
        }


        tt.setUpGame(playagainn,home,txt,names);

    }
    public void playAgain(View view){
        tt.resetGamee();
        tt.invalidate();
    }
    public void mainClick(View view){
        Intent intent = new Intent(Game.this,MainActivity.class);
        startActivity(intent);
    }
}