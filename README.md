# Regex library



## How to use

### Run the tests
To run the unit tests run the following command

`./mvnw test`

### Build jar 

To build the jar just run the following command 

`./mvnw package`


### Add jar as maven dependency to your project

    <dependency>
        <groupId>com.trustar</groupId>
        <artifactId>lisandor-interview</artifactId>
        <version>0.0.1</version>
        <scope>provided</scope>
    <dependency>

### APT extract
To use the APT extraction feature, import  `com.trustar.interview.q4.APTExtractor`  and then call the method `extract`.
This method returns a List of APTInfo with the name, aliases and related urls found in Mitre CTI github repo.
    
### Summary

There is a file where I left the assumptions made for every question. The file is `Summary.md` 