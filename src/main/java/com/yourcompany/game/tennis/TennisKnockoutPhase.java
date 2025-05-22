package com.yourcompany.game.tennis;

import com.yourcompany.game.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class TennisKnockoutPhase extends AbstractTournamentPhase<TennisPlayer, TennisMatch> {

	private static final Random RANDOM = new Random();
	private int setsPerMatch;

	
	public TennisKnockoutPhase(String name, List<TennisPlayer> participants, int setsPerMatch) {
		super(name, participants);
		this.setsPerMatch = setsPerMatch;
	}

	
	@Override
	protected void generateMatches() {
		System.out.println("Generowanie meczów pucharowych dla fazy tenisowej: " + name + " z uczestnikami: " + participants.size());
		if (participants.size() % 2 != 0) {
			System.err.println("Ostrzeżenie: Nieparzysta liczba uczestników w fazie pucharowej tenisa '" + name + "'. Potrzebna obsługa 'Bye'.");



		}


		for (int i = 0; i < participants.size(); i += 2) {
			if (i + 1 < participants.size()) {
				TennisPlayer player1 = participants.get(i);
				TennisPlayer player2 = participants.get(i + 1);
				this.matches.add(new TennisMatch(player1, player2, setsPerMatch));
				System.out.println("  Wygenerowano mecz: " + player1.getName() + " vs " + player2.getName());
			} else {
				System.out.println("  Gracz " + participants.get(i).getName() + " ma 'Bye' w tej rundzie (nieparzysta liczba graczy).");

			}
		}
		System.out.println("Łącznie wygenerowano " + this.matches.size() + " meczów dla fazy '" + name + "'.");
	}

	
	@Override
	protected void playAllMatchesInPhase() {
		System.out.println("Rozgrywanie meczów w fazie pucharowej: " + name);
		System.out.println("Liczba meczów do rozegrania w fazie '" + name + "': " + matches.size());

		for (TennisMatch match : matches) {
			System.out.println("\n--- Symulacja meczu tenisowego: " + match.getContestants().get(0).getName() + " vs " + match.getContestants().get(1).getName() + " ---");

			match.addObserver(new TennisMatchLogger("Logger meczu"));
			match.addObserver(new SimpleTennisMatchResultDisplay());

			match.executeCommand(new StartMatchCommand<>());

			TennisPlayer p1 = match.getContestants().get(0);
			TennisPlayer p2 = match.getContestants().get(1);


			int pointsSimulated = 0;
			final int MAX_POINTS_PER_MATCH_SIMULATION = 500;

			while (!match.isFinished() && pointsSimulated < MAX_POINTS_PER_MATCH_SIMULATION) {
				match.executeCommand(new TennisPointWonCommand(RANDOM.nextBoolean() ? p1 : p2));

				if (RANDOM.nextInt(100) < 5) {
					if (RANDOM.nextBoolean()) {
						match.executeCommand(new RecordViolationCommand(new FootFault(RANDOM.nextBoolean() ? p1 : p2)));
					} else {
						match.executeCommand(new RecordViolationCommand(new CodeViolation(RANDOM.nextBoolean() ? p1 : p2)));
					}
				}
				pointsSimulated++;
			}


			if (!match.isFinished()) {
				System.out.println("Mecz '" + match.getContestants().get(0).getName() + " vs " + match.getContestants().get(1).getName() + "' wymuszony do zakończenia po " + pointsSimulated + " punktach.");

				match.executeCommand(new EndMatchCommand<>());
			} else {
				match.executeCommand(new EndMatchCommand<>());
			}

			System.out.println("\n--- Log meczu ---");
			for (Event event : match.getEventLog()) {
				System.out.println(event);
			}

			System.out.println("\n--- Końcowy status meczu ---");
			System.out.println(match);
		}
	}

	
	@Override
	protected void determineQualifiers() {
		qualifiers.clear();

		if (matches.isEmpty() && participants.size() == 1) {
			TennisPlayer byePlayer = participants.get(0);
			qualifiers.add(byePlayer);
			System.out.println("Gracz " + byePlayer.getName() + " awansuje dzięki 'Bye' w fazie '" + name + "'.");
			System.out.println("Zwycięzcy fazy tenisowej '" + name + "' (Kwalifikanci do następnej rundy): " + qualifiers);
			return;
		}


		for (TennisMatch match : matches) {
			if (match.isFinished()) {
				TennisPlayer winner = match.getResult().getWinner();
				if (winner != null) {
					qualifiers.add(winner);
				} else {

					System.out.println("Mecz " + match.getContestants().get(0).getName() + " vs " + match.getContestants().get(1).getName() + " zakończył się remisem. Nie określono zwycięzcy do kwalifikacji.");
				}
			}
		}
		System.out.println("Zwycięzcy fazy tenisowej '" + name + "' (Kwalifikanci do następnej rundy): " + qualifiers);
	}

	
	public int getSetsPerMatch() {
		return setsPerMatch;
	}

	@Override
	public String toString() {
		if (!isStarted) {
			return "Faza pucharowa tenisa '" + name + "' jeszcze się nie rozpoczęła.";
		} else if (isFinished) {
			String resultString = "Faza pucharowa tenisa '" + name + "' zakończona. ";
			if (qualifiers.size() == 1 && name.equalsIgnoreCase("Finał")) {
				resultString += "Zwycięzca turnieju: " + qualifiers.get(0).getName();
			} else {
				resultString += "Zwycięzcy: " + qualifiers;
			}
			return resultString;
		} else {
			return "Faza pucharowa tenisa '" + name + "' w toku. Liczba meczów: " + matches.size();
		}
	}

	@Override
	public List<TennisPlayer> getParticipants() {
		return Collections.unmodifiableList(participants);
	}
}
