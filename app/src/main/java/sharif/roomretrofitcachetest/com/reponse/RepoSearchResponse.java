package sharif.roomretrofitcachetest.com.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import sharif.roomretrofitcachetest.com.room.entity.Repo;

public class RepoSearchResponse {
    @SerializedName("total_count")
    @Expose
    private int total;
    @SerializedName("items")
    @Expose
    private List<Repo> items;
    public  int getTotal() {
        return total;
    }

    public  void setTotal(int total) {
        this.total = total;
    }

    public  List<Repo> getItems() {
        return items;
    }

    public  void setItems(List<Repo> items) {
        this.items = items;
    }


    public  List<Integer> getRepoIds() {
        List<Integer> repoIds = new ArrayList<>();
        for (Repo repo : items) {
            repoIds.add(repo.id);
        }
        return repoIds;
    }
}
