package com.project.quanlysinhvien;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    private List<Student> studentList;

    // Constructor
    public StudentAdapter(List<Student> studentList) {
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        // Bind data to the view
        Student student = studentList.get(position);
        holder.tvStudentName.setText(student.getLastName() + " " + student.getMiddleName() + " " + student.getFirstName());
        holder.tvStudentId.setText("ID: " + student.getId());
        holder.tvStudentDepart.setText("Department ID: " + student.getDepartmentId());
        holder.tvStudentBthDay.setText("Birth Day: " + student.getBirthday());
        holder.tvStudentEmail.setText("Email: " + student.getEmail());
        holder.tvStudentPhone.setText("Phone Number: " + student.getPhoneNumber());
        holder.tvStudentAddress.setText("Address: " + student.getAddress());
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    // ViewHolder class
    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView tvStudentName, tvStudentId, tvStudentDepart, tvStudentBthDay, tvStudentEmail, tvStudentPhone, tvStudentAddress;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStudentName = itemView.findViewById(R.id.tvStudentName);
            tvStudentId = itemView.findViewById(R.id.tvStudentId);
            tvStudentDepart = itemView.findViewById(R.id.tvStudentDepart);
            tvStudentBthDay = itemView.findViewById(R.id.tvStudentBthDay);
            tvStudentEmail = itemView.findViewById(R.id.tvStudentEmail);
            tvStudentPhone = itemView.findViewById(R.id.tvStudentPhone);
            tvStudentAddress = itemView.findViewById(R.id.tvStudentAddress);
        }
    }
}
