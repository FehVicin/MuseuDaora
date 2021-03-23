package com.example.appmuseu;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.BufferedReader;
import java.net.HttpURLConnection;

public class NetworkUtils {
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    private static final String URL_API = "https://api.collection.cooperhewitt.org/rest/?";
    private static final String QUERY_PARAMETER = "method";
    private static final String ACCESS_TOKEN = "access_token";
    private static final String ID_EXHIBITION = "exhibition_id";
    private static final String ID_ITEM = "shop_item_id";
    static String searchItemInfo(String queryString){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String itemJSONString = null;
        try{
            Uri builtURI = Uri.parse(URL_API).buildUpon()
                .appendQueryParameter(QUERY_PARAMETER, "cooperhewitt.shop.items.getInfo")
                    .appendQueryParameter(ACCESS_TOKEN, "3860743240c329a9d96b09fb35b64476")
                        .appendQueryParameter(ID_ITEM, queryString)
                                .build();
            URL requestURL = new URL(builtURI.toString());
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder builder = new StringBuilder();
            String row;

            while((row = reader.readLine()) != null){
                builder.append(row);
                builder.append("\n");
            } if(builder.length() == 0){
                return null;
            }
            itemJSONString = builder.toString();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            } if(reader != null){
                try{
                    reader.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        Log.d(LOG_TAG, itemJSONString);
        return itemJSONString;
    }

    static String searchExhibitionInfo(String queryString){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String itemJSONString = null;
        try{
            Uri builtURI = Uri.parse(URL_API).buildUpon()
                    .appendQueryParameter(QUERY_PARAMETER, "cooperhewitt.exhibitions.getInfo")
                        .appendQueryParameter(ACCESS_TOKEN, "3860743240c329a9d96b09fb35b64476")
                            .appendQueryParameter(ID_EXHIBITION, queryString)
                    .build();
            URL requestURL = new URL(builtURI.toString());
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder builder = new StringBuilder();
            String row;

            while((row = reader.readLine()) != null){
                builder.append(row);
                builder.append("\n");
            } if(builder.length() == 0){
                return null;
            }
            itemJSONString = builder.toString();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            } if(reader != null){
                try{
                    reader.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        Log.d(LOG_TAG, itemJSONString);
        return itemJSONString;
    }
}
