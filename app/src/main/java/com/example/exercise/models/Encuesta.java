package com.example.exercise.models;

import java.text.SimpleDateFormat;

import java.util.Date;

public class Encuesta {
    public String userID;
    public String cedula;
    public String mail;
    public Integer eval;
    public String timeNow;

    public Encuesta() {


    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Integer getEval() {
        return eval;
    }

    public void setEval(Integer eval) {
        this.eval = eval;
    }

    public String getTimeNow() {
        return timeNow;
    }

    public void setTimeNow(String timeNow) {
        this.timeNow = timeNow;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "Encuesta{" +
                "cedula='" + cedula + '\'' +
                ", mail='" + mail + '\'' +
                ", eval=" + eval +
                ", timeNow='" + timeNow + '\'' +
                '}';
    }
}
