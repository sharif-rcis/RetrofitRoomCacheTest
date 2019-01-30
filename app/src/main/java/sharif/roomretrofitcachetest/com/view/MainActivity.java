package sharif.roomretrofitcachetest.com.view;

import android.arch.lifecycle.Observer;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import sharif.roomretrofitcachetest.com.R;
import sharif.roomretrofitcachetest.com.adapter.RepoListAdapter;
import sharif.roomretrofitcachetest.com.api.WebApiClient;
import sharif.roomretrofitcachetest.com.executors.AppExecutors;
import sharif.roomretrofitcachetest.com.networkutils.Resource;
import sharif.roomretrofitcachetest.com.repository.RepoRepository;
import sharif.roomretrofitcachetest.com.room.dao.RepoDao;
import sharif.roomretrofitcachetest.com.room.db.AppDatabase;
import sharif.roomretrofitcachetest.com.room.entity.Repo;

public class MainActivity extends AppCompatActivity {

    EditText etSearchRepos;
    RecyclerView rvReposList;
    RepoListAdapter repoListAdapter;
    RepoRepository repoRepository;
    AppDatabase appDatabase;
    public CompositeDisposable mCompositeDisposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appDatabase = provideDb(this);

        etSearchRepos = findViewById(R.id.etRepoSearch);
        rvReposList = findViewById(R.id.repo_list);

        mCompositeDisposable = new CompositeDisposable();

        repoRepository = new RepoRepository(new AppExecutors(), appDatabase, provideRepoDao(appDatabase), new WebApiClient().callRetrofit());

        repoRepository.loadRepos(etSearchRepos.getText().toString()).observe(this, new Observer<Resource<List<Repo>>>() {
            @Override
            public void onChanged(Resource<List<Repo>> listResource) {
                repoListAdapter = new RepoListAdapter(listResource.data, MainActivity.this);
            }
        });

        rvReposList.setAdapter(repoListAdapter);
        rvReposList.setLayoutManager(new LinearLayoutManager(this));
    }


    AppDatabase provideDb(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "github.db").build();
    }

    RepoDao provideRepoDao(AppDatabase db) {
        return db.repoDao();
    }
}
