package sharif.roomretrofitcachetest.com.api;

import android.arch.lifecycle.LiveData;

import retrofit2.http.GET;
import retrofit2.http.Query;
import sharif.roomretrofitcachetest.com.networkutils.ApiResponse;
import sharif.roomretrofitcachetest.com.reponse.RepoSearchResponse;

public interface WebServices {

    //https://api.github.com/search/repositories?q={q}
    //search/repositories?q={search}"
    //search/repositories?q=android

    @GET("search/repositories")
    LiveData<ApiResponse<RepoSearchResponse>> getRepo(@Query("q") String searchValue);

}
