package com.example.rmp3111;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class activity_add_to_db extends AppCompatActivity {

    private com.example.rmp3111.DatabseHandler dbHandler;
    private EditText inputFIO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_db);

        dbHandler = new com.example.rmp3111.DatabseHandler(this);
        inputFIO = findViewById(R.id.input_fio);
    }

    public void saveRecord(View view) {
        String fio = inputFIO.getText().toString().trim();

        if (!fio.isEmpty()) {
            SQLiteDatabase db = dbHandler.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(com.example.rmp3111.DatabseHandler.COLUMN_FIO, fio);

            long newRowId = db.insert(com.example.rmp3111.DatabseHandler.TABLE_NAME, null, values);

            if (newRowId != -1) {
                setResult(RESULT_OK);
                finish();
            } else {
                setResult(RESULT_CANCELED);
                finish();
            }

            db.close();
        }
    }
}