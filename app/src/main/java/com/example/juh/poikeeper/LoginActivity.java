package com.example.juh.poikeeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class LoginActivity extends AppCompatActivity {

    private Button mButton;
    private ImageView mLogoImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mButton = findViewById(R.id.login_start_button);
        mLogoImage = findViewById(R.id.login_image_logo);

        // INITIALIZE LISTENERS
        mButton.setOnClickListener(buttonListener);
        mLogoImage.setOnClickListener(imageListener);

    }

    //===============================
    //           LISTENERS
    //===============================

    private View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.e("New intent", String.format("Intented activity: {0}", LoginActivity.class.toString()));
            Intent intent = new Intent(LoginActivity.this, PoiListActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener imageListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
}
