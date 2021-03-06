package com.example.antonio.cryptracer.Price;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.antonio.cryptracer.R;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RipplePrice extends AppCompatActivity {

    String[] text = {"Load"};
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ripple_price);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("BPI Loading");
        progressDialog.setMessage("Wait...");
    }

    public void startRP(View view){
        try {
            Request request = new Request.Builder()
                    .url("https://min-api.cryptocompare.com/data/price?fsym=XRP&tsyms=USD,GBP,EUR")
                    .build();

            progressDialog.show();
            OkHttpClient okHttpClient = new OkHttpClient();

            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Toast.makeText(RipplePrice.this, "Error during BPI loading: " +
                            e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String body = response.body().string();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            load(body);
                        }
                    });
                }
            });
        } catch (Exception e){

        }
    }

    private void load(String body){
        try {
            TextView txtView = findViewById(R.id.rippleTxt);
            StringBuilder builder = new StringBuilder();
            JSONObject jsonObject = new JSONObject(body);
            double usd = jsonObject.getDouble("USD");
            double gbp = jsonObject.getDouble("GBP");
            double eur = jsonObject.getDouble("EUR");

            usd = Math.round(usd*100);
            gbp = Math.round(gbp*100);
            eur = Math.round(eur*100);

            builder.append(String.valueOf(usd/100)).append(" $").append("\n");
            builder.append(String.valueOf(gbp/100)).append(" £").append("\n");
            builder.append(String.valueOf(eur/100)).append(" €").append("\n");

            txtView.setText(builder.toString());
        } catch (Exception e){

        }
    }
}
