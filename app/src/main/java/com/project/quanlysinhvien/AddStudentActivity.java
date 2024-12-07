package com.project.quanlysinhvien;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddStudentActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etMiddleName, etAddress, etPhoneNumber, etEmail, etBirthday, etDepartmentId, etID;
    private Button btnSaveStudent;
    private myDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_student);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.add_student), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Khởi tạo các view
        etID = findViewById(R.id.etID);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etMiddleName = findViewById(R.id.etMiddleName);
        etAddress = findViewById(R.id.etAddress);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etEmail = findViewById(R.id.etEmail);
        etBirthday = findViewById(R.id.etBirthday);
        etDepartmentId = findViewById(R.id.etDepartmentId);
        btnSaveStudent = findViewById(R.id.btnSaveStudent);

        // Khởi tạo database
        db = new myDatabase(this);

        // Sự kiện khi nhấn nút lưu
        btnSaveStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(etID.getText().toString().trim());
                String firstName = etFirstName.getText().toString().trim();
                String lastName = etLastName.getText().toString().trim();
                String middleName = etMiddleName.getText().toString().trim();
                String address = etAddress.getText().toString().trim();
                String phoneNumber = etPhoneNumber.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String birthday = etBirthday.getText().toString().trim();
                int departmentId;

                try {
                    departmentId = Integer.parseInt(etDepartmentId.getText().toString().trim());
                } catch (NumberFormatException e) {
                    departmentId = -1; // Giá trị mặc định nếu không hợp lệ
                }

                // Kiểm tra thông tin nhập vào
                if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
                    Toast.makeText(AddStudentActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Tạo đối tượng Student và lưu vào database
                Student student = new Student(id, lastName, firstName, middleName, departmentId, birthday, phoneNumber, email, address);
                long result = db.addStudent(student);

                if (result > 0) {
                    Toast.makeText(AddStudentActivity.this, "Thêm sinh viên thành công!", Toast.LENGTH_SHORT).show();
                    finish(); // Quay lại màn hình trước
                } else {
                    Toast.makeText(AddStudentActivity.this, "Thêm sinh viên thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
            // Lấy dữ liệu từ các trường nhập liệu

        });
    }
}
