package osproject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author Layan
 */
public class TrainStation {

    /**
     * @param args the command line arguments
     */
    static ArrayList<Thread> list=new ArrayList<>();
    static ArrayList <LinkedList> TrainList = new ArrayList<>();
    public static int NumberOfTrains;
    static int TrainNumber;
    static int ArrivalTime;
    private static final int POOL_SIZE = 1;
    private static final int QUEUE_SIZE = 10;
    //
    //static PriorityBlockingQueue<Thread> queue = new PriorityBlockingQueue<>();
    
    public static void main(String[] args) throws Exception {
        /*read file, store data, create threads "train", 
        send thread to Common Line depends on priority*/
        //Rahaf
        readInput();

        /* less number have higer praiority
         * if there are trains with same type, the one with earlier arrival time is assigned a higher priority.
         * in the case of tie, the higher priority is given to one train in random
        */ 
        System.out.println("\nThe trains are scheduled as follow. \n"
                + "In the common line: ");
        /*Priority Scheduling with different arrival time **non-preemptive**/
        PriorityThreadScheduler pts = new PriorityThreadScheduler(
        POOL_SIZE, QUEUE_SIZE);
        for(int i=0;i<NumberOfTrains;i++){ 
            list.get(i).setName(TrainList.get(i).get(1).toString());
            pts.scheduleJob(list.get(i));
        }
            
    }//End Main
    
    public static void readInput() throws FileNotFoundException, IOException, Exception{ 
        // Nada
        FileInputStream in = null;
        try {
         in = new FileInputStream("trains.txt");
         Scanner scanner = new Scanner(in);
         
          // get the first elemnt of the file (number of trains)
          NumberOfTrains = scanner.nextInt();
          //System.out.println("Number of trains: "+NumberOfTrains);
          
           while(scanner.hasNextLine()){
           TrainNumber = scanner.nextInt();
           ArrivalTime = scanner.nextInt();
           String Type = scanner.nextLine();
           
           LinkedList list = new LinkedList();
           list.add(TrainNumber);
           list.add(ArrivalTime);
           list.add(Type);
           
           TrainList.add(list);
       
           //System.out.println(list);
           }
            //System.out.println(TrainList);// to print train info
        } finally {
         if (in != null){
             in.close();
         }
        }
        //create threads "train"
    //ArrayList<Thread> list=new ArrayList<>();
    //ExecutorService es=Executors.newFixedThreadPool(1);
     
    for(int i=0;i<NumberOfTrains;i++){
        Train t= new Train(ArrivalTime,TrainNumber);//
        list.add(t);
        Train.Priority(TrainList.get(i).get(2),t,i);
        t.estimateTime(TrainList.get(i).get(2), i);
        t.setTrainNumber((int)TrainList.get(i).get(0));
        t.setArrivalTime((int)TrainList.get(i).get(1));
        t.PrintInformation(t);
    }
        System.out.println("\n===================================");
        //don't start them...... we must have Priority Scheduling
    }//end read
}//End TainStation
