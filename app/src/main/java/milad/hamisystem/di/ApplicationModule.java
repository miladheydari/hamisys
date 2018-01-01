package milad.hamisystem.di;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import milad.hamisystem.BuildConfig;
import milad.hamisystem.utils.Constants;
import milad.hamisystem.utils.SchedulerProvider;
import milad.hamisystem.utils.SchedulerProviderImpl;

@Module
public abstract class ApplicationModule {
    @Provides
    @Singleton
    @Named("isDebug")
    public static boolean provideIsDebug() {
        return BuildConfig.DEBUG;
    }

    @Provides
    @Singleton
    @Named("networkTimeoutInSeconds")
    public static int provideNetworkTimeoutInSeconds() {
        return 30;
    }



    @Provides
    @Singleton
    @Named("BaseUrl")
    public static String provideBaseUrl() {
        return Constants.BASE_URL;
    }


    @Binds
    @Singleton
    public abstract SchedulerProvider provideAppScheduler(SchedulerProviderImpl schedulerProvider) ;

}
