# ScoreboardAPI [![](https://jitpack.io/v/IWareQ/ScoreboardAPI.svg)](https://jitpack.io/#IWareQ/ScoreboardAPI)

ScoreboardAPI is a simple library plugin for PowerNukkit/Nukkit Minecraft Bedrock core, that will help you to create
your custom scoreboards with ease.

##### [Download](https://github.com/IWareQ/ScoreboardAPI/releases)

## Usage

```java
Scoreboard scoreboard = new Scoreboard("Test Scoreboard", (sb, player) -> {
    sb.addLine(player.getName());
    sb.addLine("§1"); // used for skip line
    sb.addLine("random: " + Math.random());
    sb.addLine("random: " + Math.random());
    sb.addLine("random: " + Math.random());
    sb.addLine("random: " + Math.random());
    sb.addLine("random: " + Math.random());
    sb.addLine("§2"); // used for skip line
    sb.addLine("Online: " + Server.getInstance().getOnlinePlayers().size());
}, 2); // in seconds

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