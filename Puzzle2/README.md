**src/tools/SignalMapper.java**

That HashMap is total carbage and probably just a waste of memory. I would have done it totally different and it requires some refactoring as it is now. 

Better solution in my opinion would be to create adequate .equals for the SignalPiece and just use ArrayList and either add new SignalPieces or upgrade existing. Now that HashMap is there for no reason what so ever. 
