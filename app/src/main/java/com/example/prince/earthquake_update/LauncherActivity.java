package com.example.prince.earthquake_update;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        final ProgressBar progressBar=findViewById(R.id.progressBar);
        Toast.makeText(LauncherActivity.this,"Created By: Prashant Kumar",Toast.LENGTH_SHORT).show();
        progressBar.animate().alpha(1).setDuration(500);
        progressBar.setIndeterminate(true);
        Query_Util i=new Query_Util(LauncherActivity.this);
        i.execute();


    }
}
