package com.example.antonio.cryptracer.Currencies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.antonio.cryptracer.Charts.EtherChart;
import com.example.antonio.cryptracer.Charts.RippleChart;
import com.example.antonio.cryptracer.Didactic_units.EtherDidactic;
import com.example.antonio.cryptracer.Didactic_units.RippleDidactic;
import com.example.antonio.cryptracer.Price.EtherPrice;
import com.example.antonio.cryptracer.Price.RipplePrice;
import com.example.antonio.cryptracer.R;

public class Ether extends AppCompatActivity {

    String [] chartOptions = {"About", "Price", "Year Chart", "Daily Chart", "Hour Chart"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ether);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, chartOptions);
        final ListView lv = findViewById(R.id.etherList);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(chartOptions[position] == "About"){
                    Intent intent = new Intent(Ether.this, EtherDidactic.class);
                    startActivity(intent);
                } else if(chartOptions[position] == "Price"){
                    Intent intent = new Intent(Ether.this, EtherPrice.class);
                    startActivity(intent);
                } else if(chartOptions[position] == "Year Chart"){
                    Intent intent = new Intent(Ether.this, EtherChart.class);
                    intent.putExtra("period","year");
                    startActivity(intent);
                } else if(chartOptions[position] == "Daily Chart"){
                    Intent intent = new Intent(Ether.this, EtherChart.class);
                    intent.putExtra("period","day");
                    startActivity(intent);
                } else if(chartOptions[position] == "Hour Chart"){
                    Intent intent = new Intent(Ether.this, EtherChart.class);
                    intent.putExtra("period","hour");
                    startActivity(intent);
                }
            }
        });
    }
}
