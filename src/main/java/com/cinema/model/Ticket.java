package com.cinema.model;

/**
 * Created by Dominik on 04.06.2017.
 */
public class Ticket {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String name;

    private float value = 14;

    private Abatement type;

    private int row;
    private int column;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {

        switch (type) {
            case Student:
                return value / 2;
            case Uczeń:
                float newValue = value / (1.7f);
                newValue = Math.round(newValue);
                return newValue;
            case Emeryt:
                return Math.round(value / (1.9f));
            default:
                return value;
        }
    }

    public boolean equalsByType(Ticket ticket){
        if(this.getType() == ticket.getType()){
            return true;
        } else {
            return false;
        }
    }

    public Abatement getType() {
        return type;
    }

    public void setType(Abatement type) {
        this.type = type;
    }

    public enum Abatement{
        Normalny, Student, Uczeń, Emeryt
    }
}
