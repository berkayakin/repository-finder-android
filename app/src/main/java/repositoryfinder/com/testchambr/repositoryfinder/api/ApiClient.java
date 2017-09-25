package repositoryfinder.com.testchambr.repositoryfinder.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 * Coded by Berkay AKIN
 */

public class ApiClient {

    private static final String BASE_URL = "https://api.github.com";

    private static Retrofit retrofit = null;

    public static Retrofit getApiClient() {

        if(retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).
                    addConverterFactory(GsonConverterFactory.create()).build();
        }

        return retrofit;
    }

    public ApiInterface getApiInterface() {
        return getApiClient().create(ApiInterface.class);
    }

}