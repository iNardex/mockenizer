package com.inardex.library.mockenizer.pojo;

import java.util.Objects;

public class ComplexObject {

    private String string;
    private Integer integer;
    private Boolean aBoolean;

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public Integer getInteger() {
        return integer;
    }

    public void setInteger(Integer integer) {
        this.integer = integer;
    }

    public Boolean getaBoolean() {
        return aBoolean;
    }

    public void setaBoolean(Boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComplexObject that = (ComplexObject) o;
        return Objects.equals(string, that.string) &&
                Objects.equals(integer, that.integer) &&
                Objects.equals(aBoolean, that.aBoolean);
    }

    @Override
    public int hashCode() {
        return Objects.hash(string, integer, aBoolean);
    }
}
