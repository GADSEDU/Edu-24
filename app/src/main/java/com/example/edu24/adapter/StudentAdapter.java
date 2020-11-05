package com.example.edu24.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edu24.R;
import com.example.edu24.model.Classes;
import com.example.edu24.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder>{

    private List<User> studentDetails;
    private Context context;

    public StudentAdapter(List<User> studentDetails, Context context) {
        this.studentDetails = studentDetails;
        this.context = context;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_list, parent,
                false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        User user = studentDetails.get(position);
        holder.studentName.setText(user.getUser_first_name() + " " + user.getUser_surname());
        if (!user.getProfile_image().equals(null)){
            Picasso.get().load(Uri.parse(user.getProfile_image()))
                    .into(holder.studentImage);
        }
    }

    @Override
    public int getItemCount() {
        return studentDetails.size();
    }

    class StudentViewHolder extends RecyclerView.ViewHolder{
        TextView studentName;
        ImageView studentImage;
        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.student_name);
            studentImage = itemView.findViewById(R.id.profile_image_student);
        }
    }
}
