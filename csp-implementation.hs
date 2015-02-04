|--|    o
|  |.  -|-
|--|   / \
|  |.
|--|
|  |.
|--|
⎕ ⨅


3 destination-button
3 call-button
3 doors

1 elevator
n passengers

D_button :: *[
  P? floor  -> (
    wait()
    ⎕
    dest = floor;
    E! dest
  )
]

C_button :: *[
  P? floor  -> (
    wait()
    ⎕
    call = floor;
    E! call
  )
]

E :: *[
  openDoor() -> (
    f = getCurrentFloor();
    unload(f) -> load(f) ⎕ load(f) -> unload(f)
  ) -> closeDoor()
  ⎕
  C? floor -> add(floor) -> f = getBestFloor() -> goto(f)
  ⎕
  D? floor -> add(floor) -> f = getBestFloor() -> goto(f)
]

P :: *[
  C! currentFloor -> (
    waitForElevator()
    ⎕
    getIn() -> D! desiredFloor
    ⎕
    desiredFloor == currentFloor -> getOut()
  )
]