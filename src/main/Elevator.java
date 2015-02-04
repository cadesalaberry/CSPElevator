package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Elevator extends Thread {

    private final int MAX_NB_OF_TRIPS = 1000;

    private HashMap<Integer, ArrayList<Passenger>> insideElevator;
    private HashMap<Integer, ArrayList<Passenger>> outsideElevator;
    private HashMap<Integer, ArrayList<Passenger>> waitingForElevator;


    private int floorCount, currentFloor, tripCount;

    public Elevator() {

        this.tripCount = 0;
        this.floorCount = Floor.FLOOR_COUNT;
        this.currentFloor = Floor.random();

        insideElevator = new HashMap<Integer, ArrayList<Passenger>>();
        outsideElevator = new HashMap<Integer, ArrayList<Passenger>>();
        waitingForElevator = new HashMap<Integer, ArrayList<Passenger>>();

        for (int i = 0; i < floorCount; i++) {
            insideElevator.put(i, new ArrayList<Passenger>());
            outsideElevator.put(i, new ArrayList<Passenger>());
            waitingForElevator.put(i, new ArrayList<Passenger>());
        }
    }

    public void run() {

        try {
            do {
                if (this.isInterrupted())
                    break;

                // Open door
                Debug.print(this, "opened on floor " + currentFloor);

                Debug.print(this, "\n" + this.toString());

                List<Passenger> toUnload = new ArrayList(insideElevator.get(currentFloor));
                insideElevator.get(currentFloor).removeAll(toUnload);

                List<Passenger> toLoad = new ArrayList(waitingForElevator.get(currentFloor));
                waitingForElevator.get(currentFloor).removeAll(toLoad);


                // Unload
                for (Passenger p : toUnload) {
                    this.newPassenger(p);
                    Debug.print(this, p + " should get out.");

                    synchronized (p) {
                        p.exitElevator();
                    }
                }

                // Load
                for (Passenger p : toLoad) {
                    Debug.print(this, p + " should get in.");
                    synchronized (p) {
                        p.enterElevator();
                    }
                }

                // Simulates time during which the doors are open
                Thread.sleep(10);

                // Close
                Debug.print(this, "closed on floor " + currentFloor);

                goToFloor(this.getBestFloor());
                this.tripCount++;

            } while (tripCount < MAX_NB_OF_TRIPS);

        } catch (InterruptedException e) {
        }

        Debug.print(this, "interrupted after " + tripCount + " trips.");
    }

    public void goToFloor(int floor) {
        Debug.print(this, "going from floor " + currentFloor + " to " + floor);
        currentFloor = floor;
    }

    public void stopForOnFloor(Passenger p, int floor) {
        insideElevator.get(floor).add(p);
    }

    public synchronized void calledByOnFloor(Passenger p, int floor) {
        outsideElevator.get(floor).remove(p);
        waitingForElevator.get(floor).add(p);
    }

    public void newPassenger(Passenger p) {
        int floor = p.floor;
        outsideElevator.get(floor).add(p);
    }

    public int getBestFloor() {
        int max = 0;
        int mostAskedFloor = -1;

        for (int i = 0; i < floorCount; i++) {
            int inside = insideElevator.get(i).size();
            int outside = waitingForElevator.get(i).size();

            int weight = inside + outside;

            //Debug.print(this, "floor " + i + " has " + weight);

            if (weight >= max) {
                max = weight;
                mostAskedFloor = i;
            }
        }

        assert (-1 < mostAskedFloor);

        return mostAskedFloor;
    }

    public String toString() {
        String s = "\n";

        for (int i = 0; i < floorCount; i++) {
            s += insideElevator.get(floorCount - i - 1) + "\t| ";
            s += waitingForElevator.get(floorCount - i - 1) + "\t";
            s += outsideElevator.get(floorCount - i - 1) + "\n";
        }
        return s;
    }
}
