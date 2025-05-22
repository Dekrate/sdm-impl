package com.yourcompany.game;

import java.util.List;

public interface TournamentPhaseFactory<T extends Contestant, M extends Match<T, ?>> {

	TournamentPhase<T, M> createPhase(TournamentPhase<T, M> phaseDefinition, List<T> participants);
}
