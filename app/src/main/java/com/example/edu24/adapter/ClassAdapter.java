package com.example.edu24.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edu24.model.ModelClass;
import com.example.edu24.R;

import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClasssViewHolder> {

    private List<ModelClass> mModelClassList;
    private Context context;

    public ClassAdapter(List<ModelClass> mModelClassList, Context context) {
        this.mModelClassList = mModelClassList;
        this.context = context;
    }

    @NonNull
    @Override
    public ClasssViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_students, viewGroup,
                false);
        return new ClasssViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClasssViewHolder classsViewHolder, int position) {

        String className = mModelClassList.get(position).getClassName();
        String section = mModelClassList.get(position).getSection();
        String student = mModelClassList.get(position).getStudent();
        classsViewHolder.setData(className, section, student);

    }

    @Override
    public int getItemCount() {
        return mModelClassList.size();
    }

    class ClasssViewHolder extends RecyclerView.ViewHolder{

        private TextView className;
        private TextView section;
        private TextView student;

        public ClasssViewHolder(@NonNull View itemView) {
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
