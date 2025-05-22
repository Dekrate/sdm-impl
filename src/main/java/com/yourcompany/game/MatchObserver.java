package com.yourcompany.game;

public interface MatchObserver<T extends Contestant, M extends Match<T, ?>> {

	
	void update(M match, Event event);
}
