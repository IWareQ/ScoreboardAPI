package ru.jl1mbo.scoreboard;

import cn.nukkit.Player;
import lombok.Getter;
import ru.jl1mbo.scoreboard.line.ScoreboardLine;
import ru.jl1mbo.scoreboard.manager.ScoreboardManager;
import ru.jl1mbo.scoreboard.objective.ScoreboardObjective;
import ru.jl1mbo.scoreboard.packet.SetScorePacket;
import ru.jl1mbo.scoreboard.task.ScoreboardUpdater;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Getter
public class ScoreboardBuilder {

	private final Map<Integer, ScoreboardLine> scoreboardLines = new HashMap<>();
	private final ScoreboardUpdater scoreboardUpdater;
	private final Player player;
	private ScoreboardObjective scoreboardObjective;

	public ScoreboardBuilder(Player player) {
		this.scoreboardUpdater = new ScoreboardUpdater(this);
		this.player = player;
	}

	public ScoreboardBuilder setDisplayName(String displayName) {
		this.scoreboardObjective = new ScoreboardObjective(this.player, displayName);
		this.scoreboardObjective.setDisplayName(displayName);
		return this;
	}

	public ScoreboardBuilder setLine(int index, String text) {
		if (index >= 16 || index <= 0) {
			throw new IllegalArgumentException("Значение индекса должно быть > 0 и < 16");
		}
		ScoreboardLine scoreboardLine = this.getScoreboardLine(index);
		if (scoreboardLine == null) {
			this.scoreboardLines.put(index, new ScoreboardLine(this, index, text));
		} else {
			scoreboardLine.setText(text);
		}
		return this;
	}

	public ScoreboardBuilder removeLine(int index) {
		if (index >= 16 || index <= 0) {
			throw new IllegalArgumentException("Значение индекса должно быть > 0 и < 16");
		}
		ScoreboardLine scoreboardLine = this.getScoreboardLine(index);
		if (scoreboardLine != null) {
			scoreboardLine.hide();
			this.scoreboardLines.remove(index);
			scoreboardLine.show();
		}
		return this;
	}

	public ScoreboardBuilder addUpdater(Consumer<ScoreboardBuilder> scoreboardBuilder, int seconds) {
		this.scoreboardUpdater.addUpdater(scoreboardBuilder, seconds);
		return this;
	}

	public ScoreboardLine getScoreboardLine(int index) {
		return this.scoreboardLines.getOrDefault(index, null);
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
}