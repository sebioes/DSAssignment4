
package com.assignment4.tasks;


public class LamportTimestamp {
    private int timestamp;
    public LamportTimestamp(int time){

        timestamp = time;
    }
    public void tick(){

        // update the timestamp by 1
        timestamp++;
    }
    public int getCurrentTimestamp(){

        return timestamp;
    }
    public void updateClock(int receivedTimestamp){

        if (receivedTimestamp > timestamp) timestamp = receivedTimestamp + 1;

        //timestamp = receivedTimestamp; // update the function to choose max out of the two received timestamps
    }

}
