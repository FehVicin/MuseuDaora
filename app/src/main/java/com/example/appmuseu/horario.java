package com.example.appmuseu;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Horario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario);
        Search();
    }

    public void Search(){
        FeedReaderContract.FeedReaderDbHelper dbHelper = new FeedReaderContract.FeedReaderDbHelper(this);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE
        };

        String selection = "";
        String[] selectionArgs = {};

        String sortOrder = FeedReaderContract.FeedEntry._ID + " DESC LIMIT 4";

        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME_EXHIBITION,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        List names = new ArrayList<>();
        while(cursor.moveToNext()) {
            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE));
            names.add(name);
        }
        cursor.close();
        Log.d("name 0", String.valueOf(names.get(0)));

        for (int i = 0; i<names.size(); i++){
            switch (i){
                case 0:
                    ((TextView) findViewById(R.id.txtTitle0)).setText((String)names.get(i));
                    break;
                case 1:
                    ((TextView) findViewById(R.id.txtTitle1)).setText((String)names.get(i));
                    break;
                case 2:
                    ((TextView) findViewById(R.id.txtTitle2)).setText((String)names.get(i));
                    break;
                case 3:
                    ((TextView) findViewById(R.id.txtTitle3)).setText((String)names.get(i));
                    break;
            }
        }
        db.close();
    }
}