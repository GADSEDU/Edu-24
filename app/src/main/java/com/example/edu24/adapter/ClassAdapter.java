package com.example.edu24.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edu24.ClassActivity;
import com.example.edu24.model.Classes;
import com.example.edu24.model.ModelClass;
import com.example.edu24.R;

import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClasssViewHolder> {

    private List<Classes> mModelClassList;
    private Context context;

    public ClassAdapter(List<Classes> mModelClassList, Context context) {
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
    public void onBindViewHolder(@NonNull ClasssViewHolder holder, int position) {
        Classes classes = mModelClassList.get(position);
        holder.className.setText(classes.getClass_name());
        holder.section.setText(classes.getClass_section());
        if (classes.getClass_student() == null){
            holder.student.setText("0 Student");
        }else{
            holder.student.setText(classes.getClass_student().size() + " Student");
        }
        holder.classCode = classes.getClass_code();
        holder.classId = classes.getClass_id();
    }

    @Override
    public int getItemCount() {
        return mModelClassList.size();
    }

    class ClasssViewHolder extends RecyclerView.ViewHolder{

        private TextView className;
        private TextView section;
        private TextView student;
        private ImageView share;
        private String classCode;
        private String classId;

        public ClasssViewHolder(@NonNull View itemView) {
            super(itemView);

            className = itemView.findViewById(R.id.tv_className);
            section = itemView.findViewById(R.id.tv_section);
            student = itemView.findViewById(R.id.tv_no_student);
            share = itemView.findViewById(R.id.tv_share);
            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(context, ClassActivity.class);
                intent.putExtra("Id",  classId);
                view.getContext().startActivity(intent);
            });
            share.setOnClickListener(view -> {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "Edu24 app\nClass code: " + classCode);
                Toast.makeText(context, "class code" + classCode, Toast.LENGTH_SHORT).show();
                view.getContext().startActivity(intent);
            });
        }

    }
}
