package ru.jl1mbo.scoreboard;

import cn.nukkit.Player;
import ru.jl1mbo.scoreboard.objective.ScoreboardObjective;
import ru.jl1mbo.scoreboard.utils.DisplaySlot;
import ru.jl1mbo.scoreboard.utils.SortOrder;

public class ScoreboardBuilder {

	private ScoreboardDisplay scoreboardDisplay;

	public ScoreboardBuilder() {
		this.scoreboardDisplay = new ScoreboardDisplay();
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
		this.scoreboardDisplay.setObjective(new ScoreboardObjective(objectiveName, displayName));
		this.scoreboardDisplay.setDisplayName(displaySlot, objectiveName, displayName, sortOrder);
		return this;
	}

	public ScoreboardBuilder setLine(int index, String text) {
		if(index > 15 || index < 1){
			index = 1;
		}
		this.scoreboardDisplay.setLine(index, text);
		return this;
	}

	//TODO
	/*public ScoreboardBuilder addUpdater(Consumer<ScoreboardDisplay> scoreboardDisplay, int delay) {
		this.scoreboardDisplay.getScoreboardUpdater().addUpdater(scoreboardDisplay, delay);
		return this;
	}*/

	public void showFor(Player player) {
		this.scoreboardDisplay.show(player);
	}

	public void hideFor(Player player) {
		this.scoreboardDisplay.hide(player);
	}
}