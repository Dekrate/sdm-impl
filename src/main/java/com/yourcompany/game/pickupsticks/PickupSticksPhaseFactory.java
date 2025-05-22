package com.yourcompany.game.pickupsticks;

import com.yourcompany.game.TournamentPhaseFactory;
import com.yourcompany.game.TournamentPhase;
import com.yourcompany.game.DrawStrategy; // Import DrawStrategy

import java.util.List;


public class PickupSticksPhaseFactory implements TournamentPhaseFactory<PickupSticksPlayer, PickupSticksMatch> {

	private List<DrawStrategy<PickupSticksPlayer>> groupPhaseDrawStrategies;

	
	public PickupSticksPhaseFactory(List<DrawStrategy<PickupSticksPlayer>> groupPhaseDrawStrategies) {
		this.groupPhaseDrawStrategies = groupPhaseDrawStrategies;
	}

	@Override
	public TournamentPhase<PickupSticksPlayer, PickupSticksMatch> createPhase(TournamentPhase<PickupSticksPlayer, PickupSticksMatch> phaseDefinition, List<PickupSticksPlayer> participants) {
		if (phaseDefinition instanceof PickupSticksGroupPhase) {

			return new PickupSticksGroupPhase(phaseDefinition.getName(), participants, groupPhaseDrawStrategies);
		} else if (phaseDefinition instanceof PickupSticksKnockoutPhase) {

			return new PickupSticksKnockoutPhase(phaseDefinition.getName(), participants);
		}

		System.err.println("Unsupported PickupSticks phase type: " + phaseDefinition.getClass().getSimpleName());
		return null;
	}
}
