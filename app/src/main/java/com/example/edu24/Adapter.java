package com.example.edu24;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {


    private List<ModelClass> mModelClassList;

    public Adapter(List<ModelClass> modelClassList) {
        mModelClassList = modelClassList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_students, viewGroup,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        String className = mModelClassList.get(position).getClassName();
        String section = mModelClassList.get(position).getSection();
        String student = mModelClassList.get(position).getStudent();
        viewHolder.setData(className, section, student);

    }

    @Override
    public int getItemCount() {
        return mModelClassList.size();
    }

    class  ViewHolder extends RecyclerView.ViewHolder{

        private TextView className;
        private TextView section;
        private TextView student;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            className = itemView.findViewById(R.id.tv_className);
            section = itemView.findViewById(R.id.tv_section);
            student = itemView.findViewById(R.id.tv_no_student);
        }

        private  void  setData(String classNameText, String sectionText, String studentText){
            className.setText(classNameText);
            section.setText(sectionText);
            student.setText(studentText);
        }
    }
}
