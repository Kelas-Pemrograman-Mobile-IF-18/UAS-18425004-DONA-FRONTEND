package com.dona.apkpenjualankosmetik.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dona.apkpenjualankosmetik.R;
import com.dona.apkpenjualankosmetik.model.ModelKosmetik;
import com.dona.apkpenjualankosmetik.server.BaseURL;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterKosmetik extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<ModelKosmetik> item;

    public AdapterKosmetik(Activity activity, List<ModelKosmetik> item) {
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
            convertView = inflater.inflate(R.layout.content_kosmetik, null);


        TextView namaKos = (TextView) convertView.findViewById(R.id.txtnamaKos);
        TextView deskripsiKos = (TextView) convertView.findViewById(R.id.txtdeskripsikos);
        TextView jenisKos = (TextView) convertView.findViewById(R.id.txtjeniskos);
        TextView hargaKos = (TextView) convertView.findViewById(R.id.txthargakos);
        ImageView gambarBus = (ImageView) convertView.findViewById(R.id.gambarKos);

        namaKos.setText(item.get(position).getNamaKosmetik());
        deskripsiKos.setText(item.get(position).getDeskripsiKosmetik());
        jenisKos.setText(item.get(position).getJenisKosmetik());
        hargaKos.setText("Rp." + item.get(position).getHargaKosmetik());
        Picasso.get().load(BaseURL.baseURL + "gambar/" + item.get(position).getGambar())
                .resize(40, 40)
                .centerCrop()
                .into(gambarBus);
        return convertView;
    }
}
