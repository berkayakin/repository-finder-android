package repositoryfinder.com.testchambr.repositoryfinder.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import repositoryfinder.com.testchambr.repositoryfinder.R;
import repositoryfinder.com.testchambr.repositoryfinder.adapters.RepositoriesAdapter;
import repositoryfinder.com.testchambr.repositoryfinder.models.RepositoriesResponse;
import repositoryfinder.com.testchambr.repositoryfinder.presenters.RepositoriesActivityPresenter;

public class RepositoriesActivity extends AppCompatActivity implements RepositoriesActivityPresenter.RepositoriesActivityPresenterListener {

    RepositoriesActivityPresenter repositoriesActivityPresenter;

    @BindView(R.id.searchEditText) EditText searchEditText;
    @BindView(R.id.searchImageButton) ImageButton searchImageButton;
    @BindView(R.id.repositoriesRecyclerView) RecyclerView repositoriesRecyclerView;

    private String currentQuery = "tetris";
    private int currentPage = 1;
    private boolean isLoadingRepositories = false;

    private RepositoriesAdapter repositoriesAdapter;

    private Toast loadingToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositories);

        ButterKnife.bind(this);

        repositoriesActivityPresenter = new RepositoriesActivityPresenter(getApplicationContext(), this);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());

        repositoriesRecyclerView.setLayoutManager(linearLayoutManager);

        RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (recyclerView.getAdapter().getItemCount() != 0 && !isLoadingRepositories) {
                    int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                    if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                        getRepositories(currentQuery, currentPage+1, false, true);
                }
            }
        };

        repositoriesRecyclerView.addOnScrollListener(mScrollListener);

        //Load repositories including word tetris as default
        getRepositories(currentQuery, currentPage, true, false);
    }

    @OnClick(R.id.searchImageButton)
    void searchButtonAction () {
        if(searchEditText.getVisibility() != View.VISIBLE) {
            searchEditText.setVisibility(View.VISIBLE);
            searchEditText.requestFocus();

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(searchEditText, InputMethodManager.SHOW_IMPLICIT);
        } else {
            if(searchEditText.getText().toString().trim().length() > 0) {
                getRepositories(searchEditText.getText().toString().trim(), 1, true, true);

                searchEditText.clearFocus();

                View view = this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        }
    }

    private void getRepositories (String query, int page, boolean isNewQuery, boolean willShowLoadingMessage) {

        if(willShowLoadingMessage) {

            if(loadingToast != null)
                loadingToast.cancel();

            loadingToast = Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.loading), Toast.LENGTH_SHORT);
            loadingToast.show();
        }

        isLoadingRepositories = true;

        if(repositoriesActivityPresenter != null)
            repositoriesActivityPresenter.getRepositories(query, page, isNewQuery);
    }

    @Override
    public void repositoriesReady(RepositoriesResponse repositoriesResponse, boolean isNewQuery) {

        if(!repositoriesResponse.isIncompleteResults()) {

            if(isNewQuery) {
                repositoriesAdapter = new RepositoriesAdapter(getApplicationContext(), repositoriesResponse.getItemsList());
                repositoriesRecyclerView.setAdapter(repositoriesAdapter);
            } else {
                currentPage++;
                repositoriesAdapter.getRepositories().addAll(repositoriesResponse.getItemsList());
                repositoriesAdapter.notifyDataSetChanged();
            }

        } else {
            Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.incomplete_results), Toast.LENGTH_SHORT).show();
        }

        isLoadingRepositories = false;
    }

    @Override
    public void repositoriesFailed() {
        Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.error_msg), Toast.LENGTH_SHORT).show();

        isLoadingRepositories = false;
    }

    @Override
    protected void onDestroy () {
        super.onDestroy();

        //Preventing memory leaks
        repositoriesRecyclerView.setAdapter(null);
    }
}