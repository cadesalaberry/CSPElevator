`CSPElevator`
=============

We saw the basic operators in CSP. Here you need to define a non-trivial system based on CSP,
and then figure out how to implement it in Java.

The system describes a simple elevator that takes passengers in a 3 floor context. We will need (at least) `10 + n` process definitions: an elevator, three call-buttons, three destination-buttons, three doors to the elevator, and `n` passengers.

The elevator and passengers start at random floors. The latter then (repeatedly) take the elevator to different, randomly selected floors. Each floor has a button and a door to the elevator shaft. A passenger presses the call-button for their floor, which summons the elevator. When the elevator arrives at a floor, it signals the door to open, waits for the passengers who want to go to that floor to leave and for new passengers to enter and select a destination floor by pressing the appropriate destination-button. Once all appropriate passengers have left and everyone who called the elevator is on board with destinations selected, the door closes, and the elevator moves to another floor.

Note the following:
- Whether there are multiple passengers on the same floor or not, each passenger wanting to travel will press the call-button and individually select their destination.
- A passenger waiting on a floor may press the corresponding call-button at any time; if they do so before the elevator door closes on that floor they should be guaranteed to get on the elevator.
Passengers who just exited the elevator, however, must wait until the elevator has left the floor before pressing the call-button.
- It should be possible for passengers to enter the elevator prior to all passengers leaving, and for passengers to leave the elevator prior to all passengers entering.
- You must guarantee all passengers eventually get to their destinations. Once in the elevator a passenger stays until they get to their destination floor.
- Invalid states must be avoided: passengers should never fall into the elevator shaft (enter or leave the elevator before/after it arrives at a floor), get off at the wrong floor, attempt to walk through a closed door, etc.
- The processes described are required, but you may also create other process definitions if you find it helpful.

# Exercise

As as separate document, give CSP code for all processes, assuming an infinite simulation. You may use parametrized process definitions to avoid repeating similar definitions, and you may use recursive definitions instead of iteration if you prefer. Your design should allow for an arbitrary number of passengers. Note that the final system would be defined as the parallel composition of all your processes and `n` instances of your passenger definition.

[Check this out !](./csp-implementation.hs)

The system is implemented in Java. Attempting to be a reasonably literal implementation of the CSP code. That is, you should come up with a general implementation of every CSP operator you use, and then launch threads as your processes. The run methods of these threads then invoke your operators as per their individual process definitions. Your program should accept two command-line arguments:

```bash
java ElevatorSimulation n t
```

and then run with `n ≥ 0` passenger processes, until each each passenger has travelled in the elevator `t ≥ 0` times. Each process should print a suitable message to the console each time it makes a significant state change; for example, whenever the elevator arrives/departs a floor, when the doors are opened/closed, as passengers enter/leave or press buttons. After printing a message, a thread should sleep for `50-300ms` before continuing.