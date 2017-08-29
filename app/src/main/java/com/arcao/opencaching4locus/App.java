package com.arcao.opencaching4locus;

import android.util.Log;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import dagger.android.support.DaggerApplication;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Created by Arcao on 30.08.2017.
 */

public class App extends DaggerApplication {
  private static final String TAG = App.class.getSimpleName();

  @Inject
  void logInjection() {
    Log.i(TAG, "Injecting " + TAG);
  }

  @Override
  public void onCreate() {
    super.onCreate();

    Timber.plant(new Timber.DebugTree());
  }

  @Override
  protected AndroidInjector<App> applicationInjector() {
    return DaggerApp_Component.builder().create(this);
  }


  // =============== Dagger 2 ===============
  @dagger.Component(
      modules = {AndroidSupportInjectionModule.class}
  )
  interface Component extends AndroidInjector<App> {
    @dagger.Component.Builder
    abstract class Builder extends AndroidInjector.Builder<App> {}
  }
}
