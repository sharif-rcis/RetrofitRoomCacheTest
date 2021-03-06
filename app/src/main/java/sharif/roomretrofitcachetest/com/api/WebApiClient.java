package sharif.roomretrofitcachetest.com.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;
import sharif.roomretrofitcachetest.com.BuildConfig;

public class WebApiClient {


    private Gson gson = new GsonBuilder()
            .setLenient()
            .create();


    private Retrofit provideRetrofit() {

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(1, TimeUnit.MINUTES);

        if (BuildConfig.DEBUG){
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            clientBuilder.addInterceptor(logging);
        }

        String baseUrl = "https://api.github.com/";
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .client(clientBuilder.build())
                .build();
    }

    public WebServices callRetrofit() {
        return provideRetrofit().create(WebServices.class);
    }
}
