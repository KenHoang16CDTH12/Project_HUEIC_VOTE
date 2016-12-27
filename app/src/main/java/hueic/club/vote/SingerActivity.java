package hueic.club.vote;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import hueic.club.vote.entities.JSonObject;
import hueic.club.vote.entities.JsonVote;
import hueic.club.vote.entities.Singer;
import hueic.club.vote.util.Config;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.R.attr.rotation;
import static java.security.AccessController.getContext;

public class SingerActivity extends AppCompatActivity {
    private Singer singer;
    private Button btnVote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singer);
        Intent intent = getIntent();
        singer = (Singer) intent.getSerializableExtra(Config.TIETMUC_OBJECT);

        CircleImageView imgTietMuc = (CircleImageView) findViewById(R.id.imgTietMuc);
        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        rotation.setFillAfter(true);
        imgTietMuc.startAnimation(rotation);

        TextView tvTenTietMuc= (TextView) findViewById(R.id.tvTenTietMuc);
        TextView tvVote= (TextView) findViewById(R.id.tvVote);
        tvTenTietMuc.setText(singer.tentietmuc);
        tvVote.setText(String.valueOf(singer.sovote));

        btnVote  = (Button) findViewById(R.id.btnVote);

        btnVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnVote.setVisibility(View.INVISIBLE);
                new PostToServer().execute();
            }
        });
        ((ImageView)findViewById(R.id.btnBack)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    class PostToServer extends AsyncTask<Void,Void,JsonVote>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JsonVote doInBackground(Void... voids) {
            JsonVote jsonVote = null;
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();
            RequestBody formBody = new FormBody.Builder()
                    .add(Config.PARAM_ID_SINGER,String.valueOf(singer.id))
                    .add(Config.PARAM_IMEI,getIMEI())
                    .build();
            Request request = new Request.Builder()
                    .url(Config.URL_VOTE)
                    .put(formBody)
                    .build();

            try {
                Log.e("message", "Ket noi server");
                Response response = client.newCall(request).execute();
                final String json = response.body().string();
                Log.e("message", "Thanh cong");
                Gson gson = new GsonBuilder().create();
                jsonVote = gson.fromJson(json,JsonVote.class);
                // Do something with the response.
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Loi", e.toString());
            }
            return jsonVote;
        }

        @Override
        protected void onPostExecute(JsonVote jsonVote) {
            super.onPostExecute(jsonVote);
            if(jsonVote == null) return;
            switch (jsonVote.status){
                case Config.M_EXITED:
                    Toast.makeText(SingerActivity.this,getString(R.string.message_exited),Toast.LENGTH_LONG).show();
                    break;

                case Config.M_OUT_OF_RANGE:
                    Toast.makeText(SingerActivity.this,getString(R.string.message_out_of_range),Toast.LENGTH_LONG).show();
                    break;

                case Config.M_FAILED_UPDATE:
                    Toast.makeText(SingerActivity.this,getString(R.string.message_failed_update),Toast.LENGTH_LONG).show();
                    break;

                case Config.M_SUCCESS:
                    Toast.makeText(SingerActivity.this,getString(R.string.message_success),Toast.LENGTH_LONG).show();
                    break;

            }
            btnVote.setVisibility(View.VISIBLE);
        }
    }
    public String getIMEI(){
        TelephonyManager mngr = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = mngr.getDeviceId();
        return imei;
    }
}
