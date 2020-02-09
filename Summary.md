# Regular expressions Exercises

### Question 1
This case is pretty straightforward. I decided to iterate over pattern list to apply them sequentially, 
storing all the matches in order to return them at the end. 

### Question 2
In this case, we also have a list of patterns, but if I match a substring with one pattern, then that substring cannot 
be used in subsequent patterns.
To satisfy this restriction, every time a pattern is applied, matched substrings are removed from the text to evaluate

### Question 3
This case is similar to the first one, but we also receive a blacklist pattern. No matter what the patterns match, if it
is blacklisted, can't be matched. Regarding this, I apply the blacklist pattern first, in order to remove those matches  
from the text to evaluate. Then all the patterns are applied sequentially. 

### Question 4
I made a function that retrieves https://github.com/mitre/cti/enterprise-attack/intrusion-set. It uses Apache HttpClient 
to get it as a String. Then, apply a pattern to get all the file names. Once I get the list of fileNames, 
I iterate over it to get vía http the json files as plain text. The info from those files is evaluated with some patterns
to get only those ones that are APT descriptions. Info obtained is: Name, Aliases an URLs.



### Question 5
Potential bottlenecks: 
For questions 1 to 3, the size of the text to evaluate and the number of patterns are the most significant variables in 
terms of performance degradation. I think the way to improve it is to parallelize. For instance, in questions 1 and 3, 
the patterns could be applied in parallel. But that is not the case of question 2, because subsequent patterns can be 
affected by the result of the preceding patterns.

About APT extractions, there is a major issue with HTTP requests. One solution could be split the processing of those 
files in several jobs. The first one could be a job to keep updated files in CTI repo. 
This process could have a database to store information of the files, like last_modification_date. 
Maybe it could download to disk the files so they will be available for the next step. This would save a lot of time by 
reducing extra downloads. A second job should read those files, process them and store results in a shared database.
As each file is independent of the others, the second job can run for each file in parallel. 

#### Architecture

I think an architecture that can handle a huge volume of calls should have components that can be scaled easily. 
It should have specific and compact components to avoid long times accumulated at one. First of all, it should have an 
entry point, where the system will receive all the requests and delegate them to the workers. The number of workers 
should be flexible and able to scale up or down if necessary (in the ideal case). If the request is asynchronous, an 
event-driven architecture could be a good fit. A publish-subscribe pattern allows us to parallelize message processing 
by defining several subscribers.  If there is a group of stages in the extraction process, this model gives us the 
ability to chain multiple processing stages with different queues and databases. For synchronous requests, I think that 
all the components should be as atomic as possible to minimize the probability of sequential phases.

