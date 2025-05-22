package com.yourcompany.game;

import java.util.List;

public interface TournamentPhase<T extends Contestant, M extends Match<T, ?>> {

	String getName();

	void startPhase();

	void endPhase();

	boolean isStarted();

	boolean isFinished();

	List<M> getMatches();

	List<T> getQualifiers();

	List<T> getParticipants();
}
