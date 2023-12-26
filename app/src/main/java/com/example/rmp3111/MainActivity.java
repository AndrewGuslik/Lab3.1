package com.example.rmp3111;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private DatabseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = new DatabseHandler(this);
    }

    public void viewData(View view) {
        Intent intent = new Intent(this, activity_show_db.class);
        startActivity(intent);
    }

    public void openAddRecordActivity(View view) {
        Intent intent = new Intent(this, activity_add_to_db.class);
        startActivityForResult(intent, 1);
    }

    public void updateLastRecord(View view) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabseHandler.COLUMN_FIO, "Иванов Иван Иванович");

        String selection = DatabseHandler.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(getLastRecordId())};

        int count = db.update(DatabseHandler.TABLE_NAME, values, selection, selectionArgs);

        if (count > 0) {
            Toast.makeText(this, "Запись обновлена", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show();
        }
    }

    private int getLastRecordId() {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(" + DatabseHandler.COLUMN_ID + ") FROM " + DatabseHandler.TABLE_NAME, null);

        int id = -1;
        if (cursor.moveToFirst()) {
            id = cursor.getInt(0);
        }

        cursor.close();

        return id;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Запись добавлена", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show();
            }
        }
    }
}