package edu.huflit.doanqlthuvien.ManHinhUser;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.huflit.doanqlthuvien.DBHelper;
import edu.huflit.doanqlthuvien.ManHinhChinh;
import edu.huflit.doanqlthuvien.MyDatabase;
import edu.huflit.doanqlthuvien.OOP.User;
import edu.huflit.doanqlthuvien.R;

public class ThongTinTaiKhoan extends Fragment {
    View view;
    TextView tv_username, tv_rank;
    TextView tv_fullname, tv_email, tv_phone;
    ImageView edit_fullname, edit_email, edit_phone;
    ImageView img_avartar, img_add_avartar;
    ImageView back;
    MyDatabase database;
    ManHinhChinh manHinhChinh;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.thong_tin_tai_khoan, container, false);
        manHinhChinh = (ManHinhChinh) getActivity();
        database = new MyDatabase(getActivity());
        anhXa();
        showTextView();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login",Context.MODE_PRIVATE);
                String get_username = sharedPreferences.getString("username", null);
                if (get_username != null)
                {
                    User user = database.checkRole(get_username);
                    if (user.getRole_user().equals("admin"))
                    {
                        manHinhChinh.gotoManHinhChinhAdmin();
                    }
                    else
                    {
                        manHinhChinh.gotoManHinhUser();
                    }
                }
            }
        });
        edit_fullname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manHinhChinh.gotoEditFullName();
            }
        });
        edit_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manHinhChinh.gotoEditEmail();
            }
        });
        edit_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manHinhChinh.gotoEditPhone();
            }
        });
        return view;
    }
    public void anhXa()
    {
        tv_username = (TextView) view.findViewById(R.id.tk_username);
        tv_rank = (TextView) view.findViewById(R.id.tk_rank);

        tv_fullname = (TextView) view.findViewById(R.id.tk_fullname);
        tv_email = (TextView) view.findViewById(R.id.tk_email);
        tv_phone = (TextView) view.findViewById(R.id.tk_phone);

        img_add_avartar = (ImageView) view.findViewById(R.id.tk_add_avartar);
        img_avartar = (ImageView) view.findViewById(R.id.tk_avartar);

        back = (ImageView) view.findViewById(R.id.tk_back);

        edit_fullname = (ImageView) view.findViewById(R.id.tk_edit_fullname);
        edit_email = (ImageView) view.findViewById(R.id.tk_edit_email);
        edit_phone = (ImageView) view.findViewById(R.id.tk_edit_phone);

    }
    public void showTextView()
    {
        //Kiểm tra đăng nhập chưa, nếu đăng nhập rồi thì mới hiển thị thông tin tài khoản
        SharedPreferences get_user = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        boolean check_login = get_user.getBoolean("is_login", false);
        if (check_login == false)//Chưa đăng nhập
        {
            Toast.makeText(getActivity(), "Bạn chưa đăng nhập", Toast.LENGTH_LONG).show();
        }
        else//Đã đăng nhập
        {
            String get_username = get_user.getString("username", null);
            if (get_username != null)
            {
                Cursor cursor = database.getUserByUsername(get_username);//Truy vấn vô database lấy thông tin tài khoản
                if (cursor != null)
                {
                    int username_index = cursor.getColumnIndex(DBHelper.USERNAME_USER);
                    int rank_index = cursor.getColumnIndex(DBHelper.LOAI_KH_USER);
                    int fullname_index = cursor.getColumnIndex(DBHelper.FULLNAME_USER);
                    int email_index = cursor.getColumnIndex(DBHelper.EMAIL_USER);
                    int phone_index = cursor.getColumnIndex(DBHelper.PHONE_USER);

                    cursor.moveToFirst();
                    tv_username.setText(cursor.getString(username_index));
                    tv_rank.setText(cursor.getString(rank_index));
                    tv_fullname.setText(cursor.getString(fullname_index));
                    tv_email.setText(cursor.getString(email_index));
                    tv_phone.setText(cursor.getString(phone_index));
                }
            }
        }
    }
}
