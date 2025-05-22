package com.yourcompany.game;

import com.yourcompany.game.tennis.TennisMatch;
import com.yourcompany.game.tennis.TennisPlayer;

import java.util.List;

public interface Tournament<T extends Contestant, M extends Match<T, ?>> {

	String getName();

	List<T> getParticipants();

	void startTournament();

	void endTournament();

	List<TournamentPhase<T, M>> getFinishedPhases();

	boolean isStarted();

	boolean isFinished();

	TournamentPhase<T, M> getCurrentPhase();

	void advanceToNextPhase();
}
