public class Student {
    // Thuoc tinh 
    private String name;       // Ho ten 
    private String id;         // MSSV
    private int age;           // Tuoi
    private String className;  // Lop
    private String gender;     // Gioi tinh 

    // Constructor k tham so
    public Student() {
        // Khoi tao sinh vien trong
    }

    // Constructor co tham so 
    public Student(String name, String id, int age, String className, String gender) {
        this.name = name;
        this.id = id;
        this.age = age;
        this.className = className;
        this.gender = gender;
    }

    // Getter, setter 
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    // Tostring
    @Override
    public String toString() {
        return "Student [MSSV=" + id 
             + ", Họ Tên=" + name 
             + ", Tuổi=" + age 
             + ", Lớp=" + className 
             + ", Giới tính=" + gender + "]";
    }
}
