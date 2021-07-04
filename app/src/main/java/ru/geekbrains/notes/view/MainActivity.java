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
        initializeToolBar(R.id.notes_toolbar);
    }

    private void addFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, NotesFragment.newInstance())
                .commit();
    }

    public void initializeToolBar(int toolBarId) {
        Toolbar toolbar = findViewById(toolBarId);
        setSupportActionBar(toolbar);
    }
}