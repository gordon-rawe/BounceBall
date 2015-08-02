package rawe.gordon.com.boucingballdemo;


import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by gordon on 2015/7/25.
 */
public interface CityServiceInterface {
    @GET("/provinces")
    void getProvinces(Callback<ProvinceResponseModel> callback);
}
