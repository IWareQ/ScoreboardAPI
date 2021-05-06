package ru.jl1mbo.scoreboard;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import cn.nukkit.Player;
import cn.nukkit.network.protocol.DataPacket;
import ru.jl1mbo.scoreboard.line.ScoreboardLine;
import ru.jl1mbo.scoreboard.manager.ScoreboardManager;
import ru.jl1mbo.scoreboard.objective.ScoreboardObjective;
import ru.jl1mbo.scoreboard.packet.SetScorePacket;
import ru.jl1mbo.scoreboard.updater.ScoreboardUpdater;
import ru.jl1mbo.scoreboard.utils.DisplaySlot;
import ru.jl1mbo.scoreboard.utils.SortOrder;

public class ScoreboardBuilder {

	private final Map<Integer, ScoreboardLine> scoreboardLines = new HashMap<>();
	private final Set<Player> players = new HashSet<>();
	private ScoreboardObjective scoreboardObjective;
	private ScoreboardUpdater scoreboardUpdater;

	public ScoreboardBuilder() {
		this.scoreboardUpdater = new ScoreboardUpdater(this);
	}

	public Set<Player> getPlayers() {
		return this.players;
	}

	public ScoreboardUpdater getScoreboardUpdater() {
		return this.scoreboardUpdater;
	}

	public void broadcastPacket(DataPacket dataPacket) {
		for (Player player : this.players) {
			player.dataPacket(dataPacket);
		}
	}

	public ScoreboardBuilder setDisplayName(String objectiveName, String displayName) {
		this.setDisplayName(DisplaySlot.SIDEBAR, objectiveName, displayName, SortOrder.ASCENDING);
		return this;
	}

	public ScoreboardBuilder setDisplayName(DisplaySlot displaySlot, String objectiveName, String displayName) {
		this.setDisplayName(displaySlot, objectiveName, displayName, SortOrder.ASCENDING);
		return this;
	}

	public ScoreboardBuilder setDisplayName(DisplaySlot displaySlot, String objectiveName, String displayName, SortOrder sortOrder) {
		this.scoreboardObjective = new ScoreboardObjective(objectiveName, displayName, this);
		this.scoreboardObjective.setDisplayName(displaySlot, objectiveName, displayName, sortOrder);
		return this;
	}

	public ScoreboardObjective getObjective() {
		return this.scoreboardObjective;
	}

	public ScoreboardBuilder setLine(int index, String text) {
		if (index >= 16 || index <= 0) {
			throw new IllegalArgumentException("Index value must be > 0 and < 16. \n Значение индекса должно быть > 0 и < 16.");
		}
		ScoreboardLine scoreboardLine = this.getLine(index);

		if (scoreboardLine == null) {
			this.scoreboardLines.put(index, new ScoreboardLine(index, text, this));
		} else {
			scoreboardLine.setText(text);
			this.scoreboardLines.put(index, scoreboardLine);
			scoreboardLine.hideLine();
			scoreboardLine.showLine();
		}
		return this;
	}

	public ScoreboardBuilder removeLine(int index) {
		if (index >= 16 || index <= 0) {
			throw new IllegalArgumentException("Index value must be > 0 and < 16. \n Значение индекса должно быть > 0 и < 16.");
		}
		ScoreboardLine scoreboardLine = this.getLine(index);

		if (scoreboardLine != null) {
			scoreboardLine.hideLine();
			this.scoreboardLines.remove(index);
			scoreboardLine.showLine();
		}
		return this;
	}

	public ScoreboardLine getLine(int index) {
		return this.scoreboardLines.get(index);
	}

	public Map<Integer, ScoreboardLine> getLines() {
		return this.scoreboardLines;
	}

	public ScoreboardBuilder addUpdater(Consumer<ScoreboardBuilder> scoreboardBuilder, int delay) {
		this.scoreboardUpdater.addUpdater(scoreboardBuilder, delay);
		return this;
	}

	public void showFor(Player player) {
		if (this.players.add(player)) {
			this.scoreboardObjective.create(player);
			this.scoreboardLines.values().forEach(scoreboardLine -> {
				player.dataPacket(scoreboardLine.getSetScorePacket(SetScorePacket.TYPE_CHANGE));
			});
			ScoreboardManager.scoreboards.put(player, this);
			this.scoreboardUpdater.start();
		}
	}

	public void hideFor(Player player) {
		if (this.players.remove(player)) {
			this.scoreboardObjective.remove(player);
			ScoreboardManager.scoreboards.remove(player);
		}
		this.scoreboardUpdater.stop();
	}

	public void hideAll() {
		for (Player player : this.players) {
			this.hideFor(player);
		}
	}
}