# ticket-reservation-service

## Assumptions

- The persistence can be handled separately from the Dao layer . This implementation does not use any kind of persistence mechanism except stores the data 
   in memory
- 

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

gradle cleanTest test --tests com.walmart.tech.service.TicketServiceImplTest

```