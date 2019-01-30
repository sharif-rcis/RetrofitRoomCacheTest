package sharif.roomretrofitcachetest.com.room.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import sharif.roomretrofitcachetest.com.room.entity.RepoSearchResult;
import sharif.roomretrofitcachetest.com.room.dao.RepoDao;
import sharif.roomretrofitcachetest.com.room.entity.Repo;

@Database(entities = {Repo.class, RepoSearchResult.class}, version = 2,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    abstract public RepoDao repoDao();


}