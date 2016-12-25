package hueic.club.vote;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import hueic.club.vote.adapter.SingerAdapter;
import hueic.club.vote.entities.Singer;
import hueic.club.vote.util.Config;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ListSingerActivity extends AppCompatActivity {
    private GridView gridview;
    private SingerAdapter mAdapter;
    private List<Singer> singerList;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_singer);

        gridview = (GridView) findViewById(R.id.gridview);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Singer singer = singerList.get(position);
                Intent intent = new Intent(ListSingerActivity.this,SingerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Config.TIETMUC_OBJECT,singer);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        new LoadFromService().execute();
    }
    private class LoadFromService extends AsyncTask<Void,Void,List<Singer>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected List<Singer> doInBackground(Void... voids) {
            List<Singer> list = null;
            try
            {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(10, TimeUnit.SECONDS)
                        .build();
                Request request = new Request.Builder()
                        .url(Config.URL_LIST_TIETMUC)
                        .build();
                Response response = client.newCall(request).execute();
                String json = response.body().string();
                Gson gson = new Gson();
                list = gson.fromJson(json, new TypeToken<List<Singer>>(){}.getType());
            }catch (IOException ex){
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<Singer> singers) {
            super.onPostExecute(singers);
            if(singers==null) {
                Toast.makeText(ListSingerActivity.this,getResources().getString(R.string.errorServerMessage),Toast.LENGTH_SHORT).show();
                return;
            }else {
                mAdapter = new SingerAdapter(ListSingerActivity.this, R.layout.row_singer, singers);
                gridview.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
            progressBar.setVisibility(View.GONE);
        }

    }
}
