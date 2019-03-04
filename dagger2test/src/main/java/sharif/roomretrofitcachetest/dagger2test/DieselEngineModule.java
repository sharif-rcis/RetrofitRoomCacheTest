package sharif.roomretrofitcachetest.dagger2test;

import dagger.Module;
import dagger.Provides;

@Module
public class DieselEngineModule {

    @Provides
    Engine provideDieselEngine(DieselEngine dieselEngine){
        return dieselEngine;
    }
}
