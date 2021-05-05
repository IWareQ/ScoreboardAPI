# How to use:

How to create a scoreboard:

```java
ScoreboardManager.createScoreboard()
.setDisplayName("randomName", "Scoreboard Name")
.setLine(1, "one line")
.setLine(2, "two line")
.setLine(15, "fifteen line")
.showFor(player);
```

How to get Scoreboard to player:

```java
ScoreboardManager.getScoreboard(player); //return ScoreboardBuilder
```

How to remove a line:

```java
ScoreboardManager.getScoreboard(player)
.removeLine(2); // remove "two line"
```

# TODO:
[] autoUpdater.
