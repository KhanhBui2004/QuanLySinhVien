package com.project.quanlysinhvien;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;  // Đảm bảo sử dụng Toolbar từ androidx.appcompat.widget
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewStudents;
    private StudentAdapter studentAdapter;
    private List<Student> studentList;
    private Toolbar toolbar;
    private myDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new myDatabase(this);

        // Sử dụng Toolbar từ androidx.appcompat.widget
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);  // Đảm bảo thiết lập Toolbar

        setTitle("Danh sách sinh viên");

        recyclerViewStudents = this.findViewById(R.id.recyclerViewStudents);
        recyclerViewStudents.setLayoutManager(new LinearLayoutManager(this));
//        long result = db.addStudent(new Student(2, "Hà", "Nam", "Văn", 2, "12/02/2003", "0987654321", "Nam@gmail.com", "Kiến Trúc"));
//        long result = db.addStudent(new Student(1, "Bùi", "Khánh", "Quốc", 1, "27/08/2004", "0123456789", "khanh@gmail.com", "Bách Khoa"));
        studentList = new ArrayList<>();
//        studentList.add(new Student(1, "Bùi", "Khánh", "Quốc", 123, "27/08/2004", "0123456789", "khanh@gmail.com", "Bách Khoa"));
        studentList = db.getAllStudents();

//        studentAdapter.updateList(studentList);

        studentAdapter = new StudentAdapter(studentList);
        recyclerViewStudents.setAdapter(studentAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_manage_department) {
            // Chuyển đến Activity quản lý Khoa
            startActivity(new Intent(this, DepartmentActivity.class));
            return true;
        } else if (item.getItemId() == R.id.menu_manage_students) {
            // Chuyển đến Activity quản lý Sinh viên
            startActivity(new Intent(this, StudentsActivity.class));
            return true;
        } else if (item.getItemId() == R.id.menu_add_student) {
            startActivity(new Intent(this, AddStudentActivity.class));
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        studentList.clear();
        studentList.addAll(db.getAllStudents());
        studentAdapter.notifyDataSetChanged();
    }

}
