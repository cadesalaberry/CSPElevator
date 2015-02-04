package main;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

public class ElevatorButtons extends Thread {

    ArrayList<ArrayList<Object>> floors;
    SyncChannel channel;
    Random r;

    int floorCount = 0;

    public ElevatorButtons(int floorCount) {
        floors = new ArrayList<ArrayList<Object>>();
        r = new SecureRandom();

        this.floorCount = floorCount;

        for (int i = 0; i < this.floorCount; i++)
            floors.add(new ArrayList<Object>());

        assert (floorCount == floors.size());
    }

    public void pressRandomFloor(Passenger p) {
        int floor = Floor.random();
        pressButtonForFloor(floor, p);
    }

    private void pressButtonForFloor(int floor, Passenger p) {
        assert (0 < floor);

        Debug.print(p, "; floor:" + floor);
        floors.get(floor).add(p);
    }

    public int getBestFloor() {
        int max = 0;
        ArrayList mostAskedFloor = floors.get(0);

        for (ArrayList list : floors) {
            if (list.size() >= max) {
                max = list.size();
                mostAskedFloor = list;
            }
        }

        return floors.indexOf(mostAskedFloor);
    }

    public SyncChannel getChannel() {
        return channel;
    }

    public void setChannel(SyncChannel channel) {
        this.channel = channel;
    }

    public void run() {
        while (true) {

            Request o = channel.receive();

            if (o.message == Message.CALLING_ELEVATOR) {
                pressButtonForFloor(o.floor, o.from);

            } else {

                pressRandomFloor(o.from);
            }
        }
    }
}
