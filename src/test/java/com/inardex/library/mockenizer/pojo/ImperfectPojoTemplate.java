package com.inardex.library.mockenizer.pojo;

import java.util.List;
import java.util.Set;

public class ImperfectPojoTemplate {

    private String stringType;
    private int intType;

    private List<String> listType;
    private Set<ComplexObject> setType;

    private ImperfectPojoTemplate myself;

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

    public ImperfectPojoTemplate getMyself() {
        return myself;
    }

    public void setMyself(ImperfectPojoTemplate myself) {
        this.myself = myself;
    }
}
