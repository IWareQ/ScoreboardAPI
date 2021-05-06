package ru.jl1mbo.scoreboard;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cn.nukkit.Player;
import cn.nukkit.network.protocol.DataPacket;
import ru.jl1mbo.scoreboard.line.ScoreboardLine;
import ru.jl1mbo.scoreboard.manager.ScoreboardManager;
import ru.jl1mbo.scoreboard.objective.ScoreboardObjective;
import ru.jl1mbo.scoreboard.packet.SetScorePacket;
import ru.jl1mbo.scoreboard.utils.DisplaySlot;
import ru.jl1mbo.scoreboard.utils.SortOrder;

public class ScoreboardBuilder {

	private final Map<Integer, ScoreboardLine> scoreboardLines = new HashMap<>();
	private final Set<Player> players = new HashSet<>();
	private ScoreboardObjective scoreboardObjective;

	public Set<Player> getPlayers() {
		return this.players;
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
		if (index > 15 || index < 1) {
			index = 1;
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
		if (index > 15 || index < 1) {
			index = 1;
		}
		ScoreboardLine scoreboardLine = this.getLine(index);

		if (scoreboardLine != null) {
			scoreboardLine.hideLine();
			this.scoreboardLines.remove(index);
		}
		return this;
	}

	public ScoreboardLine getLine(int index) {
		return this.scoreboardLines.get(index);
	}

	public Map<Integer, ScoreboardLine> getLines() {
		return this.scoreboardLines;
	}

	//TODO
	/*public ScoreboardBuilder addUpdater(Consumer<ScoreboardDisplay> scoreboardDisplay, int delay) {
		this.scoreboardDisplay.getScoreboardUpdater().addUpdater(scoreboardDisplay, delay);
		return this;
	}*/

	public void showFor(Player player) {
		if (this.players.add(player)) {
			this.scoreboardObjective.create(player);
			this.scoreboardLines.values().forEach(scoreboardLine -> {
				player.dataPacket(scoreboardLine.getSetScorePacket(SetScorePacket.TYPE_CHANGE));
			});
			ScoreboardManager.scoreboards.put(player, this);
		}
	}

	public void hideFor(Player player) {
		if (this.players.remove(player)) {
			this.scoreboardObjective.remove(player);
		}
	}

	public void hideAll() {
		for (Player player : this.players) {
			if (this.players.remove(player)) {
				this.scoreboardObjective.remove(player);
			}
		}
	}
}