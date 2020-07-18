# Usage

Just give an expression to the application and it will be rolled:

```
java -jar dice.jar 1d6
```

The result will be printed on console:

```
------------
Total roll: 14
------------
```


## Rolls History

To get all the rolls:

```
java -jar dice.jar 3d6+1d12 -history
```

This will print something like this:

```
------------
Total roll: 19
------------
Roll history: [3, 5, 2] + 9
------------
```

A more detailed rolls history is available:

```
java -jar dice.jar 3d6 -fullHistory
```

```
------------
Total roll: 22
------------
Detailed roll history
Rolled 3d6 getting values [5, 4, 6] for a total of 15
Rolled 1d12 getting values [7] for a total of 7
------------
```

Both options can be combined if wished
