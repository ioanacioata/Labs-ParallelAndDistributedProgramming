# Laboratories for Parallel and Distributed Programming Course 

### Lab 1 - "Non-cooperative" multithreading  (Java)
  Due: week 4.  
  Bank Accounts problem: http://www.cs.ubbcluj.ro/~rlupsa/edu/pdp/lab-1-noncooperative-mt.html

### Lab 2 - Parallelizing into fully independent sub-tasks (Java)
  Due: week 4.  
  http://www.cs.ubbcluj.ro/~rlupsa/edu/pdp/lab-2-parallel-simple.html  
  Divide a simple task between threads. The task can easily be divided in sub-tasks requiring no cooperation at all. See the effects of false sharing, and the costs of creating threads and of switching between threads.  
  *Requirement*: write two problems: one for computing the sum of two matrices, the other for computing the product of two matrices.  
   Divide the task between a configured number of threads (going from 1 to the number of elements in the resulting matrix). See the effects on the execution time.

### Lab 3 - Advanced launching of parallel tasks (Java and C++)
  Due: week 5.  
  http://www.cs.ubbcluj.ro/~rlupsa/edu/pdp/lab-3-async.html  
1. Thread pool, in C++.
2. Thread pool, in C# or Java.
3. std::async() and std::future(), in C++.
4. Tasks and futures in C# or Java.

### Lab 4 - Complex synchronization (Java)
  Due: week 6.  
  http://www.cs.ubbcluj.ro/~rlupsa/edu/pdp/lab-4-complex-sync.html

### Lab 5 - Futures and continuations (C#)
  Due: week 7.  
  http://www.cs.ubbcluj.ro/~rlupsa/edu/pdp/lab-5-futures-continuations.html  
Write a program that is capable of simultaneously downloading several files through HTTP. Use directly the BeginConnect()/EndConnect(), BeginSend()/EndSend() and BeginReceive()/EndReceive() Socket functions, and write a simple parser for the HTTP protocol (it should be able only to get the header lines and to understand the Content-lenght: header line).  
  **Try three implementations:**
1. Directly implement the parser on the callbacks (event-driven);
2. Wrap the connect/send/receive operations in tasks, with the callback setting the result of the task;
3. Like the previous, but also use the async/await mechanism.

### Lab 6 - Parallelizing techniques
  Due: week 8.  
  http://www.cs.ubbcluj.ro/~rlupsa/edu/pdp/lab-6-parallel-algo.html

### Lab 7 - Parallelizing techniques (2)
  Due: week 9.  
  http://www.cs.ubbcluj.ro/~rlupsa/edu/pdp/lab-7-parallel-algo-2.html

### Lab 8 - MPI programming 
  Due: week 12.  
  http://www.cs.ubbcluj.ro/~rlupsa/edu/pdp/lab-8-mpi.html

### Lab 9 - Distributed protocols 
  Due: week 14.  
  http://www.cs.ubbcluj.ro/~rlupsa/edu/pdp/lab-9-distributed.html
