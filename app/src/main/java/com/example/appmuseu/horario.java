package com.example.appmuseu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Horario extends AppCompatActivity {

    TextView isopen;
    TextView day;
    TextView open;
    TextView close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario);
        isopen = findViewById(R.id.txtOpen);
        day = findViewById(R.id.txtDay);
        open = findViewById(R.id.txtTime);
        close = findViewById(R.id.txtClose);
        Search();
    }

    public void Search(){
        String url = "https://api.collection.cooperhewitt.org/rest/?method=cooperhewitt.galleries.isOpen&access_token=3860743240c329a9d96b09fb35b64476";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject date = new JSONObject(response);
                            Integer is_open = date.getInt("open");
                            if(is_open == 1){
                                isopen.setText("Status: Aberto");
                            }
                            else{
                                isopen.setText("Status: Fechado");
                            }
                            String today = "Dia: " + date.getString("ymd");
                            day.setText(today);
                            JSONObject hours = date.getJSONObject("hours");
                            String opentime = "Horário de Abertura: " + hours.getString("open");
                            String closetime = "Horário de Fechamento: " + hours.getString("close");
                            open.setText(opentime);
                            close.setText(closetime);
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
}