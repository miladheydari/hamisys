package milad.hamisystem.screens.questionlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import milad.hamisystem.R;
import milad.hamisystem.models.Answer;
import milad.hamisystem.models.Question;
import milad.hamisystem.utils.customviews.AnswerView;

public class QuestionAdapter {


    public QuestionAdapter(Context mContext, LinearLayout linearLayout, List<Question> itemsData, QuestionPresenter mPresenter) {
        linearLayout.removeAllViews();
        for (Question itemsDatum : itemsData) {
            View itemLayoutView = LayoutInflater.from(mContext).inflate(R.layout.row_question, null);
            ViewHolder viewHolder = new ViewHolder(itemLayoutView, mContext);
            viewHolder.bind(itemsDatum, new AnswerOnChangeInterface() {
                @Override
                public void answerOnChange(List<Answer> answers) {
                    mPresenter.assignAnswer(itemsDatum, answers);
                }
            });
            linearLayout.addView(itemLayoutView);
        }
    }

//    public QuestionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemLayoutView = LayoutInflater.from(mContext).inflate(R.layout.row_question, null);
//        ViewHolder holder = (ViewHolder) itemLayoutView.getTag();
////        if (holder == null) {
//            holder = new ViewHolder(itemLayoutView, mContext);
////            itemLayoutView.setTag(holder);
////        }
//        return holder;
//    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.questionTv)
        TextView mTvQuestion;
        @BindView(R.id.answerView)
        AnswerView answerView;


        ViewHolder(View itemLayoutView, Context context) {
            super(itemLayoutView);

            ButterKnife.bind(this, itemLayoutView);
        }

        void bind(Question item, AnswerOnChangeInterface answerInterface) {
            mTvQuestion.setText(item.title);
            answerView.setAnswerType(item.answerType);
            answerView.setAnswerListener(answerInterface);
            answerView.setAnswers(item.answers);
        }


    }

}