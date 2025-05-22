package com.yourcompany.game;

public interface Violation<T extends Contestant, R extends Result> {
	String getDescription();
	Penalty<T, R> getPenalty();
	T getViolatingContestant();
}