package ru.geekbrains.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeToolBar();
        addFragment(NotesFragment.newInstance());
    }

    private void addFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.notes_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void initializeToolBar() {
        Toolbar toolbar = findViewById(R.id.notes_toolbar);
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
                showMessage("Добавление заметки");
                return true;
            case R.id.menu_edit:
                // TODO: Реализовать открытие текущей заметки для редактирования.
                showMessage("Редактирование заметки");
                return true;
            case R.id.menu_remove:
                // TODO: Реализовать удаление текущей заметки.
                showMessage("Удаление заметки");
                return true;
            case R.id.menu_about:
                // TODO: Реализовать вывод информации о приложении.
                showMessage("Отображение информации о приложении");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showMessage(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}