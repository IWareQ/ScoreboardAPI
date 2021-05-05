package ru.jl1mbo.scoreboard.packet;

import java.util.ArrayList;
import java.util.List;

import cn.nukkit.network.protocol.DataPacket;

public class SetScorePacket extends DataPacket {

	public static int TYPE_CHANGE = 0;
	public static int TYPE_REMOVE = 1;
	
	public final int TYPE_PLAYER = 1;
	public final int TYPE_ENTITY = 2;
	public final int TYPE_FAKE_PLAYER = 3;

	public byte type;
	public List<ScoreEntry> entries = new ArrayList<>();
	
	@Override
	public byte pid() {
		return 0x6c;
	}
	
	@Override
	public void decode() {
		
	}
	
	@Override
	public void encode() {
		this.reset();
		this.putByte(this.type);
		this.putUnsignedVarInt(this.entries.size());
		for (ScoreEntry entry : this.entries) {
			this.putVarLong(entry.scoreboardId);
			this.putString(entry.objectiveName);
			this.putLInt(entry.score);
			if (this.type != TYPE_REMOVE) {
				this.putByte((byte) entry.type);
				switch (entry.type) {
				case TYPE_PLAYER:
				case TYPE_ENTITY:
					this.putUnsignedVarLong(entry.entityUniqueId);
					break;

				case TYPE_FAKE_PLAYER:
					this.putString(entry.customName);
					break;

				default:
					throw new IllegalArgumentException("Unknown entry " + entry.type);
				}
			}
		}
	}
}