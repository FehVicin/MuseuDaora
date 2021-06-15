package com.example.appmuseu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView txttitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txttitulo = findViewById(R.id.txttitulo);
    }

    public void clickhorario(View view) {
        Intent intent1 = new Intent(this, Horario.class);
        startActivity(intent1);
    }

    public void clickshop(View view) {
        Intent intent3 = new Intent(this, Shop.class);
        startActivity(intent3);
    }

    public void clickfale(View view) {
        Intent intent4 = new Intent(this, Faleconosco.class);
        startActivity(intent4);
    }

    public void clickexibicoes(View view) {
        Intent intent5 = new Intent(this, Exibicoes.class);
        startActivity(intent5);
    }

    public void clickcafe(View view) {
        Intent intent6 = new Intent(this, Cafe.class);
        startActivity(intent6);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        outState.putInt("COR", txttitulo.getCurrentTextColor());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        int color = savedInstanceState.getInt("COR");
        txttitulo.setTextColor(color);

    }

    public void mudarcor(View view) {
        TextView txt = (TextView) view;
        if (txt.getCurrentTextColor() != Color.RED) {
            txt.setTextColor(Color.RED);
        } else {
            txt.setTextColor(Color.BLACK);
        }
    }
}