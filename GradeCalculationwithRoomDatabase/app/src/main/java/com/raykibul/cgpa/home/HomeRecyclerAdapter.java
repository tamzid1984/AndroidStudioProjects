package com.raykibul.cgpa.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.raykibul.cgpa.DataController;
import com.raykibul.cgpa.R;
import com.raykibul.cgpa.model.Semister;

import java.util.List;

public class HomeRecyclerAdapter  extends RecyclerView.Adapter<HomeRecyclerAdapter.viewHolder> {
      List<Semister>mySemisterList;

    public HomeRecyclerAdapter(List<Semister> mySemisterList) {
        this.mySemisterList = mySemisterList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
          View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.semister_recycler_item,parent,false);
          return  new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
         Semister currentSemister = mySemisterList.get(position);
         holder.semisterNameTextView.setText(currentSemister.getSemisterName());
         holder.semisterCreditTextView.setText("Credit: "+currentSemister.getSemisterCredit()+"");

    }

    @Override
    public int getItemCount() {
       if (mySemisterList==null||mySemisterList.isEmpty()){
           return 0;
       }
       else
          return mySemisterList.size();
    }



    public  class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
         TextView semisterNameTextView,semisterCreditTextView;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            semisterNameTextView=itemView.findViewById(R.id.semistername_item_textview);
            semisterCreditTextView=itemView.findViewById(R.id.sem_textView3);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            DataController.instance.getHomeFragmentInterface().onSemisterItemClick(mySemisterList.get(getAdapterPosition()));
        }
    }
}
