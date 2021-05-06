package ru.jl1mbo.scoreboard.objective;

import cn.nukkit.Player;
import cn.nukkit.network.protocol.DataPacket;
import ru.jl1mbo.scoreboard.ScoreboardBuilder;
import ru.jl1mbo.scoreboard.packet.RemoveObjectivePacket;
import ru.jl1mbo.scoreboard.packet.SetObjectivePacket;
import ru.jl1mbo.scoreboard.utils.DisplaySlot;
import ru.jl1mbo.scoreboard.utils.SortOrder;

public class ScoreboardObjective {

	private String objectiveName;
	private String displayName;
	private ScoreboardBuilder scoreboardBuilder;

	public ScoreboardObjective(String objectiveName, String displayName, ScoreboardBuilder scoreboardBuilder) {
		this.objectiveName = objectiveName;
		this.displayName = displayName;
		this.scoreboardBuilder = scoreboardBuilder;
	}

	public void setDisplayName(DisplaySlot displaySlot, String objectiveName, String displayName, SortOrder sortOrder) {
		this.displayName = displayName;
		this.objectiveName = objectiveName;

		scoreboardBuilder.broadcastPacket(this.getSetObjectivePacket(displaySlot, objectiveName, displayName, sortOrder));
	}

	public String getObjectiveName() {
		return this.objectiveName;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public void create(Player player) {
		player.dataPacket(this.getSetObjectivePacket(this.objectiveName, this.displayName));
	}

	public void remove(Player player) {
		player.dataPacket(this.getRemoveObjectivePacket(this.objectiveName));
	}

	public DataPacket getSetObjectivePacket(String objectiveName, String displayName) {
		return this.getSetObjectivePacket(DisplaySlot.SIDEBAR, objectiveName, displayName, SortOrder.ASCENDING);
	}

	public DataPacket getSetObjectivePacket(DisplaySlot displaySlot, String objectiveName, String displayName, SortOrder sortOrder) {
		SetObjectivePacket setObjectivePacket = new SetObjectivePacket();
		setObjectivePacket.displaySlot = displaySlot.name().toLowerCase();
		setObjectivePacket.objectiveName = objectiveName;
		setObjectivePacket.displayName = displayName;
		setObjectivePacket.criteriaName = "dummy";
		setObjectivePacket.sortOrder = sortOrder.ordinal();
		return setObjectivePacket;
	}

	public DataPacket getRemoveObjectivePacket(String objectiveName) {
		RemoveObjectivePacket removeObjectivePacket = new RemoveObjectivePacket();
		removeObjectivePacket.objectiveName = objectiveName;
		return removeObjectivePacket;
	}
}