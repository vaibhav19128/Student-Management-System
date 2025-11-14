package com.example.sms;

public class Student {
    private String id; // ObjectId hex string
    private String name;
    private String rollNo;
    private String department;
    private int marks; // keep as int

    public Student() {}

    public Student(String id, String name, String rollNo, String department, int marks) {
        this.id = id;
        this.name = name;
        this.rollNo = rollNo;
        this.department = department;
        this.marks = marks;
    }

    public Student(String name, String rollNo, String department, int marks) {
        this(null, name, rollNo, department, marks);
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRollNo() { return rollNo; }
    public void setRollNo(String rollNo) { this.rollNo = rollNo; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public int getMarks() { return marks; }
    public void setMarks(int marks) { this.marks = marks; }
}
