package repositoryfinder.com.testchambr.repositoryfinder.api;

import repositoryfinder.com.testchambr.repositoryfinder.models.RepositoriesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/search/repositories")
    Call<RepositoriesResponse> getRepositories(@Query("q") String query,
                                               @Query("sort") String sort,
                                               @Query("order") String order,
                                               @Query("client_id") String clientId,
                                               @Query("client_secret") String clientSecret,
                                               @Query("page") int page,
                                               @Query("per_page") int perPage);

}
