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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AllJobActivity extends AppCompatActivity {

    private Toolbar toolbar;

    //Recyclerview
    private RecyclerView recyclerView;

    //Firebase

    private DatabaseReference mAllJobPost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_job);

        toolbar = findViewById(R.id.all_job_post);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("All Job Post");

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Database
        mAllJobPost = FirebaseDatabase.getInstance().getReference().child("Public Database");
        mAllJobPost.keepSynced(true);

        recyclerView = findViewById(R.id.recycler_all_job);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Data> options = new FirebaseRecyclerOptions.Builder<Data>()
                .setQuery(mAllJobPost, Data.class)
                .build();

        FirebaseRecyclerAdapter<Data, MyViewHolder> adapter = new FirebaseRecyclerAdapter<Data, MyViewHolder>(options) {

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.alljobpost, parent, false);
                return new MyViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, final int position, @NonNull final Data model) {

                holder.setJobTitle(model.getTitle());
                holder.setJobDescription(model.getDescription());
                holder.setJobDate(model.getDate());
                holder.setJobSkills(model.getSkills());
                holder.setJobSalary(model.getSalary());

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(AllJobActivity.this, JobDetailsActivity.class);

                        intent.putExtra("title", model.getTitle());
                        intent.putExtra("date", model.getDate());
                        intent.putExtra("description", model.getDescription());
                        intent.putExtra("skills", model.getSkills());
                        intent.putExtra("salary", model.getSalary());

                        startActivity(intent);

                }
                });

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

        public void setJobTitle(String title) {
            TextView mTitle = mView.findViewById(R.id.all_job_post_title);
            mTitle.setText(title);
        }

        void setJobDate(String date) {
            TextView mDate = mView.findViewById(R.id.all_job_post_date);
            mDate.setText(date);
        }

        public void setJobDescription(String description) {
            TextView mDescription = mView.findViewById(R.id.all_job_post_description);
            mDescription.setText(description);
        }

        public void setJobSkills(String skills) {
            TextView mSkills = mView.findViewById(R.id.all_job_post_skills);
            mSkills.setText(skills);
        }

        public void setJobSalary(String salary) {
            TextView mSalary = mView.findViewById(R.id.all_job_post_salary);
            mSalary.setText(salary);
        }

    }

}