package edu.huflit.doanqlthuvien.MyAdapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import edu.huflit.doanqlthuvien.MyDatabase;
import edu.huflit.doanqlthuvien.OOP.User;
import edu.huflit.doanqlthuvien.R;
import edu.huflit.doanqlthuvien.fragment_sach.DetailSach;
import edu.huflit.doanqlthuvien.fragments.TinNhan2;

public class MyAdapterTinNhan extends BaseAdapter {
    LayoutInflater inflater;
    TextView textView;
    ImageView imageView;
    Context context;
    public MyAdapterTinNhan(Context context)
    {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }
    @Override
    public int getCount() {
        return TinNhan2.chats.size();
    }

    @Override
    public Object getItem(int i) {
        return TinNhan2.chats.get(i);
    }

    @Override
    public long getItemId(int i) {
        return TinNhan2.chats.get(i).getId_tin_nhan();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        View view = inflater.inflate(R.layout.item_list_tin_nhan, null);


        //lấy username khi đã có id user
        MyDatabase database = new MyDatabase(context);
        User user = database.getUserByIDUser(TinNhan2.chats.get(i).getId_nguoi_gui());
        textView = (TextView) view.findViewById(R.id.chat_username);
        textView.setText(user.getUsername_user());

        textView = (TextView) view.findViewById(R.id.chat_tin_nhan);
        textView.setText(TinNhan2.chats.get(i).getNoi_dung());

        if (TinNhan2.chats.get(i).getId_nguoi_gui() != 1)
        {
            String code_color = "#1DB55A";
            int color = Color.parseColor(code_color);
            view.setBackgroundColor(color);
        }

        return view;
    }
}
