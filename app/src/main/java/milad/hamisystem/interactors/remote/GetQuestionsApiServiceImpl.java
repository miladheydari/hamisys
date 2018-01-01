package milad.hamisystem.interactors.remote;

import com.google.gson.Gson;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;
import milad.hamisystem.models.QuestionList;
import retrofit2.HttpException;


public class GetQuestionsApiServiceImpl implements GetQuestionsApiService {
    private final ApiInterface api;

    public GetQuestionsApiServiceImpl(ApiInterface api) {
        this.api = api;
    }

    @Override
    public Observable<QuestionList> getQuestions() {
        return api.getQuestions()
                .compose(this.parseHttpErrors());
    }




    <T> ObservableTransformer<T, T> parseHttpErrors() {
        return observable -> observable.onErrorResumeNext((Function<Throwable, ObservableSource<? extends T>>) throwable -> {
            if (throwable instanceof HttpException) {

                Gson gson=new Gson();
                GeneralApiException generalApiException=null;
                try {
                    generalApiException=gson.fromJson(((HttpException) throwable).response().errorBody().string(),GeneralApiException.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(generalApiException==null)
                    return Observable.error(throwable);
                else
                    return Observable.error(generalApiException);
            }
            // if not the kind we're interested in, then just report the same error to onError()
            return Observable.error(throwable);
        });
    }
}
