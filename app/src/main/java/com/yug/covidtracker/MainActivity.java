package com.yug.covidtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;


import android.widget.TextView;
import android.widget.Toast;

import com.yug.covidtracker.api.ApiUtilities;
import com.yug.covidtracker.api.CountryData;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView totalConfirm, totalRecovered, totalDeath, totalTests, totalActive;
    private TextView todayConfirm, todayRecovered, todayDeath, date, cname;
    private PieChart pieChart;
    String country = "India";




    private List<CountryData> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_YES);


        list = new ArrayList<>();
         if(getIntent().getStringExtra("country")!= null)
             country = getIntent().getStringExtra("country");

        totalConfirm = (TextView)findViewById(R.id.total_Confirm);
        todayRecovered = (TextView)findViewById(R.id.today_Recovered);
        totalRecovered = (TextView)findViewById(R.id.total_Recovered);
        todayDeath = (TextView)findViewById(R.id.today_Death);
        totalDeath = (TextView)findViewById(R.id.total_Death);
        totalTests = (TextView)findViewById(R.id.total_Tests);
        todayConfirm = (TextView)findViewById(R.id.today_Confrim);
        totalActive = (TextView)findViewById(R.id.total_Active);
        pieChart = findViewById(R.id.pie_chart);
        date = (TextView)findViewById(R.id.date);

        cname = findViewById(R.id.cname);
        cname.setText(country);
        cname.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this,CountryActivity.class));
        });


        ApiUtilities.getApiInterface().getCountryData()
                .enqueue(new Callback<List<CountryData>>() {
                    @Override
                    public void onResponse(Call<List<CountryData>> call, Response<List<CountryData>> response) {
                        list.addAll(response.body());

//                        progressBar.setVisibility(View.INVISIBLE);


                        for(int i =0;i<list.size();i++){
                            if(list.get(i).getCountry().equals(country)){
                                int confirm = Integer.parseInt(list.get(i).getCases());
                                int active = Integer.parseInt(list.get(i).getActive());
                                int recovered = Integer.parseInt(list.get(i).getRecovered());
                                int death = Integer.parseInt(list.get(i).getDeaths());


                                totalActive.setText(NumberFormat.getInstance().format(active));
                                totalConfirm.setText(NumberFormat.getInstance().format(confirm));
                                totalDeath.setText(NumberFormat.getInstance().format(death));
                                totalRecovered.setText(NumberFormat.getInstance().format(recovered));

                                todayConfirm.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayCases())));
                                todayRecovered.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayRecovered())));
                                todayDeath.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayDeaths())));
                                totalTests.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTests())));

                                setText(list.get(i).getUpdated());

                                pieChart.addPieSlice(new PieModel("Confirm",confirm,getResources().getColor(R.color.yellow)));
                                pieChart.addPieSlice(new PieModel("Active",active,getResources().getColor(R.color.blue)));
                                pieChart.addPieSlice(new PieModel("Recovered",recovered,getResources().getColor(R.color.green_pie)));
                                pieChart.addPieSlice(new PieModel("Death",death,getResources().getColor(R.color.red_pie)));

                                pieChart.startAnimation();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<CountryData>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Error : "+t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void setText(String updated) {
        DateFormat format = new SimpleDateFormat("MMM dd, yyyy");

        long miliSecond = Long.parseLong(updated);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(miliSecond);

        date.setText("Updated at "+format.format(calendar.getTime()));
    }
}