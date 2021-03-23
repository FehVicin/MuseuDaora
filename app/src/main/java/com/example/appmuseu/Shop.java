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

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Shop extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    TextView name;
    TextView status;
    ImageView item;
    ImageView search;
    EditText txt_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        name = findViewById(R.id.txtName);
        status = findViewById(R.id.txtStatus);
        item = findViewById(R.id.imgPro);
        txt_search = findViewById(R.id.txtSearchItem);
        search = findViewById(R.id.imgSearchItem);
        if(getSupportLoaderManager().getLoader(0) != null){
            getSupportLoaderManager().initLoader(0, null, this);
        }
        ConnectivityManager con = (ConnectivityManager)
            getSystemService(Context.CONNECTIVITY_SERVICE);
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
        return new LoadItem(this, queryString);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try {
            JSONObject items = new JSONObject(data);
            String result = items.getString("stat");
            if(result.equals("ok")) {
                JSONObject atbs = items.getJSONObject("item");

                JSONArray product = atbs.getJSONArray("images");
                JSONObject image = product.getJSONObject(0);
                JSONObject z = image.getJSONObject("z");
                String imageURL = z.getString("url");
                Picasso.get().load(imageURL).fit().centerInside().into(item);

                String text = "Produto: " + atbs.getString("name");
                name.setText(text);
                Integer status_id = atbs.getInt("status_id");
                if (status_id == 1) {
                    status.setText("Disponibilidade: Disponível");
                }
                else {
                    status.setText("Disponibilidade: Indisponível");
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