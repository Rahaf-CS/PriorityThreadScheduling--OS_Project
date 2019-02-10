# PriorityThreadScheduling--OS_Project
PriorityThreadScheduling-OS_Project 2018
Problem description: A train station decides to reduce the human resource by automating trains scheduling. The train station receives trains that have different levels of importance. Firstly, trains must enter to the check point. In this train station, there is one check point serving trains. As a result, only one train can enter to the check point at a time even if several trains arrived at the same time to the station. At the check point, the station's workers determine the priority of the entered train. Moreover, they estimate the time that the train will take it in the common line. Usually the priority of the train is given depending on their types [passenger train, train of goods (full), and train of goods (empty)]. Obviously, if there are trains with same type, the one with earlier arrival time is assigned a higher priority. And in the case of tie, the higher priority is given to one train in random. Similarly, the time a train takes it in the common line is decided based on the train's type.

Programs: in java using NetBeans 8.1

Train Station Class: reads the input file and starts the execution, creates object of PriorityThreadScheduler class and sends the Array List of Threads to scheduleJob() method in the PriorityThreadScheduler class.

Train Class: extends Thread, we have Priority() method that set the priority of each thread, also estimateTime() method that set the time that will be taken by each thread, and PrintInformation() method that print all threads information, but most importantly we have the run method. we used synchronized to update the time and to calculate the average waiting time and the average turnaround time.

Priority Thread Scheduler Class: the threads are scheduled according to priority using Comparator and PriorityBlockingQueue.

And a text file to test!

There are definetly some room for improvment. *

By:  Rahaf, Layan, Nada, and Sara
