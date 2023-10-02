package edu.huflit.doanqlthuvien;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import edu.huflit.doanqlthuvien.fragment_admin.ManHinhChinhAdmin;
import edu.huflit.doanqlthuvien.fragment_dau_sach.AddDauSach;
import edu.huflit.doanqlthuvien.fragment_dau_sach.ManHinhDauSach;
import edu.huflit.doanqlthuvien.fragment_dau_sach.UpdateDauSach;
import edu.huflit.doanqlthuvien.fragments.HomeFragment;

public class ManHinhChinh extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final int FR_HOME = 0;
    private static final int FR_MAN_HINH_CHINH_ADMIN = 1;

    private int currentFragment = FR_HOME;

    DrawerLayout drawerLayout;
    Toolbar toolbar;

    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;

    ImageView imageView_test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chinh);
        setTitle("");
        anhXa();
        replaceFragment(new ManHinhChinhAdmin());
        //ADD Toolbar
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,   R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.action_admin_1)
                {
                    if (currentFragment != FR_MAN_HINH_CHINH_ADMIN)
                    {
                        replaceFragment(new ManHinhChinhAdmin());
                        currentFragment = FR_MAN_HINH_CHINH_ADMIN;
                    }
                }
                return true;
            }
        });
    }
    public void anhXa()
    {
        drawerLayout = (DrawerLayout) findViewById(R.id.hdrawer_layout);
        toolbar = (Toolbar) findViewById(R.id.htoolbar);
        navigationView = (NavigationView) findViewById(R.id.hnavigation_view);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_tittle1)
        {

        }
        else if (id == R.id.nav_tittle2)
        {

        }

        //Đóng drawer
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        //Nếu drawer đang ở thì khi ấn back sẽ back
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();//ngược lại thì cho thoát áp
        }
    }
    private void replaceFragment(Fragment fragment)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.hcontent_frame, fragment);
        transaction.commit();
    }

    //HÀM CHUYỂN FRAGMENT
    public void gotoManHinhDauSach()
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ManHinhDauSach manHinhDauSach = new ManHinhDauSach();

        fragmentTransaction.replace(R.id.hcontent_frame, manHinhDauSach);
        fragmentTransaction.commit();
    }
    public void gotoManHinhAddDauSach()
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        AddDauSach addDauSach = new AddDauSach();

        fragmentTransaction.replace(R.id.hcontent_frame, addDauSach);
        fragmentTransaction.commit();
    }
    public void gotoManHinhChinhAdmin()
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ManHinhChinhAdmin manHinhChinhAdmin = new ManHinhChinhAdmin();

        fragmentTransaction.replace(R.id.hcontent_frame, manHinhChinhAdmin);
        fragmentTransaction.commit();
    }
    public void gotoUpdateDauSach()
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        UpdateDauSach updateDauSach = new UpdateDauSach();

        fragmentTransaction.replace(R.id.hcontent_frame, updateDauSach);
        fragmentTransaction.commit();
    }
}