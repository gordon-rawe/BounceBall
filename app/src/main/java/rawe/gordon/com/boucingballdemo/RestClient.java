package rawe.gordon.com.boucingballdemo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by gordon on 2015/7/25.
 */
public class RestClient {
    private static String BASE_URL = "http://gordonrawe.coding.io/landscape";
    private static RestClient ourInstance = new RestClient();
    private CityServiceInterface cityApiService;

    public static RestClient getInstance() {
        return ourInstance;
    }

    private RestClient() {
        Gson gson = new GsonBuilder().create();
        RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(BASE_URL)
                .setConverter(new GsonConverter(gson)).build();
        cityApiService = restAdapter.create(CityServiceInterface.class);
    }

    public CityServiceInterface getCityApiService() {
        return this.cityApiService;
    }
}
