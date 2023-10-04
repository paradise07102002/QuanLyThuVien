package edu.huflit.doanqlthuvien.fragments;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import edu.huflit.doanqlthuvien.Login;
import edu.huflit.doanqlthuvien.MainActivity;
import edu.huflit.doanqlthuvien.ManHinhChinh;
import edu.huflit.doanqlthuvien.ManHinhChinh2;
import edu.huflit.doanqlthuvien.MyDatabase;
import edu.huflit.doanqlthuvien.OOP.User;
import edu.huflit.doanqlthuvien.R;

public class Login2 extends Fragment {
    View view;
    private View background;
    ImageView back;
    Button btn_login;
    TextView next_dang_ky;
    MyDatabase database;
    EditText username, password;
    ManHinhChinh manHinhChinh;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login2, container, false);
        manHinhChinh = (ManHinhChinh) getActivity();
        database = new MyDatabase(getActivity());
        anhXa();
        setColorTextView();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dangNhap();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manHinhChinh.gotoManHinhChinhAdmin();
            }
        });
        next_dang_ky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manHinhChinh.gotoDangKy();
            }
        });
        return view;
    }
    public void anhXa()
    {
        username = (EditText) view.findViewById(R.id.login_username2);
        password = (EditText) view.findViewById(R.id.login_pass2);
        btn_login = (Button) view.findViewById(R.id.login_login2);
        back = (ImageView) view.findViewById(R.id.login2_back);
        next_dang_ky = (TextView) view.findViewById(R.id.login2_next_dangky);
    }
    //CODE KHI ẤN BUTTON ĐĂNG NHẬP
    public void dangNhap()
    {
        if (username.getText().toString().trim().length() == 0)
        {
            String p = "<font color='#FF0000'>Chưa nhập username</font>";
            username.setHint(Html.fromHtml(p));
            return;
        }
        if (password.getText().toString().trim().length() == 0)
        {
            String p = "<font color='#FF0000'>Chưa nhập password</font>";
            password.setHint(Html.fromHtml(p));
            return;
        }
        boolean check = database.checkLogin(username.getText().toString().trim(), password.getText().toString().trim());
        if (check == false)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Tài khoản hoặc mật khẩu không chính xác!");
            builder.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.create().show();
            return;
        }
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username.getText().toString().trim());
        editor.putBoolean("is_login", true);
        editor.apply();

        User user = database.checkRole(username.getText().toString().trim());

        Menu navigationMenu = ManHinhChinh.navigationView.getMenu();
        MenuItem menuItem = navigationMenu.findItem(R.id.nav_tittle6);
        menuItem.setTitle("Đăng xuất");


        if (user.getRole_user().equals("admin"))
        {
            Intent intent = new Intent(getActivity(), ManHinhChinh.class);
            startActivity(intent);
        }
        else if (user.getRole_user().equals("user"))
        {
            Intent intent = new Intent(getActivity(), ManHinhChinh2.class);
            startActivity(intent);
        }
        else if (user.getRole_user().equals("librarian"))
        {

        }
    }
    public void setColorTextView()
    {
        int[] colors = {
                Color.parseColor("#99FFFF"),
                Color.parseColor("#CC00FF"),
                Color.parseColor("#CC99FF"),
                Color.parseColor("#CCFFFF")
        };
        // Tạo một `ValueAnimator` để chuyển đổi màu sắc
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), colors[0], colors[1], colors[2], colors[3]);
        valueAnimator.setDuration(6000);
        //lặp lại vô hạn
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        // Bắt đầu `ValueAnimator`
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                // Lấy giá trị màu hiện tại
                int color = (int) valueAnimator.getAnimatedValue();
                // Đặt màu cho textview
                btn_login.setBackgroundColor((int) valueAnimator.getAnimatedValue());
            }
        });
    }
}
