package milad.hamisystem.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import milad.hamisystem.HamisysApplication;

@Module
public class AndroidModule {

    @Provides
    @Singleton
    public static Context provideContext(HamisysApplication application) {
        return application.getApplicationContext();
    }


}
