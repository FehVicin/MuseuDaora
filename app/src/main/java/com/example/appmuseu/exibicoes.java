package com.example.appmuseu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class Exibicoes extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    TextView title;
    TextView active;
    TextView info;
    ImageView search;
    EditText txt_search;
    Integer i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibicoes);
        title = findViewById(R.id.txtTitle);
        active = findViewById(R.id.txtActive);
        info = findViewById(R.id.txtInfo);
        search = findViewById(R.id.imgSearchExhi);
        txt_search = findViewById(R.id.txtSearchExhi);
        if (getSupportLoaderManager().getLoader(0) != null) {
            getSupportLoaderManager().initLoader(0, null, this);
        }
    }

    public void Search(View view) {
        String queryString = txt_search.getText().toString();

        InputMethodManager inputMethodManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }

        if (networkInfo != null && networkInfo.isConnected()
                && queryString.length() != 0) {
            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString", queryString);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);
        } else {
            if (queryString.length() == 0) {
                txt_search.setError("Escreva um id válido");
                return;
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "Verifique sua conexão", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String queryString = "";
        if(args != null){
            queryString = args.getString("queryString");
        }
        return new LoadExhibition(this, queryString);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try {
            JSONObject art = new JSONObject(data);
            String result = art.getString("stat");
            if(result.equals("ok")){
                JSONObject exhibition = art.getJSONObject("exhibition");
                String name = "Exibição: " + exhibition.getString("title");
                title.setText(name);
                String text = exhibition.getString("text");
                info.setText(text);
                Integer is_active = exhibition.getInt("is_active");
                if(is_active == 1){
                    active.setText("Aberto");
                }
                else{
                    active.setText("Fechado");
                }
            }
            else{
                Toast.makeText(getApplicationContext(), "Este id não é válido", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}