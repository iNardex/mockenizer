package com.inardex.library.mockenizer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * The {@code Mockenizer} class is the core of this library, contain all public method you need to use.
 * Is born to mock or test your pojo and improve your project coverage.
 *
 * @author iNardex
 * @see com.inardex.library.mockenizer.MockEngine
 */
public final class Mockenizer {

    /**
     * Create an instance of the {@code Class} passed as parameter.
     * <p>This method fill all field that have getter/setter with random param;
     * if the field is another pojo, it will be mock too.
     *
     * <p><b>if the field is another pojo, it will be mock too.</b>
     *
     * <p>Example:
     * <blockquote><pre>
     *     Pojo mockedPojo = Mockenizer.mock(Pojo.class);
     * </pre></blockquote>
     *
     * @param clazz
     *        {@code Class} of the pojo you want mock
     *
     * @param <T>
     *        {@code Class} of the pojo you want mock
     *
     * @return The mocked instance of clazz
     *
     * @throws com.inardex.library.mockenizer.MockenizeException
     *         This Exception are throws in case the library can't generate the mock instance
     */
    public static final <T> T mock(Class<T> clazz) throws MockenizeException {
        try {
            return new MockEngine(false).buildInstance(clazz);
        } catch (MockenizeException e) {
            throw e;
        } catch (Exception e) {
            throw new MockenizeException("I cant make this mock instance. Im sorry! :-(");
        }
    }

    /**
     * Create an instance of the {@code Class} passed as parameter.
     * <p>This method fill all field that have getter/setter with random param;
     * if the field is another pojo, it will be mock too.
     *
     * <p><b>For work the pojo must have the default constructor.</b>
     *
     * <p>Example:
     * <blockquote><pre>
     *     Pojo mockedPojo = Mockenizer.mock(Pojo.class, true);
     * </pre></blockquote>
     *
     * @param clazz
     *        {@code Class} of the pojo you want mock
     *
     * @param <T>
     *        {@code Class} of the pojo you want mock
     *
     * @param force
     *        if this param is true {@code MockenizeException} are never throw.
     *        If an error are encountered will be null.
     *
     * @return  The mocked instance of clazz
     *
     * @throws com.inardex.library.mockenizer.MockenizeException
     *         This Exception are throws in case the library can't generate the mock instance
     */
    public static final <T> T mock(Class<T> clazz, boolean force) {
        try {
            return new MockEngine(force).buildInstance(clazz);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * This method verify the pojos class passed as a List into parameter.
     * <p>The checks are:
     *      <p>1. Verify if equals contract are respected.
     *      <p>2. If {@code toString()} method is fast
     *      <p>3. Verify if getter and setter working properly.
     *
     * <p>For every checked pojos will be returned an {@link TestedPojoResult} contains the test result;
     * if one of this 3 check fails {@link TestedPojoResult#getMessage} return the error message.
     *
     * <p>If the library can't generate the pojo the message are "SKIPPED" and all 3 test are checked like passed.
     *
     * <p><b>For work the pojo must have the default constructor.</b>
     *
     * @param pojos {@code List} of pojos {@code Class} you want test
     *
     * @return a List with test results.
     *
     */
    public static final List<TestedPojoResult> testPojos(List<Class> pojos) {
        MockEngine engine = new MockEngine(true);
        List<TestedPojoResult> testResult = new ArrayList<>();
        pojos.forEach(p -> {
            try {
                Object obj1 = engine.buildInstance(p);
                Object obj2 = engine.buildInstance(p);

                if (obj1 == null)
                    throw new MockenizeException("Can't generate. Skip");

                StringBuilder msg = new StringBuilder();

                boolean equalsRespected = TestUtils.equalsTest(p, obj1, obj2, msg);

                boolean notSlowToString = TestUtils.toStringTest(p, obj1, msg);

                boolean gettersSettersTestRes = TestUtils.getterSetterTest(engine, p, obj1, msg);

                TestedPojoResult result = new TestedPojoResult(p, equalsRespected, gettersSettersTestRes, notSlowToString, msg.toString());
                testResult.add(result);
            } catch (MockenizeException me) {
                testResult.add(TestedPojoResult.skipped(p));
            }
        });
        return testResult;
    }

    /**
     * This method verify all pojos contained into passed package.
     * <p>The checks are:
     *      <p>1. Verify if equals contract are respected.
     *      <p>2. If {@code toString()} method is fast
     *      <p>3. Verify if getter and setter working properly.
     *
     * <p>For every checked pojos will be returned an {@link TestedPojoResult} contains the test result;
     * if one of this 3 check fails {@link TestedPojoResult#getMessage} return the error message.
     *
     * <p>If the library can't generate the pojo the message are "SKIPPED" and all 3 test are checked like passed.
     *
     * <p><b>For work the pojo must have the default constructor.</b>
     *
     * @param myPackage
     *        package name of pojos {@code Class} you want test
     *
     * @return List with test results.
     *
     */
    public static final List<TestedPojoResult> testPojos(String myPackage) {
        if (myPackage == null || myPackage.isEmpty())
            return Collections.emptyList();

        Set<Class<?>> allClasses = ReflectionUtils.findClassByPackage(myPackage);

        if (allClasses == null || allClasses.isEmpty())
            throw new MockenizeException("Unable to find classes");

        return testPojos(new ArrayList<>(allClasses));
    }

    /**
     * This method mock all field annotated with {@link Mock} annotation into passed instance.
     *
     * @param classInstance
     *        instance where annotated field must be searched
     *
     * @throws MockenizeException
     *         This Exception are throws in case the library can't generate the mock instance
     */
    public static final void fillAnnotated(Object classInstance) throws MockenizeException {
        Field[] fields = classInstance.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Mock.class)) {
                try{
                    Class classToCreate = Class.forName(field.getType().getName());
                    Object mockInstance = mock(classToCreate, true);
                    field.set(classInstance, mockInstance);
                } catch (ClassNotFoundException | IllegalAccessException e) {
                    throw new MockenizeException("Error on mocking " + field.getType().getName() + " class");
                }
            }
        }
    }

    private Mockenizer() {
        throw new UnsupportedOperationException("All method are static. You don't need initialize this object.");
    }

}
