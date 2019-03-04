package sharif.roomretrofitcachetest.dagger2test;

import android.util.Log;

import javax.inject.Inject;

public class Car {
    private static final String TAG = "Car";

    @Inject Engine engine;
    Wheels wheels;

    @Inject
    public Car(Wheels wheels) {
        this.wheels = wheels;
    }

    @Inject
    public void enableRemote(Remote remote){

        remote.setListener(this);
    }

    public void drive(){
        engine.startEngine();
        Log.d(TAG, "drive: Driving.....");
    }
}
