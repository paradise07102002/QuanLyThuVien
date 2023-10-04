package edu.huflit.doanqlthuvien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class ManHinhChinh2 extends AppCompatActivity {

    private static final int FR_HOME = 0;
    private static final int FR_MAN_HINH_CHINH_ADMIN = 1;
    private static final int FR_LOGIN = 2;

    private int currentFragment = FR_HOME;

    DrawerLayout drawerLayout;
    Toolbar toolbar;

    public static NavigationView navigationView;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chinh2);
    }
}