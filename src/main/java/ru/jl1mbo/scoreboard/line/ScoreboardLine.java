package ru.jl1mbo.scoreboard.line;

import cn.nukkit.network.protocol.DataPacket;
import lombok.Getter;
import ru.jl1mbo.scoreboard.ScoreboardBuilder;
import ru.jl1mbo.scoreboard.packet.SetScorePacket;
import ru.jl1mbo.scoreboard.packet.entry.ScoreEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

@Getter
public class ScoreboardLine {

	private final ScoreboardBuilder scoreboardBuilder;
	private int index;
	private String text;

	public ScoreboardLine(ScoreboardBuilder scoreboardBuilder, int index, String text) {
		this.scoreboardBuilder = scoreboardBuilder;
		this.index = index;
		this.text = text;
		this.refresh();
	}

	public void setText(String text) {
		this.text = text;
		this.refresh();
	}

	public void setIndex(int index) {
		this.index = index;
		this.refresh();
	}

	public void refresh() {
		this.hideLine();
		this.showLine();
	}

	public void hideLine() {
		this.scoreboardBuilder.getPlayer().dataPacket(this.getSetScorePacket(SetScorePacket.TYPE_REMOVE));
	}

	public void showLine() {
		this.scoreboardBuilder.getPlayer().dataPacket(this.getSetScorePacket(SetScorePacket.TYPE_CHANGE));
	}

	public DataPacket getSetScorePacket(int type) {
		SetScorePacket setScorePacket = new SetScorePacket();
		setScorePacket.type = (byte) type;
		List<ScoreEntry> entries = new ArrayList<>();
		for (Entry<Integer, ScoreboardLine> entry : this.scoreboardBuilder.getScoreboardLines().entrySet()) {
			entries.add(new ScoreEntry(entry.getKey(), entry.getKey(), ScoreEntry.TYPE_FAKE_PLAYER, 0, entry.getValue().getText()));
		}
		setScorePacket.entries = entries;
		return setScorePacket;
	}
}