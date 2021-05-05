package ru.jl1mbo.scoreboard.line;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import cn.nukkit.network.protocol.DataPacket;
import ru.jl1mbo.scoreboard.ScoreboardBuilder;
import ru.jl1mbo.scoreboard.packet.ScoreEntry;
import ru.jl1mbo.scoreboard.packet.SetScorePacket;

public class ScoreboardLine {

	private int index;
	private String text;
	private ScoreboardBuilder scoreboardBuilder;

	public ScoreboardLine(int index, String text, ScoreboardBuilder scoreboardBuilder) {
		this.index = index;
		this.text = text;
		this.scoreboardBuilder = scoreboardBuilder;
		this.showLine();
	}

	public void setText(String text) {
		this.text = text;
		this.showLine();
	}

	public String getText() {
		return this.text;
	}

	public void setIndex(int index) {
		this.index = index;
		this.showLine();
	}

	public int getIndex() {
		return this.index;
	}

	public void hideLine() {
		this.scoreboardBuilder.broadcastPacket(this.getScorePacket(SetScorePacket.TYPE_REMOVE));
	}

	public void showLine() {
		this.scoreboardBuilder.broadcastPacket(this.getScorePacket(SetScorePacket.TYPE_CHANGE));
	}

	public DataPacket getScorePacket(int action) {
		SetScorePacket setScorePacket = new SetScorePacket();
		setScorePacket.type = (byte) action;
		List<ScoreEntry> entries = new ArrayList<>();
		for (Entry<Integer, ScoreboardLine> entry : this.scoreboardBuilder.getLines().entrySet()) {
			entries.add(new ScoreEntry(entry.getKey(), this.scoreboardBuilder.getObjective().getObjectiveName(), entry.getKey(), ScoreEntry.TYPE_FAKE_PLAYER, 0, entry.getValue().getText()));
		}
		setScorePacket.entries = entries;
		return setScorePacket;
	}
}