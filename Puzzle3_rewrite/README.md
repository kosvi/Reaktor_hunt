# Puzzle 3 - rewrite

## Plan

**(default package)** *bad habit*

class Main
- create Scanner and other required objects
- starts cli

**package ui**

class Cli
- User-interface

**logic**

class Coordinate
- Simple coordinate used to store coordinates in our grid

class Grid
- This grid contains the whole map, it creates it and stores it

class Step
- Single step in our steps list

class StepList
- Basically just an arraylist, but just doesn't allow duplicates and in case of a duplicate, it keeps the one with smaller distance

class Pathfinder
- Finds the path from start to finish. Uses steplist to collect possible moves and to find the shorters path

**config**

class Characters
- static finals with all the characters used in program

