package com.example.sms;

import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class StudentDAO {
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public StudentDAO() {
        // default connection - adjust if your mongod uses auth or different host/port
        mongoClient = new MongoClient("localhost", 27017);
        database = mongoClient.getDatabase("studentdb");
        collection = database.getCollection("students");
    }

    public void insertStudent(Student s) {
        Document doc = new Document("name", s.getName())
                .append("rollno", s.getRollNo())
                .append("department", s.getDepartment())
                .append("marks", s.getMarks()); // store as number
        collection.insertOne(doc);
        // set id back to student
        ObjectId id = doc.getObjectId("_id"); 
        if (id != null) s.setId(id.toHexString());
    }

    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        for (Document doc : collection.find()) {
            String id = doc.getObjectId("_id") != null ? doc.getObjectId("_id").toHexString() : null;
            String name = doc.get("name") != null ? doc.get("name").toString() : "";
            String rollno = doc.get("rollno") != null ? doc.get("rollno").toString() : "";
            String dept = doc.get("department") != null ? doc.get("department").toString() : "";
            int marks = 0;
            Object marksObj = doc.get("marks");
            if (marksObj instanceof Number) {
                marks = ((Number) marksObj).intValue();
            } else if (marksObj != null) {
                try {
                    marks = Integer.parseInt(marksObj.toString());
                } catch (NumberFormatException ex) {
                    marks = 0;
                }
            }
            Student s = new Student(id, name, rollno, dept, marks);
            list.add(s);
        }
        return list;
    }

    public Student findById(String idHex) {
        Document d = collection.find(new Document("_id", new ObjectId(idHex))).first();
        if (d == null) return null;
        String id = d.getObjectId("_id") != null ? d.getObjectId("_id").toHexString() : null;
        String name = d.get("name") != null ? d.get("name").toString() : "";
        String rollno = d.get("rollno") != null ? d.get("rollno").toString() : "";
        String dept = d.get("department") != null ? d.get("department").toString() : "";
        int marks = 0;
        Object marksObj = d.get("marks");
        if (marksObj instanceof Number) marks = ((Number) marksObj).intValue();
        else if (marksObj != null) {
            try { marks = Integer.parseInt(marksObj.toString()); } catch (Exception e) { marks = 0; }
        }
        return new Student(id, name, rollno, dept, marks);
    }

    public void updateStudent(String idHex, Student s) {
        Document filter = new Document("_id", new ObjectId(idHex));
        Document update = new Document("$set", new Document("name", s.getName())
                .append("rollno", s.getRollNo())
                .append("department", s.getDepartment())
                .append("marks", s.getMarks()));
        collection.updateOne(filter, update);
    }

    public void deleteStudent(String idHex) {
        Document filter = new Document("_id", new ObjectId(idHex));
        collection.deleteOne(filter);
    }

    public void close() {
        if (mongoClient != null) mongoClient.close();
    }
}
