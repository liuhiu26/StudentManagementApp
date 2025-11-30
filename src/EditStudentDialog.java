import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class EditStudentDialog extends JDialog {
    private JTextField tfId, tfName, tfAge, tfClass;
    private JRadioButton rbMale, rbFemale;
    private JButton btnSave, btnCancel;
    private boolean updated = false;
    private Student student;
    private List<Student> studentList;
    
    public EditStudentDialog(Frame owner, Student student, List<Student> studentList) {
        super(owner, "Chỉnh sửa sinh viên", true);
        this.studentList = studentList;

        tfId = new JTextField(student.getId(), 20);
        tfName = new JTextField(student.getName(), 20);
        tfAge = new JTextField(String.valueOf(student.getAge()), 20);
        tfClass = new JTextField(student.getClassName(), 20);

        // RadioButton giới tính
        rbMale = new JRadioButton("Nam");
        rbFemale = new JRadioButton("Nữ");
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbMale);
        bg.add(rbFemale);

        if ("Nam".equalsIgnoreCase(student.getGender())) {
            rbMale.setSelected(true);
        } else {
            rbFemale.setSelected(true);
        }

        JPanel genderPanel = new JPanel();
        genderPanel.add(rbMale);
        genderPanel.add(rbFemale);

        JPanel formPanel = new JPanel(new GridLayout(5, 2));
        formPanel.add(new JLabel("Mã số:"));
        formPanel.add(tfId);
        formPanel.add(new JLabel("Họ tên:"));
        formPanel.add(tfName);
        formPanel.add(new JLabel("Tuổi:"));
        formPanel.add(tfAge);
        formPanel.add(new JLabel("Lớp:"));
        formPanel.add(tfClass);
        formPanel.add(new JLabel("Giới tính:"));
        formPanel.add(genderPanel);

        btnSave = new JButton("Lưu");
        btnCancel = new JButton("Hủy");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        btnSave.addActionListener(e -> {
            student.setId(tfId.getText().trim());
            student.setName(tfName.getText().trim());
            student.setAge(Integer.parseInt(tfAge.getText().trim()));
            student.setClassName(tfClass.getText().trim());
            student.setGender(rbMale.isSelected() ? "Nam" : "Nữ");
            updated = true;
            dispose();
        });

        btnCancel.addActionListener(e -> {
            updated = false;
            dispose();
        });

        this.setLayout(new BorderLayout());
        this.add(formPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.pack();
        this.setLocationRelativeTo(owner);
    }

    public boolean isUpdated() {
        return updated;
    }
}
