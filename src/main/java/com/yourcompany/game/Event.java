package com.yourcompany.game;

public class Event {
	private String description;
	private long timestamp;
	private Contestant contestant;

	public Event(String description, Contestant contestant) {
		this.description = description;
		this.timestamp = System.currentTimeMillis();
		this.contestant = contestant;
	}

	public String getDescription() {
		return description;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public Contestant getContestant() {
		return contestant;
	}

	@Override
	public String toString() {
		return "Event{" +
				"description='" + description + '\'' +
				", timestamp=" + timestamp +
				", contestant=" + (contestant != null ? contestant.getName() : "N/A") +
				'}';
	}
}