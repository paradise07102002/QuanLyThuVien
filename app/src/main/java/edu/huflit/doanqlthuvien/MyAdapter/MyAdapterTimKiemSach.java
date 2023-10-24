package edu.huflit.doanqlthuvien.MyAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.huflit.doanqlthuvien.R;
import edu.huflit.doanqlthuvien.fragment_sach.ManHinhSach;
import edu.huflit.doanqlthuvien.fragment_sach.TimkiemSach;

public class MyAdapterTimKiemSach extends BaseAdapter {
    LayoutInflater inflater;
    TextView textView;
    ImageView imageView;
    Context context;
    public MyAdapterTimKiemSach(Context context)
    {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }
    @Override
    public int getCount() {
        return TimkiemSach.saches.size();
    }

    @Override
    public Object getItem(int i) {
        return TimkiemSach.saches.get(i);
    }

    @Override
    public long getItemId(int i) {
        return TimkiemSach.saches.get(i).getMa_sach_s();
    }


    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        View view = inflater.inflate(R.layout.item_list_tim_kiem_sach, null);

        textView = (TextView) view.findViewById(R.id.tks_tv1);
        textView = (TextView) view.findViewById(R.id.tks_tv2);
        textView = (TextView) view.findViewById(R.id.tks_tv3);
        textView.setText(Integer.toString(TimkiemSach.saches.get(i).getMa_sach_s()));
        textView = (TextView) view.findViewById(R.id.tks_tv4);
        textView.setText(TimkiemSach.saches.get(i).getTen_sach_s());

        byte[] bytes = TimkiemSach.saches.get(i).getImage_sach();
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        imageView = (ImageView) view.findViewById(R.id.tks_image);
        imageView.setImageBitmap(bitmap);

        return view;
    }
}
