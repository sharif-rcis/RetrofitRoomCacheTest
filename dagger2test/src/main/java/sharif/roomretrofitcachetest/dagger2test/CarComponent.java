package sharif.roomretrofitcachetest.dagger2test;

import dagger.Component;

@Component(modules = {WheelsModule.class,DieselEngineModule.class})
public interface CarComponent {
    Car getCar();
    void inject(MainActivity mainActivity);
}
