package main;

public class LobbyBoy extends Thread {

    Elevator elevator;
    SyncChannel channel;

    public LobbyBoy(Elevator e, SyncChannel passengerChannel) {
        this.elevator = e;
        this.channel = passengerChannel;
    }

    public void processRequest(Request rq) {

        switch (rq.message) {
            case CALLING_ELEVATOR:
                elevator.calledByOnFloor(rq.from, rq.floor);
                break;
            case GETOUT_AT_FLOOR:
                elevator.stopForOnFloor(rq.from, rq.floor);
                break;
            default:
                Debug.print(this, "Unrecognized message.");
        }
    }

    public void run() {

        while (true) {
            try {
                // Wait for everything to be set up.
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
                //e.printStackTrace();
            }
        }
    }
}
