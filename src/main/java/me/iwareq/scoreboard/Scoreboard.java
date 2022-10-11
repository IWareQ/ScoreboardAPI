package me.iwareq.scoreboard;

import cn.nukkit.Player;
import lombok.Getter;
import lombok.ToString;
import me.iwareq.scoreboard.line.ScoreboardLine;
import me.iwareq.scoreboard.manager.ScoreboardManager;
import me.iwareq.scoreboard.packet.RemoveObjectivePacket;
import me.iwareq.scoreboard.packet.SetDisplayObjectivePacket;
import me.iwareq.scoreboard.packet.SetScorePacket;
import me.iwareq.scoreboard.packet.data.DisplaySlot;
import me.iwareq.scoreboard.packet.data.ScorerInfo;
import me.iwareq.scoreboard.packet.data.SortOrder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

@ToString
public class Scoreboard {

	@Getter
	private final Set<Player> viewers = new HashSet<>();

	private final String displayName;

	private final Map<Integer, ScoreboardLine> lines = new HashMap<>();

	private final BiConsumer<Scoreboard, Player> callback;

	private final ScoreboardManager manager;
	@Getter
	private final int updateTime;

	private int lastIndex;

	public Scoreboard(String displayName, BiConsumer<Scoreboard, Player> callback, int updateTime) {
		this.displayName = displayName;
		this.callback = callback;
		this.updateTime = updateTime;
		this.manager = ScoreboardAPI.getInstance().getScoreboardManager();
	}

	public void setLine(int index, String text) {
		this.checkLineIndex(index);

		this.lastIndex = index;

		ScoreboardLine line = new ScoreboardLine(this, text);
		this.lines.put(index, line);
	}

	public void addLine(String text) {
		this.lastIndex++;

		this.setLine(this.lastIndex, text);
	}

	private void checkLineIndex(int index) {
		if (index < 1 || index > 15) {
			throw new IllegalArgumentException("The line index value should be from 1 to 15");
		}
	}

	public void refresh() {
		this.viewers.removeIf(p -> {
			boolean remove = !p.isConnected() || !p.isOnline();
			if (remove) {
				this.manager.removeScoreboard(p);
			}

			return remove;
		});

		this.viewers.forEach(player -> {
			this.hide(player, false);
			this.show(player, false);
		});
	}

	public void show(Player player) {
		this.show(player, true);
	}

	private void show(Player player, boolean add) {
		if (!add || this.viewers.add(player)) {
			this.callback.accept(this, player);

			SetDisplayObjectivePacket objectivePacket = new SetDisplayObjectivePacket();
			objectivePacket.setDisplaySlot(DisplaySlot.SIDEBAR);
			objectivePacket.setObjectiveId("objective");
			objectivePacket.setDisplayName(this.displayName);
			objectivePacket.setCriteria("dummy");
			objectivePacket.setSortOrder(SortOrder.ASCENDING);

			player.dataPacket(objectivePacket);

			SetScorePacket scorePacket = new SetScorePacket(SetScorePacket.Action.SET);
			this.lines.forEach((index, line) ->
					scorePacket.getInfos().add(new ScorerInfo(index, "objective", index, line.getText())));

			player.dataPacket(scorePacket);

			if (add) {
				this.manager.addScoreboard(player, this);
			}
		}
	}

	public void hide(Player player) {
		this.hide(player, true);
	}

	private void hide(Player player, boolean remove) {
		if (!remove || this.viewers.remove(player)) {
			RemoveObjectivePacket packet = new RemoveObjectivePacket();
			packet.setObjectiveId("objective");

			player.dataPacket(packet);

			if (remove) {
				this.manager.removeScoreboard(player);
			}
		}
	}
}
