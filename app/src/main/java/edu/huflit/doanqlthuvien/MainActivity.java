package edu.huflit.doanqlthuvien;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;

import edu.huflit.doanqlthuvien.OOP.User;

public class MainActivity extends AppCompatActivity {
    ImageView img_main;
    MyDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
//        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        //SharedPreferences.Editor editor = sharedPreferences.edit();
        //editor.putBoolean("is_login", false);
        //editor.apply();
        img_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                boolean check = sharedPreferences.getBoolean("is_login", false);
                if (check == false)
                {
                    Intent inext = new Intent(getApplicationContext(), ManHinhChinh.class);
                    startActivity(inext);
                }
                else
                {
                    Intent inext = new Intent(getApplicationContext(), ManHinhChinh.class);
                    startActivity(inext);
                }
            }
        });
    }
    public void anhXa()
    {
        img_main = (ImageView) findViewById(R.id.img_main);
    }

}