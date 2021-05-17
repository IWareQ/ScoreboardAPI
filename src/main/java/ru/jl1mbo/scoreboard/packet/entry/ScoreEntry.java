package ru.jl1mbo.scoreboard.packet.entry;

public class ScoreEntry {

	public static final int TYPE_PLAYER = 1,
							TYPE_ENTITY = 2,
							TYPE_FAKE_PLAYER = 3;

	public int scoreboardId, score, type, entityUniqueId;

	public String customName;

	public ScoreEntry(int scoreboardId, int score, int type, int entityUniqueId, String customName) {
		this.scoreboardId = scoreboardId;
		this.score = score;
		this.type = type;
		this.entityUniqueId = entityUniqueId;
		this.customName = customName;
	}
}