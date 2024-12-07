package com.project.quanlysinhvien;

import java.io.Serializable;

public class Student implements Serializable {
    private int id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String address;
    private String phoneNumber;
    private String email;
    private String birthday;
    private int departmentId;
    private String gender;

    public Student(int id, String lastName, String firstName, String middleName, int departmentId, String birthday, String phoneNumber, String email, String address) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.departmentId = departmentId;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }

    public Student() {
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullName(){
        String fn = firstName + " " + middleName + " " + lastName;
        fn = fn.replace("  ", " ");
        return fn;
    }
    @Override
    public String toString() {
        return "Student{" +
                "HoTen='" + firstName + '\'' +
                ", maSV='" + id + '\'' +
                ", SDT='" + phoneNumber + '\'' +
                ", diaChi='" + address + '\'' +
                ", ngaySinh='" + birthday + '\'' +
                ", khoa='" + departmentId + '\'' +
                ", gioiTinh='" + gender + '\'' +
                '}';
    }
}
