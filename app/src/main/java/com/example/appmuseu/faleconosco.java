package com.example.appmuseu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class Faleconosco extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faleconosco);
    }
    public void btninsta (View view){
        Uri uri = Uri.parse("https://www.instagram.com/");
        Intent it = new
                Intent(Intent.ACTION_VIEW,uri);
        startActivity(it);
    }
}