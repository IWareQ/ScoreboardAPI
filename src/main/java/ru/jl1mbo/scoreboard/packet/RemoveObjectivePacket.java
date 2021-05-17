package ru.jl1mbo.scoreboard.packet;

import cn.nukkit.network.protocol.DataPacket;

public class RemoveObjectivePacket extends DataPacket {

	public String objectiveName;

	@Override
	public byte pid() {
		return 0x6a;
	}

	@Override
	public void decode() {/**/}

	@Override
	public void encode() {
		this.reset();
		this.putString(this.objectiveName);
	}
}