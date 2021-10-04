package com.example.quizactivity;

public class Question {
    private int txtResId;
    private boolean answer;

    public Question(int txtResId, boolean answer) {
        this.txtResId = txtResId;
        this.answer = answer;
    }

    public int getTxtResId() {
        return txtResId;
    }

    public void setTxtResId(int txtResId) {
        this.txtResId = txtResId;
    }

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }
}
