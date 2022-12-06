# ScoreboardAPI [![](https://jitpack.io/v/IWareQ/ScoreboardAPI.svg)](https://jitpack.io/#IWareQ/ScoreboardAPI)

ScoreboardAPI is a simple library plugin for PowerNukkit/Nukkit Minecraft Bedrock core, that will help you to create
your custom scoreboards with ease.

##### [Download](https://github.com/IWareQ/ScoreboardAPI/releases)

## Usage

```java
Scoreboard scoreboard = new Scoreboard("Test Scoreboard", DisplaySlot.SIDEBAR, 20); // update time in ticks (20 ticks = 1 second)
scoreboard.setHandler(pl -> {
    scoreboard.addLine(pl.getName());
    scoreboard.addLine("§1"); // used for skip line
    scoreboard.addLine("random: " + Math.random());
    scoreboard.addLine("random: " + Math.random());
    scoreboard.addLine("random: " + Math.random());
    scoreboard.addLine("random: " + Math.random());
    scoreboard.addLine("random: " + Math.random());
    scoreboard.addLine("§2"); // used for skip line
    scoreboard.addLine("Online: " + Server.getInstance().getOnlinePlayers().size());
});

scoreboard.show(player);
```

## Maven

#### Repository

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

#### Dependency
```xml
<dependency>
    <groupId>com.github.IWareQ</groupId>
    <artifactId>ScoreboardAPI</artifactId>
    <version>Tag</version>
</dependency>
```