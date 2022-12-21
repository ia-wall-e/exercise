package com.example.exercise.models;

public class Materia {
    private String cedula, materia,profesor,salon, materiaID;

    public Materia() {
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public String getSalon() {
        return salon;
    }

    public void setSalon(String salon) {
        this.salon = salon;
    }

    public String getMateriaID() {
        return materiaID;
    }

    public void setMateriaID(String materiaID) {
        this.materiaID = materiaID;
    }

    @Override
    public String toString() {
        return  "MATERIA: "+materia + "\n" +
                "PROFESOR: "+ profesor + "\n" +
                "SALON: "+ salon + "\n";
    }
}
