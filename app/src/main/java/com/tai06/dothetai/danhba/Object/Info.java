package com.tai06.dothetai.danhba.Object;

import java.io.Serializable;
import java.util.Objects;

public class Info implements Serializable {
    private int id;
    private String name;
    private String number;

    public Info() {
    }

    public Info(int id, String name, String number) {
        this.id = id;
        this.name = name;
        this.number = number;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Info info = (Info) o;
        return id == info.id &&
                Objects.equals(name, info.name) &&
                Objects.equals(number, info.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, number);
    }
}
