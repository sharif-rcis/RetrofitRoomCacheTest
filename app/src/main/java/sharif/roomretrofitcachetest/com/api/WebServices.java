package sharif.roomretrofitcachetest.com.api;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import retrofit2.http.GET;
import retrofit2.http.Query;
import sharif.roomretrofitcachetest.com.networkutils.ApiResponse;
import sharif.roomretrofitcachetest.com.repository.RepoSearchResponse;

public interface WebServices {

    //https://api.github.com/search/repositories?q={q}

    @GET("search/repositories?q={search}")
    MutableLiveData<ApiResponse<RepoSearchResponse>> getRepo(@Query("search") String searchValue);

}
