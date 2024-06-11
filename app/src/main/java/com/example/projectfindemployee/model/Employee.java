package com.example.projectfindemployee.model;
public class Employee {

    private int id;
    private String name,surname;

    private String mail;

    private double coordinateX=0.0,coordinateY=0.0;
    private String password;



    @Override
    public String toString() {
        return "Employee{" +
                ", name='" + name + '\'' +
                ", surname='"+surname+'\''+
                ", mail='"+mail+'\''+
                ", id=" + id +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getMail() {
        return mail;
    }

    public double getCoordinateX() {
        return coordinateX;
    }

    public double getCoordinateY() {
        return coordinateY;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setCoordinateX(double coordinateX) {
        this.coordinateX = coordinateX;
    }

    public void setCoordinateY(double coordinateY) {
        this.coordinateY = coordinateY;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
