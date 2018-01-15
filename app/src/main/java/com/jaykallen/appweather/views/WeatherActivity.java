package com.jaykallen.appweather.views;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jaykallen.appweather.R;
import com.jaykallen.appweather.models.WeatherModel;
import com.jaykallen.appweather.utils.Helper;
import com.jaykallen.appweather.utils.RestAPIService;
import com.jaykallen.appweather.utils.SharedPrefsManager;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class WeatherActivity extends AppCompatActivity {
    WeatherModel mWeather;
    TextView mTempTextView, mWeatherTextView, mTempRangeTextView;
    EditText mCityEditText;
    ImageView mWeatherIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        mTempTextView = (TextView)findViewById(R.id.curr_temp_textview);
        mWeatherIcon = (ImageView)findViewById(R.id.weather_icon);
        mWeatherTextView = (TextView)findViewById(R.id.weather_textview);
        mTempRangeTextView = (TextView)findViewById(R.id.temp_range_textview);
        mCityEditText = (EditText)findViewById(R.id.city_edittext);
        Timber.d("**************** Main Activity Started *****************");
        // This pre-populates the data from the last entry used.
        mCityEditText.setText(SharedPrefsManager.getValue(this, "city", "New York,NY,us"));
    }

    // Created by Jay Kallen on 11/5/2017 for the JP Morgan Interview Assignment
    // Normally I would add an onResume method that repopulates the data from the ViewModel using the MVVM design pattern
    // Don't forget the simple espresso tests I wrote for this.

    public void onWeatherClick(View view) {
        String city = mCityEditText.getText().toString();
        // I would only populate the shared prefs if a valid city is found (with weather data)
        SharedPrefsManager.setValue(this, "city", city);
        String parsed = city.replaceAll("\\s+", ""); // This removes all the spaces in the entry
        // I could add defensive code that graciously handles unexpected edge cases such as only allowing A-Z, a-z and commas
        getWeather(parsed);
    }

    private void updateUI() {
        // I would have a function in here that would hide / unhide the weather data field (so Sharknado doesn't appear - lol)
        if (mWeather != null) {
            Timber.d("Weather Received: " + mWeather.getId());
            mWeatherTextView.setText(("Forecast: " + mWeather.getWeather().get(0).getDescription()));
            mTempTextView.setText(("Current Temp: " + Helper.calcK2F(mWeather.getMain().getTemp() + "") + " F"));
            mTempRangeTextView.setText(("High " + Helper.calcK2F(mWeather.getMain().getTempMax() + "")
                    + " F, Low " + Helper.calcK2F(mWeather.getMain().getTempMin() + "") + " F"));
            displayIcon();
        } else {
            Timber.d("mWeather is returning null for some reason");
            Toast.makeText(this, "No data found for the city " , Toast.LENGTH_LONG).show();
        }
    }

    private void displayIcon() {
        String iconUrl = "http://openweathermap.org/img/w/" + mWeather.getWeather().get(0).getIcon() + ".png";
        Timber.d("Icon for display is " + iconUrl);
        Picasso.with(WeatherActivity.this).load(iconUrl).into(mWeatherIcon);
    }

    // Normally I would put all the retrofit stuff into a class in the utilities and then use RxJava to retrieve the data
    private void getWeather (String city) {
        String appid = "4003650e8ddff5c5047708b14c8ed741";
        Timber.d("Start Retrofit Get Weather");
        RestAPIService service = initiateRetrofit();
        Call<WeatherModel> call = service.queryWeather(city, appid);
        Timber.d("Url: " + call.request().url());
        call.enqueue(new retrofit2.Callback<WeatherModel>() {
            @Override
            public void onResponse(Call<WeatherModel> call, retrofit2.Response<WeatherModel> response) {
                Timber.d("Successful Query. Message: " + response.message());
                mWeather = response.body();
                updateUI();
            }
            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {
                Timber.d("Failed Call: " + t);
            }
        });
    }

    private static RestAPIService initiateRetrofit() {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestAPIService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        return retrofit.create(RestAPIService.class);
    }

}
