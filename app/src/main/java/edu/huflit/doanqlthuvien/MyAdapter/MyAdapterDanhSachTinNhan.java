package edu.huflit.doanqlthuvien.MyAdapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import edu.huflit.doanqlthuvien.DBHelper;
import edu.huflit.doanqlthuvien.MyDatabase;
import edu.huflit.doanqlthuvien.OOP.User;
import edu.huflit.doanqlthuvien.R;
import edu.huflit.doanqlthuvien.fragments.TinNhan;
import edu.huflit.doanqlthuvien.fragments.TinNhan2;

public class MyAdapterDanhSachTinNhan extends BaseAdapter {
    LayoutInflater inflater;
    TextView textView;
    ImageView imageView;
    Context context;
    public MyAdapterDanhSachTinNhan(Context context)
    {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }
    @Override
    public int getCount() {
        return TinNhan.users.size();
    }

    @Override
    public Object getItem(int i) {
        return TinNhan.users.get(i);
    }

    @Override
    public long getItemId(int i) {
        return TinNhan.users.get(i).getId_user();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        View view = inflater.inflate(R.layout.item_list_danh_sach_tin_nhan, null);

        textView = (TextView) view.findViewById(R.id.tin_nhan_username);
        textView.setText(TinNhan.users.get(i).getUsername_user());

        //Lấy số lượng tin nhắn chua đọc của người dùng gửi admin
        MyDatabase database = new MyDatabase(context);
        Cursor cursor = database.getCountMessageUserSendAdmin(TinNhan.users.get(i).getId_user());
        if (cursor != null)
        {
            int countt = 0;
            int count_message_index = cursor.getColumnIndex(DBHelper.COUNT_NEW_MESSAGE);
            while (cursor.moveToNext())
            {
                countt = countt + cursor.getInt(count_message_index);
                textView = (TextView) view.findViewById(R.id.tin_nhan_new_chat);
                textView.setText(Integer.toString(countt));
            }
        }

        imageView = (ImageView) view.findViewById(R.id.tin_nhan_img);

        return view;
    }
}
