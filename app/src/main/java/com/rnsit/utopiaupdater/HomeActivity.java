package com.rnsit.utopiaupdater;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button update_post,update_result;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        context = this;

        update_post = (Button)findViewById(R.id.update_post);
        update_result = (Button)findViewById(R.id.update_result);
        update_post.setOnClickListener(this);
        update_result.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.update_post:
                intent = new Intent(context,PostActivity.class);
                startActivity(intent);
                break;
            case R.id.update_result:
                intent = new Intent(context,ResultActivity.class);
                startActivity(intent);
                break;
        }
    }
}
