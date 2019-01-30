package sharif.roomretrofitcachetest.com.view;

import android.arch.lifecycle.Observer;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import sharif.roomretrofitcachetest.com.R;
import sharif.roomretrofitcachetest.com.adapter.RepoListAdapter;
import sharif.roomretrofitcachetest.com.api.WebApiClient;
import sharif.roomretrofitcachetest.com.executors.AppExecutors;
import sharif.roomretrofitcachetest.com.networkutils.Resource;
import sharif.roomretrofitcachetest.com.networkutils.Status;
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

    List<Repo> repoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appDatabase = provideDb(this);

        etSearchRepos = findViewById(R.id.etRepoSearch);
        rvReposList = findViewById(R.id.repo_list);


        repoRepository = new RepoRepository(new AppExecutors(), appDatabase, provideRepoDao(appDatabase), new WebApiClient().callRetrofit());

        etSearchRepos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                repoRepository.loadRepos(s.toString()).observe(MainActivity.this, new Observer<Resource<List<Repo>>>() {
                    @Override
                    public void onChanged(Resource<List<Repo>> listResource) {
                        if (listResource.status == Status.SUCCESS){
                            repoList = listResource.data;
                            repoListAdapter = new RepoListAdapter(repoList, MainActivity.this);
                        }else if (listResource.status == Status.LOADING){
                            Toast.makeText(MainActivity.this, "Loading Fetch Data", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MainActivity.this, "Error Fetching data", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });

        rvReposList.setLayoutManager(new LinearLayoutManager(this));
       /* repoListAdapter = new RepoListAdapter(repoList, MainActivity.this);*/
        rvReposList.setAdapter(repoListAdapter);

    }


    AppDatabase provideDb(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "github.db").build();
    }

    RepoDao provideRepoDao(AppDatabase db) {
        return db.repoDao();
    }
}
