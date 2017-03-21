# Java JPA with Oracle Coherence Demo

This project demonstrates how to use Oracle Coherence. It show how you can make a jsf employee search page on the coherence cache which gets its data from a JPA datasource. Coherence runs above JPA. In this project also will update the cache entries with a salary raise and coherence will take care to update the right records in the employee table.

## Oracle Coherence

Oracle Coherence is the industry leading in-memory data grid solution that enables organizations to predictably scale mission-critical applications by providing fast access to frequently used data. As data volumes and customer expectations increase, driven by the “internet of things”, social, mobile, cloud and always-connected devices, so does the need to handle more data in real-time, offload over-burdened shared data services and provide availability guarantees.

Oracle Coherence is also a distributed data grid solution for clustered applications and application servers. It is a very easy to handle. It does everything automatically like backing up the cache on different nodes, distributing the load and recovering from cache servers or adding new coherence servers to the cluster.

More information:
- [Java Coherence](https://coherence.java.net/)
- [Oracle Coherence](http://www.oracle.com/technetwork/middleware/coherence/documentation/index.html)
- [Oracle Coherence First Application](https://docs.oracle.com/cd/E15357_01/coh.360/e15723/gs_example.htm#COHDG5039)

## Requirements

This project requires that you have [Java Development Kit 8][jdk8], [Oracle Coherence][oraclecoherence] and [JDeveloper][jdeveloper] installed on
your system.

[jdk8]: http://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html
[jdeveloper]: http://www.oracle.com/technetwork/developer-tools/jdev/downloads/index.html
[oraclecoherence]: http://www.oracle.com/technetwork/middleware/coherence/downloads/index.html