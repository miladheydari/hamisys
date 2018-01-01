package milad.hamisystem.di;

import dagger.Binds;
import dagger.Module;
import milad.hamisystem.interactors.QuestionInteractor;
import milad.hamisystem.interactors.QuestionInteractorImpl;

@Module
public abstract class InteractorModule {

    @Binds
    public abstract QuestionInteractor provideQuestionInteractor(QuestionInteractorImpl interactor);

}
