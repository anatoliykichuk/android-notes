package ru.geekbrains.notes.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import ru.geekbrains.notes.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addFragment();
        initializeToolBar();
    }

    private void addFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.notes_container, NotesFragment.newInstance())
                .commit();
    }

    private void initializeToolBar() {
        Toolbar toolbar = findViewById(R.id.notes_toolbar);
        setSupportActionBar(toolbar);
    }
}