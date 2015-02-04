package main;


public class Request {
    public Passenger from;
    public Message message;
    public int floor;

    public Request(Passenger from, Message message, int floor) {
        this.message = message;
        this.floor = floor;
        this.from = from;
    }
}