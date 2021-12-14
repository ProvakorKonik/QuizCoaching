package com.konik.quiz.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.konik.quiz.Adapter.ContestAdapter;
import com.konik.quiz.LoginCheck;
import com.konik.quiz.LoginRegistration;
import com.konik.quiz.Model.ContestsModel;
import com.konik.quiz.R;
import com.konik.quiz.RecylerviewClickInterface;
import com.konik.quiz.ViewModel.ContestVM;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecylerviewClickInterface {
    //05Nov2021
    private ImageView mUserImage;
    private TextView mUserText;
    //RecyclerView
    private RecyclerView mContest_RecylcerView;
    List<ContestsModel> listContestItem = new ArrayList<>();;
    ContestAdapter mContest_adapter;

    //Firebase Auth
    private String dUserUID = "NO";
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener; //For going to Account Activity Page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUserImage = (ImageView)findViewById(R.id.main_user_image);
        mUserText = (TextView)findViewById(R.id.main_text_user_name);
        mContest_RecylcerView = (RecyclerView)findViewById(R.id.main_quiz_recylerview) ;
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() { ///for going to Account Activity Page
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if(user != null){
                    dUserUID = user.getUid();
                    checkUserType();

                }else{

                    Intent intent = new Intent(getApplicationContext(), LoginCheck.class);
                    startActivity(intent);
                }
            }
        };


        mUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginCheck.class);
                intent.setFlags( intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });
    }
    String dUserType =  "NO";
    public void checkUserType(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference user_data_ref = db.collection("Rakib").document("Info");
        user_data_ref.collection("ALL_USER").document(dUserUID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    dUserType = documentSnapshot.getString("userType");
                    String dUserName= documentSnapshot.getString( "name");
                    String dUserPhotoURL = documentSnapshot.getString("photoURL");
                    mUserText.setText("Hello "+dUserName);
                    Picasso.get().load(dUserPhotoURL).fit().centerCrop().into(mUserImage);
                    callViewModel();
                }else{
                    Toast.makeText(getApplicationContext(),"Error User Information Not Found", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, LoginRegistration.class);
                    startActivity(intent);
                }
            }
        });
    }
    //View Model
    private ContestVM contestsVM;
    private void callViewModel() {
        Log.d("ViewModel", "allViewModel:2 ContestActivityVM start");
        contestsVM = new ViewModelProvider(this).get(ContestVM.class);
        contestsVM.ContestsList().observe(this, new Observer<List<ContestsModel>>() {
            @Override
            public void onChanged(List<ContestsModel> contest_model_list) {
                Log.d("ViewModel", "allViewModel:2 onChanged listview4 size = "+contest_model_list.size());
                if (contest_model_list.get(0).getQuizName().equals("NULL")){
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "No Items Found", Snackbar.LENGTH_LONG)
                            .setAction("CLOSE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                            .show();
                }else{

                    mContest_adapter = new ContestAdapter(MainActivity.this,contest_model_list,MainActivity.this,dUserType);
                    mContest_adapter.notifyDataSetChanged();
                    //
                    listContestItem = contest_model_list;
                    int orientation = getResources().getConfiguration().orientation;
                    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        mContest_RecylcerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
                        mContest_RecylcerView.setAdapter(mContest_adapter);
                    } else {
                        mContest_RecylcerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL,false));
                        //mContest_RecylcerView.setLayoutManager(new GridLayoutManager(MainActivity.this,1));
                        mContest_RecylcerView.setAdapter(mContest_adapter);
                    }
                }
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onItemClick(int position) {
        String dsContestUID = listContestItem.get(position).getQuizUID();
        long dlDuration = listContestItem.get(position).getQuiziDuration();
        Intent intent = new Intent(getApplicationContext(), Exam.class);
        intent.putExtra("dsContestUID", dsContestUID);
        intent.putExtra("dlDuration", dlDuration);
        intent.putExtra("dsBtnMode", "StartExam");
        startActivity(intent);
    }

    @Override
    public void onAddQuesItemClick(int position) {
        String dsContestUID = listContestItem.get(position).getQuizUID();

        Intent intent = new Intent(getApplicationContext(), QuestionAdd.class);
        intent.putExtra("dsContestUID", dsContestUID);
        startActivity(intent);
    }
}