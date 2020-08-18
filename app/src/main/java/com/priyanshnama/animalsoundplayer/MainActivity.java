package com.priyanshnama.animalsoundplayer;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ImageButton play,pause;
    private TextView last_Played;
    private MediaPlayer mediaPlayer;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = findViewById(R.id.play);
        pause = findViewById(R.id.pause);
        last_Played = findViewById(R.id.last_played);
        TextView bark = findViewById(R.id.bark);
        TextView meow = findViewById(R.id.meow);

        SharedPreferences sharedPreferences = getSharedPreferences("last_played", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();

        mediaPlayer = MediaPlayer.create(this, sharedPreferences.getInt("last_played",R.raw.bark));
        last_Played.setText(sharedPreferences.getString("last_played","click on a animal name"));

        bark.setOnClickListener(v -> changeSound(R.raw.bark, "bark"));
        meow.setOnClickListener(v -> changeSound(R.raw.meow, "meow"));
        play.setOnClickListener(v -> buttonClicked());
        pause.setOnClickListener(v -> buttonClicked());
        mediaPlayer.setOnCompletionListener(v ->pause_the_sound());

    }

    private void pause_the_sound() {
        play.setVisibility(View.VISIBLE);
        pause.setVisibility(View.GONE);
        mediaPlayer.pause();
        mediaPlayer.setOnCompletionListener(v ->pause_the_sound());
    }

    private void play_the_sound(){
        play.setVisibility(View.GONE);
        pause.setVisibility(View.VISIBLE);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(v ->pause_the_sound());
    }

    private void changeSound(int sound, String last_played) {
        mediaPlayer = MediaPlayer.create(this,sound);
        play_the_sound();
        editor.putInt("last_played",sound);
        editor.putString("last_played",last_played);
        last_Played.setText(last_played);
        editor.commit();
        mediaPlayer.setOnCompletionListener(v ->pause_the_sound());
    }

    private void buttonClicked() {
        if(mediaPlayer.isPlaying())
            pause_the_sound();
        else
            play_the_sound();
    }
}