package com.konik.quiz.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.konik.quiz.Model.QuestionModel;

import java.util.ArrayList;
import java.util.List;

public class ExamVM extends AndroidViewModel {
    public ExamVM(@NonNull Application application) {
        super(application);
        Log.d("ViewModel", "allViewModel:4 QuestionsListVM start");
    }
    public MutableLiveData mLiveData;
    public MutableLiveData<List<QuestionModel>> ContestsList(String dsContestUID) {

        List<QuestionModel> listQuestiontItem ; listQuestiontItem = new ArrayList<>();
        CollectionReference notebookRef;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Log.d("ViewModel", "allViewModel:4 LoadLevel4List start");

        notebookRef = db.collection("Rakib").document("Info")
                .collection("AllContest").document(dsContestUID).collection("Questions");

        if(mLiveData == null) {
            mLiveData = new MutableLiveData();
            notebookRef
                    //.orderBy("Serial", Query.Direction.ASCENDING)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {   //documnet er vitore je multiple document query ache er jonno for loop
                            String data = "";
                            if(queryDocumentSnapshots.isEmpty()) {
                                //String quesUID, String userSelectedAnswer, int answerOnIndex, String ques, String ans, String falseA, String falseB, String falseC, String falseD, long serial
                                listQuestiontItem.add(new QuestionModel("NULL","userSelectedAnswer",1, "ques","ans", "f1", "f2", "f3", "f4" , 0));
                                mLiveData.postValue(listQuestiontItem);
                                Log.d("ViewModel", "allViewModel:4 queryDocumentSnapshots empty");
                            }else {
                                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                    QuestionModel questionModel = documentSnapshot.toObject(QuestionModel.class);
                                    String dsQuesUID = questionModel.getQuesUID();
                                    String dsUserSelectedAnswer = questionModel.getUserSelectedAnswer();
                                    String dsQues = questionModel.getQues();
                                    String dsAns = questionModel.getAns();
                                    String dsF1 = questionModel.getFalseA();
                                    String dsF2 = questionModel.getFalseB();
                                    String dsF3 = questionModel.getFalseC();
                                    String dsF4 = questionModel.getFalseD();
                                    long dlSerial = questionModel.getSerial();
                                    listQuestiontItem.add(new QuestionModel(dsQuesUID,dsUserSelectedAnswer,1, dsQues,dsAns,dsF1,dsF2, dsF3,  dsF4,dlSerial));
                                    mLiveData.postValue(listQuestiontItem);
                                }
                                mLiveData.postValue(listQuestiontItem);
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }
        return mLiveData;
    }
}
