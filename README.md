# js-library-analyzer

### Prerequisites:
1. Maven 3
2. Java 8+

## Running the tool

From the root directory run:

`mvn clean install`

and then run the following command, forwarding the search term in the run arguments
for example:

`mvn exec:java -Dexec.mainClass="de.scalable.capital.Main" -Dexec.args="cinema"`
