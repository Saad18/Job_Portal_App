package com.example.jobportalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jobportalapp.model.Data;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostJobActivity extends AppCompatActivity {

    private FloatingActionButton fabBtn;

    Toolbar toolbar;

    //Recycler view..

    private RecyclerView recyclerView;

    //Firebase..

    private FirebaseAuth mAuth;
    private DatabaseReference JobPostDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);


        toolbar = findViewById(R.id.toolbar_post_Job);
        setSupportActionBar(toolbar);

        //Back Button Enable
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        fabBtn = findViewById(R.id.fab_add);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser mUser = mAuth.getCurrentUser();
        assert mUser != null;
        String uId = mUser.getUid();

        JobPostDatabase = FirebaseDatabase.getInstance().getReference().child("Job Post").child(uId);

        recyclerView = findViewById(R.id.recycler_job_post_id);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),InsertJobPostActivity.class));
            }
        });

    }


    //Firebase Recyclerview
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Data> options = new FirebaseRecyclerOptions.Builder<Data>()
                .setQuery(JobPostDatabase, Data.class)
                .build();

        FirebaseRecyclerAdapter<Data, MyViewHolder> adapter = new FirebaseRecyclerAdapter<Data, MyViewHolder>(options) {

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.job_post_item, parent, false);
                return new MyViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, final int position, @NonNull final Data model) {

               holder.setTitle(model.getTitle());
               holder.setJobDescription(model.getDescription());
               holder.setDate(model.getDate());
               holder.setJobSkills(model.getSkills());
               holder.setJobSalary(model.getSalary());

            }


        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        View mView;

        MyViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setTitle(String title) {
            TextView mTitle= mView.findViewById(R.id.job_title);
            mTitle.setText(title);
        }

        void setDate(String date) {
            TextView mDate = mView.findViewById(R.id.job_date);
            mDate.setText(date);
        }

        public void setJobDescription(String description){
            TextView mDescription = mView.findViewById(R.id.job_description);
            mDescription.setText(description);
        }
        public void setJobSkills(String skills){
            TextView mSkills = mView.findViewById(R.id.job_skills);
            mSkills.setText(skills);
        }
        public void setJobSalary(String salary){
            TextView mSalary = mView.findViewById(R.id.job_salary);
            mSalary.setText(salary);
        }

    }



}