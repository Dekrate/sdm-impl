package com.yourcompany.game.tennis;

import com.yourcompany.game.TournamentPhaseFactory;
import com.yourcompany.game.TournamentPhase;
import com.yourcompany.game.DrawStrategy;

import java.util.List;


public class TennisPhaseFactory implements TournamentPhaseFactory<TennisPlayer, TennisMatch> {

	private List<DrawStrategy<TennisPlayer>> groupPhaseDrawStrategies;

	
	public TennisPhaseFactory(List<DrawStrategy<TennisPlayer>> groupPhaseDrawStrategies) {
		this.groupPhaseDrawStrategies = groupPhaseDrawStrategies;
	}

	@Override
	public TournamentPhase<TennisPlayer, TennisMatch> createPhase(TournamentPhase<TennisPlayer, TennisMatch> phaseDefinition, List<TennisPlayer> participants) {





		if (phaseDefinition instanceof TennisGroupPhase) {


			return new TennisGroupPhase(phaseDefinition.getName(), phaseDefinition.getParticipants(), groupPhaseDrawStrategies);
		} else if (phaseDefinition instanceof TennisKnockoutPhase) {


			int setsPerMatch = ((TennisKnockoutPhase) phaseDefinition).getSetsPerMatch();
			return new TennisKnockoutPhase(phaseDefinition.getName(), participants, setsPerMatch);
		}

		return null;
	}
}
