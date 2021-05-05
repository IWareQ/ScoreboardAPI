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

	public ScoreboardObjective(String objectiveName, String displayName) {
		this.objectiveName = objectiveName;
		this.displayName = displayName;
	}

	public void setDisplayName(DisplaySlot displaySlot, String objectiveName, String displayName, SortOrder sortOrder, ScoreboardBuilder scoreboardBuilder) {
		this.displayName = displayName;
		this.objectiveName = objectiveName;

		SetObjectivePacket setObjectivePacket = new SetObjectivePacket();
		setObjectivePacket.displaySlot = displaySlot.name().toLowerCase();
		setObjectivePacket.objectiveName = objectiveName;
		setObjectivePacket.displayName = displayName;
		setObjectivePacket.criteriaName = "dummy";
		setObjectivePacket.sortOrder = sortOrder.ordinal();
		scoreboardBuilder.broadcastPacket(setObjectivePacket);
	}

	public String getObjectiveName() {
		return this.objectiveName;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public void create(Player player) {
		DataPacket packet = this.getSetObjectivePacket(this.objectiveName, this.displayName);
		player.dataPacket(packet);
	}

	public void remove(Player player) {
		DataPacket packet = this.getRemoveObjectivePacket(this.objectiveName);
		player.dataPacket(packet);
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