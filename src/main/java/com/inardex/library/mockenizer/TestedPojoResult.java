package com.inardex.library.mockenizer;

/**
 * The {@code TestedPojoResult} class contains the result of checked pojo.
 * This class are used by {@link Mockenizer#testPojos(String)} and {@link Mockenizer#testPojos(java.util.List)}
 *
 * @author iNardex
 * @see com.inardex.library.mockenizer.Mockenizer
 */
public class TestedPojoResult {

    private Class testedClass;

    private boolean equalsRespected;
    private boolean getterSetterRespected;
    private boolean notSlowToString;

    private String message;

    TestedPojoResult(Class testedClass, boolean equalsRespected, boolean getterSetterRespected, boolean notSlowToString, String message) {
        this.testedClass = testedClass;
        this.equalsRespected = equalsRespected;
        this.getterSetterRespected = getterSetterRespected;
        this.notSlowToString = notSlowToString;
        this.message = message;
    }

    /**
     * Indicate if all test are passed
     *
     * @return boolean result
     */
    public boolean isOk() {
        return equalsRespected && getterSetterRespected && notSlowToString;
    }

    /**
     * Indicate if equals method are working properly
     *
     * @return boolean result
     */
    public boolean isEqualsRespected() {
        return equalsRespected;
    }

    /**
     * Indicate if all getter and setter are working properly
     *
     * @return boolean result
     */
    public boolean isGetterSetterRespected() {
        return getterSetterRespected;
    }

    /**
     * Indicate if toString() method is fast
     *
     * @return boolean result
     */
    public boolean isNotSlowToString() {
        return notSlowToString;
    }

    /**
     * This class identify the checked pojo
     *
     * @return the checked {@code Class}
     */
    public Class getTestedClass() {
        return testedClass;
    }

    /**
     * If almost one test are not passed can we found an error message
     *
     * @return error message
     */
    public String getMessage() {
        return message;
    }

    static TestedPojoResult skipped(Class p) {
        return new TestedPojoResult(p, true, true, true, "SKIPPED");
    }
}
