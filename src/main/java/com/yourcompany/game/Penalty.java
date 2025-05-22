package com.yourcompany.game;

public interface Penalty<T extends Contestant, R extends Result> {
	void applyPenalty(Match<T, R> match, T contestant);
}
