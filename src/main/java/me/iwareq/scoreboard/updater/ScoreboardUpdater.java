package me.iwareq.scoreboard.updater;

import cn.nukkit.scheduler.Task;
import lombok.AllArgsConstructor;
import me.iwareq.scoreboard.Scoreboard;
import me.iwareq.scoreboard.manager.ScoreboardManager;

@AllArgsConstructor
public class ScoreboardUpdater extends Task {

	private final ScoreboardManager manager;

	@Override
	public void onRun(int currentTick) {
		for (Scoreboard scoreboard : this.manager.getScoreboards().values()) {
			int updateTime = scoreboard.getUpdateTime();
			if (updateTime != 0 && currentTick % updateTime == 0) {
				scoreboard.refresh();
			}
		}
	}
}
