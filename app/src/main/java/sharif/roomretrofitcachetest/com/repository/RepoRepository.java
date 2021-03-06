package sharif.roomretrofitcachetest.com.repository;

import android.arch.lifecycle.LiveData;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import sharif.roomretrofitcachetest.com.reponse.RepoSearchResponse;
import sharif.roomretrofitcachetest.com.api.WebServices;
import sharif.roomretrofitcachetest.com.executors.AppExecutors;
import sharif.roomretrofitcachetest.com.networkutils.ApiResponse;
import sharif.roomretrofitcachetest.com.networkutils.NetworkBoundResource;
import sharif.roomretrofitcachetest.com.networkutils.Resource;
import sharif.roomretrofitcachetest.com.room.dao.RepoDao;
import sharif.roomretrofitcachetest.com.room.db.AppDatabase;
import sharif.roomretrofitcachetest.com.room.entity.Repo;
import sharif.roomretrofitcachetest.com.room.entity.RepoSearchResult;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class RepoRepository {

    private final AppDatabase db;

    private final RepoDao repoDao;

    private final WebServices webServices;

    private final AppExecutors appExecutors;

    private CacheValidityLimiter<String> validityLimiter = new CacheValidityLimiter<>(1, TimeUnit.MINUTES);

    public  RepoRepository(AppExecutors appExecutors, AppDatabase db, RepoDao repoDao, WebServices webServices) {
        this.db = db;
        this.repoDao = repoDao;
        this.webServices = webServices;
        this.appExecutors = appExecutors;
    }

    public LiveData<Resource<List<Repo>>> loadRepos(String searchValue) {
        return new NetworkBoundResource<List<Repo>, RepoSearchResponse>(appExecutors) {
            @Override
            protected  void saveCallResult(RepoSearchResponse item) {
                List<Integer> repoIds = item.getRepoIds();
                RepoSearchResult repoSearchResult = new RepoSearchResult(searchValue, repoIds, item.getTotal());
                db.beginTransaction();
                try {
                    repoDao.insertRepos(item.getItems());
                    repoDao.insert(repoSearchResult);
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                //repoDao.insertRepos(item);
            }

            @Override
            protected   boolean shouldFetch(@Nullable List<Repo> data) {
                return data == null || data.isEmpty() || validityLimiter.shouldFetch("FETCH_IF_DATA_OLD");
                //return true;
            }

            @NonNull
            @Override
            protected   LiveData<List<Repo>> loadFromDb() {
                return repoDao.loadRepositories();
                //return repoDao.search(searchValue);
            }

            @NonNull
            @Override
            protected  LiveData<ApiResponse<RepoSearchResponse>> createCall() {
                if (!TextUtils.isEmpty(searchValue)){
                    return webServices.getRepo(searchValue);
                }else {
                    return webServices.getRepo("android");
                }

            }

            @Override
            protected void onFetchFailed() {
                validityLimiter.reset("FETCH_IF_DATA_OLD");
            }
        }.asLiveData();
    }


}
