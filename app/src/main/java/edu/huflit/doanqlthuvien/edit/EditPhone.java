package edu.huflit.doanqlthuvien.edit;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import edu.huflit.doanqlthuvien.DBHelper;
import edu.huflit.doanqlthuvien.ManHinhChinh;
import edu.huflit.doanqlthuvien.MyDatabase;
import edu.huflit.doanqlthuvien.R;

public class EditPhone extends Fragment {
    View view;
    ImageView back;
    Button update;
    EditText phone;
    MyDatabase database;
    ManHinhChinh manHinhChinh;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.edit_phone, container, false);
        anhXa();
        database = new MyDatabase(getActivity());
        manHinhChinh = (ManHinhChinh) getActivity();
        showPhone();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manHinhChinh.gotoThongTinTaiKhoan();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phone.length() == 0)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Số điện thoại không được để trống");
                    builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.create().show();
                }
                else
                {
                    SharedPreferences get_user = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
                    String get_username = get_user.getString("username", null);

                    database.editPhone(get_username, phone.getText().toString().trim());
                    Toast.makeText(getActivity(), "Cập nhật số điện thoại thành công", Toast.LENGTH_LONG).show();
                    manHinhChinh.gotoThongTinTaiKhoan();
                }
            }
        });
        return view;
    }
    public void anhXa()
    {
        back = (ImageView) view.findViewById(R.id.edit_phone_back);
        update = (Button) view.findViewById(R.id.btn_update_phone);
        phone = (EditText) view.findViewById(R.id.edt_edit_phone);
    }
    public void showPhone()
    {
        SharedPreferences get_user = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        String get_username = get_user.getString("username", null);
        if (get_username != null)
        {
            Cursor cursor = database.getUserByUsername(get_username);//Truy vấn vô database lấy thông tin tài khoản
            if (cursor != null)
            {
                int phone_index = cursor.getColumnIndex(DBHelper.PHONE_USER);
                cursor.moveToFirst();
                phone.setText(cursor.getString(phone_index));

            }
        }
    }
}
