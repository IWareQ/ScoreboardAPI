package me.iwareq.scoreboard.updater;

import cn.nukkit.scheduler.Task;
import lombok.AllArgsConstructor;
import me.iwareq.scoreboard.Scoreboard;

@AllArgsConstructor
public class ScoreboardUpdater extends Task {

	private final Scoreboard scoreboard;

	@Override
	public void onRun(int currentTick) {
		this.scoreboard.refresh();
	}
}
