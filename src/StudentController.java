import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;


public class StudentController implements ActionListener {
    // Controller: Xu ly logic va su kien tu View
    private StudentView view;
    private List<Student> studentList;  // Danh sach sinh vien 
    private Student student;
    
    public StudentController(StudentView view) {
        this.view = view;
        this.studentList = new ArrayList<>();  // Khoi tao danh sach trong

        // Gan listener cho cac nut
        this.view.getBtnAdd().addActionListener(this);
        this.view.getBtnEdit().addActionListener(this);
        this.view.getBtnDelete().addActionListener(this);
        this.view.getBtnSearch().addActionListener(this);
        this.view.getMenuItemSave().addActionListener(this);
        this.view.getMenuItemExit().addActionListener(this);
        this.view.getMenuItemAbout().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Add":
                addStudent();
                break;
            case "Edit":
                editStudent();
                break;
            case "Delete":
                deleteStudent();
                break;
            case "Search":
                searchStudent();
                break;
            case "Save":
                saveToFile();
                break;
            case "Exit":
                exitProgram();
                break;
            case "About":
                showAbout();
                break;
        }
    }

    // Them sinh vien moi
    private void addStudent() {
        // Thong tin trong view
        String name = view.getNameInput().trim();
        String id   = view.getIdInput().trim();
        String ageText = view.getAgeInput().trim();
        String className = view.getClassInput().trim();
        String gender = view.getGenderInput();

        // Check lai du lieu 
        if (name.isEmpty() || id.isEmpty() || ageText.isEmpty() || className.isEmpty()) {
            // Bao loi neu nhap vao trong
            JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin!", 
                                                "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int age = 0;
        try {
            age = Integer.parseInt(ageText);
            if (age <= 0) {
                // Tuoi > 0
                JOptionPane.showMessageDialog(view, "Tuổi phải là số nguyên dương!", 
                                                    "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            // Tuoi la so nguyen
            JOptionPane.showMessageDialog(view, "Tuổi phải là số nguyên hợp lệ!", 
                                                "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Check MSSV trung nhau
        String newId = id;
        for (Student s : studentList) {
             if (s != student && s.getId().equalsIgnoreCase(newId)) {
                JOptionPane.showMessageDialog(view, "Mã số sinh viên đã tồn tại!", 
                                                    "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Tao Student moi roi them vao danh sach
        Student newSt = new Student(name, id, age, className, gender);
        studentList.add(newSt);
        // Cap nhat lai bang
        view.showStudentList(studentList);
        // Xoa trang cho nhap vao de chuan bi nhap tiep 
        view.clearForm();
    }

    // Sua thong tin sinh vien 
    private void editStudent() {
        // Chi so hang duoc chon trong bang
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow == -1) {
            // Phai chon sinh vien 
            JOptionPane.showMessageDialog(view, "Vui lòng chọn sinh viên cần sửa!", 
                                                "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // MSSV cua hang duoc chon 
        String selectedId = view.getTableModel().getValueAt(selectedRow, 1).toString();
        // Tim Student tuong ung theo MSSV
        Student studentToEdit = null;
        for (Student s : studentList) {
            if (s.getId().equalsIgnoreCase(selectedId)) {
                studentToEdit = s;
                break;
            }
        }
        if (studentToEdit == null) {
            // Khong tim thay sinh vien 
            JOptionPane.showMessageDialog(view, "Không tìm thấy sinh viên cần sửa!", 
                                                "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Hop thoai JDialog sua thong tin sinh vien
        EditStudentDialog editDialog = new EditStudentDialog(view, studentToEdit, studentList);
        editDialog.setVisible(true);
        // Cap nhap lai bang 
        view.showStudentList(studentList);
    }

    // Xoa sinh vien 
    private void deleteStudent() {
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn sinh viên cần xóa!", 
                                                "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // MSSv 
        String selectedId = view.getTableModel().getValueAt(selectedRow, 1).toString();
        // Xac nhan lai 
        int confirm = JOptionPane.showConfirmDialog(view, 
                        "Bạn có chắc chắn muốn xóa sinh viên có mã số " + selectedId + "?", 
                        "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return; 
        }
        // Xoa sinh vien 
        Student studentToRemove = null;
        for (Student s : studentList) {
            if (s.getId().equalsIgnoreCase(selectedId)) {
                studentToRemove = s;
                break;
            }
        }
        if (studentToRemove != null) {
            studentList.remove(studentToRemove);
            //Cap nhat lai bang 
            view.showStudentList(studentList);
        }
    }

    // Tim kiem sinh vien theo ten hoac mssv
    private void searchStudent() {
        String keyword = view.getSearchKeyword().trim();
        String criteria = view.getSearchCriteria(); 
        if (keyword.isEmpty()) {
            // Hien toan bo danh sach neu tu khoa trong 
            view.showStudentList(studentList);
            return;
        }
        // Danh sach tim duoc 
        List<Student> resultList = new ArrayList<>();
        for (Student s : studentList) {
            if (criteria.equals("Họ Tên")) {
                // So sanh chuoi ten 
                if (s.getName().toLowerCase().contains(keyword.toLowerCase())) {
                    resultList.add(s);
                }
            } else if (criteria.equals("Mã Số")) {
                if (s.getId().toLowerCase().contains(keyword.toLowerCase())) {
                    resultList.add(s);
                }
            }
        }
        // Cap nhat lai bang 
        view.showStudentList(resultList);
        if (resultList.isEmpty()) {
            // Thong bao khong tim thay sinh vien 
            JOptionPane.showMessageDialog(view, "Không tìm thấy sinh viên phù hợp.", 
                                                "Kết quả tìm kiếm", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Luu danh sach vao file "students.txt"
    private void saveToFile() {
    if (studentList.isEmpty()) {
        JOptionPane.showMessageDialog(view, "Danh sách sinh viên trống, không có gì để lưu.", 
                                            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        return;
    }
    
        // Tạo tên file mới với thời gian hiện tại
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new java.util.Date());
        String fileName = "students_" + timestamp + ".txt";
    
        try {
            java.io.BufferedWriter bw = new java.io.BufferedWriter(
                                            new java.io.FileWriter(fileName));
            for (Student s : studentList) {
                bw.write(s.getName() + "\t" + s.getId() + "\t" + s.getAge() 
                        + "\t" + s.getClassName() + "\t" + s.getGender());
                bw.newLine();
            }
            bw.close();
            JOptionPane.showMessageDialog(view, "Đã lưu danh sách sinh viên vào file " + fileName, 
                                                "Lưu thành công", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Lỗi khi lưu file: " + ex.getMessage(), 
                                                "Lỗi I/O", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Thoat chuong trinh 
    private void exitProgram() {
        int confirm = JOptionPane.showConfirmDialog(view, 
                        "Bạn có chắc muốn thoát ứng dụng?", 
                        "Xác nhận thoát", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    // Hop thoai about
    private void showAbout() {
        String info = "Ứng dụng Quản Lý Sinh Viên\n"
                    + "Phiên bản 1.0\n"
                    + "Tác giả: Lưu Quang Hiếu";  
        JOptionPane.showMessageDialog(view, info, 
                                      "About", JOptionPane.INFORMATION_MESSAGE);
    }
}

