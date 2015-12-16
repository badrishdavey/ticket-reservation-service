# ticket-reservation-service

## Assumptions / Design

- The persistence can be handled separately from the Dao layer . This implementation does not use any kind of persistence mechanism except stores the data 
   in memory
- Handling some level of Concurrency using Synchronized Set and Synchronized List
- using TreeSet to store the Seats for better read and writes latency
- Using a design pattern to basically de-couple the data extraction logic from the service logic
- Using TestNG such that it can support multi threading testing out-of-box using annotations



## Stack


- Java 1.8
- Gradle 2.4
- TestNG




## Compile

```
$ gradle compileJava compileTestJava

```


## Build

```
$ gradle build

```

## Run a particular Test Case

```

gradle cleanTest test --tests *TicketServiceImplTest

```


