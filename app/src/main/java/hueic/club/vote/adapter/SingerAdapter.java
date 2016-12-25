package hueic.club.vote.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

import hueic.club.vote.R;
import hueic.club.vote.entities.Singer;

/**
 * Created by kennguyen on 25/12/2016.
 */

public class SingerAdapter extends ArrayAdapter<Singer> {
    private Context context;
    private List<Singer> singerList;
    static class ViewHolder{
        ImageView imgTietMuc;
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
            convertView=mInflater.inflate(R.layout.row_singer,null);
            holder = new ViewHolder();
            holder.imgTietMuc = (ImageView) convertView.findViewById(R.id.imgTietMuc);
            holder.tvSoBaoDanh = (TextView) convertView.findViewById(R.id.tvSoBaoDanh);
            holder.tvVote = (TextView) convertView.findViewById(R.id.tvVote);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        Singer singer = singerList.get(position);
        holder.tvSoBaoDanh.setText(singer.soBaoDanh);
        holder.tvVote.setText(String.valueOf(singer.soVote));
        return convertView;

    }
}
