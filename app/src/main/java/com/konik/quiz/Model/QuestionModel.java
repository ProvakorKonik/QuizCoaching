package com.konik.quiz.Model;

public class QuestionModel {

        private String QuesUID = "NO";
        private String UserSelectedAnswer = "NO";
        private int AnswerOnIndex = 1;
        private String Ques = "NO";
        private String Ans = "NO";
        private String FalseA = "NO";
        private String FalseB = "NO";
        private String FalseC = "NO";
        private String FalseD = "NO";
        private long Serial = 0;

    public QuestionModel() {
    }

    public QuestionModel(String quesUID, String userSelectedAnswer, int answerOnIndex, String ques, String ans, String falseA, String falseB, String falseC, String falseD, long serial) {
        QuesUID = quesUID;
        UserSelectedAnswer = userSelectedAnswer;
        AnswerOnIndex = answerOnIndex;
        Ques = ques;
        Ans = ans;
        FalseA = falseA;
        FalseB = falseB;
        FalseC = falseC;
        FalseD = falseD;
        Serial = serial;
    }

    public String getQuesUID() {
        return QuesUID;
    }

    public void setQuesUID(String quesUID) {
        QuesUID = quesUID;
    }

    public String getUserSelectedAnswer() {
        return UserSelectedAnswer;
    }

    public void setUserSelectedAnswer(String userSelectedAnswer) {
        UserSelectedAnswer = userSelectedAnswer;
    }

    public int getAnswerOnIndex() {
        return AnswerOnIndex;
    }

    public void setAnswerOnIndex(int answerOnIndex) {
        AnswerOnIndex = answerOnIndex;
    }

    public String getQues() {
        return Ques;
    }

    public void setQues(String ques) {
        Ques = ques;
    }

    public String getAns() {
        return Ans;
    }

    public void setAns(String ans) {
        Ans = ans;
    }

    public String getFalseA() {
        return FalseA;
    }

    public void setFalseA(String falseA) {
        FalseA = falseA;
    }

    public String getFalseB() {
        return FalseB;
    }

    public void setFalseB(String falseB) {
        FalseB = falseB;
    }

    public String getFalseC() {
        return FalseC;
    }

    public void setFalseC(String falseC) {
        FalseC = falseC;
    }

    public String getFalseD() {
        return FalseD;
    }

    public void setFalseD(String falseD) {
        FalseD = falseD;
    }

    public long getSerial() {
        return Serial;
    }

    public void setSerial(long serial) {
        Serial = serial;
    }
}
