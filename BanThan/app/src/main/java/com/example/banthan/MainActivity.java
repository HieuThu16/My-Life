package com.example.banthan;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

// MainActivity.java
public class MainActivity extends AppCompatActivity {
    private RecyclerView skillsRecycler;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        skillsRecycler = findViewById(R.id.skills_recycler);
        bottomNav = findViewById(R.id.bottom_navigation);

        setupSkillsRecycler();
        setupBottomNavigation();
    }

    private void setupSkillsRecycler() {
        List<Skill> skills = new ArrayList<>();
        skills.add(new Skill("English", "Language Development", 60));
        skills.add(new Skill("Coding", "Programming Skills", 75));

        SkillsAdapter adapter = new SkillsAdapter(skills);
        skillsRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        skillsRecycler.setAdapter(adapter);

        adapter.setOnItemClickListener(position -> {
            if (skills.get(position).getTitle().equals("English")) {
                startActivity(new Intent(this, EnglishDetailActivity.class));
            }
        });
    }

    private void setupBottomNavigation() {
        bottomNav.setOnItemSelectedListener(item -> {
            // Handle navigation
            return true;
        });
    }
}

