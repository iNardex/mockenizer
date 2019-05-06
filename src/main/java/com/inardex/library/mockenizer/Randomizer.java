package com.inardex.library.mockenizer;

import java.util.Random;

class Randomizer {

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static Random r = new Random();

    private Randomizer() {
        throw new UnsupportedOperationException("Util class!!!");
    }

    static String randomString() {
        int count = 20;
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (r.nextInt() & Integer.MAX_VALUE) % ALPHA_NUMERIC_STRING.length();
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    static int randomInt() {
        return r.nextInt() & Integer.MAX_VALUE;
    }

    static long randomLong() {
        return r.nextLong() & Long.MAX_VALUE;
    }

    static double randomDouble() {
        return r.nextDouble();
    }

    static boolean randomBoolean() {
        return (r.nextInt() % 2) == 1;
    }

}
