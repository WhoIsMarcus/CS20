/*
Program: UEmp.java          Last Date of this Revision: March 31, 2026

Purpose: Demonstrate inheritance with University Employees

Author: Marcus McCrea
School: CHHS
Course: Computer Programming 20
*/

package Mastery;

public class UEmp {
    // Base class for University Employee
    static class Employee {
        private String name;
        private double salary;

        // Constructor
        public Employee(String name, double salary) {
            this.name = name;
            this.salary = salary;
        }

        // Method to return employee name
        public String getName() {
            return name;
        }

        // Method to return employee salary
        public double getSalary() {
            return salary;
        }
    }

    // Faculty class inherits from Employee
    static class Faculty extends Employee {
        private String department;

        // Constructor
        public Faculty(String name, double salary, String department) {
            super(name, salary); // Call the base class constructor
            this.department = department;
        }

        // Method to return department
        public String getDepartment() {
            return department;
        }
    }

    // Staff class inherits from Employee
    static class Staff extends Employee {
        private String jobTitle;

        // Constructor
        public Staff(String name, double salary, String jobTitle) {
            super(name, salary); // Call the base class constructor
            this.jobTitle = jobTitle;
        }

        // Method to return job title
        public String getJobTitle() {
            return jobTitle;
        }
    }

    public static void main(String[] args) {
        Faculty facultyMember = new Faculty("Dr. Smith", 90000, "Computer Science");
        Staff staffMember = new Staff("Jane Doe", 50000, "Administrative Assistant");

        System.out.println("Faculty: " + facultyMember.getName() + ", Salary: $" + facultyMember.getSalary() + ", Department: " + facultyMember.getDepartment());
        System.out.println("Staff: " + staffMember.getName() + ", Salary: $" + staffMember.getSalary() + ", Job Title: " + staffMember.getJobTitle());
    }
}