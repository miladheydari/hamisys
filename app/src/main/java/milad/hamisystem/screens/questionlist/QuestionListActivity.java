package milad.hamisystem.screens.questionlist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import milad.hamisystem.HamisysApplication;
import milad.hamisystem.R;
import milad.hamisystem.models.Question;
import milad.hamisystem.utils.customviews.LoadingLayout;


public class QuestionListActivity extends AppCompatActivity implements QuestionContract.View {

    @BindView(R.id.rvQuestion)
    LinearLayout rvQuestion;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    @BindView(R.id.sendBtn)
    com.rey.material.widget.Button btnSend;
    @BindView(R.id.myToolbar)
    Toolbar toolbar;

    @Inject
    public QuestionPresenter mPresenter;
    private QuestionAdapter mListAdapter;


    private List<Question> questions = new ArrayList<>();
    private List<Question> questionAnswerRes = new ArrayList<>();


    @OnClick(R.id.sendBtn)
    public void send(View v) {
        Log.d("Result", ((new Gson()).toJson(questionAnswerRes)));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        HamisysApplication.getComponent().plus(new QuestionPresenterModule()).inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        loadinglayout.setState(LoadingLayout.STATE_SHOW_DATA);
        loadinglayout.setListener(() -> mPresenter.onLoadQuestion());


        mListAdapter = new QuestionAdapter(QuestionListActivity.this, rvQuestion, questions, mPresenter);


        mPresenter.onViewAttached(this);
        mPresenter.subscribe();

        mPresenter.onLoadQuestion();

    }


    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.unsubscribe();
    }


    @Override
    public void showQuestions(List<Question> tasks) {
        questions.clear();
        questions.addAll(tasks);
        mListAdapter = new QuestionAdapter(QuestionListActivity.this, rvQuestion, questions, mPresenter);

    }

    @Override
    public void showToast(String txt) {
        Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
    }



    public void hideLoadingForQuestion() {
        if (loadinglayout.getState() != LoadingLayout.STATE_SHOW_DATA)
            loadinglayout.setState(LoadingLayout.STATE_SHOW_DATA);
    }

    @Override
    public void answerOnChange(List<Question> questions) {
        questionAnswerRes.clear();
        questionAnswerRes.addAll(questions);
    }

    @Override
    public void onError() {
        mPresenter.onLoadQuestion();
    }


}

