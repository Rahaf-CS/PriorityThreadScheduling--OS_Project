package osproject;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import static osproject.TrainStation.TrainList;

/**
 *
 * @author Layan
 */
public class Train extends Thread{
    
    String Type;
    int TrainNumber,burstTime, startTime, l ,ArrivalTime;
    static int priority, s=0 , waitTime, turnaroundTime, counter;
    private static volatile boolean value;

    Train(int ArrivalTime, int TrainNumber){
        this.ArrivalTime = ArrivalTime;
        this.TrainNumber = TrainNumber;
    }

    public int getTrainNumber() {
        return TrainNumber;
    }

    public void setTrainNumber(int TrainNumber) {
        this.TrainNumber = TrainNumber;
    }
    public int getArrivalTime() {
        return ArrivalTime;
    }

    public void setArrivalTime(int ArrivalTime) {
        this.ArrivalTime = ArrivalTime;
    }
    public int add(int value){
        synchronized(this){
            this.startTime = startTime + value;
        }
        return startTime;
    }
    @Override
    public void run(){
       try {
        /*common line a line for the trains to enter and exit...*/
        //1 priority schedulling, 2 update start time
        System.out.printf("Time %d: train %d enters the line\n", s, TrainNumber);
        Thread.sleep(burstTime);
        //yield();
        System.out.printf("Time %d: train %d leaves the line\n", (s+burstTime), TrainNumber);
        
        WaitingTime();
        TurnaroundTime();
        s = add(s+burstTime);
        //print average waiting time and average turnaround time
        counter();
        if(counter == TrainStation.NumberOfTrains){
            System.out.println("\n===================================\n");
            int avgTT = Train.getTurnaround()/ TrainStation.NumberOfTrains;
            int avgWT = Train.getWaiting()/ TrainStation.NumberOfTrains;
            System.out.printf("Average waiting time for all trains is: %d minutes\n"
                    + "Average turnaround time for all trains is: %d minutes\n", avgWT, avgTT);
        }
        
        } catch (InterruptedException ex) {
            Logger.getLogger(Train.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void PrintInformation(Thread t)throws Exception{
        System.out.printf("Thread id %d : %d %d %d %d \n", t.getId(),
                getTrainNumber() , getArrivalTime() , t.getPriority() , burstTime );
     }
    
   /**calculate the priority of the train,
         *train type
         *passenger train-->Priority=1
         *train of goods (full)-->Priority=2
         *train of goods (empty)-->Priority=3
        ##less number--> higher priority
     * @param train
     * @param t
     * @param index*/ 
   public static void Priority(Object train,Thread t,int index){
         String TrainType=(String)train;
         
        if(null != TrainType)
            switch (TrainType) {
            case " passenger":
              t.setPriority(1);
                break;
            case " fullgoods":  
                t.setPriority(2);
                break;
            case " emptygoods":
                t.setPriority(3);
                break;
            default:
                break;
        }
       TrainList.get(index).add(3, priority);
    }
    
    /*the time a train takes it in the common line 
        is decided based on the train's type.
        * passenger train is given a time ranging from 20-30 minutes 
        * train of goods (full) from 40-50 minutes 
        * train of goods (empty) from 30-40 minutes.*/
    public void estimateTime(Object get, int index) {//4
        String trainType = (String) get;
        Random r = new Random();
        if(null != trainType)switch (trainType) {
            case " passenger": burstTime = 20 + r.nextInt(10);
                break;
            case " fullgoods": burstTime = 40 + r.nextInt(10);
                break;
            case " emptygoods": burstTime = 30 + r.nextInt(10);
                break;
            default:
                break;
        }
        TrainList.get(index).add(4, burstTime);//add as 4th element 
    }
    public void WaitingTime(){ //wt = start time - arrival time
        synchronized(this){
            this.waitTime = waitTime + (s - ArrivalTime);
        }  
    }
    public void TurnaroundTime(){ //TT = Completion - AT
        synchronized(this){
            this.turnaroundTime = turnaroundTime + ((s+burstTime) - ArrivalTime);
        }    
    }
    public static int getWaiting(){
        return waitTime;
    }
    public static int getTurnaround(){
        return turnaroundTime;
    }
    public void counter(){//to calculate the average wt and tt
        synchronized(this){
            this.counter = counter+1;
        }    
    }
    public static int getCount(){
        return counter;
    }

}//End Train
