### Introduction to design and implementation
* Tried to follow SOLID design principle.
* Programmed to interfaces mostly.
* Tried to keep it simple but have possibility of extension.
* Put as much validation as I have domain knowledge. Mostly around error handling.
* `Parser` and `Filter` are abstract concepts and serve as an API. (For example can be implemented for parsing JSON files)

#### Assumptions
* Only one filter could be applied.
* Order of CSV columns are not important.

#### Trade-offs
* Hardcoded XPath expressions for simplicity. They could be encapsulated through interface to better conform to open-close principle.

### How to execute
* Checkout the code.
```shell
git clone git@github.com:xafarr/TradeReportingEngine.git
```
* Change directory to project folder
```shell
cd TradeReportingEngine
```
* Execute following in shell
```shell
./mvnw compile && ./mvnw exec:java -Dexec.mainClass="com.xafarr.Main"
# To view the result
cat event.csv
```