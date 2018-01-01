package milad.hamisystem.screens.questionlist;

import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import milad.hamisystem.HamisysApplication;
import milad.hamisystem.interactors.QuestionInteractor;
import milad.hamisystem.interactors.remote.GeneralApiException;
import milad.hamisystem.models.Answer;
import milad.hamisystem.models.Question;
import milad.hamisystem.models.QuestionList;
import retrofit2.adapter.rxjava2.HttpException;

public class QuestionPresenter implements QuestionContract.Presenter {


    private QuestionContract.View viewLayer;
    private CompositeDisposable compositeDisposable;
    private final QuestionInteractor mQuestionInteractor;
    private static final String TAG = "QuestionPresenter";

    private List<Question> questionResults = new ArrayList<>();

    @Inject
    public QuestionPresenter(QuestionInteractor questionInteractor) {
        this.compositeDisposable = new CompositeDisposable();
        this.mQuestionInteractor = questionInteractor;
    }

    @Override
    public void onLoadQuestion() {


        Disposable disposable=
                mQuestionInteractor.getQuestions()
                        .subscribeWith(new DisposableObserver<List<Question>>() {
                            @Override
                            public void onComplete() {
                                Log.d(TAG, "onCompleted: ");
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (e instanceof HttpException) {
                                    Log.d(TAG, "onError StatusCode: "+((HttpException) e).code());
                                }
                                else if(e instanceof GeneralApiException)
                                {
                                    Log.d(TAG, "onError message: "+((GeneralApiException) e).message);
                                }
                                else
                                {
                                    Log.d(TAG, "onError");
                                }
                                viewLayer.showToast("error ");
                                viewLayer.onError();
                            }

                            @Override
                            public void onNext(List<Question> questions) {
                                Log.d(TAG, "onNext");
                                viewLayer.hideLoadingForQuestion();
                                viewLayer.showQuestions(questions);
                            }
                        });
        compositeDisposable.add(disposable);
    }

    @Override
    public void assignAnswer(Question question, List<Answer> answers) {
        if (questionResults.contains(question)) {
            Question questionTemp = questionResults.get(questionResults.lastIndexOf(question));
            questionTemp.answers.clear();
            questionTemp.answers.addAll(answers);
        } else {
            Question questionTemp = new Question();
            questionTemp.id = question.id;
            questionTemp.answers = new ArrayList<>();
            questionTemp.answers.addAll(answers);
            questionResults.add(questionTemp);
        }
        viewLayer.answerOnChange(questionResults);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void onViewAttached(QuestionContract.View view) {
        viewLayer = view;
    }
}
