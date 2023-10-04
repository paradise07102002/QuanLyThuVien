package edu.huflit.doanqlthuvien.fragment_admin;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import edu.huflit.doanqlthuvien.MainActivity;
import edu.huflit.doanqlthuvien.ManHinhChinh;
import edu.huflit.doanqlthuvien.R;
import edu.huflit.doanqlthuvien.fragment_dau_sach.ManHinhDauSach;

public class ManHinhChinhAdmin extends Fragment {
    View view;
    View background;
    //Các view
    ImageView img_next_dau_sach, img_next_sach;

    private ManHinhChinh manHinhChinh;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.man_hinh_chinh_admin, container, false);
        manHinhChinh = (ManHinhChinh) getActivity();
        anhXa();
        setColorTextView();

        //ẤN VÀO ẢNH ĐỂ TỚI MÀN HÌNH ĐẦU SÁCH
        img_next_dau_sach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manHinhChinh.gotoManHinhDauSach();
            }
        });

        img_next_sach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manHinhChinh.gotoManHinhSach();
            }
        });
        return view;
    }
    public void anhXa()
    {
        background = (View) view.findViewById(R.id.ibackground_mainscreen_admin);
        img_next_dau_sach = (ImageView) view.findViewById(R.id.next_dau_sach);
        img_next_sach = (ImageView) view.findViewById(R.id.next_mh_sach);
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
                background.setBackgroundColor((int) valueAnimator.getAnimatedValue());
            }
        });
    }
}
