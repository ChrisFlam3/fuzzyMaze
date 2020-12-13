# fuzzyMaze
Vehicle simulation with fuzzy logic in randomly generated maze

# How does it work?
It generates random maze using Prim's minimum spanning tree algorithm. For graphics-javaFX is used. Vehicle movement is simulated using 3 dynamic sensors readout(front, left, right). These distances are inputs for fuzzy deduction in jFuzzy which drives the vehicle movement. Simulation is completly dynamic and distances are measured in real time using very fast trigonometry based calculations so you can think of it as analytical solution for this specific conditions.

# Rules
- Vehicle starts from top left cell near upper wall with initial velocity vector v=[-1,0]
- Vehicle is following right side wall of the maze relative to the initial position and velocity
- Vehicle can not collide nor move within walls
- Vehicle has to explore entire maze within single loop

# Visualization
![](https://github.com/ChrisFlam3/fuzzyMaze/blob/master/maze.gif?raw=true)

# Implementation details
Java version: 11  
Dependencies: JavaFX, JFuzzy  
Owner: Christopher Misan  
