package hueic.club.vote;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import hueic.club.vote.entities.Singer;
import hueic.club.vote.util.Config;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static java.security.AccessController.getContext;

public class SingerActivity extends AppCompatActivity {
    Singer singer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singer);
        Intent intent = getIntent();
        singer = (Singer) intent.getSerializableExtra(Config.TIETMUC_OBJECT);

        TextView tvTenTietMuc= (TextView) findViewById(R.id.tvTenTietMuc);
        TextView tvVote= (TextView) findViewById(R.id.tvVote);
        tvTenTietMuc.setText(singer.tenTietMuc);
        tvVote.setText(String.valueOf(singer.soVote));

        Button btnVote= (Button) findViewById(R.id.btnVote);

        btnVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    class PostToServer extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();
            RequestBody formBody = new FormBody.Builder()
                    .add(Config.PARAM_ID_SINGER,String.valueOf(singer.id))
                    .add(Config.PARAM_IMEI,getIMEI())
                    .build();
            Request request = new Request.Builder()
                    .url(Config.URL_VOTE)
                    .post(formBody)
                    .build();

            try {
                Response response = client.newCall(request).execute();

                // Do something with the response.
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(SingerActivity.this,"Hien thi test",Toast.LENGTH_LONG).show();
        }
    }
    public String getIMEI(){
        TelephonyManager mngr = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = mngr.getDeviceId();
        return imei;
    }
}
