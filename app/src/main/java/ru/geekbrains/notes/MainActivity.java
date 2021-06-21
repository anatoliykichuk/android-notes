package ru.geekbrains.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeToolBar();
    }

    private void initializeToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notes_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.menu_add:
                // TODO: Реализовать добавление заметки.
                return true;
            case R.id.menu_edit:
                // TODO: Реализовать открытие текущей заметки для редактирования.
                return true;
            case R.id.menu_remove:
                // TODO: Реализовать удаление текущей заметки.
                return true;
            case R.id.menu_about:
                // TODO: Реализовать вывод информации о приложении.
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}