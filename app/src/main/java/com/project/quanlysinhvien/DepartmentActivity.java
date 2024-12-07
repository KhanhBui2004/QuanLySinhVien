package com.project.quanlysinhvien;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DepartmentActivity extends AppCompatActivity {

    private RecyclerView recyclerViewDepartments;
    private DepartmentAdapter departmentAdapter;
    private List<Department> departmentList;
    private Toolbar toolbar;
    private myDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);

        // Thêm Padding cho hệ thống thanh trạng thái và các thanh điều hướng
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.department), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new myDatabase(this);
        // Sử dụng Toolbar từ androidx.appcompat.widget.Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);  // Đảm bảo thiết lập Toolbar

        setTitle("Quản lý khoa");

        // Khởi tạo RecyclerView
        recyclerViewDepartments = findViewById(R.id.recyclerViewDepartments);
        recyclerViewDepartments.setLayoutManager(new LinearLayoutManager(this));

        departmentList = new ArrayList<>();
        departmentList.add(new Department(1, "Khoa Công nghệ thông tin"));
        departmentList.add(new Department(2, "Khoa Điện tử - Viễn thông"));

        // Khởi tạo Adapter và set cho RecyclerView
        departmentAdapter = new DepartmentAdapter(departmentList);
        recyclerViewDepartments.setAdapter(departmentAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);  // Đảm bảo sử dụng đúng menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_manage_department) {
            // Chuyển đến Activity quản lý Khoa
            return true;
        } else if (item.getItemId() == R.id.menu_manage_students) {
            // Chuyển đến Activity quản lý Sinh viên
            startActivity(new Intent(this, StudentsActivity.class));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
