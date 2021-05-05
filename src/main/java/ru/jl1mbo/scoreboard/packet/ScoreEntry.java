package ru.jl1mbo.scoreboard.packet;

public class ScoreEntry {

	public static int TYPE_PLAYER = 1;
	public static int TYPE_ENTITY = 2;
	public static int TYPE_FAKE_PLAYER = 3;

	public int scoreboardId;
	public String objectiveName;
	public int score;

	public int type;

	public int entityUniqueId;
	public String customName;
	
	public ScoreEntry(int scoreboardId, String objectiveName, int score, int type, int entityUniqueId, String customName) {
		this.scoreboardId = scoreboardId;
		this.objectiveName = objectiveName;
		this.score = score;
		this.type = type;
		this.entityUniqueId = entityUniqueId;
		this.customName = customName;
	}
}