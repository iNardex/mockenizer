package com.inardex.library.mockenizer.pojo;

import java.util.List;
import java.util.Set;

public class PerfectPojoTemplate {

    private String stringType;
    private int intType;
    private long longType;

    private List<String> listType;
    private Set<ComplexObject> setType;

    public String getStringType() {
        return stringType;
    }

    public void setStringType(String stringType) {
        this.stringType = stringType;
    }

    public int getIntType() {
        return intType;
    }

    public void setIntType(int intType) {
        this.intType = intType;
    }

    public List<String> getListType() {
        return listType;
    }

    public void setListType(List<String> listType) {
        this.listType = listType;
    }

    public Set<ComplexObject> getSetType() {
        return setType;
    }

    public void setSetType(Set<ComplexObject> setType) {
        this.setType = setType;
    }

    public long getLongType() {
        return longType;
    }

    public void setLongType(long longType) {
        this.longType = longType;
    }
}
