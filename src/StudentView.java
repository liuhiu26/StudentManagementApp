import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StudentView extends JFrame {
    // View: Xay dung giao dien nguoi dung 

    // Swing component ( Thanh phan giao dien) 
    private JTextField txtName;       // Ho ten 
    private JTextField txtId;         // MS
    private JTextField txtAge;        // Tuoi
    private JComboBox<String> cbClass;// Lop
    private JRadioButton rbMale;      
    private JRadioButton rbFemale;    
    private ButtonGroup bgGender;     // Chon gioi tinh

    private JButton btnAdd;          // Them
    private JButton btnEdit;         // Sua
    private JButton btnDelete;       // Xoa
    private JButton btnSearch;       // Tim kiem

    private JTextField txtSearch;    // O nhap tim kiem
    private JComboBox<String> cbSearchBy; // Tieu chi tim kiem

    private JTable table;            // Bang danh sach 
    private DefaultTableModel tableModel; // Model cua JTable them/xoa du lieu bang

    // Menu button
    private JMenuItem menuItemSave;   // Save
    private JMenuItem menuItemExit;   // Exit
    private JMenuItem menuItemAbout;  // About

    // Constructor thiet lap giao dien
    public StudentView() {
        initUI();  // Khoi tao thanh phan giao dien
    }

    private void initUI() {
        // Thuoc tinh JFrame
        this.setTitle("Quản Lý Sinh Viên");               
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        // Form nhap thong tin 
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Thông tin Sinh viên"));

        // Label cho Hoten, MSSV, tuoi, lop, gioitinh
        JLabel lbName   = new JLabel("Họ Tên:");
        JLabel lbId     = new JLabel("Mã Số:");
        JLabel lbAge    = new JLabel("Tuổi:");
        JLabel lbClass  = new JLabel("Lớp:");
        JLabel lbGender = new JLabel("Giới tính:");

        txtName = new JTextField();
        txtId   = new JTextField();
        txtAge  = new JTextField();
        // ComboBox lop cho phep chon tu danh sach
        cbClass = new JComboBox<>(new String[] {"D22CQAT01-N", "D23CQCN02-N", "D21CQCN01-N", "D23CQCN01-N"}); 
        cbClass.setEditable(true); // cho phep nguoi dung tu nhap

        // RadioButton gioitinh
        rbMale   = new JRadioButton("Nam");
        rbFemale = new JRadioButton("Nữ");
        bgGender = new ButtonGroup();
        bgGender.add(rbMale);
        bgGender.add(rbFemale);
        rbMale.setSelected(true); // mac dinh Nam

        // formPanel them cac thanh phan
        formPanel.add(lbName);   formPanel.add(txtName);
        formPanel.add(lbId);     formPanel.add(txtId);
        formPanel.add(lbAge);    formPanel.add(txtAge);
        formPanel.add(lbClass);  formPanel.add(cbClass);
        formPanel.add(lbGender);
        // radio button gioitinh nam ngang
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genderPanel.add(rbMale);
        genderPanel.add(rbFemale);
        formPanel.add(genderPanel);

        // Panel chuc nang 
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btnAdd    = new JButton("Thêm");
        btnEdit   = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        // Action command trong controller
        btnAdd.setActionCommand("Add");
        btnEdit.setActionCommand("Edit");
        btnDelete.setActionCommand("Delete");
        // ButtonPanel
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);

        // Panel phía trên: form nhap và panel nut
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Panel tim kiem (o duoi cung) 
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));
        JLabel lbSearch = new JLabel("Từ khóa:");
        txtSearch = new JTextField(15);
        cbSearchBy = new JComboBox<>(new String[] {"Họ Tên", "Mã Số"}); // 
        btnSearch = new JButton("Tìm kiếm");
        btnSearch.setActionCommand("Search");
        // Add searchPanel
        searchPanel.add(lbSearch);
        searchPanel.add(txtSearch);
        searchPanel.add(cbSearchBy);
        searchPanel.add(btnSearch);

        // JTable hien thi danh sach sinh vien
        String[] columnNames = {"Họ Tên", "Mã Số", "Tuổi", "Lớp", "Giới Tính"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            // Khong duoc sua truc tiep tren bang
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // chỉ cho phép chọn một hàng tại một thời điểm
        JScrollPane scrollPane = new JScrollPane(table);

        // Menu Bar: File, Help
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        JMenu menuHelp = new JMenu("Help");
        menuItemSave = new JMenuItem("Save");
        menuItemExit = new JMenuItem("Exit");
        menuItemAbout = new JMenuItem("About");
        // Action command cho menu items
        menuItemSave.setActionCommand("Save");
        menuItemExit.setActionCommand("Exit");
        menuItemAbout.setActionCommand("About");
        // Add vao menu
        menuFile.add(menuItemSave);
        menuFile.addSeparator();     // phan cach
        menuFile.add(menuItemExit);
        menuHelp.add(menuItemAbout);
        // Add menu vao menu bar
        menuBar.add(menuFile);
        menuBar.add(menuHelp);
        // Cho menu bar vao frame
        this.setJMenuBar(menuBar);

        // Add panel vao frame
        this.add(topPanel, BorderLayout.NORTH);    // Form nhập + nút ở phía trên
        this.add(scrollPane, BorderLayout.CENTER); // Bảng ở giữa
        this.add(searchPanel, BorderLayout.SOUTH); // Ô tìm kiếm ở dưới cùng

        // Kich thuoc cua so 
        this.setSize(600, 400);
        this.setLocationRelativeTo(null); // cua so o giua man hinh
        this.setVisible(true);
    }

    // Getter de Controller truy cap component tuong ung
    public String getNameInput() {
        return txtName.getText();
    }
    public String getIdInput() {
        return txtId.getText();
    }
    public String getAgeInput() {
        return txtAge.getText();
    }
    public String getClassInput() {
        return cbClass.getSelectedItem().toString();
    }
    public String getGenderInput() {
        // Gioi tinh da chon 
        if (rbMale.isSelected()) {
            return "Nam";
        } else {
            return "Nữ";
        }
    }
    public String getSearchKeyword() {
        return txtSearch.getText();
    }
    public String getSearchCriteria() {
        return cbSearchBy.getSelectedItem().toString(); 
    }
    public JButton getBtnAdd() {
        return btnAdd;
    }
    public JButton getBtnEdit() {
        return btnEdit;
    }
    public JButton getBtnDelete() {
        return btnDelete;
    }
    public JButton getBtnSearch() {
        return btnSearch;
    }
    public JMenuItem getMenuItemSave() {
        return menuItemSave;
    }
    public JMenuItem getMenuItemExit() {
        return menuItemExit;
    }
    public JMenuItem getMenuItemAbout() {
        return menuItemAbout;
    }
    public JTable getTable() {
        return table;
    }
    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    // Tien ich de xoa trang sau khi them thanh cong
    public void clearForm() {
        txtName.setText("");
        txtId.setText("");
        txtAge.setText("");
        cbClass.setSelectedItem("");   // comboBox ve trang thai trong
        rbMale.setSelected(true);      // gioi tinh Nam
    }

    // Hien thi danh sach sinh vien len bang (Jtable update) 
    public void showStudentList(java.util.List<Student> studentList) {
        // Xoa du lieu hien co
        tableModel.setRowCount(0);
        // Duyet qua danh sach sinh vien va them tung dong
        for (Student s : studentList) {
            Object[] rowData = {
                s.getName(), s.getId(), s.getAge(), s.getClassName(), s.getGender()
            };
            tableModel.addRow(rowData);
        }
    }
}
