package repositoryfinder.com.testchambr.repositoryfinder.presenters;

import android.content.Context;

import repositoryfinder.com.testchambr.repositoryfinder.models.RepositoriesResponse;
import repositoryfinder.com.testchambr.repositoryfinder.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 * Coded by Berkay AKIN
 */

public class RepositoriesActivityPresenter extends BasePresenter {

    private final int perPage = 10;

    //We define the call here to be able to cancel it later
    private Call<RepositoriesResponse> repositoriesCall;

    private final RepositoriesActivityPresenterListener repositoriesActivityPresenterListener;

    public interface RepositoriesActivityPresenterListener {
        void repositoriesReady(RepositoriesResponse repositoriesResponse, String query, boolean isNewQuery);
        void repositoriesFailed();
    }

    public RepositoriesActivityPresenter (Context context, RepositoriesActivityPresenterListener repositoriesActivityPresenterListener) {
        setContext(context);
        this.repositoriesActivityPresenterListener = repositoriesActivityPresenterListener;
        createApiInterface();
    }

    public void getRepositories (final String query, int page, final boolean isNewQuery) {

        //Cancel previous call
        if(repositoriesCall != null)
            repositoriesCall.cancel();

        repositoriesCall = getApiInterface().getRepositories(query, "stars", "desc", Constants.GITHUB_CLIENT_ID, Constants.GITHUB_CLIENT_SECRET, page, perPage);

        //We define what is going to happen when the call will succeed or fail
        repositoriesCall.enqueue(new Callback<RepositoriesResponse>() {
            @Override
            public void onResponse(Call<RepositoriesResponse> call, Response<RepositoriesResponse> response) {
                RepositoriesResponse repositoriesResponse = response.body();

                if(repositoriesResponse != null)
                    repositoriesActivityPresenterListener.repositoriesReady(repositoriesResponse, query, isNewQuery);
            }

            @Override
            public void onFailure(Call<RepositoriesResponse> call, Throwable t) {
                if(!call.isCanceled()) {
                    repositoriesActivityPresenterListener.repositoriesFailed();
                }
            }
        });

    }

}
