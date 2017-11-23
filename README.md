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
  Perform the multiplication of 2 polynomials. Use both the regular O(n2) algorithm and the Karatsuba algorithm, and each in both the sequencial form and a parallelized form. Compare the 4 variants.

### Lab 7 - Parallelizing techniques (2)
  Due: week 9.    
  http://www.cs.ubbcluj.ro/~rlupsa/edu/pdp/lab-7-parallel-algo-2.html  
  Solve both problems below:  

1. Given a sequence of n numbers, compute the sums of the first k numbers, for each k between 1 and n. Parallelize the computations, to optimize for low latency on a large number of processors. Use at most 2*n additions, but no more than 2*log(n) additions on each computation path from inputs to an output. Example: if the input sequence is 1 5 2 4, then the output should be 1 6 8 12.

2. Add n big numbers. We want the result to be obtained digit by digit, starting with the least significant one, and as soon as possible. For this reason, you should use n-1 threads, each adding two numbers. Each thread should pass the result to the next thread. Arrange the threads in a binary tree. Each thread should pass the sum to the next thread through a queue, digit by digit.

### Lab 8 - Parallelizing techniques (3 - parallel explore)
  Due: week 10.  
  http://www.cs.ubbcluj.ro/~rlupsa/edu/pdp/lab-8-parallel-algo-3.html  
  Given a directed graph, find a Hamiltonean cycle, if one exists. Use multiple threads to parallelize the search.
  
### Lab 9 — OpenCL programming
  Due: week 12?.    
http://www.cs.ubbcluj.ro/~rlupsa/edu/pdp/lab-9-opencl.html

### Lab 9 - Distributed protocols 
  Due: week 14.    
  http://www.cs.ubbcluj.ro/~rlupsa/edu/pdp/lab-9-distributed.html  
  Implement a simple distributed shared memory (DSM) mechanism. The lab shall have a main program and a DSM library. There shall be a predefined number of communicating processes. The DSM mechanism shall provide a predefined number of integer variables residing on each of the processes. The DSM shall provide the following operations:

1. write a value to a variable (local or residing in another process);   
2. a callback informing the main program that a variable managed by the DSM has changed. All processes shall receive the same sequence of data change callbacks; it is not allowed that process P sees first a change on a variable A and then a change on a variable B, while another process Q sees the change on B first and the change on A second.  
3. a "compare and exchange" operation, that compares a variable with a given value and, if equal, it sets the variable to another given value. Be careful at the interaction with the previous requirement.
