package hueic.club.vote.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.zip.Inflater;

import de.hdodenhof.circleimageview.CircleImageView;
import hueic.club.vote.R;
import hueic.club.vote.entities.Singer;
import hueic.club.vote.util.Config;

/**
 * Created by kennguyen on 25/12/2016.
 */

public class SingerAdapter extends ArrayAdapter<Singer> {
    private Context context;
    private List<Singer> singerList;
    static class ViewHolder{
        FrameLayout frameMain;
        CircleImageView imgTietMuc;
        TextView tvSoBaoDanh;
        TextView tvVote;
    }
    public SingerAdapter(Context context, int resource, List<Singer> objects) {
        super(context, resource, objects);
        this.context = context;
        this.singerList = objects;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView==null) {
            convertView = mInflater.inflate(R.layout.row_singer,null);
            holder = new ViewHolder();
            holder.frameMain = (FrameLayout) convertView.findViewById(R.id.main);
            holder.imgTietMuc = (CircleImageView) convertView.findViewById(R.id.imgTietMuc);
            holder.tvSoBaoDanh = (TextView) convertView.findViewById(R.id.tvSoBaoDanh);
            holder.tvVote = (TextView) convertView.findViewById(R.id.tvVote);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        Singer singer = singerList.get(position);
        switch (singer.trangthai){
            case Config.STATUS_DANGDIEN:
                holder.frameMain.setBackgroundResource(R.drawable.bg_dangdien);
                holder.tvSoBaoDanh.setBackgroundResource(R.drawable.bd_dangdien);
                Animation rotation = AnimationUtils.loadAnimation(context, R.anim.scale);
                rotation.setFillAfter(true);
                holder.frameMain.startAnimation(rotation);
                break;

            case Config.STATUS_CHUANBIDIEN:
                holder.frameMain.setBackgroundResource(R.drawable.bg_chuanbidien);
                holder.tvSoBaoDanh.setBackgroundResource(R.drawable.bd_chuanbidien);
                break;

            case Config.STATUS_DADIEN:
                holder.frameMain.setBackgroundResource(R.drawable.bg_dadien);
                holder.tvSoBaoDanh.setBackgroundResource(R.drawable.bd_dadien);
                break;

            case Config.STATUS_CHUADIEN:
                holder.frameMain.setBackgroundResource(R.drawable.bg_chuadien);
                holder.tvSoBaoDanh.setBackgroundResource(R.drawable.bd_chuadien);
                break;

        }
        Picasso.with(context).load("https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcTer2m0HKlcJrrp8hTV7SkzVsdgi1rLb2Z3DUT-VATcAdR0ZLMS").into(holder.imgTietMuc);
        holder.tvSoBaoDanh.setText("SBD:"+singer.sobaodanh);
        holder.tvVote.setText(String.valueOf(singer.sovote));
        return convertView;
    }
}
