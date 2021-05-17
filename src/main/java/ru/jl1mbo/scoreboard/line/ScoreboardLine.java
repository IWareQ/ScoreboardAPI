package ru.jl1mbo.scoreboard.line;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import cn.nukkit.network.protocol.DataPacket;
import ru.jl1mbo.scoreboard.ScoreboardBuilder;
import ru.jl1mbo.scoreboard.packet.SetScorePacket;
import ru.jl1mbo.scoreboard.packet.entry.ScoreEntry;

public class ScoreboardLine {

	private int index;
	private String text;
	private final ScoreboardBuilder scoreboardBuilder;

	public ScoreboardLine(int index, String text, ScoreboardBuilder scoreboardBuilder) {
		this.index = index;
		this.text = text;
		this.scoreboardBuilder = scoreboardBuilder;
		this.reflesh();
	}

	public void setText(String text) {
		this.text = text;
		this.reflesh();
	}

	public String getText() {
		return this.text;
	}

	public void setIndex(int index) {
		this.index = index;
		this.reflesh();
	}

	public int getIndex() {
		return this.index;
	}
	
	public void reflesh() {
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
		for (Entry<Integer, ScoreboardLine> entry : this.scoreboardBuilder.getLines().entrySet()) {
			entries.add(new ScoreEntry(entry.getKey(), entry.getKey(), ScoreEntry.TYPE_FAKE_PLAYER, 0, entry.getValue().getText()));
		}
		setScorePacket.entries = entries;
		return setScorePacket;
	}
}