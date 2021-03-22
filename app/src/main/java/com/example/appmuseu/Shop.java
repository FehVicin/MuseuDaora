package com.example.appmuseu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Shop extends AppCompatActivity {

    TextView name;
    TextView status;
    String id;
    ImageView item;
    ImageView next;
    ImageView previous;
    Integer i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        name = findViewById(R.id.txtName);
        status = findViewById(R.id.txtStatus);
        item = findViewById(R.id.imgPro);
        next = findViewById(R.id.imgNItem);
        previous = findViewById(R.id.imgPItem);
        Search();
    }

    public void Search(){
        String url = "https://api.collection.cooperhewitt.org/rest/?method=cooperhewitt.shop.items.getList&access_token=3860743240c329a9d96b09fb35b64476&page=1&per_page=100";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject items = new JSONObject(response);
                            JSONArray prod = items.getJSONArray("items");
                            JSONObject atbs = prod.getJSONObject(i);
                            id = atbs.getString("id");
                            imgSearch();
                            String text = "Produto: " + atbs.getString("name");
                            name.setText(text);
                            Integer status_id = atbs.getInt("status_id");
                            if(status_id == 1){
                                status.setText("Disponibilidade: Disponível");
                            }
                            else{
                                status.setText("Disponibilidade: Indisponível");
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

    public void imgSearch(){
        String url = "https://api.collection.cooperhewitt.org/rest/?method=cooperhewitt.shop.items.getImages&access_token=3860743240c329a9d96b09fb35b64476&shop_item_id=" + id;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject images = new JSONObject(response);
                            JSONArray prod = images.getJSONArray("images");
                            JSONObject image = prod.getJSONObject(0);
                            JSONObject z = image.getJSONObject("z");
                            String imageURL = z.getString("url");
                            Picasso.get().load(imageURL).into(item);

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