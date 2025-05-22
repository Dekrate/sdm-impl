package com.yourcompany.game.pickupsticks;

import com.yourcompany.game.*;
import com.yourcompany.game.tennis.TennisMatch;
import com.yourcompany.game.tennis.TennisPlayer;


import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Arrays;


public class PickupSticksTournament implements Tournament<PickupSticksPlayer, PickupSticksMatch> {

	private String name;
	private List<PickupSticksPlayer> participants;
	private LinkedList<TournamentPhase<PickupSticksPlayer, PickupSticksMatch>> phaseDefinitions;
	private TournamentPhase<PickupSticksPlayer, PickupSticksMatch> currentPhase;
	private boolean isStarted;
	private boolean isFinished;
	private PickupSticksPlayer winner;
	private List<TournamentPhase<PickupSticksPlayer, PickupSticksMatch>> finishedPhases;
	private TournamentPhaseFactory<PickupSticksPlayer, PickupSticksMatch> phaseFactory;

	
	public PickupSticksTournament(String name, List<PickupSticksPlayer> participants, List<TournamentPhase<PickupSticksPlayer, PickupSticksMatch>> phaseDefinitions, TournamentPhaseFactory<PickupSticksPlayer, PickupSticksMatch> phaseFactory) {
		this.name = name;
		this.participants = new ArrayList<>(participants);
		this.phaseDefinitions = new LinkedList<>(phaseDefinitions);
		this.isStarted = false;
		this.isFinished = false;
		this.winner = null;
		this.currentPhase = null;
		this.finishedPhases = new ArrayList<>();
		this.phaseFactory = phaseFactory;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<PickupSticksPlayer> getParticipants() {
		return participants;
	}

	@Override
	public void startTournament() {
		if (!isStarted) {
			isStarted = true;
			System.out.println("Tournament '" + name + "' started!");
			if (!phaseDefinitions.isEmpty()) {
				TournamentPhase<PickupSticksPlayer, PickupSticksMatch> firstPhaseDefinition = phaseDefinitions.poll();
				currentPhase = phaseFactory.createPhase(firstPhaseDefinition, participants);
				if (currentPhase != null) {
					((AbstractTournamentPhase<PickupSticksPlayer, PickupSticksMatch>) currentPhase).runPhase();
				} else {
					System.err.println("Failed to create the first phase from definition using the factory.");
					isFinished = true;
				}
			} else {
				System.err.println("Tournament '" + name + "' has no phase definitions defined!");
				isFinished = true;
			}
		} else {
			System.out.println("Tournament '" + name + "' is already in progress.");
		}
	}

	@Override
	public void endTournament() {
		if (isStarted && !isFinished) {
			isFinished = true;
			System.out.println("Tournament '" + name + "' finished!");
			if (currentPhase != null && currentPhase.isFinished()) {
				List<PickupSticksPlayer> finalQualifiers = currentPhase.getQualifiers();
				if (finalQualifiers != null && finalQualifiers.size() == 1) {
					winner = finalQualifiers.get(0);
					System.out.println("Tournament winner: " + winner.getName());
				} else {
					System.err.println("Could not determine a single winner for the tournament.");
				}
			} else {
				System.err.println("Cannot determine winner: current phase is not finished.");
			}
		} else if (!isStarted) {
			System.out.println("Tournament '" + name + "' has not started yet.");
		} else {
			System.out.println("Tournament '" + name + "' is already finished.");
		}
	}

	@Override
	public List<TournamentPhase<PickupSticksPlayer, PickupSticksMatch>> getFinishedPhases() {
		return Collections.unmodifiableList(finishedPhases);
	}

	@Override
	public boolean isStarted() {
		return isStarted;
	}

	@Override
	public boolean isFinished() {
		return isFinished;
	}

	@Override
	public TournamentPhase<PickupSticksPlayer, PickupSticksMatch> getCurrentPhase() {
		return currentPhase;
	}

	
	public void advanceToNextPhase() {
		if (!isStarted || isFinished) {
			System.out.println("Cannot advance phase: Tournament is not in progress.");
			return;
		}

		if (currentPhase != null && currentPhase.isFinished()) {
			finishedPhases.add(currentPhase);

			List<PickupSticksPlayer> qualifiers = currentPhase.getQualifiers();

			if (!phaseDefinitions.isEmpty()) {
				TournamentPhase<PickupSticksPlayer, PickupSticksMatch> nextPhaseDefinition = phaseDefinitions.poll();

				TournamentPhase<PickupSticksPlayer, PickupSticksMatch> nextPhase = phaseFactory.createPhase(nextPhaseDefinition, qualifiers);

				if (nextPhase != null) {
					currentPhase = nextPhase;
					((AbstractTournamentPhase<PickupSticksPlayer, PickupSticksMatch>) currentPhase).runPhase();
					System.out.println("Advanced to next phase: " + currentPhase.getName());
				} else {
					System.err.println("Failed to create the next phase from definition using the factory.");
					currentPhase = null;
					endTournament();
				}
			} else {
				endTournament();
			}
		} else if (currentPhase != null && !currentPhase.isFinished()) {
			System.out.println("Cannot advance phase: Current phase '" + currentPhase.getName() + "' is not finished.");
		} else if (currentPhase == null) {
			System.out.println("Cannot advance phase: No current phase started.");
		}
	}

	
	public List<PickupSticksMatch> getAllMatches() {
		List<PickupSticksMatch> allMatches = new ArrayList<>();
		for (TournamentPhase<PickupSticksPlayer, PickupSticksMatch> phase : finishedPhases) {

			for (Match<PickupSticksPlayer, ?> match : phase.getMatches()) {
				allMatches.add((PickupSticksMatch) match);
			}
		}
		if (currentPhase != null) {
			for (Match<PickupSticksPlayer, ?> match : currentPhase.getMatches()) {
				allMatches.add((PickupSticksMatch) match);
			}
		}
		return allMatches;
	}

	@Override
	public String toString() {
		if (!isStarted) {
			return "PickupSticks Tournament '" + name + "' has not started yet. Participants: " + participants.size();
		} else if (isFinished) {
			return "PickupSticks Tournament '" + name + "' is finished. Winner: " + (winner != null ? winner.getName() : "N/A");
		} else {
			return "PickupSticks Tournament '" + name + "' is in progress. Current phase: " + (currentPhase != null ? currentPhase.getName() : "None");
		}
	}
}
