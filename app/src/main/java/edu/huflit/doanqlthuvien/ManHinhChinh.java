package edu.huflit.doanqlthuvien;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.huflit.doanqlthuvien.ManHinhUser.DoiMatKhau;
import edu.huflit.doanqlthuvien.ManHinhUser.ManHinhUser;
import edu.huflit.doanqlthuvien.ManHinhUser.QuanLySachMuonUser;
import edu.huflit.doanqlthuvien.ManHinhUser.ThongTinTaiKhoan;
import edu.huflit.doanqlthuvien.OOP.User;
import edu.huflit.doanqlthuvien.YeuThich.MH_YeuThich;
import edu.huflit.doanqlthuvien.edit.EditEmail;
import edu.huflit.doanqlthuvien.edit.EditFullName;
import edu.huflit.doanqlthuvien.edit.EditPhone;
import edu.huflit.doanqlthuvien.fragment_admin.EditHeader;
import edu.huflit.doanqlthuvien.fragment_admin.ManHinhChinhAdmin;
import edu.huflit.doanqlthuvien.fragment_dau_sach.AddDauSach;
import edu.huflit.doanqlthuvien.fragment_dau_sach.ManHinhDauSach;
import edu.huflit.doanqlthuvien.fragment_dau_sach.UpdateDauSach;
import edu.huflit.doanqlthuvien.fragment_muon_tra.ManHinhCreatePhieuMuon;
import edu.huflit.doanqlthuvien.fragment_muon_tra.ManHinhMuonTra;
import edu.huflit.doanqlthuvien.fragment_muon_tra.QuanLySachMuon;
import edu.huflit.doanqlthuvien.fragment_sach.AddSach;
import edu.huflit.doanqlthuvien.fragment_sach.DetailSach;
import edu.huflit.doanqlthuvien.fragment_sach.ManHinhSach;
import edu.huflit.doanqlthuvien.fragment_sach.TimkiemSach;
import edu.huflit.doanqlthuvien.fragment_sach.UpdateSach;
import edu.huflit.doanqlthuvien.fragments.DangKy2;
import edu.huflit.doanqlthuvien.fragments.DatePickerFragment;
import edu.huflit.doanqlthuvien.fragments.HomeFragment;
import edu.huflit.doanqlthuvien.fragments.Login2;
import edu.huflit.doanqlthuvien.fragments.TinNhan;
import edu.huflit.doanqlthuvien.fragments.TinNhan2;
import edu.huflit.doanqlthuvien.fragments.TinNhan3;

