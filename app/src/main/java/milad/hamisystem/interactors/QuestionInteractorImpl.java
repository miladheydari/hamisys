package milad.hamisystem.interactors;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import milad.hamisystem.interactors.remote.GetQuestionsApiService;
import milad.hamisystem.models.Question;
import milad.hamisystem.utils.SchedulerProvider;


public class QuestionInteractorImpl implements QuestionInteractor {

    private final GetQuestionsApiService getQuestionsApiService;
    private final SchedulerProvider scheduler;

    @Inject
    public QuestionInteractorImpl(GetQuestionsApiService getQuestionsApiService, SchedulerProvider scheduler) {
        this.getQuestionsApiService = getQuestionsApiService;
        this.scheduler = scheduler;
    }

    @Override
    public Observable<List<Question>> getQuestions() {
        return this.getQuestionsApiService.getQuestions()
                .map(questionList -> questionList.data)
                .subscribeOn(scheduler.backgroundThread())
                .observeOn(scheduler.mainThread());
    }
}
