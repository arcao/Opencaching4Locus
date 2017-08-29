package com.arcao.opencaching4locus.ui.dashboard;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
import com.arcao.opencaching4locus.App;
import com.arcao.opencaching4locus.ui.base.BaseActivity;
import dagger.Binds;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;
import javax.inject.Inject;

/**
 * Created by Arcao on 30.08.2017.
 */

public class DashboardActivity extends BaseActivity {
  @Inject App app;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Toast.makeText(app, "test", Toast.LENGTH_LONG).show();
    finish();
  }

  // =============== Dagger 2 ===============
  @dagger.Subcomponent
  interface Component extends AndroidInjector<DashboardActivity> {

    @dagger.Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<DashboardActivity> {}
  }

  @dagger.Module(subcomponents = Component.class)
  abstract class Module {
    @Binds
    @IntoMap
    @ActivityKey(DashboardActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bind(Component.Builder builder);
  }
}
