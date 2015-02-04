package main;

import java.security.SecureRandom;
import java.util.Random;

public class Floor {

    public static final int FLOOR_COUNT = 3;
    public static Random r = new SecureRandom();

    public static int random() {
        int floor = r.nextInt(FLOOR_COUNT);
        return floor;
    }
}
