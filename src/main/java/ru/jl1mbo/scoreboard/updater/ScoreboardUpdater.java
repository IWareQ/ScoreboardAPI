package ru.jl1mbo.scoreboard.updater;

public class ScoreboardUpdater {
	/*
		private ScoreboardDisplay scoreboardDisplay;

		private Map<Integer, Consumer<ScoreboardDisplay>> tasks;

		private Thread thread;

		private boolean started;

		public ScoreboardUpdater(ScoreboardDisplay scoreboardDisplay) {
			this.scoreboardDisplay = scoreboardDisplay;
			this.tasks = new HashMap<>();
		}

		public ScoreboardDisplay getScoreboardDisplay() {
			return this.scoreboardDisplay;
		}

		public Map<Integer, Consumer<ScoreboardDisplay>> getTasks() {
			return this.tasks;
		}

		public void clearTasks() {
			this.tasks.clear();
		}

		public void stop() {
			this.thread.interrupt();
			if (!this.thread.isInterrupted()) {
				this.thread.stop();
			}
		}

		public ScoreboardUpdater addUpdater(Consumer<ScoreboardDisplay> task, int delay) {
			if (delay <= 0) {
				throw new IllegalArgumentException("Delay value must be > 0");
			}
			this.tasks.put(delay, task);
			return this;
		}

		public void start() {
			startTaskExecution();
			this.started = true;
		}

		private void startTaskExecution() {
			AtomicLong time = new AtomicLong();

			Runnable updater = () -> {
				while (!Thread.interrupted()) {
					try {
						Thread.sleep(50L);
					} catch (InterruptedException ignored) {
					}

					tasks.entrySet()
					.stream()
					.filter(entry -> time.get() % entry.getKey() == 0)
					.forEach(entry -> ((Map<Integer, Consumer<ScoreboardDisplay>>) entry.getValue()).forEach(consumer -> consumer.accept(scoreboardDisplay)));

					time.incrementAndGet();
				}
			};
			executionThread = new Thread(updater, String.format("%s-Updater", scoreboardDisplay.getObjective().getName()));
			executionThread.start();
		}
	*/
}