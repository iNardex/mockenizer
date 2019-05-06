package com.inardex.library.mockenizer.pojo;

import java.util.StringJoiner;

public class BadPojoTemplate {

    private String string;
    private Integer integer;
    private Boolean aBoolean;

    public String getString() {
        return string;
    }

    public void setString(String string) {
        string = string;
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
        return false;
    }

    @Override
    public String toString() {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            //Not to do
        }
        return new StringJoiner(", ", BadPojoTemplate.class.getSimpleName() + "[", "]")
                .add("string='" + string + "'")
                .add("integer=" + integer)
                .add("aBoolean=" + aBoolean)
                .toString();
    }
}
