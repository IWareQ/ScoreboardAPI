package ru.jl1mbo.scoreboard;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.nukkitx.network.util.Preconditions;

import cn.nukkit.Player;
import cn.nukkit.network.protocol.DataPacket;
import ru.jl1mbo.scoreboard.line.ScoreboardLine;
import ru.jl1mbo.scoreboard.objective.ScoreboardObjective;
import ru.jl1mbo.scoreboard.packet.SetScorePacket;
import ru.jl1mbo.scoreboard.utils.DisplaySlot;
import ru.jl1mbo.scoreboard.utils.SortOrder;

public class ScoreboardDisplay {

	private final Map<Player, ScoreboardDisplay> scoreboards = new HashMap<>();
	private final Map<Integer, ScoreboardLine> scoreboardLines = new HashMap<>();

	private ScoreboardObjective scoreboardObjective;

	private final Set<Player> players = new HashSet<>();

	public void setObjective(ScoreboardObjective scoreboardObjective) {
		this.scoreboardObjective = scoreboardObjective;

		for (Player player : unregisterForAll()) {
			show(player);
		}

		reShowLine();
	}

	public ScoreboardObjective getObjective() {
		return this.scoreboardObjective;
	}

	public void setDisplayName(DisplaySlot displaySlot, String objectiveName, String displayName, SortOrder sortOrder) {
		this.scoreboardObjective.setDisplayName(displaySlot, objectiveName, displayName, sortOrder, this);
	}

	public void setLine(int index, String text) {
		ScoreboardLine scoreboardLine = this.getLine(index);

		if (scoreboardLine == null) {
			this.scoreboardLines.put(index, new ScoreboardLine(index, text, this));
		} else {
			scoreboardLine.setText(text);
		}
	}

	public ScoreboardLine getLine(int index) {
		return this.scoreboardLines.get(index);
	}

	public Map<Integer, ScoreboardLine> getLines() {
		return this.scoreboardLines;
	}

	public void broadcastPacket(DataPacket dataPacket) {
		for (Player player : this.players) {
			player.dataPacket(dataPacket);
		}
	}

	public void unregister(Player player) {
		Preconditions.checkState(players.contains(player), "Player %s is not receiving this sidebar.", player.getName());

		scoreboardLines.values().forEach(line -> {
			player.dataPacket(line.getScorePacket(SetScorePacket.TYPE_CHANGE));
		});

		this.scoreboardObjective.remove(player);
		this.players.remove(player);
	}

	public Collection<Player> unregisterForAll() {
		Set<Player> copied = new HashSet<>(players);
		players.forEach(this::unregister);

		return copied;
	}

	private void reShowLine() {
		this.scoreboardLines.values().forEach(ScoreboardLine::showLine);
	}

	public void show(Player player) {
		if (players.add(player)) {
			this.scoreboardObjective.create(player);
			this.scoreboardLines.values().forEach(scoreboardLine -> {
				player.dataPacket(scoreboardLine.getScorePacket(SetScorePacket.TYPE_CHANGE));
			});
			this.scoreboards.put(player, this);
		}
	}

	public void hide(Player player) {
		this.scoreboardObjective.remove(player);
		this.players.remove(player);
	}

	public void hideAll() {
		for (Player player : this.players) {
			this.scoreboardObjective.remove(player);
			this.players.remove(player);
		}
	}
}