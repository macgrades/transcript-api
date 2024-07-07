# MacGrades transcript API

## Running locally
In Terminal: `& mvn azure-functions:run -f "{pom.xml path}"`

or 

In VSCode: `Maven -> Azure Java Function -> Plugins -> azure-functions -> run`

## Adding a new function
* Create function in `src\main\java\com\function\ritual17`
* `mvn package -DskipTests`