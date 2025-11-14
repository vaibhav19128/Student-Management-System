package com.example.sms;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MainGui extends JFrame {
    private StudentDAO dao;
    private JTextField txtId = new JTextField(24);
    private JTextField txtName = new JTextField(24);
    private JTextField txtRoll = new JTextField(24);
    private JTextField txtDept = new JTextField(24);
    private JTextField txtMarks = new JTextField(24);
    private DefaultTableModel tableModel;
    private JTable table;

    public MainGui() {
        super("Student Management System - Swing + MongoDB (fixed)"); 
        dao = new StudentDAO();

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        txtId.setEditable(false);
        form.add(new JLabel("ID (auto):")); form.add(txtId);
        form.add(new JLabel("Name:")); form.add(txtName);
        form.add(new JLabel("Roll No:")); form.add(txtRoll);
        form.add(new JLabel("Department:")); form.add(txtDept);
        form.add(new JLabel("Marks:")); form.add(txtMarks);

        JPanel buttons = new JPanel();
        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnRefresh = new JButton("Refresh");
        buttons.add(btnAdd); buttons.add(btnUpdate); buttons.add(btnDelete); buttons.add(btnRefresh);

        String[] cols = {"ID", "Name", "Roll No", "Department", "Marks"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(700, 250));
        JScrollPane scrollPane = new JScrollPane(table);

        add(form, BorderLayout.NORTH);
        add(buttons, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        btnAdd.addActionListener(e -> addStudent());
        btnUpdate.addActionListener(e -> updateStudent());
        btnDelete.addActionListener(e -> deleteStudent());
        btnRefresh.addActionListener(e -> loadStudents());

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int r = table.getSelectedRow();
                if (r >= 0) {
                    txtId.setText((String) tableModel.getValueAt(r, 0));
                    txtName.setText((String) tableModel.getValueAt(r, 1));
                    txtRoll.setText((String) tableModel.getValueAt(r, 2));
                    txtDept.setText((String) tableModel.getValueAt(r, 3));
                    txtMarks.setText(String.valueOf(tableModel.getValueAt(r, 4)));
                }
            }
        });

        loadStudents();
    }

    private void addStudent() {
        try {
            String name = txtName.getText().trim();
            String roll = txtRoll.getText().trim();
            String dept = txtDept.getText().trim();
            int marks = Integer.parseInt(txtMarks.getText().trim());
            if (name.isEmpty() || roll.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name and Roll No are required.");
                return;
            }
            Student s = new Student(name, roll, dept, marks);
            dao.insertStudent(s);
            JOptionPane.showMessageDialog(this, "Inserted with id: " + s.getId());
            clearForm();
            loadStudents();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Marks must be an integer.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void updateStudent() {
        String id = txtId.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Select a student to update.");
            return;
        }
        try {
            String name = txtName.getText().trim();
            String roll = txtRoll.getText().trim();
            String dept = txtDept.getText().trim();
            int marks = Integer.parseInt(txtMarks.getText().trim());
            Student s = new Student(name, roll, dept, marks);
            dao.updateStudent(id, s);
            JOptionPane.showMessageDialog(this, "Updated.");
            clearForm();
            loadStudents();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Marks must be an integer.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void deleteStudent() {
        String id = txtId.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Select a student to delete.");
            return;
        }
        int opt = JOptionPane.showConfirmDialog(this, "Delete student?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (opt == JOptionPane.YES_OPTION) {
            dao.deleteStudent(id);
            JOptionPane.showMessageDialog(this, "Deleted.");
            clearForm();
            loadStudents();
        }
    }

    private void loadStudents() {
        tableModel.setRowCount(0);
        List<Student> students = dao.getAllStudents();
        for (Student s : students) {
            tableModel.addRow(new Object[]{
                    s.getId(),
                    s.getName(),
                    s.getRollNo(),
                    s.getDepartment(),
                    s.getMarks()
            });
        }
    }

    private void clearForm() {
        txtId.setText(""); txtName.setText(""); txtRoll.setText(""); txtDept.setText(""); txtMarks.setText(""); 
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainGui gui = new MainGui();
            gui.setVisible(true);
        });
    }
}
