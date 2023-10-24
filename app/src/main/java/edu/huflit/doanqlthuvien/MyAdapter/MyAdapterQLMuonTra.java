package edu.huflit.doanqlthuvien.MyAdapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import edu.huflit.doanqlthuvien.DBHelper;
import edu.huflit.doanqlthuvien.ManHinhUser.ManHinhUser;
import edu.huflit.doanqlthuvien.MyDatabase;
import edu.huflit.doanqlthuvien.OOP.Sach;
import edu.huflit.doanqlthuvien.R;
import edu.huflit.doanqlthuvien.fragment_muon_tra.QuanLySachMuon;
import edu.huflit.doanqlthuvien.fragment_sach.ManHinhSach;

public class MyAdapterQLMuonTra extends BaseAdapter {
    LayoutInflater inflater;
    TextView textView;
    ImageView imageView;
    Context context;
    MyDatabase database;
    public MyAdapterQLMuonTra(Context context)
    {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }
    @Override
    public int getCount() {
        return QuanLySachMuon.muonTraSaches.size();
    }

    @Override
    public Object getItem(int i) {
        return QuanLySachMuon.muonTraSaches.get(i);
    }

    @Override
    public long getItemId(int i) {
        return QuanLySachMuon.muonTraSaches.get(i).getMa_muon_tra_mts();
    }


    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        View view = inflater.inflate(R.layout.item_list_quan_ly_muon_tra, null);

        textView = (TextView) view.findViewById(R.id.tv_qlmt1);
        textView = (TextView) view.findViewById(R.id.tv_qlmt2);

        textView = (TextView) view.findViewById(R.id.tv_qlmt3);
        textView.setText(Integer.toString(QuanLySachMuon.muonTraSaches.get(i).getMa_muon_tra_mts()));


        database = new MyDatabase(context);
        Cursor cursor = database.getSachByMaSach(QuanLySachMuon.muonTraSaches.get(i).getMa_sach_mts());
        if (cursor != null)
        {
            Sach sach = new Sach();
            int ten_sach_index  =cursor.getColumnIndex(DBHelper.TEN_SACH_S);
            int image_sach_index = cursor.getColumnIndex(DBHelper.IMAGE_SACH);
            cursor.moveToFirst();
            sach.setTen_sach_s(cursor.getString(ten_sach_index));
            sach.setImage_sach(cursor.getBlob(image_sach_index));

            textView = (TextView) view.findViewById(R.id.tv_qlmt4);
            textView.setText(sach.getTen_sach_s());

            byte[] bytes = sach.getImage_sach();
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            imageView = (ImageView) view.findViewById(R.id.qlmt_image);
            imageView.setImageBitmap(bitmap);
        }
        //Truy vấn từ mã sách lấy tên sách và ảnh sách


        return view;
    }

}
