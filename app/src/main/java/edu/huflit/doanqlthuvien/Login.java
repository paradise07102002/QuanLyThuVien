package edu.huflit.doanqlthuvien;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.animation.ArgbEvaluator;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.shape.ShapeAppearanceModel;

public class Login extends AppCompatActivity {
    private View background;
    Button btn_login;
    TextView tv_login;
    MyDatabase database;
    EditText username, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        database = new MyDatabase(this);
        anhXa();

        //Tạo màu nền
        setBackground();
        setColorTextView();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dangNhap();
            }
        });
    }
    public void anhXa()
    {
        username = (EditText) findViewById(R.id.login_username);
        password = (EditText) findViewById(R.id.login_pass);
        btn_login = (Button) findViewById(R.id.login_login);
        background = (View) findViewById(R.id.background_login);
        tv_login = (TextView) findViewById(R.id.tv_login);
    }
    //Code hiệu ứng chuyển màu
    public void test()
    {
        int color1 = Color.parseColor("#FF0000");
        int color2 = Color.parseColor("#FFA500");
        int color3 = Color.parseColor("#FFFF00");
        int color4 = Color.parseColor("#00FF00");

        ValueAnimator valueAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), color1, color2, color3, color4);
        valueAnimator.setDuration(2000);

        //hiệu ứng chuyển màu lặp đi lặp lại
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                tv_login.setBackgroundColor((int) valueAnimator.getAnimatedValue());
            }
        });
        valueAnimator.start();
    }
    //CODE HIỆU ỨNG MÀU
    public void test2()
    {
        int color1 = Color.parseColor("#8264FA");
        int color2 = Color.parseColor("#AE29F0");

        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
        gradientDrawable.setColors(new int[]{color1, color2});
        gradientDrawable.setGradientRadius(100);
        background.setBackground(gradientDrawable);
    }
    //Tạo màu nền cho màn hình Login
    public void setBackground()
    {
        int color1 = Color.parseColor("#8264FA");
        int color2 = Color.parseColor("#AE29F0");

        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
        gradientDrawable.setColors(new int[]{color1, color2});
        gradientDrawable.setGradientRadius(100);
        background.setBackground(gradientDrawable);
    }
    //Màu chuyển động cho chữ
    public void setColorTextView()
    {
        int[] colors = {
                Color.parseColor("#FF0000"),
                Color.parseColor("#FFA500"),
                Color.parseColor("#FFFF00"),
                Color.parseColor("#00FF00")
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
                tv_login.setTextColor(color);
                btn_login.setBackgroundColor((int) valueAnimator.getAnimatedValue());
            }
        });
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
            AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
            builder.setMessage("Tại khoản hoặc mật khẩu không chính xác!");
            builder.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.create().show();
            return;
        }
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username.getText().toString().trim());
        editor.putString("password", password.getText().toString().trim());
        editor.apply();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}