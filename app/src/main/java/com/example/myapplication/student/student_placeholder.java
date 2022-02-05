package com.example.myapplication.student;

import java.util.List;

public class student_placeholder implements studentInfo{
    //Student should have an ID;
    private final int id;
    //Student should have a URL to their photo
    private final String URL;
    //Student should have a first name
    private final String firstName;
    //Finally student should have a list of classes
    //Format TBD
    private final List<String> class_list;

    //This is our student that we will use for creating new students
    //Note** the main user should have an ID of 0 (to indicate they are the first user).
    public student_placeholder(int id, String URL, String firstName, List<String> class_list ){
        this.id = id;
        this.URL = URL;
        this.firstName = firstName;
        this.class_list = class_list;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getURL() {
        return URL;
    }

    @Override
    public String getfirstName() {
        return firstName;
    }

    @Override
    public List<String> getclass_list() {
        return class_list;
    }
}
