package com.tai06.dothetai.danhba.Object;

import java.io.Serializable;
import java.util.Objects;

public class History implements Serializable {
    private int id;
    private String name;
    private String number;
    private String date;
    private int id_db;

    public History() {
    }

    public History(int id, String name, String number, String date, int id_db) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.date = date;
        this.id_db = id_db;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId_db() {
        return id_db;
    }

    public void setId_db(int id_db) {
        this.id_db = id_db;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        History history = (History) o;
        return id == history.id &&
                id_db == history.id_db &&
                Objects.equals(name, history.name) &&
                Objects.equals(number, history.number) &&
                Objects.equals(date, history.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, number, date, id_db);
    }
}
