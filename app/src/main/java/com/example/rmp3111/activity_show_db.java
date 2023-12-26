package com.example.rmp3111;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class activity_show_db extends AppCompatActivity {

    private DatabseHandler dbHandler;
    private TextView displayText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_db);

        dbHandler = new DatabseHandler(this);
        displayText = findViewById(R.id.display_text);

        displayData();
    }

    private void displayData() {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabseHandler.TABLE_NAME, null);

        StringBuilder stringBuilder = new StringBuilder();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(DatabseHandler.COLUMN_ID));
            String fio = cursor.getString(cursor.getColumnIndex(DatabseHandler.COLUMN_FIO));
            String timestamp = cursor.getString(cursor.getColumnIndex(DatabseHandler.COLUMN_TIMESTAMP));

            stringBuilder.append("ID: ").append(id)
                    .append(", ФИО: ").append(fio)
                    .append(", Время добавления записи: ").append(timestamp)
                    .append("\n");
        }

        cursor.close();
        db.close();

        displayText.setText(stringBuilder.toString());
    }
}