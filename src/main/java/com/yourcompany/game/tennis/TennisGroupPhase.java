package com.yourcompany.game.tennis;

import com.yourcompany.game.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class TennisGroupPhase extends AbstractTournamentPhase<TennisPlayer, TennisMatch> {

	private static final Random RANDOM = new Random();

	private TennisStandings standings;
	private List<DrawStrategy<TennisPlayer>> drawStrategies;

	
	public TennisGroupPhase(String name, List<TennisPlayer> participants, List<DrawStrategy<TennisPlayer>> drawStrategies) {
		super(name, participants);
		this.drawStrategies = drawStrategies;
		this.standings = new TennisStandings(participants, drawStrategies);
	}

	
	@Override
	protected void generateMatches() {
		System.out.println("Generowanie meczów każdy z każdym dla grupy tenisowej: " + name);
		for (int i = 0; i < participants.size(); i++) {
			for (int j = i + 1; j < participants.size(); j++) {
				TennisPlayer player1 = participants.get(i);
				TennisPlayer player2 = participants.get(j);

				this.matches.add(new TennisMatch(player1, player2, 3));
			}
		}
	}


	
	@Override
	protected void playAllMatchesInPhase() {
		System.out.println("Rozgrywanie meczów w fazie grupowej: " + name);
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

			updateStandings();
		}
	}

	
	@Override
	protected void determineQualifiers() {

		standings.updateStandings(matches);
		System.out.println("\nOstateczna klasyfikacja dla grupy tenisowej '" + name + "':\n" + standings.getStandingsTable());

		List<TennisPlayer> ranked = standings.getRankedContestants();
		if (ranked.size() >= 2) {
			qualifiers.add(ranked.get(0));
			qualifiers.add(ranked.get(1));
			System.out.println("Kwalifikanci z grupy tenisowej '" + name + "': " + qualifiers.get(0).getName() + " i " + qualifiers.get(1).getName());
		} else if (ranked.size() == 1) {
			qualifiers.add(ranked.get(0));
			System.out.println("Kwalifikanci z grupy tenisowej '" + name + "': " + qualifiers.get(0).getName());
		}
		else {
			System.out.println("Nie udało się określić kwalifikantów dla grupy tenisowej '" + name + "'. Za mało graczy.");
		}
	}

	
	public void updateStandings() {
		standings.updateStandings(matches);
		System.out.println("\nKlasyfikacja w grupie tenisowej '" + name + "' zaktualizowana:");
		System.out.println(standings.getStandingsTable());
	}

	
	public TennisStandings getStandings() {
		return standings;
	}

	@Override
	public String toString() {
		if (!isStarted) {
			return "Faza grupowa tenisa '" + name + "' jeszcze się nie rozpoczęła.";
		} else if (isFinished) {
			return "Faza grupowa tenisa '" + name + "' zakończona. Kwalifikanci: " + qualifiers;
		} else {
			return "Faza grupowa tenisa '" + name + "' w toku. Mecze: " + matches.size() + ". Klasyfikacja:\n" + standings.getStandingsTable();
		}
	}

	@Override
	public List<TennisPlayer> getParticipants() {
		return participants;
	}
}
