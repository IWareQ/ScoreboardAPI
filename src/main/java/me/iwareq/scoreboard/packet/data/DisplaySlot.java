package me.iwareq.scoreboard.packet.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum DisplaySlot {

	SIDEBAR("sidebar"),
	LIST("list"),
	BELOW_NAME("belowname");

	@Getter
	private final String name;
}
