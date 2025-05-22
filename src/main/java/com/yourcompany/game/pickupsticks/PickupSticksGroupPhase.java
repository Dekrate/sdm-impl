package com.yourcompany.game.pickupsticks;

import com.yourcompany.game.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class PickupSticksGroupPhase extends AbstractTournamentPhase<PickupSticksPlayer, PickupSticksMatch> {

	private static final Random RANDOM = new Random();

	private PickupSticksStandings standings;
	private List<DrawStrategy<PickupSticksPlayer>> drawStrategies;


	public PickupSticksGroupPhase(String name, List<PickupSticksPlayer> participants, List<DrawStrategy<PickupSticksPlayer>> drawStrategies) {
		super(name, participants);
		this.drawStrategies = drawStrategies;
		this.standings = new PickupSticksStandings(participants, drawStrategies);
	}


	@Override
	protected void generateMatches() {
		System.out.println("Generowanie meczów każdy z każdym dla grupy bierek: " + name);
		for (int i = 0; i < participants.size(); i++) {
			for (int j = i + 1; j < participants.size(); j++) {
				PickupSticksPlayer player1 = participants.get(i);
				PickupSticksPlayer player2 = participants.get(j);
				this.matches.add(new PickupSticksMatch(player1, player2));
			}
		}
	}


	@Override
	protected void playAllMatchesInPhase() {
		System.out.println("Rozgrywanie meczów w fazie grupowej: " + name);
		for (PickupSticksMatch match : matches) {
			System.out.println("\n--- Symulacja meczu bierek: " + match.getContestants().get(0).getName() + " vs " + match.getContestants().get(1).getName() + " ---");

			match.addObserver(new PickupSticksMatchLogger("Logger meczu bierek"));

			match.executeCommand(new StartMatchCommand<>());

			PickupSticksPlayer p1 = match.getContestants().get(0);
			PickupSticksPlayer p2 = match.getContestants().get(1);

			StickType[] stickTypes = StickType.values();
			for (int i = 0; i < 10; i++) {
				if (RANDOM.nextBoolean()) {
					match.executeCommand(new CollectStickCommand(p1, stickTypes[RANDOM.nextInt(stickTypes.length)]));
					match.executeCommand(new EndTurnCommand(p1));
				} else {
					match.executeCommand(new CollectStickCommand(p2, stickTypes[RANDOM.nextInt(stickTypes.length)]));
					match.executeCommand(new EndTurnCommand(p2));
				}
			}

			match.executeCommand(new EndMatchCommand<>());

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
		System.out.println("\nOstateczna klasyfikacja dla grupy bierek '" + name + "':\n" + standings.getStandingsTable());

		List<PickupSticksPlayer> ranked = standings.getRankedContestants();
		if (ranked.size() >= 2) {
			qualifiers.add(ranked.get(0));
			qualifiers.add(ranked.get(1));
			System.out.println("Kwalifikanci z grupy bierek '" + name + "': " + qualifiers.get(0).getName() + " i " + qualifiers.get(1).getName());
		} else if (ranked.size() == 1) {
			qualifiers.add(ranked.get(0));
			System.out.println("Kwalifikanci z grupy bierek '" + name + "': " + qualifiers.get(0).getName());
		}
		else {
			System.out.println("Nie udało się określić kwalifikantów dla grupy bierek '" + name + "'. Za mało graczy.");
		}
	}

	
	public void updateStandings() {
		standings.updateStandings(matches);
		System.out.println("\nKlasyfikacja w grupie bierek '" + name + "' zaktualizowana:");
		System.out.println(standings.getStandingsTable());
	}

	
	public PickupSticksStandings getStandings() {
		return standings;
	}

	@Override
	public String toString() {
		if (!isStarted) {
			return "Faza grupowa bierek '" + name + "' jeszcze się nie rozpoczęła.";
		} else if (isFinished) {
			return "Faza grupowa bierek '" + name + "' zakończona. Kwalifikanci: " + qualifiers;
		} else {
			return "Faza grupowa bierek '" + name + "' w toku. Mecze: " + matches.size() + ". Klasyfikacja:\n" + standings.getStandingsTable();
		}
	}

	@Override
	public List<PickupSticksPlayer> getParticipants() {
		return participants;
	}
}
