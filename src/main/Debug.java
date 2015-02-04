package main;

/**
 * Created by ricocheteur on 01/12/14.
 */
public class Debug {
    public static void print(Object o, String in) {

        String s;

        if (o instanceof Elevator) {
            s = "Elevator: ";
        } else if (o instanceof Passenger) {
            s = "Passenger #" + ((Passenger) o).id + ": ";
        } else if (o instanceof LobbyBoy) {
            s = "LobbyBoy: ";
        } else {
            s = in;
        }


        if (s != "") System.out.println(s + in);
    }
}
