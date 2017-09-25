package repositoryfinder.com.testchambr.repositoryfinder.presenters;

import android.content.Context;

import repositoryfinder.com.testchambr.repositoryfinder.api.ApiClient;
import repositoryfinder.com.testchambr.repositoryfinder.api.ApiInterface;

/*
 * Coded by Berkay AKIN
 */

public class BasePresenter {
    private Context context;
    private ApiInterface apiInterface;

    public void createApiInterface() {
        this.apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    }

    public ApiInterface getApiInterface() {
        if(apiInterface == null)
            createApiInterface();

        return apiInterface;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}