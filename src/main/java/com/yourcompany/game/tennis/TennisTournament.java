package com.yourcompany.game.tennis;

import com.yourcompany.game.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;


public class TennisTournament implements Tournament<TennisPlayer, TennisMatch> {

	private String name;
	private List<TennisPlayer> participants;
	private LinkedList<TournamentPhase<TennisPlayer, TennisMatch>> phaseDefinitions;
	private TournamentPhase<TennisPlayer, TennisMatch> currentPhase;
	private boolean isStarted;
	private boolean isFinished;
	private TennisPlayer winner;
	private List<TournamentPhase<TennisPlayer, TennisMatch>> finishedPhases;
	private TournamentPhaseFactory<TennisPlayer, TennisMatch> phaseFactory;

	
	public TennisTournament(String name, List<TennisPlayer> participants, List<TournamentPhase<TennisPlayer, TennisMatch>> phaseDefinitions, TournamentPhaseFactory<TennisPlayer, TennisMatch> phaseFactory) {
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
	public List<TennisPlayer> getParticipants() {
		return participants;
	}

	@Override
	public void startTournament() {
		if (!isStarted) {
			isStarted = true;
			System.out.println("Turniej '" + name + "' rozpoczęty!");
			if (!phaseDefinitions.isEmpty()) {
				TournamentPhase<TennisPlayer, TennisMatch> firstPhaseDefinition = phaseDefinitions.poll();
				currentPhase = phaseFactory.createPhase(firstPhaseDefinition, participants);
				if (currentPhase == null) {
					System.err.println("Nie udało się utworzyć pierwszej fazy z definicji za pomocą fabryki.");
					isFinished = true;
				}

			} else {
				System.err.println("Turniej '" + name + "' nie ma zdefiniowanych faz!");
				isFinished = true;
			}
		} else {
			System.out.println("Turniej '" + name + "' jest już w toku.");
		}
	}

	@Override
	public void endTournament() {
		if (isStarted && !isFinished) {
			isFinished = true;
			System.out.println("Turniej '" + name + "' zakończony!");
			if (currentPhase != null && currentPhase.isFinished()) {
				List<TennisPlayer> finalQualifiers = currentPhase.getQualifiers();
				if (finalQualifiers != null && finalQualifiers.size() == 1) {
					winner = finalQualifiers.get(0);
					System.out.println("Zwycięzca turnieju: " + winner.getName());
				} else {
					System.err.println("Nie udało się określić pojedynczego zwycięzcy turnieju.");
				}
			} else {
				System.err.println("Nie można określić zwycięzcy: bieżąca faza nie jest zakończona.");
			}
		} else if (!isStarted) {
			System.out.println("Turniej '" + name + "' jeszcze się nie rozpoczął.");
		} else {
			System.out.println("Turniej '" + name + "' jest już zakończony.");
		}
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
	public TournamentPhase<TennisPlayer, TennisMatch> getCurrentPhase() {
		return currentPhase;
	}

	
	@Override
	public void advanceToNextPhase() {
		if (!isStarted || isFinished) {
			System.out.println("Nie można przejść do następnej fazy: Turniej nie jest w toku.");
			return;
		}

		if (currentPhase != null && currentPhase.isFinished()) {
			finishedPhases.add(currentPhase);

			List<TennisPlayer> qualifiers = currentPhase.getQualifiers();

			if (!phaseDefinitions.isEmpty()) {
				TournamentPhase<TennisPlayer, TennisMatch> nextPhaseDefinition = phaseDefinitions.poll();

				TournamentPhase<TennisPlayer, TennisMatch> nextPhase = phaseFactory.createPhase(nextPhaseDefinition, qualifiers);

				if (nextPhase != null) {
					currentPhase = nextPhase;

					System.out.println("Przejście do następnej fazy: " + currentPhase.getName());
				} else {
					System.err.println("Nie udało się utworzyć następnej fazy z definicji za pomocą fabryki.");
					currentPhase = null;
					endTournament();
				}
			} else {

				endTournament();
			}
		} else if (currentPhase != null && !currentPhase.isFinished()) {
			System.out.println("Nie można przejść do następnej fazy: Bieżąca faza '" + currentPhase.getName() + "' nie jest zakończona.");
		} else if (currentPhase == null) {
			System.out.println("Nie można przejść do następnej fazy: Brak rozpoczętej bieżącej fazy.");
		}
	}

	
	public List<TennisMatch> getAllMatches() {
		List<TennisMatch> allMatches = new ArrayList<>();
		for (TournamentPhase<TennisPlayer, TennisMatch> phase : finishedPhases) {
			for (Match<TennisPlayer, ?> match : phase.getMatches()) {
				allMatches.add((TennisMatch) match);
			}
		}
		if (currentPhase != null) {
			for (Match<TennisPlayer, ?> match : currentPhase.getMatches()) {
				allMatches.add((TennisMatch) match);
			}
		}
		return allMatches;
	}

	@Override
	public String toString() {
		if (!isStarted) {
			return "Turniej Tenisowy '" + name + "' jeszcze się nie rozpoczął. Uczestnicy: " + participants.size();
		} else if (isFinished) {
			return "Turniej Tenisowy '" + name + "' zakończony. Zwycięzca: " + (winner != null ? winner.getName() : "N/A");
		} else {
			return "Turniej Tenisowy '" + name + "' w toku. Bieżąca faza: " + (currentPhase != null ? currentPhase.getName() : "Brak");
		}
	}

	@Override
	public List<TournamentPhase<TennisPlayer, TennisMatch>> getFinishedPhases() {
		return Collections.unmodifiableList(finishedPhases);
	}
}
