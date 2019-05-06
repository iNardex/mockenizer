package com.inardex.library.mockenizer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

class PojoField {

    private Field field;
    private Parameter param;

    private Method getter;
    private Method setter;

    public PojoField(Field field, Parameter param, Method getter, Method setter) {
        this.field = field;
        this.param = param;
        this.getter = getter;
        this.setter = setter;
    }

    public Parameter getParam() {
        return param;
    }

    public Field getField() {
        return field;
    }

    public Method getGetter() {
        return getter;
    }

    public Method getSetter() {
        return setter;
    }
}
