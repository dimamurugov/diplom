package com.mynew.diplom2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ActivityLogin extends AppCompatActivity implements View.OnClickListener {
    Button btnReg,btnLog;
    EditText etNameLog, etPassLog;

    SQLiteDatabase db;
    DBpeopls dBpeopls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnReg = (Button) findViewById(R.id.btnReg);
        btnReg.setOnClickListener(this);

        btnLog = (Button) findViewById(R.id.btnLog);
        btnLog.setOnClickListener(this);

        etNameLog = (EditText) findViewById(R.id.etNameLog);
        etPassLog = (EditText) findViewById(R.id.etPassLog);

        dBpeopls = new DBpeopls(this);
        SQLiteDatabase db = dBpeopls.getWritableDatabase();

    }

    @Override
    public void onClick(View v) {

        String name = etNameLog.getText().toString();
        String pass = etPassLog.getText().toString();

        String selection = null;
        String[] selectionArgs = null;

        db = dBpeopls.getWritableDatabase();
        ContentValues cv= new ContentValues();

        Cursor c = null;

        switch (v.getId()){
            case R.id.btnReg:
                    Intent intent = new Intent(this, registration.class);
                    startActivity(intent);
                break;

            case R.id.btnLog:
                Log.d("mLog", "--- Нашли его ---");

                selection = "name = ? AND pass = ?";
                selectionArgs = new String[] { name, pass };
                c = db.query("contacts", null, selection, selectionArgs, null, null, null);

                if (c.getCount() != 0) {
                    //Log.d("mLog", "Result = " + c.getString(2));

//                    Intent intent2 = new Intent(this, list.class);
//                    startActivity(intent2);
                } else Log.d("mLog", "Не найдено");

                if (c != null) {
                    if (c.moveToFirst()) {
                        String str;
                        do {
                            str = "";
                            for (String cn : c.getColumnNames()) {
                                str = str.concat(cn + " = "
                                        + c.getString(c.getColumnIndex(cn)) + "; ");
                            }
                            Log.d("mLog", str);

                        } while (c.moveToNext());
                    }
                    c.close();
                } else
                    Log.d("mLog", "Cursor is null");



            break;
            default:
                break;
        }
//        if (c != null) {
//            if (c.moveToFirst()) {
//                String str;
//                do {
//                    str = "";
//                    for (String cn : c.getColumnNames()) {
//                        str = str.concat(cn + " = "
//                                + c.getString(c.getColumnIndex(cn)) + "; ");
//                    }
//                    Log.d("mLog", str);
//
//                } while (c.moveToNext());
//            }
//            c.close();
//        } else
//            Log.d("mLog", "Cursor is null");
        dBpeopls.close();
    }
}

