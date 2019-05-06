package com.inardex.library.mockenizer;

import java.util.Date;

class TestUtils {

    private static final Long TOSTRING_MS_LIMIT = 300L;

    static boolean getterSetterTest(MockEngine engine, Class p, Object obj1, StringBuilder msg) {
        boolean gettersSettersTestRes = engine.getterSetterTest(obj1, p);
        if (!gettersSettersTestRes)
            msg.append("The class ").append(p.getName()).append(" have wrong getter/setter.");
        return gettersSettersTestRes;
    }

    static boolean toStringTest(Class p, Object obj1, StringBuilder msg) {
        Date start = new Date();
        obj1.toString();
        long diff = new Date().getTime() - start.getTime();
        boolean notSlowToString = diff < TOSTRING_MS_LIMIT;
        if (!notSlowToString)
            msg.append("toString() execution of class ").append(p.getName())
                    .append(" takes ").append(diff).append(" ms. IS SLOW!");
        return notSlowToString;
    }

    static boolean equalsTest(Class p, Object obj1, Object obj2, StringBuilder msg) {
        boolean equalsRespected = obj1.equals(obj1) && !obj1.equals(obj2);
        if (!equalsRespected)
            msg.append("The class ").append(p.getName()).append(" does not respect the equals contract.");
        return equalsRespected;
    }

    private TestUtils() {
        throw new UnsupportedOperationException("All method are static. You don't need initialize this object.");
    }
}
