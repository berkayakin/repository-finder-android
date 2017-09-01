package repositoryfinder.com.testchambr.repositoryfinder.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RepositoriesResponse extends BaseResponse {

    @SerializedName("total_count")
    private int TotalCount;

    @SerializedName("items")
    private List<Repository> ItemsList;

    public int getTotalCount() {
        return TotalCount;
    }

    public List<Repository> getItemsList() {
        return ItemsList;
    }
}