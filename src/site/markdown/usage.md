# Usage

## Rolling Expressions

```
java -jar dice.jar roll 1d6
```

The result will be printed on console:

```
------------
Total roll: 14
------------
```


### Rolls History

To get all the rolls:

```
java -jar dice.jar roll 3d6+1d12 -history
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
java -jar dice.jar roll 3d6+1d12 -fullHistory
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

## Gathering Dice

```
java -jar dice.jar gather gather 3d6+2+1d12
```

The result will be printed on console:

```
------------
Found 2 dice sets
------------
3d6, 1d12
------------
```
