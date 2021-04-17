package com.raykibul.cgpa.home;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.raykibul.cgpa.DataController;
import com.raykibul.cgpa.GradeRepository;
import com.raykibul.cgpa.R;
import com.raykibul.cgpa.model.Semister;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeFragmentInterface {
      View root;
      GradeRepository repository;
      RecyclerView recyclerView;
      HomeRecyclerAdapter adapter;
      List<Semister> allSemisters= new ArrayList<>();
      DataController controller;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root=inflater.inflate(R.layout.fragment_home, container, false);
        repository=new GradeRepository(getActivity().getApplication());
        recyclerView=root.findViewById(R.id.home_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        allSemisters=repository.GetAllSemisters();
        adapter = new HomeRecyclerAdapter(allSemisters);
        recyclerView.setAdapter(adapter);
        controller=DataController.getInstance();
        controller.setHomeFragmentInterface(this);

        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.custom_user_input_dialog);
                final EditText semisterNameEditex = dialog.findViewById(R.id.dialog_semisterName_editext);
                Button createButton =dialog.findViewById(R.id.dialog_create_button);

                createButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       if (semisterNameEditex.getText().toString().equals("")){
                           Toast.makeText(getActivity(), "Please Insert Semister Name", Toast.LENGTH_LONG).show();
                       }else{
                           String semisterName =semisterNameEditex.getText().toString();
                           Toast.makeText(getActivity(), semisterName+ " Is Semister Name", Toast.LENGTH_SHORT).show();
                           insertSemister(semisterName);
                           dialog.dismiss();

                       }
                    }
                });

                dialog.show();

            }
        });

         return root;
    }

private  void  insertSemister(String semisterName){
     Semister temp=new Semister(semisterName,0.0);
     allSemisters.add(temp);
     adapter.notifyDataSetChanged();
     repository.InsertSemister(temp);
}


    @Override
    public void onSemisterItemClick(Semister semister) {

        controller.setCurrentSemister(semister);
        NavHostFragment.findNavController(HomeFragment.this)
                .navigate(R.id.action_FirstFragment_to_SecondFragment);
    }
}