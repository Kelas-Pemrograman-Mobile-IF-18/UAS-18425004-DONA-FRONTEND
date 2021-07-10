package com.dona.apkpenjualankosmetik.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dona.apkpenjualankosmetik.R;
import com.dona.apkpenjualankosmetik.model.ModelCart;

import java.util.List;

public class AdapterCart extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<ModelCart> item;

    public AdapterCart(Activity activity, List<ModelCart> item) {
        this.activity = activity;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.content_cart, null);


        TextView namaKosmetik = (TextView) convertView.findViewById(R.id.txtnamaKosmetik);
        TextView deskripsiKos = (TextView) convertView.findViewById(R.id.txtdeskripsikos);
        TextView jenisKos = (TextView) convertView.findViewById(R.id.txtjeniskos);
        TextView hargaKos = (TextView) convertView.findViewById(R.id.txthargaKos);
        TextView jumlahKos = (TextView) convertView.findViewById(R.id.txtjumlahKos);
        TextView jumlahHarga = (TextView) convertView.findViewById(R.id.txtjumlahHarga);

//        ImageView gambarBus = (ImageView) convertView.findViewById(R.id.gambarBus);

        namaKosmetik.setText(item.get(position).getNamaKosmetik());
        deskripsiKos.setText(item.get(position).getDeskripsiKos());
        jenisKos.setText(item.get(position).getJenisKos());
        hargaKos.setText("Rp." + item.get(position).getHargaKos());
        jumlahKos.setText(item.get(position).getJumlahKos());
        jumlahHarga.setText("Rp." + item.get(position).getJumlahHarga());
//        Picasso.get().load(BaseURL.baseURL + "gambar/" + item.get(position).getGambar())
//                .resize(40, 40)
//                .centerCrop()
//                .into(gambarBus);
        return convertView;
    }

}
