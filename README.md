Как создать Scoreboard:
----------------
```java
ScoreboardManager.newBuilder(player)
.setDisplayName("Название scoreboard")
.setLine(1, "первая линия")
.setLine(2, "вторая линия")
.setLine(15, "пятнадцатая линия")
.show();
```
----------------

Как создать автоматически обновляемый Scoreboard:
----------------
```java
ScoreboardManager.newBuilder(player)
.setDisplayName("Название scoreboard")
.setLine(1, "первая линия")
.setLine(2, "вторая линия")
.addUpdater(scoreboard -> {
	scoreboard.setLine(3, "Число: " + new Random().nextInt(100));
}, 1) //время писать в секундах
.setLine(15, "пятнадцатая линия")
.show();
```
----------------

Как получить Scoreboard игрока:
----------------
```java
ScoreboardManager.getScoreboard(player);
```
----------------