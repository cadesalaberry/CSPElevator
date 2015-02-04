package main;

public class Passenger extends Thread {

    int floor, id, t;

    SyncChannel channel;
    Elevator elevator;

    public Passenger(int id, int t, int floor) {
        this.floor = floor;
        this.id = id;
        this.t = t;
    }

    public void setChannel(SyncChannel c) {
        this.channel = c;
    }

    public void setElevator(Elevator e) {
        this.elevator = e;
    }

    public void run() {
        try {
            Debug.print(this, "Initialized on floor " + floor);

            for (int i = 0; i < t; i++) {

                //Press button
                //Request request = new Request(this, Message.CALLING_ELEVATOR, floor);
                //channel.send(request);

                elevator.calledByOnFloor(this, floor);

                Debug.print(this, "waiting for elevator on floor " + floor + "...");

                synchronized (this) {
                    this.wait();
                }

                Debug.print(this, "getting in elevator.");

                // Randomly ask for a floor
                int desiredFloor;
                do {
                    desiredFloor = Floor.random();
                } while (desiredFloor == floor);

                floor = desiredFloor;


                //request = new Request(this, Message.GETOUT_AT_FLOOR, desiredFloor);
                //channel.send(request);
                elevator.stopForOnFloor(this, desiredFloor);

                Debug.print(this, "waiting to reach floor " + desiredFloor + "...");
                synchronized (this) {
                    this.wait();
                }

                Debug.print(this, "getting out of elevator.");

            }

        } catch (Exception e) {
        }

        Debug.print(this, "Max number of trips reached.");
    }

    public synchronized void exitElevator() {
        this.notifyAll();
    }

    public synchronized void enterElevator() {
        this.notifyAll();
    }

    public String toString() {
        return "#" + this.id;
    }
}