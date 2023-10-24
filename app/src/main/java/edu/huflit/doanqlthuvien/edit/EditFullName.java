package edu.huflit.doanqlthuvien.edit;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.Image;
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
import edu.huflit.doanqlthuvien.fragment_sach.UpdateSach;

public class EditFullName extends Fragment {
    View view;
    ImageView back;
    Button update;
    EditText fullname;
    MyDatabase database;
    ManHinhChinh manHinhChinh;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.edit_fullname, container, false);
        anhXa();
        database = new MyDatabase(getActivity());
        manHinhChinh = (ManHinhChinh) getActivity();
        showFullName();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manHinhChinh.gotoThongTinTaiKhoan();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fullname.length() == 0)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Fullname không được để trống");
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

                    database.editFullName(get_username, fullname.getText().toString().trim());
                    Toast.makeText(getActivity(), "Cập nhật fullname thành công", Toast.LENGTH_LONG).show();
                    manHinhChinh.gotoThongTinTaiKhoan();
                }
            }
        });
        return view;
    }
    public void anhXa()
    {
        back = (ImageView) view.findViewById(R.id.edit_fullname_back);
        update = (Button) view.findViewById(R.id.btn_update_fullname);
        fullname = (EditText) view.findViewById(R.id.edt_edit_fullname);
    }
    public void showFullName()
    {
        SharedPreferences get_user = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        String get_username = get_user.getString("username", null);
        if (get_username != null)
        {
            Cursor cursor = database.getUserByUsername(get_username);//Truy vấn vô database lấy thông tin tài khoản
            if (cursor != null)
            {
                int fullname_index = cursor.getColumnIndex(DBHelper.FULLNAME_USER);
                cursor.moveToFirst();
                fullname.setText(cursor.getString(fullname_index));

            }
        }
    }
}
