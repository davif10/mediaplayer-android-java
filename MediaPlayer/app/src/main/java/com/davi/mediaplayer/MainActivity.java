package com.davi.mediaplayer;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private SeekBar seekVolume;
    private AudioManager audioManager;
    private TextView textVolume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.teste);
        //Configura o Audio Manager
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //Cria a variável e coloca o volume atual do aparelho em um textView
        textVolume = findViewById(R.id.textVolume);
        int volumeAtual = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        textVolume.setText(""+volumeAtual);

        inicializarSeekBar();

    }


    private void inicializarSeekBar(){
        seekVolume = findViewById(R.id.seekVolume);

        //Configura o Audio Manager
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //recupera os valores de volume máximo e o volume atual
        int volumeMaximo = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int volumeAtual = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        //Configura os valores máximos para a SeekBar

        seekVolume.setMax(volumeMaximo);

        //Configura o progresso atual do Seekbar
        seekVolume.setProgress(volumeAtual);

        seekVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,AudioManager.FLAG_SHOW_UI);
                //Inserir a informação do volume no App
                int volumeAtual = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                textVolume.setText(""+volumeAtual);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    public void executarSom(View view){
        if(mediaPlayer != null){
            mediaPlayer.start();
        }
    }

    public void pausarSom(View view){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    public void pararSom(View view){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.teste);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

    }
}
