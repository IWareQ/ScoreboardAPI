package ru.jl1mbo.scoreboard.updater;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

import ru.jl1mbo.scoreboard.ScoreboardBuilder;

public class ScoreboardUpdater {

	private Map<Long, Consumer<ScoreboardBuilder>> tasks;
	private ScoreboardBuilder scoreboardBuilder;
	private boolean isStarting = false;
	private Thread thread;

	public ScoreboardUpdater(ScoreboardBuilder scoreboardBuilder) {
		this.scoreboardBuilder = scoreboardBuilder;
		this.tasks = new HashMap<>();
	}

	public Map<Long, Consumer<ScoreboardBuilder>> getTasks() {
		return this.tasks;
	}

	public ScoreboardBuilder getScoreboardBuilder() {
		return this.scoreboardBuilder;
	}

	public ScoreboardBuilder addUpdater(Consumer<ScoreboardBuilder> task, long delay) {
		if (delay <= 0L) {
			throw new IllegalArgumentException("Delay value must be > 0. \n Значение задержки должно быть > 0.");
		}
		this.tasks.put(delay, task);
		return this.scoreboardBuilder;
	}

	public void start() {
		if (!this.isStarting) {
			AtomicLong atomicLong = new AtomicLong();

			Runnable updater = () -> {
				while (!Thread.interrupted()) {
					try {
						Thread.sleep(50L);
					} catch (InterruptedException ignored) {

					}

					this.tasks.entrySet().stream()
					.filter(entry -> atomicLong.get() % entry.getKey() == 0)
					.forEach(entry -> entry.getValue().accept(this.scoreboardBuilder));

					atomicLong.incrementAndGet();
				}
			};
			this.thread = new Thread(updater, String.format("%s-Updater", this.scoreboardBuilder.getObjective().getDisplayName()));
			this.thread.start();
			this.isStarting = true;
		}
	}

	public void stop() {
		if (this.isStarting) {
			this.thread.interrupt();
			if (!this.thread.isInterrupted()) {
				this.thread.stop();
			}
		}
	}
}