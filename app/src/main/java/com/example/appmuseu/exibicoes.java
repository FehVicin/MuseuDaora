package com.example.appmuseu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Exibicoes extends AppCompatActivity {

    TextView title;
    TextView active;
    TextView info;
    ImageView next;
    ImageView previous;
    Integer i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibicoes);
        title = findViewById(R.id.txtTitle);
        active = findViewById(R.id.txtActive);
        info = findViewById(R.id.txtInfo);
        next = findViewById(R.id.imgNext);
        previous = findViewById(R.id.imgPrevious);
        Search();
    }

    public void Search(){
        String url = "https://api.collection.cooperhewitt.org/rest/?method=cooperhewitt.exhibitions.getList&access_token=3860743240c329a9d96b09fb35b64476&page=1&per_page=100";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject art = new JSONObject(response);
                            JSONArray exhibition = art.getJSONArray("exhibitions");
                            JSONObject atbs = exhibition.getJSONObject(i);
                            String text = "Exibição: " + atbs.getString("title");
                            title.setText(text);
                            info.setText(atbs.getString("text"));
                            Integer is_active = atbs.getInt("is_active");
                            if(is_active == 1){
                                active.setText("Status: Aberto");
                            }
                            else{
                                active.setText("Status: Fechado");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
        queue.add(request);
    }

    public void Next(View v){
        i++;
        Search();
    }

    public void Previous(View v){
        i--;
        if(i < 0) {
            i = 0;
        }
        Search();
    }
}