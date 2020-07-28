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


## Possible future improvements

* Unit tests for the parsing logic (use static html resource)
* Provide a command line interface with descriptions of command (e.g. via `commons cli`) 
* Distribute logic of extracting javascript libraries between a pool of threads (division by url)
* Make number of returned libraries customizable (e.g. return top n used libraries, where n is given in the input)
* Add a retry mechanism in case of network issues
* Improve extraction of javascript library names (to avoid repetition, and inclusion of local only libs)
* Sanitize input. For example input param starting with '&' would always return empty list (e.g. `'&munich'`)
* Look into possible improvements for the search result page. Currently retrival is partly
dependant on css classes. Look into whether they can be replaced with ids.