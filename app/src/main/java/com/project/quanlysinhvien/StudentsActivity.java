package com.project.quanlysinhvien;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class StudentsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewStudents;
    private StudentAdapter studentAdapter;
    private List<Student> studentList;
    private Toolbar toolbar;
    private myDatabase db;
    private Button btn;
    private SearchView searchView;
    private Spinner spinnerDepartment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        // Cập nhật padding để tránh bị che bởi thanh trạng thái hoặc thanh điều hướng
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.students), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn = this.findViewById(R.id.btnSearch);
        searchView = this.findViewById(R.id.searchView);

        spinnerDepartment = findViewById(R.id.spinnerDepartment);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"Khoa CNTT", "Khoa Kinh Tế", "Khoa Cơ Khí"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartment.setAdapter(adapter);

        db = new myDatabase(this);

        // Sử dụng Toolbar từ androidx.appcompat.widget
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);  // Đảm bảo thiết lập Toolbar

        setTitle("Quản lý sinh viên");

        // Khởi tạo RecyclerView
        recyclerViewStudents = findViewById(R.id.recyclerViewStudents);
        recyclerViewStudents.setLayoutManager(new LinearLayoutManager(this));

        // Khởi tạo danh sách sinh viên mẫu
        studentList = new ArrayList<>();
        studentList = db.getAllStudents();

        // Khởi tạo adapter cho RecyclerView
        studentAdapter = new StudentAdapter(studentList);
        recyclerViewStudents.setAdapter(studentAdapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchView.getQuery().toString().trim();
                if (!query.isEmpty()) {
                    studentList = db.searchStudent(query); // Tìm kiếm sinh viên
                    studentAdapter = new StudentAdapter(studentList); // Cập nhật danh sách hiển thị
                } else {
                    Toast.makeText(StudentsActivity.this, "Vui lòng nhập từ khóa tìm kiếm", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu vào Activity
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
            return true;
        } else {
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
