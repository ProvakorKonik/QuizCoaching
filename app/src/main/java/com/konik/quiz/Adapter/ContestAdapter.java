package com.konik.quiz.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.konik.quiz.Model.ContestsModel;
import com.konik.quiz.R;
import com.konik.quiz.RecylerviewClickInterface;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ContestAdapter     extends RecyclerView.Adapter<ContestAdapter.ContestAdapter_Holder> {
private Context mContext;
private List<ContestsModel> mData;
private RecylerviewClickInterface recylerviewClickInterface;
String dUserType ;
public ContestAdapter (android.content.Context mContext, List<ContestsModel> mData, RecylerviewClickInterface recylerviewClickInterface, String dUserType) {
        this.mContext = mContext;
        this.mData = mData;
        this.recylerviewClickInterface = recylerviewClickInterface;
        this.dUserType = dUserType;
        }

@NonNull
@Override
public ContestAdapter.ContestAdapter_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_contest,parent,false); //connecting to cardview
        return new ContestAdapter.ContestAdapter_Holder(view);
        }



    @Override
public void onBindViewHolder(@NonNull ContestAdapter.ContestAdapter_Holder holder, int position) {
        String dPhotoURL = mData.get(position).getQuizPhotoUrl();
        Picasso.get().load(dPhotoURL).fit().centerCrop().into(holder.mItemImageView);

        String dsTitle = mData.get(position).getQuizName();
        long diViews = mData.get(position).getQuiziPriority();
        String Syllabus = mData.get(position).getQuizSyllabus();

        holder.mItemTittleText.setText(dsTitle);
        holder.mItemBioText.setText(Syllabus);


            if(dUserType.equals("Teacher")){
                holder.mItemImageViewAddQuestion.setVisibility(View.VISIBLE);
            }else{
                holder.mItemImageViewAddQuestion.setVisibility(View.GONE);
            }

        }

@Override
public int getItemCount() {
        return mData.size();
        }


class ContestAdapter_Holder extends RecyclerView.ViewHolder {

    ImageView mItemImageView,mItemImageViewAddQuestion;
    TextView mItemTittleText;
    TextView mItemBioText;
    Button mItemStrartBtn;
    public ContestAdapter_Holder(@NonNull View itemView) {
        super(itemView);

        mItemImageView = (ImageView) itemView.findViewById(R.id.item_contest_item_img);
        mItemImageViewAddQuestion = (ImageView)itemView.findViewById(R.id.item_contest_add_ques);
        mItemTittleText = (TextView)itemView.findViewById(R.id.item_contest_title_id);
        mItemBioText = (TextView)itemView.findViewById(R.id.item_contest_syllabus_text);
        mItemStrartBtn = (Button) itemView.findViewById(R.id.item_contest_start_btn);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recylerviewClickInterface .onItemClick(getAdapterPosition());
            }
        });
        mItemStrartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recylerviewClickInterface .onItemClick(getAdapterPosition());
            }
        });

        mItemImageViewAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recylerviewClickInterface.onAddQuesItemClick(getAdapterPosition());
            }
        });

    }
}



}
