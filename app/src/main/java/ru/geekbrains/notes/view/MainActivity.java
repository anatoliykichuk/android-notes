package ru.geekbrains.notes.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

import ru.geekbrains.notes.R;
import ru.geekbrains.notes.observer.Publisher;

public class MainActivity extends AppCompatActivity {

    private Publisher publisher = new Publisher();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeToolBar();
        addFragment();
    }

    private void addFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, NotesFragment.newInstance())
                .commit();
    }

    private void initializeToolBar() {
        Toolbar toolbar = findViewById(R.id.notes_toolbar);
        setSupportActionBar(toolbar);
    }

    public Publisher getPublisher() {
        return publisher;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        List fragments = getSupportFragmentManager().getFragments();

        if (fragments.size() == 0) {
            return;
        }

        Fragment fragment = (Fragment) fragments.get(0);

        //if (fragment.getId() != R.id.)


    }
 }