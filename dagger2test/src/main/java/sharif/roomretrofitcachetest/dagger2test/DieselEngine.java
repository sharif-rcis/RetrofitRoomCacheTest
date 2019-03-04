package sharif.roomretrofitcachetest.dagger2test;

import android.util.Log;

import javax.inject.Inject;

public class DieselEngine implements Engine {
    private static final String TAG = "Car";

    @Inject
    public DieselEngine() {
    }

    @Override
    public void startEngine() {
        Log.d(TAG, "startEngine: Diesel Engine Start.....");
    }
}
