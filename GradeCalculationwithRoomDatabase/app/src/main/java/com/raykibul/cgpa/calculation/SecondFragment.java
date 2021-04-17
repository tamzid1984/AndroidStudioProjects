package com.raykibul.cgpa.calculation;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.raykibul.cgpa.DataController;
import com.raykibul.cgpa.GradeRepository;
import com.raykibul.cgpa.R;
import com.raykibul.cgpa.model.Course;

import java.util.ArrayList;
import java.util.List;

public class SecondFragment extends Fragment {
  View rootview;
  DataController controller;

  EditText creditText,gpaText;
  Button addButton;
  TextView cgpaTextView;
  double totalCredit=0;
  double productofGPAandCredit =0;
  RecyclerView recyclerView;
  CourseRecyclerAdapter adapter;
  List<Course> myCourses =new ArrayList<>();
  FloatingActionButton fab;

  GradeRepository repository;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview=inflater.inflate(R.layout.fragment_second, container, false);
        controller=DataController.getInstance();
        repository=new GradeRepository(getActivity().getApplication());
        myCourses=repository.GetCourseById(controller.getCurrentSemister().getId());
        cgpaTextView=rootview.findViewById(R.id.textView3);
        if (myCourses.size()>0){
            CalculateCGPAList(myCourses);
        }
        creditText=rootview.findViewById(R.id.editTextTextPersonName2);
        gpaText=rootview.findViewById(R.id.editTextTextPersonName);
        addButton=rootview.findViewById(R.id.button);

        recyclerView=rootview.findViewById(R.id.courseRecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter= new CourseRecyclerAdapter(myCourses);
        recyclerView.setAdapter(adapter);
        fab=rootview.findViewById(R.id.fab_courseFragment);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|
                ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
               Delete(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);



        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(creditText.getText().toString().equals("")||gpaText.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Please All insert Fields", Toast.LENGTH_SHORT).show();
                }else{
                    CalculateCGPA(gpaText.getText().toString(),creditText.getText().toString());
                }

            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setMessage("Do you want to save?")
                        .setTitle("Warning!!")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                              //save courselist
                             if (myCourses==null||myCourses.size()==0){
                                 Toast.makeText(getActivity(), "Add a course First", Toast.LENGTH_SHORT).show();
                             }else{
                                 repository.InsertCourseList(myCourses);
                                 Toast.makeText(getActivity(), "Courses Saved!!", Toast.LENGTH_SHORT).show();

                             }

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();


            }
        });

        Toast.makeText(getActivity(), controller.getCurrentSemister().getSemisterName(), Toast.LENGTH_SHORT).show();
        return rootview;
    }

    private void CalculateCGPAList(List<Course> myCourses) {
        for (int i=0;i<myCourses.size();i++){
             Course temp = myCourses.get(i);
             totalCredit+=temp.getCourseCredit();
             productofGPAandCredit+=(temp.getCourseCredit()*temp.getCourseGpa());

        }

        double cgpa = productofGPAandCredit /totalCredit;
        cgpaTextView.setText(String.format("CGPA : %.2f",cgpa));
    }

    private  void CalculateCGPA(String gpa,String credit){
              double gpaValue =Double.parseDouble(gpa);
              double creditValue =Double.parseDouble(credit);

              productofGPAandCredit +=(gpaValue*creditValue);
              totalCredit+=creditValue;
              double cgpa = productofGPAandCredit /totalCredit;
              cgpaTextView.setText(String.format("CGPA : %.2f",cgpa));

              Course course =new Course(gpaValue,creditValue,controller.getCurrentSemister().getId());
              myCourses.add(course);
              adapter.notifyDataSetChanged();
    }

  private  void Delete(int position ){
        Course course = myCourses.get(position);
        repository.DeleteCourse(course);
        myCourses.remove(course);
        adapter.notifyDataSetChanged();

  }
}