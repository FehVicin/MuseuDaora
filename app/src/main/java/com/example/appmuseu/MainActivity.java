package com.example.appmuseu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickhorario (View view) {
        Intent intent1 = new Intent(this, Horario.class);
        startActivity(intent1);
    }

    public void clicklocal (View view) {
        Intent intent2 = new Intent(this, Local.class);
        startActivity(intent2);
    }

    public void clickgaleria (View view) {
        Intent intent3 = new Intent(this, Galeria.class);
        startActivity(intent3);
    }

    public void clickfale (View view) {
        Intent intent4 = new Intent(this, Faleconosco.class);
        startActivity(intent4);
    }

    public void clickexibicoes (View view) {
        Intent intent5 = new Intent(this, Exibicoes.class);
        startActivity(intent5);
    }
}
