package milad.hamisystem.screens.questionlist;

import dagger.Module;
import dagger.Provides;


@Module
public class QuestionPresenterModule {

    @Provides
    public QuestionContract.Presenter providePresenter(QuestionPresenter presenter) {
        return presenter;
    }

}
