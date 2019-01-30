package sharif.roomretrofitcachetest.com.room.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.Nullable;

import java.util.List;

import sharif.roomretrofitcachetest.com.repository.RepoTypeConverter;

@Entity
@TypeConverters(RepoTypeConverter.class)
public class RepoSearchResult {
    @PrimaryKey
    public int id;
    public final String query;
    public final List<Integer> repoIds;
    public final int totalCount;

    public RepoSearchResult(String query, List<Integer> repoIds, int totalCount) {
        this.query = query;
        this.repoIds = repoIds;
        this.totalCount = totalCount;
    }

}
