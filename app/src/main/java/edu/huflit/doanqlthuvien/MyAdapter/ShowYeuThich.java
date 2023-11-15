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

import edu.huflit.doanqlthuvien.DBHelper;
import edu.huflit.doanqlthuvien.MyDatabase;
import edu.huflit.doanqlthuvien.R;
import edu.huflit.doanqlthuvien.YeuThich.MH_YeuThich;

public class ShowYeuThich extends BaseAdapter {
    LayoutInflater inflater;
    TextView textView;
    ImageView imageView;
    Context context;
    public ShowYeuThich(Context context)
    {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }
    @Override
    public int getCount() {
        return MH_YeuThich.yeuThiches.size();
    }

    @Override
    public Object getItem(int i) {
        return MH_YeuThich.yeuThiches.get(i);
    }

    @Override
    public long getItemId(int i) {
        return MH_YeuThich.yeuThiches.get(i).getMa_yeu_thich();
    }


    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        View view = inflater.inflate(R.layout.item_list_yeu_thich, null);

        textView = (TextView) view.findViewById(R.id.yt_1);
        textView = (TextView) view.findViewById(R.id.yt_2);
        textView = (TextView) view.findViewById(R.id.yt_3);
        textView.setText(Integer.toString(MH_YeuThich.yeuThiches.get(i).getMa_yeu_thich()));

        //Lấy ảnh sách
        MyDatabase database = new MyDatabase(context);
        Cursor cursor = database.getSachByMaSach(MH_YeuThich.yeuThiches.get(i).getMa_sach_yt());
        int image_index = cursor.getColumnIndex(DBHelper.IMAGE_SACH);
        int ten_sach_index = cursor.getColumnIndex(DBHelper.TEN_SACH_S);
        cursor.moveToFirst();

        textView = (TextView) view.findViewById(R.id.yt_4);
        textView.setText(cursor.getString(ten_sach_index));

        byte[] bytes = cursor.getBlob(image_index);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        imageView = (ImageView) view.findViewById(R.id.yt_img);
        imageView.setImageBitmap(bitmap);

        return view;
    }
}
