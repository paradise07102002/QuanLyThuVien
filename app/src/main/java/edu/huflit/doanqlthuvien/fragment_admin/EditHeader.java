package edu.huflit.doanqlthuvien.fragment_admin;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.huflit.doanqlthuvien.DBHelper;
import edu.huflit.doanqlthuvien.MyDatabase;
import edu.huflit.doanqlthuvien.OOP.User;
import edu.huflit.doanqlthuvien.R;

public class EditHeader extends Fragment {
    View view;
    public static ImageView img_avartar;
    public static TextView username, email;
    public static MyDatabase database;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_header_nav, container, false);
        database = new MyDatabase(getActivity());
        anhXa();

        return view;
    }
    public void anhXa()
    {
        img_avartar = (ImageView) view.findViewById(R.id.avatar);
        username = (TextView) view.findViewById(R.id.username1);
        email = (TextView) view.findViewById(R.id.email);
    }
    public static void showHeader(String get_username)
    {
        //SharedPreferences get_user = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        //String get_username = get_user.getString("username", null);
//        if (get_username != null)
//        {
            Cursor cursor = database.getUserByUsername(get_username);
            if (cursor != null)
            {
                int username_index = cursor.getColumnIndex(DBHelper.USERNAME_USER);
                int email_index = cursor.getColumnIndex(DBHelper.EMAIL_USER);
                cursor.moveToFirst();
                username.setText(cursor.getString(username_index));
                email.setText(cursor.getString(email_index));
            }
        //}
    }
}
