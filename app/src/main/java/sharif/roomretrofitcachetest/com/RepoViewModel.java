package sharif.roomretrofitcachetest.com;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import sharif.roomretrofitcachetest.com.networkutils.Resource;
import sharif.roomretrofitcachetest.com.repository.RepoRepository;
import sharif.roomretrofitcachetest.com.room.entity.Repo;

public class RepoViewModel extends ViewModel {

    private MutableLiveData<String> searchValue = new MutableLiveData<>();
    private RepoRepository repoRepository;

    public RepoViewModel(RepoRepository repoRepository) {
        this.repoRepository = repoRepository;
    }


    public LiveData<Resource<List<Repo>>> getSearchResultLiveData() {
        //return Transformations.switchMap(searchValue, input -> repoRepository.loadRepos(searchValue.getValue()));
        // return Transformations.switchMap(searchValue, input -> getRepoList());
        return Transformations.switchMap(searchValue, new Function<String, LiveData<Resource<List<Repo>>>>() {
            @Override
            public LiveData<Resource<List<Repo>>> apply(String input) {
                return getRepoList();
            }
        });

    }

    public void setSearchValue(String input) {
        if (input.toLowerCase().equals(searchValue.getValue())) {
            return;
        }
        searchValue.setValue(input);
    }

    private LiveData<Resource<List<Repo>>> getRepoList() {
        return repoRepository.loadRepos(searchValue.getValue());
    }
}
