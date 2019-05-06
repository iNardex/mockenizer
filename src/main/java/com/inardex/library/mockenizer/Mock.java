package com.inardex.library.mockenizer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>A field element annotated will be filled with a mock instance of yourself.
 * <p>For fill element annotated you need call {@link Mockenizer#fillAnnotated(Object)} method.
 *
 * @author  iNardex
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Mock {
}
