package repositoryfinder.com.testchambr.repositoryfinder.models;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {

    @SerializedName("incomplete_results")
    private boolean IncompleteResults;

    public boolean isIncompleteResults() {
        return IncompleteResults;
    }
}