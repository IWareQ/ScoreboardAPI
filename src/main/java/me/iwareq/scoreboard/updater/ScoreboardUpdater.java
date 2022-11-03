package me.iwareq.scoreboard.updater;

import cn.nukkit.scheduler.AsyncTask;
import me.iwareq.scoreboard.Scoreboard;

public class ScoreboardUpdater extends AsyncTask {

	private final Scoreboard scoreboard;

	private int time;

	public ScoreboardUpdater(Scoreboard scoreboard) {
		this.scoreboard = scoreboard;
		this.time = scoreboard.getUpdateTime();
	}

	@Override
	public void onRun() {
		this.time--;
		if (this.time <= 0) {
			scoreboard.refresh();

			this.time = scoreboard.getUpdateTime();
		}
	}
}
