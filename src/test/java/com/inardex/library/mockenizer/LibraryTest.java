package com.inardex.library.mockenizer;

import com.inardex.library.mockenizer.pojo.BadPojoTemplate;
import com.inardex.library.mockenizer.pojo.ComplexObject;
import com.inardex.library.mockenizer.pojo.ImperfectPojoTemplate;
import com.inardex.library.mockenizer.pojo.PerfectPojoTemplate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LibraryTest {

    @Mock
    public PerfectPojoTemplate test;

    @Before
    public void init() {
        Mockenizer.fillAnnotated(this);
    }

    @Test
    public void annotationFillTest() {
        Assert.assertNotNull(test);
    }

    @Test
    public void simpleMockTest() throws MockenizeException {
        PerfectPojoTemplate mocked = Mockenizer.mock(PerfectPojoTemplate.class);
        Assert.assertNotNull(mocked);
    }

    @Test(expected = MockenizeException.class)
    public void imperfectMockTestNotForced() throws MockenizeException {
        Mockenizer.mock(ImperfectPojoTemplate.class);
    }

    @Test
    public void imperfectMockTestForced() {
        ImperfectPojoTemplate mocked = Mockenizer.mock(ImperfectPojoTemplate.class, true);
        Assert.assertNotNull(mocked);
    }

    @Test
    public void pojosTest() {
        final List<Class> pojosOK = Arrays.asList(
                ComplexObject.class,
                ImperfectPojoTemplate.class,
                PerfectPojoTemplate.class
        );
        Mockenizer.testPojos(pojosOK).forEach(r -> Assert.assertTrue(r.getMessage(), r.isOk()));

        Mockenizer.testPojos(Collections.singletonList(BadPojoTemplate.class))
                .forEach(r -> Assert.assertFalse(r.isOk()));
    }

    @Test
    public void pojosTestByPackage() {
        List<TestedPojoResult> pojoResults = Mockenizer.testPojos("com.inardex.library.mockenizer.pojo");
        Assert.assertNotNull(pojoResults);
        Assert.assertFalse(pojoResults.isEmpty());
    }

}
