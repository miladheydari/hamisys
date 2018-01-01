package milad.hamisystem.di;


import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import milad.hamisystem.HamisysApplication;
import milad.hamisystem.di.common.ApiModule;
import milad.hamisystem.di.common.ClientModule;
import milad.hamisystem.screens.questionlist.QuestionComponent;
import milad.hamisystem.screens.questionlist.QuestionPresenterModule;

@Singleton
@Component( modules = {
        AndroidModule.class,
        ApplicationModule.class,
        ApiModule.class,
        InteractorModule.class,
        ClientModule.class,
})
public interface ApplicationComponent {


    QuestionComponent plus(QuestionPresenterModule module);


    @Component.Builder
    interface Builder
    {
        ApplicationComponent build();
        @BindsInstance Builder application(HamisysApplication application);
    }

}

