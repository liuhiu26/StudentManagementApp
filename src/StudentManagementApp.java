public class StudentManagementApp {
    public static void main(String[] args) {
        // Tao va hien thi giao dien 
        StudentView view = new StudentView();
        // Tao controller truyen vao view
        StudentController controller = new StudentController(view);
    }
}
