# Dice Notation Tools for Java CLI

CLI for running the [Dice Notation Tools][dice-notation-tools] through line command.

To run the project first package it:

```
mvn clean package
```

Afterwards a runnable jar will be in the target folder. It can be run like this:

```
java -jar target/dice.jar roll 1d6
```

To show other commands:

```
java -jar target/dice.jar -h
```

[dice-notation-tools]: https://github.com/Bernardo-MG/dice-notation-java
