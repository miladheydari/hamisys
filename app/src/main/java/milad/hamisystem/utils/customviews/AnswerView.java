package milad.hamisystem.utils.customviews;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.rey.material.widget.CheckBox;


import java.util.ArrayList;
import java.util.List;

import milad.hamisystem.R;
import milad.hamisystem.screens.questionlist.AnswerOnChangeInterface;
import milad.hamisystem.models.Answer;
import milad.hamisystem.models.AnswerType;
import milad.hamisystem.utils.Utility;


public class AnswerView extends RelativeLayout implements CompoundButton.OnCheckedChangeListener {

    private Context mContext;
    private RadioGroup mRadioGroup;
    private LinearLayout mCheckBoxList;
    private EditText mEditText;
    private LinearLayout mAnswerChildList;
    private List<Answer> mAnswers;
    private AnswerType mAnswerType;
    private List<Answer> result = new ArrayList<>();
    private AnswerOnChangeInterface mAnswerListener;
    private boolean flagFetchedView = false;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public AnswerView(Context context) {
        super(context);
        initializeViews(context);
    }

    public AnswerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public AnswerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AnswerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initializeViews(context);
    }

    private void initializeViews(Context context) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.row_answer, this, true);
        this.mContext = context;
        mAnswerChildList = (LinearLayout) v.findViewById(R.id.answerChildList);
        mRadioGroup = (RadioGroup) v.findViewById(R.id.radioList);
        mCheckBoxList = (LinearLayout) v.findViewById(R.id.checkboxList);
        mEditText = (EditText) v.findViewById(R.id.editText);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void fetchView() {
        removeAllViews();
        initializeViews(mContext);
        if (mAnswerType != null) {
            if (mAnswerType.equals(AnswerType.RADIO)) {
                mRadioGroup.setVisibility(VISIBLE);
                mCheckBoxList.setVisibility(GONE);
                mEditText.setVisibility(GONE);
                for (int i = 0; i < mAnswers.size(); i++) {
                    Answer answer = mAnswers.get(i);

                    RadioButton radioButton = new RadioButton(mContext);
                    radioButton.setText(answer.value);
                    radioButton.setGravity(Gravity.CENTER | Gravity.LEFT);
                    radioButton.setTag(R.string.default_value_tag, answer);
                    radioButton.setOnCheckedChangeListener(this);
                    mRadioGroup.addView(radioButton);
                }


            } else if (mAnswerType.equals(AnswerType.CHECK)) {
                mRadioGroup.setVisibility(GONE);
                mCheckBoxList.setVisibility(VISIBLE);
                mEditText.setVisibility(GONE);
                for (Answer answer : mAnswers) {
                    CheckBox checkBox = new CheckBox(mContext);
                    checkBox.setText(answer.value);
                    checkBox.setGravity(Gravity.CENTER | Gravity.LEFT);
                    checkBox.setTag(R.string.default_value_tag, answer);
                    checkBox.setOnCheckedChangeListener(this);
                    mCheckBoxList.addView(checkBox);

                }
            } else if (mAnswerType.equals(AnswerType.TEXT)) {
                mRadioGroup.setVisibility(GONE);
                mCheckBoxList.setVisibility(GONE);
                mEditText.setVisibility(VISIBLE);
                Answer answer = mAnswers.get(0);
                mEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        result.clear();
                        Answer answer1 = Utility.copy(answer);
                        answer1.value = String.valueOf(s);
                        result.add(answer1);
                        mAnswerListener.answerOnChange(result);

                    }
                });

            }
        }

    }


    public void setAnswers(List<Answer> mAnswers) {
        this.mAnswers = mAnswers;
        fetchView();
    }

    public List<Answer> getmAnswers() {
        return mAnswers;
    }

    public void setAnswerType(AnswerType mAnswerType) {
        this.mAnswerType = mAnswerType;
    }


    public void setAnswerListener(AnswerOnChangeInterface mAnswerListener) {
        this.mAnswerListener = mAnswerListener;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        Answer newAnswer = Utility.copy((Answer) buttonView.getTag(R.string.default_value_tag));
        if (buttonView.getTag(R.string.value_tag) != null)
            newAnswer = Utility.copy((Answer) buttonView.getTag(R.string.value_tag));


        AnswerView answerView = new AnswerView(mContext);
        if (isChecked) {
            result.add(newAnswer);
            if (newAnswer.answerType != null) {
                answerView.setAnswerType(newAnswer.answerType);
                Answer finalNewAnswer = newAnswer;
                answerView.setAnswerListener(answers -> {
                    finalNewAnswer.subAnswers.clear();
                    finalNewAnswer.subAnswers.addAll(answers);
                    mAnswerListener.answerOnChange(result);
                });
                answerView.setAnswers(newAnswer.subAnswers);
                mAnswerChildList.addView(answerView);
                buttonView.setTag(R.string.view_tag, answerView);
            }
            buttonView.setTag(R.string.value_tag, newAnswer);
        } else {
            result.remove(newAnswer);
            if (newAnswer.answerType != null)
                mAnswerChildList.removeView((AnswerView) buttonView.getTag(R.string.view_tag));
            buttonView.setTag(R.string.value_tag, null);
        }
        mAnswerListener.answerOnChange(result);
    }

}
