How to create Scoreboard | Как создать Scoreboard:
----------------
```java
ScoreboardManager.createScoreboard()
.setDisplayName("test scoreboard", "Scoreboard name")
.setLine(1, "one line | первая линия")
.setLine(2, "two line | вторая линия")
.setLine(15, "fifteen line | пятнадцатая линия")
.showFor(player);
```
----------------

How to create an automatically updated Scoreboard | Как создать автоматически обновляемый Scoreboard:
----------------
```java
ScoreboardManager.createScoreboard()
.setDisplayName("test scoreboard", "Scoreboard name")
.setLine(1, "one line | первая линия")
.setLine(2, "two line | вторая линия")
.addUpdater(scoreboard -> {
	scoreboard.setLine(2, "Number | Число: " + new Random().nextInt(100));
}, 20) //time to write in ticks | время писать в тиках
.setLine(15, "fifteen line | пятнадцатая линия")
.showFor(player);
```
----------------

How to get a Player Scoreboard | Как получить Scoreboard игрока:
----------------
```java
ScoreboardManager.getScoreboard(player); //return ScoreboardBuilder
```
----------------

How to delete a line | Как удалить строку:
----------------
```java
ScoreboardManager.getScoreboard(player)
.removeLine(2); //will remove the second line | удалит вторую линию 
```
----------------
