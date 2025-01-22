package com.example.banthan;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

// EnglishDetailActivity.java
public class EnglishDetailActivity extends AppCompatActivity {
    private RecyclerView englishSkillsRecycler;
    private MaterialButton addSkillButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english_detail);

        // Set up the MaterialToolbar as the app bar
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable back navigation
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        }

        // Handle toolbar navigation clicks
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }


    private void setupEnglishSkills() {
        List<EnglishSkill> skills = new ArrayList<>();
        skills.add(new EnglishSkill("Listening", "IELTS Listening Practice", 65, "TED Talk - 30 mins"));
        skills.add(new EnglishSkill("Speaking", "Speaking Practice", 55, "Partner Practice"));
        skills.add(new EnglishSkill("Reading", "Reading Comprehension", 70, "BBC News"));
        skills.add(new EnglishSkill("Writing", "Academic Writing", 60, "Essay - 250 words"));

        EnglishSkillsAdapter adapter = new EnglishSkillsAdapter(skills) {
            @NonNull
            @Override
            public EnglishSkillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }

            @Override
            public void onBindViewHolder(@NonNull EnglishSkillViewHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 0;
            }
        };
        englishSkillsRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        englishSkillsRecycler.setAdapter(adapter);
    }

    private void setupAddButton() {
        addSkillButton.setOnClickListener(v -> {
            // Handle add new skill
        });
    }
}
