package com.example.appmuseu;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class LoadExhibition extends AsyncTaskLoader<String> {
    private String mQueryString;
    LoadExhibition(Context context, String queryString){
        super(context);
        mQueryString = queryString;
    }

    @Override
    protected void onStartLoading(){
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.searchExhibitionInfo(mQueryString);
    }
}
