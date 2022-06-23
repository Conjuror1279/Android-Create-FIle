package com.example.filecreationactivity;

import static android.widget.Toast.LENGTH_LONG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Button mBtnCreateFile;
    private TextView notificationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI(getApplicationContext());
    }

    private void initUI(Context context) {
        mBtnCreateFile = (Button) findViewById(R.id.create_file);
        notificationView = (TextView) findViewById(R.id.notification_text);
        mBtnCreateFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                File fileFolder = new File(Environment.getDataDirectory(), "PlayBackShots");
                File videoFile = new File("VideoView.mp4");
                if(!videoFile.exists()) {
                    try {
                        videoFile.createNewFile();
                        Toast.makeText(getApplicationContext(), "File Created Successfully!", LENGTH_LONG);
                        notificationView.setText("File Created Successfully");
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(), e.toString(), LENGTH_LONG);
                        notificationView.setText("Error" + e.toString());
                    }
                }

            }
        });
    }
}