package com.example.filecreationactivity;

import static android.widget.Toast.LENGTH_LONG;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button mBtnCreateFile, mBtnShowFiles;
    private TextView notificationView;
    private Context mContext;
    private static final int REQUEST_PERMISSION_CODE = 12345;
    private File videoDir;

    private static final String[] REQUIRED_PERMISSION_LIST = new String[] {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        initUI(getApplicationContext());
    }

    private void initUI(Context context) {
        mBtnCreateFile = (Button) findViewById(R.id.create_file);
        mBtnShowFiles = (Button) findViewById(R.id.show_files);
        notificationView = (TextView) findViewById(R.id.notification_text);
        mBtnCreateFile.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                checkAndRequestPermissions();
            }
        });
        mBtnShowFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAllSavedFiles();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void createFile() {
//        File fileFolder = new File(Environment.getDataDirectory(), "PlayBackShots");

        // File Creation Options: context.getDataDir, MainActivity.this.getExternalFilesDirs(Environment.DIRECTORY_PICTURES
        // Environment.getExternalDir
        videoDir = new File(getApplicationContext().getFilesDir()
                + "/droame");
        notificationView.setText(videoDir.getPath());
        if(!videoDir.exists()) {
            videoDir.mkdirs();
            Toast.makeText(MainActivity.this, "File Created Successfully!", LENGTH_LONG);
            notificationView.setText("folder created successfully");
        }
        File textFile = new File(videoDir + "sampleTest1.txt");
        try {
            textFile.createNewFile();
            notificationView.setText("File Created successfully!");
        } catch (IOException e) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    notificationView.setText("Create New File Error:" + e.toString());
                }
            }, 5000);
        }
        try {
            FileOutputStream fos = new FileOutputStream(textFile);
            fos.write("Some Random Text".getBytes(StandardCharsets.UTF_8));
            fos.close();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    notificationView.setText("Write to File Successful!");
                }
            }, 5000);

        } catch (IOException e) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    notificationView.setText("Write to File Error:" + e.toString());
                }
            }, 10000);
        }
    }

    private void showAllSavedFiles() {
        if(videoDir == null) {
            notificationView.setText("No Video Directory found");
            return;
        }
        File[] videoFiles = videoDir.listFiles();
        if(videoFiles != null) {
            StringBuilder notify = new StringBuilder("Files Present :\n");
            for (File videoFile : videoFiles) {
                notify.append(videoFile.getName() + "\n");
            }
            notificationView.setText(notify);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void checkAndRequestPermissions() {
        // Check for permissions
        List<String> missingPermission = new ArrayList<>();
        for (String eachPermission : REQUIRED_PERMISSION_LIST) {
            if (ContextCompat.checkSelfPermission(mContext, eachPermission) != PackageManager.PERMISSION_GRANTED) {
                missingPermission.add(eachPermission);
            }
        }
        // Request for missing permissions
        if (missingPermission.isEmpty()) {
//            Toast.setResultToToast("Permissions Checked!");
            createFile();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this,
                    missingPermission.toArray(new String[missingPermission.size()]),
                    REQUEST_PERMISSION_CODE);
        }

    }
}