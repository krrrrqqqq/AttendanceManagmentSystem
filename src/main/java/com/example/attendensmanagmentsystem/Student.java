package com.example.attendensmanagmentsystem;

import javafx.beans.property.*;

public class Student extends User {
    private final StringProperty firstDate = new SimpleStringProperty();
    private final StringProperty secondDate = new SimpleStringProperty();
    private final StringProperty thirdDate = new SimpleStringProperty();
    private final StringProperty fourthDate = new SimpleStringProperty();
    private final StringProperty fifthDate = new SimpleStringProperty();

    private final IntegerProperty firstDateAttendance = new SimpleIntegerProperty();
    private final IntegerProperty secondDateAttendance = new SimpleIntegerProperty();
    private final IntegerProperty thirdDateAttendance = new SimpleIntegerProperty();
    private final IntegerProperty fourthDateAttendance = new SimpleIntegerProperty();
    private final IntegerProperty fifthDateAttendance = new SimpleIntegerProperty();

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final IntegerProperty totalAbsences = new SimpleIntegerProperty();
    private final DoubleProperty gpa = new SimpleDoubleProperty();
    private final StringProperty fName = new SimpleStringProperty();
    private final StringProperty lName = new SimpleStringProperty();
    private final StringProperty gender = new SimpleStringProperty();
    private final DoubleProperty attendancePercentage = new SimpleDoubleProperty();

    private final StringProperty group = new SimpleStringProperty();
    private final StringProperty ID = new SimpleStringProperty();
    private final StringProperty GPA = new SimpleStringProperty();





    public Student(int id, String group, String fullName, double gpa,
                   String firstDate, String secondDate, String thirdDate,
                   String fourthDate, String fifthDate, double attendancePercentage) {
        setId(id);
        setGroup(group);
        setFullName(fullName);
        setGpa(gpa);
        setFirstDate(firstDate);
        setSecondDate(secondDate);
        setThirdDate(thirdDate);
        setFourthDate(fourthDate);
        setFifthDate(fifthDate);
        setAttendancePercentage(attendancePercentage);
    }

    public Student() {

    }
    public String getFirstDate() {
        return firstDate.get();
    }
    public StringProperty firstDateProperty() {
        return firstDate;
    }
    public void setFirstDate(String firstDate) {
        this.firstDate.set(firstDate);
    }

    public String getSecondDate() {
        return secondDate.get();
    }
    public StringProperty secondDateProperty() {
        return secondDate;
    }
    public void setSecondDate(String secondDate) {
        this.secondDate.set(secondDate);
    }

    public String getThirdDate() {
        return thirdDate.get();
    }
    public StringProperty thirdDateProperty() {
        return thirdDate;
    }
    public void setThirdDate(String thirdDate) {
        this.thirdDate.set(thirdDate);
    }

    public String getFourthDate() {
        return fourthDate.get();
    }
    public StringProperty fourthDateProperty() {
        return fourthDate;
    }
    public void setFourthDate(String fourthDate) {
        this.fourthDate.set(fourthDate);
    }

    public String getFifthDate() {
        return fifthDate.get();
    }
    public StringProperty fifthDateProperty() {
        return fifthDate;
    }
    public void setFifthDate(String fifthDate) {
        this.fifthDate.set(fifthDate);
    }

    public double getAttendancePercentage() {
        return attendancePercentage.get();
    }
    public void setAttendancePercentage(double attendancePercentage) {
        this.attendancePercentage.set(attendancePercentage);
    }




    public String getFullName() {
        return getfName() + " " + getlName();
    }




    public int getFirstDateAttendance() {
        return firstDateAttendance.get();
    }

    public IntegerProperty firstDateAttendanceProperty() {
        return firstDateAttendance;
    }

    public void setFirstDateAttendance(int firstDateAttendance) {
        this.firstDateAttendance.set(firstDateAttendance);
    }


    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getGroup() {
        return group.get();
    }

    public StringProperty groupProperty() {
        return group;
    }

    public void setGroup(String group) {
        this.group.set(group);
    }

    public StringProperty IDProperty() {
        return ID;
    }

    public String getID() {
        return ID.get();
    }

    public void setID(String ID) {
        this.ID.set(ID);
    }

    public String getGPA() {
        return GPA.get();
    }

    public StringProperty GPAProperty() {
        return GPA;
    }

    public void setGPA(String GPA) {
        this.GPA.set(GPA);
    }
    public String getfName() {
        return fName.get();
    }

    public StringProperty fNameProperty() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName.set(fName);
    }

    public String getlName() {
        return lName.get();
    }

    public StringProperty lNameProperty() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName.set(lName);
    }

    public void setFullName(String fullName) {
        String[] names = fullName.split("\\s+");
        if (names.length > 0) {
            setfName(names[0]);
        }
        if (names.length > 1) {
            setlName(names[1]);
        }
    }


    public double getGpa() {
        return gpa.get();
    }

    public DoubleProperty gpaProperty() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa.set(gpa);
    }



}