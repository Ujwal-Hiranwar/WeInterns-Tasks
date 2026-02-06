import java.util.*;

/**
 * Student Grade Management System
 * A console-based application for teachers to manage student grades
 */
public class StudentGradeManagementSystem {
    
    // HashMap to store student data (Name -> Grade)
    private HashMap<String, Double> studentGrades;
    private Scanner scanner;
    
    public StudentGradeManagementSystem() {
        studentGrades = new HashMap<>();
        scanner = new Scanner(System.in);
    }
    
    /**
     * Display the main menu
     */
    public void displayMenu() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║  STUDENT GRADE MANAGEMENT SYSTEM          ║");
        System.out.println("╚════════════════════════════════════════════╝");
        System.out.println("1. Add Student Grade");
        System.out.println("2. Update Student Grade");
        System.out.println("3. Remove Student");
        System.out.println("4. View All Students");
        System.out.println("5. Search Student");
        System.out.println("6. Generate Grade Report");
        System.out.println("0. Exit");
        System.out.println("════════════════════════════════════════════");
        System.out.print("Enter your choice: ");
    }
    
    /**
     * Add a new student with their grade
     */
    public void addStudent() {
        System.out.print("\nEnter student name: ");
        String name = scanner.nextLine().trim();
        
        if (name.isEmpty()) {
            System.out.println("❌ Error: Student name cannot be empty!");
            return;
        }
        
        if (studentGrades.containsKey(name)) {
            System.out.println("❌ Error: Student '" + name + "' already exists!");
            System.out.print("Do you want to update the grade? (yes/no): ");
            String choice = scanner.nextLine().trim().toLowerCase();
            if (choice.equals("yes") || choice.equals("y")) {
                updateStudent(name);
            }
            return;
        }
        
        System.out.print("Enter grade (0-100): ");
        try {
            double grade = Double.parseDouble(scanner.nextLine().trim());
            
            if (grade < 0 || grade > 100) {
                System.out.println("❌ Error: Grade must be between 0 and 100!");
                return;
            }
            
            studentGrades.put(name, grade);
            System.out.println("✅ Student '" + name + "' added successfully with grade: " + grade);
            
        } catch (NumberFormatException e) {
            System.out.println("❌ Error: Invalid grade format! Please enter a number.");
        }
    }
    
    /**
     * Update an existing student's grade
     */
    public void updateStudent() {
        System.out.print("\nEnter student name to update: ");
        String name = scanner.nextLine().trim();
        updateStudent(name);
    }
    
    private void updateStudent(String name) {
        if (!studentGrades.containsKey(name)) {
            System.out.println("❌ Error: Student '" + name + "' not found!");
            return;
        }
        
        System.out.println("Current grade: " + studentGrades.get(name));
        System.out.print("Enter new grade (0-100): ");
        
        try {
            double grade = Double.parseDouble(scanner.nextLine().trim());
            
            if (grade < 0 || grade > 100) {
                System.out.println("❌ Error: Grade must be between 0 and 100!");
                return;
            }
            
            studentGrades.put(name, grade);
            System.out.println("✅ Grade updated successfully for '" + name + "': " + grade);
            
        } catch (NumberFormatException e) {
            System.out.println("❌ Error: Invalid grade format! Please enter a number.");
        }
    }
    
    /**
     * Remove a student from the system
     */
    public void removeStudent() {
        System.out.print("\nEnter student name to remove: ");
        String name = scanner.nextLine().trim();
        
        if (studentGrades.containsKey(name)) {
            double grade = studentGrades.remove(name);
            System.out.println("✅ Student '" + name + "' (Grade: " + grade + ") removed successfully!");
        } else {
            System.out.println("❌ Error: Student '" + name + "' not found!");
        }
    }
    
    /**
     * View all students with their grades
     */
    public void viewAllStudents() {
        System.out.println("\n════════════════════════════════════════════");
        System.out.println("           ALL STUDENTS");
        System.out.println("════════════════════════════════════════════");
        
        if (studentGrades.isEmpty()) {
            System.out.println("No students in the system.");
            return;
        }
        
        System.out.printf("%-30s %10s %10s%n", "Student Name", "Grade", "Letter");
        System.out.println("────────────────────────────────────────────");
        
        for (Map.Entry<String, Double> entry : studentGrades.entrySet()) {
            String letterGrade = getLetterGrade(entry.getValue());
            System.out.printf("%-30s %10.2f %10s%n", 
                entry.getKey(), entry.getValue(), letterGrade);
        }
    }
    
    /**
     * Search for a specific student
     */
    public void searchStudent() {
        System.out.print("\nEnter student name to search: ");
        String name = scanner.nextLine().trim();
        
        if (studentGrades.containsKey(name)) {
            double grade = studentGrades.get(name);
            String letterGrade = getLetterGrade(grade);
            
            System.out.println("\n════════════════════════════════════════════");
            System.out.println("Student Name: " + name);
            System.out.println("Numeric Grade: " + grade);
            System.out.println("Letter Grade: " + letterGrade);
            System.out.println("Status: " + (grade >= 60 ? "PASS ✅" : "FAIL ❌"));
            System.out.println("════════════════════════════════════════════");
        } else {
            System.out.println("❌ Student '" + name + "' not found!");
        }
    }
    
    /**
     * Generate a comprehensive grade report
     */
    public void generateReport() {
        if (studentGrades.isEmpty()) {
            System.out.println("\n❌ No students in the system. Cannot generate report.");
            return;
        }
        
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║         STUDENT GRADE REPORT              ║");
        System.out.println("╚════════════════════════════════════════════╝");
        
        // Calculate statistics
        double total = 0;
        double highest = Double.MIN_VALUE;
        double lowest = Double.MAX_VALUE;
        String topStudent = "";
        String lowestStudent = "";
        
        int aCount = 0, bCount = 0, cCount = 0, dCount = 0, fCount = 0;
        int passCount = 0;
        
        for (Map.Entry<String, Double> entry : studentGrades.entrySet()) {
            double grade = entry.getValue();
            total += grade;
            
            if (grade > highest) {
                highest = grade;
                topStudent = entry.getKey();
            }
            
            if (grade < lowest) {
                lowest = grade;
                lowestStudent = entry.getKey();
            }
            
            // Count letter grades
            String letter = getLetterGrade(grade);
            switch (letter) {
                case "A": aCount++; break;
                case "B": bCount++; break;
                case "C": cCount++; break;
                case "D": dCount++; break;
                case "F": fCount++; break;
            }
            
            if (grade >= 60) passCount++;
        }
        
        double average = total / studentGrades.size();
        
        // Display report
        System.out.println("\n📊 OVERALL STATISTICS");
        System.out.println("────────────────────────────────────────────");
        System.out.println("Total Students: " + studentGrades.size());
        System.out.println("Average Grade: " + String.format("%.2f", average));
        System.out.println("Highest Grade: " + String.format("%.2f", highest) + 
            " (" + topStudent + ")");
        System.out.println("Lowest Grade: " + String.format("%.2f", lowest) + 
            " (" + lowestStudent + ")");
        System.out.println("Pass Rate: " + String.format("%.1f%%", 
            (passCount * 100.0 / studentGrades.size())));
        
        System.out.println("\n📈 GRADE DISTRIBUTION");
        System.out.println("────────────────────────────────────────────");
        System.out.println("A (90-100): " + aCount + " students");
        System.out.println("B (80-89):  " + bCount + " students");
        System.out.println("C (70-79):  " + cCount + " students");
        System.out.println("D (60-69):  " + dCount + " students");
        System.out.println("F (0-59):   " + fCount + " students");
        
        // Top 5 students
        ArrayList<Map.Entry<String, Double>> sortedList = 
            new ArrayList<>(studentGrades.entrySet());
        sortedList.sort((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()));
        
        System.out.println("\n🏆 TOP PERFORMERS");
        System.out.println("────────────────────────────────────────────");
        int count = Math.min(5, sortedList.size());
        for (int i = 0; i < count; i++) {
            Map.Entry<String, Double> entry = sortedList.get(i);
            System.out.printf("%d. %-25s %.2f (%s)%n", 
                (i + 1), entry.getKey(), entry.getValue(), 
                getLetterGrade(entry.getValue()));
        }
        
        System.out.println("════════════════════════════════════════════");
    }
    
    /**
     * Convert numeric grade to letter grade
     */
    private String getLetterGrade(double grade) {
        if (grade >= 90) return "A";
        else if (grade >= 80) return "B";
        else if (grade >= 70) return "C";
        else if (grade >= 60) return "D";
        else return "F";
    }
    
    /**
     * Main application loop
     */
    public void run() {
        boolean running = true;
        
        while (running) {
            displayMenu();
            
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                
                switch (choice) {
                    case 1:
                        addStudent();
                        break;
                    case 2:
                        updateStudent();
                        break;
                    case 3:
                        removeStudent();
                        break;
                    case 4:
                        viewAllStudents();
                        break;
                    case 5:
                        searchStudent();
                        break;
                    case 6:
                        generateReport();
                        break;
                    case 0:
                        System.out.println("\n👋 Thank you for using Student Grade Management System!");
                        System.out.println("Goodbye!");
                        running = false;
                        break;
                    default:
                        System.out.println("❌ Invalid choice! Please enter a number between 0-6.");
                }
                
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input! Please enter a number.");
            }
        }
        
        scanner.close();
    }
    
    /**
     * Main method to start the application
     */
    public static void main(String[] args) {
        StudentGradeManagementSystem system = new StudentGradeManagementSystem();
        system.run();
    }
}
