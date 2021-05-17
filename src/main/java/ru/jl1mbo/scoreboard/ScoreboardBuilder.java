package ru.jl1mbo.scoreboard;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import cn.nukkit.Player;
import ru.jl1mbo.scoreboard.line.ScoreboardLine;
import ru.jl1mbo.scoreboard.manager.ScoreboardManager;
import ru.jl1mbo.scoreboard.objective.ScoreboardObjective;
import ru.jl1mbo.scoreboard.packet.SetScorePacket;
import ru.jl1mbo.scoreboard.task.ScoreboardUpdater;

public class ScoreboardBuilder {

	private final Map<Integer, ScoreboardLine> scoreboardLines = new HashMap<>();
	private final ScoreboardUpdater scoreboardUpdater;
	private ScoreboardObjective scoreboardObjective;
	private final Player player;

	public ScoreboardBuilder(Player player) {
		this.scoreboardUpdater = new ScoreboardUpdater(this);
		this.player = player;
	}

	public ScoreboardBuilder setDisplayName(String displayName) {
		this.scoreboardObjective = new ScoreboardObjective(displayName, this.player);
		this.scoreboardObjective.setDisplayName(displayName);
		return this;
	}

	public ScoreboardObjective getScoreboardObjective() {
		return this.scoreboardObjective;
	}

	public ScoreboardBuilder setLine(int index, String text) {
		if (index >= 16 || index <= 0) {
			throw new IllegalArgumentException("Значение индекса должно быть > 0 и < 16");
		}
		ScoreboardLine scoreboardLine = this.getLine(index);
		if (scoreboardLine == null) {
			this.scoreboardLines.put(index, new ScoreboardLine(index, text, this));
		} else {
			scoreboardLine.setText(text);
		}
		return this;
	}

	public ScoreboardBuilder removeLine(int index) {
		if (index >= 16 || index <= 0) {
			throw new IllegalArgumentException("Значение индекса должно быть > 0 и < 16");
		}
		ScoreboardLine scoreboardLine = this.getLine(index);
		if (scoreboardLine != null) {
			scoreboardLine.hideLine();
			this.scoreboardLines.remove(index);
			scoreboardLine.showLine();
		}
		return this;
	}

	public ScoreboardBuilder addUpdater(Consumer<ScoreboardBuilder> scoreboardBuilder, int seconds) {
		this.scoreboardUpdater.addUpdater(scoreboardBuilder, seconds);
		return this;
	}

	public ScoreboardUpdater getScoreboardUpdater() {
		return this.scoreboardUpdater;
	}

	public ScoreboardLine getLine(int index) {
		return this.scoreboardLines.getOrDefault(index, null);
	}

	public Map<Integer, ScoreboardLine> getLines() {
		return this.scoreboardLines;
	}

	public void show() {
		ScoreboardBuilder scoreboardBuilder = ScoreboardManager.getScoreboard(player);
		if (scoreboardBuilder == null) {
			this.scoreboardObjective.create();
			for (ScoreboardLine scoreboardLine : this.scoreboardLines.values()) {
				player.dataPacket(scoreboardLine.getSetScorePacket(SetScorePacket.TYPE_CHANGE));
			}
			ScoreboardManager.putScoreboard(this.player, this);
			this.scoreboardUpdater.start();
		}
	}

	public void hide() {
		this.scoreboardObjective.remove();
		ScoreboardManager.removeScoreboard(this.player);
		this.scoreboardUpdater.stop();
	}

	public Player getPlayer() {
		return this.player;
	}
}