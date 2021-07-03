package com.alma.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.alma.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_loginEnter).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
       Intent open = new Intent(this, OptionsActivity.class);
       this.finish();
       startActivity(open);
    }
}