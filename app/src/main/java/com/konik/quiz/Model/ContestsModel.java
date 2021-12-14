package com.konik.quiz.Model;

import java.util.Date;

public class ContestsModel {
    private String QuizUID = "NO";
    private String QuizName = "NO";
    private String QuizPhotoUrl = "NO";
    private String QuizSyllabus = "NO";
    private String QuizExtra = "NO";
    private String QuizCreator = "NO";
    private long QuiziTotalQues = 0;
    private long QuiziPriority = 0;
    private long QuiziTotalParticipant = 0;
    private long QuiziDuration = 0;
    private Date QuiziDate = null;

    public ContestsModel() {
    }

    public ContestsModel(String quizUID, String quizName, String quizPhotoUrl, String quizSyllabus, String quizExtra, String quizCreator, long quiziTotalQues, long quiziPriority, long quiziTotalParticipant, long quiziDuration, Date quiziDate) {
        QuizUID = quizUID;
        QuizName = quizName;
        QuizPhotoUrl = quizPhotoUrl;
        QuizSyllabus = quizSyllabus;
        QuizExtra = quizExtra;
        QuizCreator = quizCreator;
        QuiziTotalQues = quiziTotalQues;
        QuiziPriority = quiziPriority;
        QuiziTotalParticipant = quiziTotalParticipant;
        QuiziDuration = quiziDuration;
        QuiziDate = quiziDate;
    }

    public String getQuizUID() {
        return QuizUID;
    }

    public String getQuizName() {
        return QuizName;
    }

    public String getQuizPhotoUrl() {
        return QuizPhotoUrl;
    }

    public String getQuizSyllabus() {
        return QuizSyllabus;
    }

    public String getQuizExtra() {
        return QuizExtra;
    }

    public String getQuizCreator() {
        return QuizCreator;
    }

    public long getQuiziTotalQues() {
        return QuiziTotalQues;
    }

    public long getQuiziPriority() {
        return QuiziPriority;
    }

    public long getQuiziTotalParticipant() {
        return QuiziTotalParticipant;
    }

    public long getQuiziDuration() {
        return QuiziDuration;
    }

    public Date getQuiziDate() {
        return QuiziDate;
    }
}
