package sharif.roomretrofitcachetest.dagger2test;

import android.util.Log;

import javax.inject.Inject;

public class PetrolEngine implements Engine {
    private static final String TAG = "Car";

    @Inject
    public PetrolEngine() {
    }

    @Override
    public void startEngine() {
        Log.d(TAG, "startEngine: Petrol Engine Start....");
    }
}
