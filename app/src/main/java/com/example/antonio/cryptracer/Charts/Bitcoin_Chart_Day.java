package com.example.antonio.cryptracer.Charts;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.antonio.cryptracer.R;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.ArrayList;

public class Bitcoin_Chart_Day extends AppCompatActivity {

    public static final String API_CALL = "https://min-api.cryptocompare.com/data/histoday?fsym=BTC&tsym=USD&limit=365&aggregate=3&e=CCCAGG";
    OkHttpClient okHttpClient = new OkHttpClient();
    private CandleStickChart candleStickChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitcoin_chart_day);
        run();
    }

    private void run(){
        Request request = new Request.Builder()
                .url(API_CALL)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(Bitcoin_Chart_Day.this, "Error during data loading"
                        + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {
                final String body = response.body().string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        parseAPIresponse(body);
                    }
                });
            }
        });
    }

    private void parseAPIresponse(String body){
        try{
            candleStickChart = (CandleStickChart) findViewById(R.id.daily_chart);
            ArrayList<CandleEntry> entries = new ArrayList<>();
            JSONObject jsonResponse = new JSONObject(body);
            JSONArray CandleStickEntries = jsonResponse.getJSONArray("Data");
            for(int i = 0; i < CandleStickEntries.length(); i++){
                JSONObject CandleStickEntry = CandleStickEntries.getJSONObject(i);
                float close = (float) CandleStickEntry.getDouble("close");
                float high = (float) CandleStickEntry.getDouble("high");
                float low = (float) CandleStickEntry.getDouble("low");
                float open = (float) CandleStickEntry.getDouble("open");
                entries.add(new CandleEntry(i, high, low, open, close));
            }
            CandleDataSet cds = new CandleDataSet(entries, "Daily fluctuations for the last 365 days");
            cds.setColor(Color.rgb(80, 80, 80));
            cds.setShadowColor(Color.DKGRAY);
            cds.setShadowWidth(0.7f);
            cds.setDecreasingColor(Color.RED);
            cds.setDecreasingPaintStyle(Paint.Style.FILL);
            cds.setIncreasingColor(Color.GREEN);
            cds.setIncreasingPaintStyle(Paint.Style.FILL);
            cds.setNeutralColor(Color.GREEN);
            cds.setValueTextColor(Color.BLACK);
            CandleData cd = new CandleData(cds);
            candleStickChart.setData(cd);
            candleStickChart.animateY(5000);
            candleStickChart.animateX(5000);
            candleStickChart.invalidate();

        } catch (Exception e){

        }
    }
}
