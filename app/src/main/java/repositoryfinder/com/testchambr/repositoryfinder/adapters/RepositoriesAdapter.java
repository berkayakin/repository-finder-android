package repositoryfinder.com.testchambr.repositoryfinder.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import repositoryfinder.com.testchambr.repositoryfinder.R;
import repositoryfinder.com.testchambr.repositoryfinder.models.Repository;
import repositoryfinder.com.testchambr.repositoryfinder.transformations.CircleTransform;

public class RepositoriesAdapter extends RecyclerView.Adapter<RepositoriesAdapter.MyViewHolder> {

    private List<Repository> repositories = new ArrayList<>();
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ownerAvatar) ImageView ownerAvatar;
        @BindView(R.id.repositoryName) TextView repositoryName;
        @BindView(R.id.repositoryDescription) TextView repositoryDescription;
        @BindView(R.id.repositoryOwnerName) TextView repositoryOwnerName;
        @BindView(R.id.repositorySize) TextView repositorySize;
        @BindView(R.id.repositoryLanguage) TextView repositoryLanguage;

        public MyViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }

    public RepositoriesAdapter(Context context, List<Repository> repositories) {
        this.repositories = repositories;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.repository_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Repository repository = repositories.get(position);

        holder.setIsRecyclable(false);

        holder.repositoryName.setText(repository.getName());

        if(repository.getDescription() != null) {
            holder.repositoryDescription.setText(repository.getDescription());
        } else {
            holder.repositoryDescription.setText(getContext().getResources().getString(R.string.no_description_available));
        }

        if(repository.getLanguage() != null) {
            holder.repositoryLanguage.setText(repository.getLanguage());
        } else {
            //Hide the language information if it is null
            holder.repositoryLanguage.setVisibility(View.GONE);
        }

        String repositoryOwnerString = " " + repository.getItemOwner().getLogin();

        holder.repositoryOwnerName.setText(repositoryOwnerString);

        String repositorySizeString = getContext().getResources().getString(R.string.size) + ": " + repository.getSize();
        holder.repositorySize.setText(repositorySizeString);

        if(repository.isHasWiki() && getContext() != null) {
            holder.itemView.setBackgroundColor(getContext().getResources().getColor(R.color.lightGreen));
        }

        Picasso.with(getContext()).load(repository.getItemOwner().getAvatarURL()).transform(new CircleTransform()).into(holder.ownerAvatar);
    }

    //Optional method
    public void swap(List<Repository> datas){
        repositories.clear();
        repositories.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }

    public List<Repository> getRepositories() {
        return repositories;
    }

    private Context getContext () {
        return context;
    }
}
