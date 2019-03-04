package sharif.roomretrofitcachetest.dagger2test;

import android.util.Log;

import javax.inject.Inject;


public class Remote {
    private static final String TAG = "Car";

    @Inject
    public Remote() {
    }

    void setListener(Car car) {
        Log.d(TAG, "setListener: Enable remote......");
    }

}
