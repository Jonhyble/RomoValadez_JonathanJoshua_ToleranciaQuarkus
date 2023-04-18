package com.prueba.model;

public class Division {
    private Long divisionId;
    private int number1, number2;

    private float result;

    public Division() {
    }

    public Division(Long divisionId, int number1, int number2, float result) {
        this.divisionId = divisionId;
        this.number1 = number1;
        this.number2 = number2;
        this.result = result;
    }

    public Long getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(Long divisionId) {
        this.divisionId = divisionId;
    }

    public int getNumber1() {
        return number1;
    }

    public void setNumber1(int number1) {
        this.number1 = number1;
    }

    public int getNumber2() {
        return number2;
    }

    public void setNumber2(int number2) {
        this.number2 = number2;
    }

    public float getResult() {
        return result;
    }

    public void setResult(float result) {
        this.result = result;
    }
}
