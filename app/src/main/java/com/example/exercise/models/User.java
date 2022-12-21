package com.example.exercise.models;

public class User {
    public String userID;
    public String mail;
    public String name;
    public String carrera;
    public String cedula;

    public User() {
        /*this.mail = mail;
        this.name = name;
        this.carrera = carrera;
        this.cedula = cedula;*/
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getCedula() {
        return cedula;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    @Override
    public String toString() {
        return "User{" +
                "mail='" + mail + '\'' +
                ", name='" + name + '\'' +
                ", carrera='" + carrera + '\'' +
                ", cedula='" + cedula + '\'' +
                '}';
    }
}
