package com.inardex.library.mockenizer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class MockEngine {

    private final boolean force;

    MockEngine(boolean force) {
        this.force = force;
    }

    final <T> T buildInstance(Class<T> clazz) throws MockenizeException {
        Object obj;
        try {
            Constructor constructor = clazz.getConstructor();
            obj = constructor.newInstance();
        } catch (Exception e) {
            if (force)
                return null;
            throw new MockenizeException("Class " + clazz.getName() + " have not default constructor with no param. I need it pls");
        }

        //Need this check for loop problem
        if (inExecutions.contains(clazz))
            throw new MockenizeException("Im not able to mock the same class in loop. It's really what you need ?");
        inExecutions.add(clazz);

        for (PojoField fields : findFieldIntoClass(clazz)) {
            try {
                Object paramInstance = buildParam(fields.getParam().getParameterizedType().getTypeName());
                fields.getSetter().invoke(obj, paramInstance);
            } catch (Exception e) {
                if (!force)
                    throw new MockenizeException(e.getMessage());
            }
        }

        inExecutions.remove(clazz);
        return (T) obj;
    }

    final boolean getterSetterTest(Object instance, Class clazz) {

        for (PojoField fields : findFieldIntoClass(clazz)) {
            try {
                Optional paramOpt = Optional.ofNullable(
                        buildParam(fields.getParam().getParameterizedType().getTypeName())
                );
                if (!paramOpt.isPresent())
                    continue;

                fields.getSetter().invoke(instance, paramOpt.get());
                Optional returnedOpt = Optional.ofNullable(fields.getGetter().invoke(instance));

                if (!returnedOpt.isPresent() || !returnedOpt.get().equals(paramOpt.get()))
                    return false;
            } catch (Exception e) {
                //Not to do
            }
        }

        return true;
    }

    private Object buildParam(String paramType) throws MockenizeException {
        List<String> types = scanGenericsType(paramType);
        boolean haveSubGenerics = types.size() > 1;

        switch (types.get(0)) {
            case "java.lang.String":
                return Randomizer.randomString();
            case "int":
            case "java.lang.Integer":
                return Randomizer.randomInt();
            case "long":
            case "java.lang.Long":
                return Randomizer.randomLong();
            case "double":
            case "java.lang.Double":
                return Randomizer.randomDouble();
            case "java.util.Date":
                return new Date();
            case "boolean":
            case "java.lang.Boolean":
                return Randomizer.randomBoolean();
            case "java.util.List":
                return haveSubGenerics ? Stream.of(buildParam(types.get(1))).collect(Collectors.toList()) : new ArrayList<>();
            case "java.util.Set":
                return haveSubGenerics ? Stream.of(buildParam(types.get(1))).collect(Collectors.toSet()) : new HashSet<>();
            case "java.util.Map":
                return Collections.emptyMap();
            case "java.math.BigDecimal":
                return BigDecimal.valueOf(Randomizer.randomDouble());
            default:
                try {
                    return buildInstance(Class.forName(paramType));
                } catch (Exception e) {
                    throw new MockenizeException("Im not able to mock field: " + paramType + ". Can you fix me ?");
                }
        }
    }

    private List<String> scanGenericsType(String type) {
        if (!type.contains("<"))
            return Collections.singletonList(type);


        List<String> types = new ArrayList<>();
        int countNestedGenerics = 0;
        int base = 0;
        for (int i = 0; i < type.length(); i++) {
            if ('<' == (type.charAt(i))) {
                if (base == 0) {
                    types.add(type.substring(base, i));
                    base = i + 1;
                } else {
                    countNestedGenerics++;
                }
            }
            if ('>' == (type.charAt(i))) {
                if (base != 0 && countNestedGenerics == 0) {
                    types.add(type.substring(base, i));
                    return types;
                } else
                    countNestedGenerics--;
            }
        }

        return Collections.singletonList(type);
    }

    private List<PojoField> findFieldIntoClass(Class clazz) {
        final Method[] methods = clazz.getDeclaredMethods();
        return Arrays.stream(clazz.getDeclaredFields()).map(field -> {

            final String fieldName = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);

            //TODO: need check something
            Optional<Method> setterOpt = Arrays.stream(methods)
                    .filter(m -> m.getName().equals("set" + fieldName) && m.getParameters().length == 1)
                    .findFirst();

            Optional<Method> getterOpt = Arrays.stream(methods)
                    .filter(m -> (m.getName().equals("get" + fieldName) || m.getName().equals("is" + fieldName))
                            && m.getParameters().length == 0)
                    .findFirst();

            if (!setterOpt.isPresent() || !getterOpt.isPresent())
                return null;

            return new PojoField(field, setterOpt.get().getParameters()[0], getterOpt.get(), setterOpt.get());
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private List<Class> inExecutions = new ArrayList<>();

}
