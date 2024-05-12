package sbu.cs;

import java.util.ArrayList;
import java.util.List;

public class TaskScheduler
{
    public static class Task implements Runnable
    {
        String taskName;
        int processingTime;

        public Task(String taskName, int processingTime) {
            this.taskName = taskName;
            this.processingTime = processingTime;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(processingTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static ArrayList<String> doTasks(ArrayList<Task> tasks)
    {
        ArrayList<String> finishedTasks = new ArrayList<>();

        tasks.sort((t1, t2) -> t2.processingTime - t1.processingTime);

        for(Task task : tasks)
        {
            Thread thread = new Thread(task);
            thread.start();
            try{
                thread.join();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            finishedTasks.add(task.taskName);
        }

        return finishedTasks;
    }

    public static void main(String[] args) {

    }
}
