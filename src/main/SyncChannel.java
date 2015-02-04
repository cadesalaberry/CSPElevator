package main;

public class SyncChannel {
    private Request msg;

    public synchronized void send(Request o) {
        msg = o;
        notify();
        while (msg != null) {
            try {
                wait();  // not needed for async
            } catch (Exception e) {
                Debug.print(this, "Error: Problem waiting when sending.");
            }
        }
    }

    public synchronized Request receive() {
        while (msg == null)
            try {
                wait();
            } catch (Exception e) {
                Debug.print(this, "Error: Problem waiting when receiving.");
            }
        Request ret = msg;
        msg = null; // Indicate it has been consumed
        notify();  // not needed for async
        return ret;
    }
}
