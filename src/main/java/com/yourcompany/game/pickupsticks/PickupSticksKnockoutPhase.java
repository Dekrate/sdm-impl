package com.yourcompany.game.pickupsticks;

import com.yourcompany.game.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class PickupSticksKnockoutPhase extends AbstractTournamentPhase<PickupSticksPlayer, PickupSticksMatch> {

	private static final Random RANDOM = new Random();


	public PickupSticksKnockoutPhase(String name, List<PickupSticksPlayer> participants) {
		super(name, participants);
	}

	@Override
	protected void generateMatches() {
		System.out.println("Generowanie meczów pucharowych dla fazy bierek: " + name);
		if (participants.size() % 2 != 0) {
			System.err.println("Ostrzeżenie: Nieparzysta liczba uczestników w fazie pucharowej bierek '" + name + "'. Potrzebna obsługa 'Bye'.");

		}


		for (int i = 0; i < participants.size(); i += 2) {
			if (i + 1 < participants.size()) {
				PickupSticksPlayer player1 = participants.get(i);
				PickupSticksPlayer player2 = participants.get(i + 1);
				this.matches.add(new PickupSticksMatch(player1, player2));
			}
		}
	}

	@Override
	protected void playAllMatchesInPhase() {
		System.out.println("Rozgrywanie meczów w fazie pucharowej: " + name);
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
		}
	}


	@Override
	protected void determineQualifiers() {
		qualifiers.clear();
		for (PickupSticksMatch match : matches) {
			if (match.isFinished()) {
				PickupSticksPlayer winner = match.getResult().getWinner();
				if (winner != null) {
					qualifiers.add(winner);
				} else {

					System.out.println("Mecz " + match.getContestants().get(0).getName() + " vs " + match.getContestants().get(1).getName() + " zakończył się remisem. Nie określono zwycięzcy do kwalifikacji.");
				}
			}
		}
		System.out.println("Zwycięzcy fazy bierek '" + name + "' (Kwalifikanci do następnej rundy): " + qualifiers);
	}

	@Override
	public String toString() {
		if (!isStarted) {
			return "Faza pucharowa bierek '" + name + "' jeszcze się nie rozpoczęła.";
		} else if (isFinished) {
			String resultString = "Faza pucharowa bierek '" + name + "' zakończona. ";
			if (qualifiers.size() == 1 && name.equalsIgnoreCase("Finał")) {
				resultString += "Zwycięzca turnieju: " + qualifiers.get(0).getName();
			} else {
				resultString += "Zwycięzcy: " + qualifiers;
			}
			return resultString;
		} else {
			return "Faza pucharowa bierek '" + name + "' w toku. Liczba meczów: " + matches.size();
		}
	}

	@Override
	public List<PickupSticksPlayer> getParticipants() {
		return participants;
	}
}
