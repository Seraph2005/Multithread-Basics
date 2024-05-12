# MultiThread Basics
### Theoretical Questions
1- In first code, thread will be interrupted before being executed and two messages will be printed. First "Thread was interupted!" because we haven't used join method and main thread won't wait for new thread, so the thread is actually interrupted...
And then "Thread will be finished here!!!" because the finally section always executes.

2- Here, the runnable method will be executed in the main thread, so the program will output "Running in: main", just like we haven't made any extra thread.

3- Since we have used the join method, thread-0 won't be interrupted, the message "Running in: Thread-0" will be shown and after executing, program will go back to main thread, so when we print the thread's name, the output is "main".
