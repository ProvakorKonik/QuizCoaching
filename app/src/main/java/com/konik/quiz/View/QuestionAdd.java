package com.konik.quiz.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.konik.quiz.R;

import java.util.HashMap;
import java.util.Map;

public class QuestionAdd extends AppCompatActivity {

    private TextView mHeadText;
    private EditText mTypeQuestionEdit, mTypeAnswerEdit, mF2AnswerEdit, mF3AnswerEdit, mF4AnswerEdit;
    private Button mAddBtn;

    private String dsQuestion = "NO", dsAnswer = "NO", dsF2 = "NO", dsF3 = "NO", dsF4="NO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_add);

        getIntentMethod();
        mHeadText = (TextView)findViewById(R.id.ques_add_head_text);
        mTypeQuestionEdit = (EditText)findViewById(R.id.ques_add_edit);
        mTypeAnswerEdit = (EditText)findViewById(R.id.ques_add_answer_edit);
        mF2AnswerEdit = (EditText)findViewById(R.id.ques_add_false2_edit);
        mF3AnswerEdit = (EditText)findViewById(R.id.ques_add_false3_edit);
        mF4AnswerEdit = (EditText)findViewById(R.id.ques_add_false4_edit);
        mAddBtn = (Button)findViewById(R.id.ques_add_btn) ;
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkData();
            }
        });
    }
    private void checkData() {
        dsQuestion = mTypeQuestionEdit.getText().toString();
        dsAnswer = mTypeAnswerEdit.getText().toString();
        dsF2 = mF2AnswerEdit.getText().toString();
        dsF3 = mF3AnswerEdit.getText().toString();
        dsF4 = mF4AnswerEdit.getText().toString();
        if(dsQuestion.equals("NO") || mTypeAnswerEdit.equals("NO") || mF2AnswerEdit.equals("NO") || mF3AnswerEdit.equals("NO") || mF4AnswerEdit.equals("NO") ){
            Toast.makeText(getApplicationContext(),"Please Fillup All", Toast.LENGTH_SHORT).show();;
        }else if(dsQuestion.equals("") || mTypeAnswerEdit.equals("") || mF2AnswerEdit.equals("") || mF3AnswerEdit.equals("") || mF4AnswerEdit.equals("") ) {
            Toast.makeText(getApplicationContext(),"Please Fillup Al", Toast.LENGTH_SHORT).show();
        }else{
            uploadData();
        }
    }
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private void uploadData() {

        Map<String, Object> note = new HashMap<>();
        note.put("Ques", dsQuestion);
        note.put("Ans", dsAnswer);
        note.put("FalseA", dsF2);
        note.put("FalseB", dsF2);
        note.put("FalseC", dsF3);
        note.put("FalseD", dsF4);


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        DocumentReference quesRef = db.collection("Rakib").document("Info")
                .collection("AllContest").document(dsContestUID);

        quesRef.collection("Questions").add(note)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(),"Successfully Uploaded", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        mTypeQuestionEdit.setText("");
                        mTypeAnswerEdit.setText("");
                        mF2AnswerEdit.setText("");
                        mF3AnswerEdit.setText("");
                        mF4AnswerEdit.setText("");

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                mAddBtn.setText("Try Again");
                Toast.makeText(getApplicationContext(),"Failed Please Try Again", Toast.LENGTH_SHORT).show();

            }
        });


    }


    String dsContestUID = "NO";
    private boolean intentFoundError = true;
    private void getIntentMethod() {
        //////////////GET INTENT DATA
        final Intent intent = getIntent();
        if(intent.getExtras() != null)
        {

            dsContestUID = intent.getExtras().getString("dsContestUID");    //Sylhet
            intentFoundError = CheckIntentMethod(dsContestUID);

            if(!intentFoundError){

            }else{
                Toast.makeText(getApplicationContext(),"Intent error", Toast.LENGTH_SHORT).show();;
            }
        }else{
            dsContestUID = "NO";
            Toast.makeText(getApplicationContext(),"Intent error", Toast.LENGTH_SHORT).show();;

        }

    }
    private boolean CheckIntentMethod(String dsTestIntent){
        if(TextUtils.isEmpty(dsTestIntent)) {
            dsTestIntent= "NO";
            Toast.makeText(getApplicationContext(), "intent NULL  " , Toast.LENGTH_SHORT).show();
        }else if (dsTestIntent.equals("")){
            dsTestIntent= "NO";
            Toast.makeText(getApplicationContext(), "intent 404" , Toast.LENGTH_SHORT).show();
        }else{
            intentFoundError = false;
        }
        return intentFoundError;
    }
}