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
import com.konik.quiz.Model.ContestsModel;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class ContestVM extends AndroidViewModel {
    public ContestVM(@NonNull Application application) {
        super(application);
        Log.d("ViewModel", "allViewModel:4 ContestsVM start");
    }
    public MutableLiveData mLiveData;
    public MutableLiveData<List<ContestsModel>> ContestsList() {
        List<ContestsModel> listContestItem ; listContestItem =new ArrayList<>();
        CollectionReference notebookRef;        //Firrebase database link
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Log.d("ViewModel", "allViewModel:4 LoadLevel4List start");



        notebookRef = db.collection("Rakib").document("Info")
                .collection("AllContest");

        if(mLiveData == null) {
            mLiveData = new MutableLiveData();
            notebookRef
                    //.orderBy("CoiPriority", Query.Direction.ASCENDING)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {   //documnet er vitore je multiple document query ache er jonno for loop
                            String data = "";
                            if(queryDocumentSnapshots.isEmpty()) {
                                //String coUID, String coName, String coPhotoUrl, String coPassword, String coExtra, String coCreator,
                                // String quizUID, String quizName, String quizPhotoUrl, String quizSyllabus, String quizExtra, String quizCreator, long quiziTotalQues, long quiziPriority, long quiziTotalParticipant, long quiziDuration, Date quiziDate
                                listContestItem.add(new ContestsModel("UID","NULL", "PhotoUrl","Syllabus", "quizExtra", "quizCreator", 0 , 0,  0,0,null));
                                mLiveData.postValue(listContestItem);
                                Log.d("ViewModel", "allViewModel:4 queryDocumentSnapshots empty");
                            }else {
                                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                    ContestsModel contestsModel = documentSnapshot.toObject(ContestsModel.class);
                                    //messageModel.setDocumentID(documentSnapshot.getId());
                                    String dsContest_UID = documentSnapshot.getId();
                                    String dsContest_Name = contestsModel.getQuizName();
                                    String dsContest_PhotoUrl = contestsModel.getQuizPhotoUrl();
                                    String dsContest_Syllabus = contestsModel.getQuizSyllabus();
                                    String dsContest_Extra = contestsModel.getQuizExtra();
                                    String dsContest_Creator = contestsModel.getQuizCreator();

                                    long diContest_TotalQuestion = contestsModel.getQuiziTotalQues();
                                    long diContest_Priority = contestsModel.getQuiziPriority();
                                    long diContest_Participent = contestsModel.getQuiziTotalParticipant();
                                    long diContest_Duration = contestsModel.getQuiziDuration();
                                    Date diContest_Date = contestsModel.getQuiziDate();
                                    //String bookUID, String bookName, String bookPhotoUrl, String bookBio, String bookCreator, String bookExtra, String bookPassword, int bookiPriority, int bookiViewCount, int bookiTotalLevel
                                    listContestItem.add(new ContestsModel(dsContest_UID,dsContest_Name, dsContest_PhotoUrl,dsContest_Syllabus, dsContest_Extra,  dsContest_Creator,
                                             diContest_TotalQuestion, diContest_Priority,diContest_Participent ,diContest_Duration ,diContest_Date ));
                                    mLiveData.postValue(listContestItem);
                                }
                                mLiveData.postValue(listContestItem);    //All Items level 4 , it is a one type category

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
