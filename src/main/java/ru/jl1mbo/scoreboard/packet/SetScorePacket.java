package ru.jl1mbo.scoreboard.packet;

import cn.nukkit.network.protocol.DataPacket;
import ru.jl1mbo.scoreboard.packet.entry.ScoreEntry;

import java.util.ArrayList;
import java.util.List;

public class SetScorePacket extends DataPacket {

	public static final int
			TYPE_CHANGE = 0,
			TYPE_REMOVE = 1;

	public byte type;
	public List<ScoreEntry> scoreEntries = new ArrayList<>();

	@Override
	public byte pid() {
		return 0x6c;
	}

	@Override
	public void decode() {/**/}

	@Override
	public void encode() {
		this.reset();
		this.putByte(this.type);
		this.putUnsignedVarInt(this.scoreEntries.size());
		for (ScoreEntry scoreEntry : this.scoreEntries) {
			this.putVarLong(scoreEntry.scoreboardId);
			this.putString("objective");
			this.putLInt(scoreEntry.score);
			if (this.type != TYPE_REMOVE) {
				this.putByte((byte) scoreEntry.type);
				switch (scoreEntry.type) {
					case ScoreEntry.TYPE_PLAYER:
					case ScoreEntry.TYPE_ENTITY:
						this.putUnsignedVarLong(scoreEntry.entityUniqueId);
						break;
					case ScoreEntry.TYPE_FAKE_PLAYER:
						this.putString(scoreEntry.customName);
						break;
					default:
						throw new IllegalArgumentException("Неизвестный тип: " + scoreEntry.type);
				}
			}
		}
	}
}