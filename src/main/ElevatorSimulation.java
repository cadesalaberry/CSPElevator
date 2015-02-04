package main;

import java.util.ArrayList;
import java.util.List;

public class ElevatorSimulation {

    public static void main(String[] args) {
        if (args.length < 2) {

            System.out.println("Invalid args. Running default simulation.");
            int passengerCount = 5;
            int stepCount = 1;

            runSimulation(passengerCount, stepCount);

            return;
        }

        int n, t;

        t = Integer.parseInt(args[0]);
        n = Integer.parseInt(args[1]);

        if (!validateArgs(n, t)) {
            return;
        }
    }

    private static void runSimulation(int n, int t) {
        Elevator elevator = new Elevator();
        SyncChannel channel = new SyncChannel();
        LobbyBoy lobbyBoy = new LobbyBoy(elevator, channel);

        List<Passenger> passengerList = new ArrayList<Passenger>();

        for (int i = 0; i < n; i++) {
            Passenger p = new Passenger(i, t, Floor.random());
            p.setElevator(elevator);
            p.setChannel(channel);

            elevator.newPassenger(p);
            passengerList.add(p);
        }

        for (Passenger p: passengerList) {
            p.start();
        }

        lobbyBoy.start();

        elevator.start();

        for (Passenger p: passengerList) {
            try {
                p.join();
                Debug.print(p, "Has completed its " + t + "trips.");
            } catch (InterruptedException e) {
                Debug.print(p, "Interrupted while joining.");
            }
        }

        elevator.interrupt();
        lobbyBoy.interrupt();
    }

    public static boolean validateArgs(int n, int t) {

        boolean valid = true;

        // Checks the number of passenger threads to spawn.
        if (!(0 <= n)) {

            System.err.println("Error: p should be greater than 0.");
            valid = false;
        }

        // Checks the number of times passenger should go in the elevator.
        if (!(0 <= t)) {

            System.err.println("Error: t should be greater than 0.");
            valid = false;
        }

        return valid;
    }

}