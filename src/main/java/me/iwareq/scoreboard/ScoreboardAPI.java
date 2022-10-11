package me.iwareq.scoreboard;

import cn.nukkit.plugin.PluginBase;
import lombok.Getter;
import me.iwareq.scoreboard.manager.ScoreboardManager;
import me.iwareq.scoreboard.updater.ScoreboardUpdater;

public class ScoreboardAPI extends PluginBase {

	@Getter
	private static ScoreboardAPI instance;
	@Getter
	private ScoreboardManager scoreboardManager;

	@Override
	public void onEnable() {
		ScoreboardAPI.instance = this;

		this.scoreboardManager = new ScoreboardManager();

		this.getServer().getScheduler().scheduleRepeatingTask(new ScoreboardUpdater(this.scoreboardManager), 20);
	}
}
