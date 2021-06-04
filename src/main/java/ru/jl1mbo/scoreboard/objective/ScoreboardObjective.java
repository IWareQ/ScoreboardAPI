package ru.jl1mbo.scoreboard.objective;

import cn.nukkit.Player;
import cn.nukkit.network.protocol.DataPacket;
import lombok.Getter;
import ru.jl1mbo.scoreboard.packet.RemoveObjectivePacket;
import ru.jl1mbo.scoreboard.packet.SetObjectivePacket;
import ru.jl1mbo.scoreboard.types.SortOrder;

@Getter
public class ScoreboardObjective {

	private final Player player;
	private String displayName;

	public ScoreboardObjective(Player player, String displayName) {
		this.player = player;
		this.displayName = displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
		player.dataPacket(this.getSetObjectivePacket(displayName));
	}

	public void create() {
		player.dataPacket(this.getSetObjectivePacket(this.displayName));
	}

	public void remove() {
		player.dataPacket(this.getRemoveObjectivePacket());
	}

	public DataPacket getSetObjectivePacket(String displayName) {
		return this.getSetObjectivePacket(displayName, SortOrder.ASCENDING);
	}

	public DataPacket getSetObjectivePacket(String displayName, SortOrder sortOrder) {
		SetObjectivePacket setObjectivePacket = new SetObjectivePacket();
		setObjectivePacket.displaySlot = "sidebar";
		setObjectivePacket.objectiveName = "objective";
		setObjectivePacket.displayName = displayName;
		setObjectivePacket.criteriaName = "dummy";
		setObjectivePacket.sortOrder = sortOrder.ordinal();
		return setObjectivePacket;
	}

	public DataPacket getRemoveObjectivePacket() {
		RemoveObjectivePacket removeObjectivePacket = new RemoveObjectivePacket();
		removeObjectivePacket.objectiveName = "objective";
		return removeObjectivePacket;
	}
}