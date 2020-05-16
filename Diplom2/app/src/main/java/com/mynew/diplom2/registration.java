package com.mynew.diplom2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;



public class registration extends AppCompatActivity implements View.OnClickListener {

    Button btnAdd, btnRead;
    EditText etNameReg, etPassReg;
    CheckBox etCheck;

    DBpeopls dBpeopls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnRead = (Button) findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);

        etNameReg = (EditText) findViewById(R.id.etNameReg);
        etPassReg = (EditText) findViewById(R.id.etPassReg);

        etCheck = findViewById(R.id.checkBox);

        // создаем объект для создания и управления версиями БД
        dBpeopls = new DBpeopls(this);
    }

    @Override
    public void onClick(View v) {

        // получаем данные из полей ввода
        String name = etNameReg.getText().toString();
        String pass = etPassReg.getText().toString();
        Integer check = 0;





        // подключаемся к БД
        SQLiteDatabase db = dBpeopls.getWritableDatabase();

        // создаем объект для данных
        ContentValues cv = new ContentValues();


        switch (v. getId()) {
            case R.id.btnAdd:
                Log.d("mLog","buttun Add");
                cv.put(DBpeopls.KEY_NAME, name);
                cv.put(DBpeopls.KEY_PASS, pass);

                if (etCheck.isChecked()) {
                    check = 1;
                } else
                    check = 0;

                cv.put(DBpeopls.KEY_DOCTOR, check);

                db.insert(DBpeopls.TABLE_CONTACTS, null, cv);
                break;

            case R.id.btnRead:
                Cursor cursor = db.query(DBpeopls.TABLE_CONTACTS, null, null, null, null, null, null);
                Log.d("mLog","button Read");
                if (cursor.moveToFirst()) {
                    int idIndex = cursor.getColumnIndex(DBpeopls.KEY_ID);
                    int nameIndex = cursor.getColumnIndex(DBpeopls.KEY_NAME);
                    int passIndex = cursor.getColumnIndex(DBpeopls.KEY_PASS);
                    int checkIndex = cursor.getColumnIndex(DBpeopls.KEY_DOCTOR);
                    do {
                        Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                                ", name = " + cursor.getString(nameIndex) +
                                ", pass = " + cursor.getString(passIndex) +
                                ", check = " + cursor.getString(checkIndex));

                    } while (cursor.moveToNext());
                } else
                    Log.d("mLog","0 rows");

                cursor.close();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
        dBpeopls.close();
    }
}