public class ManHinhChinh extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final int FR_HOME = 0;
    private static final int FR_MAN_HINH_CHINH_ADMIN = 1;
    private static final int FR_MAN_HINH_CHINH_USER = 2;
    private static final int FR_TIN_NHAN = 3;
    private static final int FR_DOI_MK = 4;
    private static final int FR_LOGIN = 5;

    private int currentFragment = FR_HOME;

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    public static NavigationView navigationView;
    BottomNavigationView bottomNavigationView;
    MyDatabase database;
    EditText edt_tim_kiem;
    ImageView img_tim_kiem;
    TextView show_new_message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chinh);
        setTitle("");
        database = new MyDatabase(getApplicationContext());
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);

        anhXa();

        SharedPreferences get_user = getSharedPreferences("login", MODE_PRIVATE);
        String username = get_user.getString("username", null);
        boolean is_login = get_user.getBoolean("is_login", false);
        if (is_login == false)
        {
            TextView show_username2 = navigationView.getHeaderView(0).findViewById(R.id.username1);
            TextView show_email2 = navigationView.getHeaderView(0).findViewById(R.id.email1);
            show_email2.setText("Email");
            show_username2.setText("Username");
            if (currentFragment != FR_MAN_HINH_CHINH_USER)
            {
                replaceFragment(new ManHinhUser());
                currentFragment = FR_MAN_HINH_CHINH_USER;
            }
        }
        else
        {
            //LẤY USER NAME EMAIL SHOW LÊN HEADER
            TextView show_username = navigationView.getHeaderView(0).findViewById(R.id.username1);
            TextView show_email = navigationView.getHeaderView(0).findViewById(R.id.email1);
            Cursor cursor = database.getUserByUsername(username);

                int username_index = cursor.getColumnIndex(DBHelper.USERNAME_USER);
                int email_index = cursor.getColumnIndex(DBHelper.EMAIL_USER);
                cursor.moveToFirst();

                show_username.setText(cursor.getString(username_index));
                show_email.setText(cursor.getString(email_index));
                cursor.close();

            User user = database.checkRole(username);
            if (user.getRole_user().equals("admin"))
            {
                if (currentFragment != FR_MAN_HINH_CHINH_ADMIN)
                {
                    replaceFragment(new ManHinhChinhAdmin());
                    currentFragment = FR_MAN_HINH_CHINH_ADMIN;
                }
            }
            else
            {
                if (currentFragment != FR_MAN_HINH_CHINH_USER)
                {
                    replaceFragment(new ManHinhUser());
                    currentFragment = FR_MAN_HINH_CHINH_USER;
                }
            }
            nhacHenTraSach();
        }
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
                    SharedPreferences get_user = getSharedPreferences("login", MODE_PRIVATE);
                    String username = get_user.getString("username", null);
                    boolean iss_login = get_user.getBoolean("is_login", false);
                    if (iss_login == false)
                    {

                    }
                    else
                    {
                        User user = database.checkRole(username);
                        if (user.getRole_user().equals("admin"))
                        {
                            if (currentFragment != FR_MAN_HINH_CHINH_ADMIN)
                            {
                                currentFragment = FR_MAN_HINH_CHINH_ADMIN;
                            }
                            replaceFragment(new ManHinhChinhAdmin());
                        }
                        else
                        {
                            if (currentFragment != FR_MAN_HINH_CHINH_USER)
                            {
                                currentFragment = FR_MAN_HINH_CHINH_USER;
                            }
                            replaceFragment(new ManHinhUser());
                        }
                    }
                }
                if (itemId == R.id.action_tin_nhan)
                {
                    //

                    //
                    SharedPreferences get_user = getSharedPreferences("login", MODE_PRIVATE);
                    String username = get_user.getString("username", null);
                    boolean iss_login = get_user.getBoolean("is_login", false);
                    if (iss_login == false)
                    {
                        Toast.makeText(getApplicationContext(), "Bạn chưa đăng nhập", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        User user = database.checkRole(username);
                        if (user.getRole_user().equals("admin"))
                        {
                            if (currentFragment != FR_TIN_NHAN)
                            {
                                currentFragment = FR_TIN_NHAN;
                            }
                            replaceFragment(new TinNhan());
                        }
                        else
                        {
                            if (currentFragment != FR_TIN_NHAN)
                            {
                                currentFragment = FR_TIN_NHAN;
                            }
                            replaceFragment(new TinNhan2());
                        }
                    }
                }
                if (itemId == R.id.action_like)
                {
                    SharedPreferences get_user = getSharedPreferences("login", MODE_PRIVATE);
                    boolean iss_login = get_user.getBoolean("is_login", false);
                    if (iss_login == false)
                    {
                        Toast.makeText(getApplicationContext(), "Bạn chưa đăng nhập", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        replaceFragment(new MH_YeuThich());
                    }
                }
                return true;
            }
        });
        //Kiểm tra đăng nhập, nếu chưa thì hiện đăng nhập, ngược lại hiện đăng xuất trên menu
        //SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        Menu navigationMenu = navigationView.getMenu();
        MenuItem menuItem = navigationMenu.findItem(R.id.nav_tittle6);
        boolean check_login = sharedPreferences.getBoolean("is_login", false);
        if (check_login == false)
        {
            menuItem.setTitle("Đăng nhập");
            show_new_message.setVisibility(View.INVISIBLE);
        }
        else
        {
            menuItem.setTitle("Đăng xuất");
        }
        //
        Menu bottomnavigationMenu = bottomNavigationView.getMenu();
        MenuItem menuItem_bot = bottomnavigationMenu.findItem(R.id.action_tin_nhan);

        boolean iss_login = get_user.getBoolean("is_login", false);
        if (iss_login == false)
        {
            menuItem_bot.setTitle("Hỗ trợ");
        }
        else
        {
            User user = database.checkRole(username);
            if (user.getRole_user().equals("admin"))
            {
                menuItem_bot.setTitle("Tin nhắn");
                //show số lượng tin nhắn mới
                int countt = countNewMessage();
                show_new_message.setText(Integer.toString(countt));
            }
            else
            {
                menuItem_bot.setTitle("Hỗ trợ");
            }
        }


        //TÌM KIẾM SÁCH
        img_tim_kiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_tim_kiem.length() == 0)
                {
                    Toast.makeText(ManHinhChinh.this, "Vui lòng nhập thông tin tìm kiếm", Toast.LENGTH_LONG).show();
                }
                else
                {
                    String ten_sach_tim_kiem = edt_tim_kiem.getText().toString().trim();
                    SharedPreferences name_sach = getSharedPreferences("search_sach", MODE_PRIVATE);
                    SharedPreferences.Editor editor = name_sach.edit();
                    editor.putString("name_sach", ten_sach_tim_kiem);
                    editor.apply();
                    replaceFragment(new TimkiemSach());
                }
            }
        });
    }
    public void anhXa()
    {
        drawerLayout = (DrawerLayout) findViewById(R.id.hdrawer_layout);
        toolbar = (Toolbar) findViewById(R.id.htoolbar);
        navigationView = (NavigationView) findViewById(R.id.hnavigation_view);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);

        img_tim_kiem = (ImageView) findViewById(R.id.img_tim_kiem);
        edt_tim_kiem = (EditText) findViewById(R.id.edt_tim_kiem);
        show_new_message = (TextView) findViewById(R.id.show_new_message);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_tittle3)
        {
            SharedPreferences get_user = getSharedPreferences("login", Context.MODE_PRIVATE);
            String username = get_user.getString("username", null);
            if (username == null)
            {
                Toast.makeText(ManHinhChinh.this, "Bạn chưa đăng nhập", Toast.LENGTH_LONG).show();
            }
            else
            {
                replaceFragment(new QuanLySachMuonUser());
            }
        }else if (id == R.id.nav_tittle4)
        {
            SharedPreferences get_user = getSharedPreferences("login", MODE_PRIVATE);
            boolean isss_login = get_user.getBoolean("is_login", false);
            if (isss_login == false)
            {
                Toast.makeText(ManHinhChinh.this, "Bạn chưa đăng nhập", Toast.LENGTH_LONG).show();
            }
            else
            {
                replaceFragment(new ThongTinTaiKhoan());
            }
        }
        else if (id == R.id.nav_tittle5)
        {
            SharedPreferences get_user = getSharedPreferences("login", MODE_PRIVATE);
            boolean isss_login = get_user.getBoolean("is_login", false);
            if (isss_login == false)
            {
                Toast.makeText(ManHinhChinh.this, "Bạn chưa đăng nhập", Toast.LENGTH_LONG).show();
            }
            else
            {
                if (currentFragment != FR_DOI_MK)
                {
                    replaceFragment(new DoiMatKhau());
                    currentFragment = FR_DOI_MK;
                }
            }
        }
        else if (id == R.id.nav_tittle6)
        {
            SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            boolean check_login = sharedPreferences.getBoolean("is_login", false);
            if (check_login == false)
            {
                if (currentFragment != FR_LOGIN)
                {
                    currentFragment = FR_LOGIN;
                    replaceFragment(new Login2());
                }
//                else
//                {
//                    replaceFragment(new Login2());
//                }
            }
            else if (check_login)
            {
                //Khi đăng xuất thì header ko còn hiện username và email
                TextView show_username2 = navigationView.getHeaderView(0).findViewById(R.id.username1);
                TextView show_email2 = navigationView.getHeaderView(0).findViewById(R.id.email1);
                show_email2.setText("Email");
                show_username2.setText("Username");

                SharedPreferences sharedPreferences1 = getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor editorr = sharedPreferences1.edit();
                editorr.putBoolean("is_login", false);
                editorr.putString("username", null);
                editorr.apply();
                Toast.makeText(ManHinhChinh.this, "Đã đăng xuất", Toast.LENGTH_LONG).show();

                //Kiểm tra đăng nhập, nếu chưa thì hiện đăng nhập, ngược lại hiện đăng xuất trên menu
                Menu navigationMenu = navigationView.getMenu();
                MenuItem menuItem = navigationMenu.findItem(R.id.nav_tittle6);
                menuItem.setTitle("Đăng nhập");
                replaceFragment(new ManHinhUser());
                currentFragment = FR_HOME;

                SharedPreferences get_user = getSharedPreferences("login", MODE_PRIVATE);

                Menu bottomnavigationMenu = bottomNavigationView.getMenu();
                MenuItem menuItem_bot = bottomnavigationMenu.findItem(R.id.action_tin_nhan);
                menuItem_bot.setTitle("Hỗ trợ");

                show_new_message.setVisibility(View.INVISIBLE);
            }
        }

        //Đóng drawer
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        if (sharedPreferences.getBoolean("is_login", false))
        {
            Cursor cursor = database.getUserByUsername(sharedPreferences.getString("username", null));
            cursor.moveToFirst();
            int role_index = cursor.getColumnIndex(DBHelper.ROLE_USER);
            String role = cursor.getString(role_index);
            if (role.equals("admin"))
            {
                Menu menuu = navigationView.getMenu();
                MenuItem menuItem = menuu.findItem(R.id.nav_tittle3);
                menuItem.setVisible(false);

            }
        }
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

    //HÀM CHUYỂN FRAGMENT (ĐẦU SÁCH)
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
    //HÀM CHUYỂN FRAGMENT (SÁCH)
    public void gotoManHinhSach()
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ManHinhSach manHinhSach = new ManHinhSach();

        fragmentTransaction.replace(R.id.hcontent_frame, manHinhSach);
        fragmentTransaction.commit();
    }
    public void gotoManHinhAddSach()
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        AddSach addSach = new AddSach();

        fragmentTransaction.replace(R.id.hcontent_frame, addSach);
        fragmentTransaction.commit();
    }
    public void gotoUpdateSach()
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        UpdateSach updateSach = new UpdateSach();

        fragmentTransaction.replace(R.id.hcontent_frame, updateSach);
        fragmentTransaction.commit();
    }
    public void gotoDetailSach()
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        DetailSach detailSach = new DetailSach();

        fragmentTransaction.replace(R.id.hcontent_frame, detailSach);
        fragmentTransaction.commit();
    }
    public void gotoDangKy()
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        DangKy2 dangKy2 = new DangKy2();

        fragmentTransaction.replace(R.id.hcontent_frame, dangKy2);
        fragmentTransaction.commit();
    }
    public void gotoLogin()
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Login2 login2 = new Login2();

        fragmentTransaction.replace(R.id.hcontent_frame, login2);
        fragmentTransaction.commit();
    }
    public void gotoMuonTra()
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ManHinhMuonTra manHinhMuonTra = new ManHinhMuonTra();

        fragmentTransaction.replace(R.id.hcontent_frame, manHinhMuonTra);
        fragmentTransaction.commit();
    }
    public void gotoCreatePhieuMuon()
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ManHinhCreatePhieuMuon manHinhCreatePhieuMuon = new ManHinhCreatePhieuMuon();

        fragmentTransaction.replace(R.id.hcontent_frame, manHinhCreatePhieuMuon);
        fragmentTransaction.commit();
    }
    public void gotoQuanLyMuonTra()
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        QuanLySachMuon quanLySachMuon = new QuanLySachMuon();

        fragmentTransaction.replace(R.id.hcontent_frame, quanLySachMuon);
        fragmentTransaction.commit();
    }
    public void gotoManHinhUser()
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ManHinhUser manHinhUser = new ManHinhUser();

        fragmentTransaction.replace(R.id.hcontent_frame, manHinhUser);
        fragmentTransaction.commit();
    }
    public void gotoEditFullName()
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        EditFullName editFullName = new EditFullName();

        fragmentTransaction.replace(R.id.hcontent_frame, editFullName);
        fragmentTransaction.commit();
    }
    public void gotoEditEmail()
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        EditEmail editEmail = new EditEmail();

        fragmentTransaction.replace(R.id.hcontent_frame, editEmail);
        fragmentTransaction.commit();
    }
    public void gotoEditPhone()
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        EditPhone editPhone = new EditPhone();

        fragmentTransaction.replace(R.id.hcontent_frame, editPhone);
        fragmentTransaction.commit();
    }
    public void gotoThongTinTaiKhoan()
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ThongTinTaiKhoan thongTinTaiKhoan = new ThongTinTaiKhoan();

        fragmentTransaction.replace(R.id.hcontent_frame, thongTinTaiKhoan);
        fragmentTransaction.commit();
    }
    public void gotoChatAdmin()
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        TinNhan3 tinNhan3 = new TinNhan3();

        fragmentTransaction.replace(R.id.hcontent_frame, tinNhan3);
        fragmentTransaction.commit();
    }
    public void gotoTinNhan()
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        TinNhan tinNhan = new TinNhan();

        fragmentTransaction.replace(R.id.hcontent_frame, tinNhan);
        fragmentTransaction.commit();
    }
    //NHẮC HẸN TRẢ SÁCH
    public void nhacHenTraSach()
    {
        //Lấy id của user
        int id_user;
        SharedPreferences get_user = getSharedPreferences("login", MODE_PRIVATE);
        Cursor get_id_user = database.getUserByUsername(get_user.getString("username", null));
        if (get_id_user != null)
        {
            int id_index = get_id_user.getColumnIndex(DBHelper.ID_USER);
            get_id_user.moveToFirst();
            id_user = get_id_user.getInt(id_index);

            //Lấy bảng mượn trả có chưa id của user này
            Cursor cursor = database.getNgayTra(id_user);
            if (cursor != null)
            {
                int ngay_tra_index = cursor.getColumnIndex(DBHelper.NGAY_TRA_MTS);
                while (cursor.moveToNext())
                {
                    String ngay_tra = cursor.getString(ngay_tra_index);
                    //Ép chuỗi về Date
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    Date date_tra_sach;
                    try {
                        date_tra_sach = sdf.parse(ngay_tra);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    //Lấy ngày tháng năm hiện tại
                    Date currentDate = new Date();
                    //So sánh ngày trả và ngày hiện tại
                    int so_sanh = currentDate.compareTo(date_tra_sach);
                    if (so_sanh == 0)
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ManHinhChinh.this);
                        builder.setIcon(R.drawable.icon_question);
                        builder.setMessage("Hôm nay là hạn trả sách");
                        builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        builder.create().show();
                        return;
                    }
                    else if (so_sanh > 0)
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ManHinhChinh.this);

                        builder.setMessage("Bạn đã trễ hẹn trả sách");
                        builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        builder.create().show();
                        return;
                    }
                }
            }
        }
    }
    public int countNewMessage()
    {
        int count_chat = 0;
        //Lấy số lượng tin nhắn mới
        Cursor cursor = database.getCountMessage();
        if (cursor != null)
        {
            int new_message_index = cursor.getColumnIndex(DBHelper.COUNT_NEW_MESSAGE);
            while (cursor.moveToNext())
            {
                count_chat = count_chat + cursor.getInt(new_message_index);
            }
        }
        return count_chat;
    }
    public void updateMessage()
    {
        show_new_message.setText(Integer.toString(countNewMessage()));

    }
}