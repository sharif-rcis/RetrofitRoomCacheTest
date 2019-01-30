package sharif.roomretrofitcachetest.com.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sharif.roomretrofitcachetest.com.R;
import sharif.roomretrofitcachetest.com.room.entity.Repo;

public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.RepoHolder> {

    public List<Repo> repoList;
    Context context;


    public RepoListAdapter(List<Repo> repoList, Context context) {
        this.context = context;
        this.repoList = repoList;
    }

    @NonNull
    @Override
    public RepoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_item, parent, false);

        return new RepoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoHolder holder, int position) {
        Repo repo = repoList.get(position);
        holder.tvName.setText(repo.name);
        holder.tvDesc.setText(repo.description);
        holder.tvStars.setText(String.valueOf(repo.stars));
    }

    @Override
    public int getItemCount() {
        return repoList.size();
    }

    class RepoHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvDesc;
        TextView tvStars;

        public RepoHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.name);
            tvDesc = itemView.findViewById(R.id.desc);
            tvStars = itemView.findViewById(R.id.stars);
        }
    }

}
