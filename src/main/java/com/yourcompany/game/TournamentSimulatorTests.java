package com.yourcompany.game;

import com.yourcompany.game.Tournament;
import com.yourcompany.game.TournamentPhase;
import com.yourcompany.game.TournamentPhaseFactory;
import com.yourcompany.game.DrawStrategy;
import com.yourcompany.game.AbstractTournamentPhase;
import com.yourcompany.game.Contestant;
import com.yourcompany.game.Match;
import com.yourcompany.game.Event;

import com.yourcompany.game.pickupsticks.*;
import com.yourcompany.game.tennis.*;



import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
import java.util.Random;

public class TournamentSimulatorTests {

	
	private static <T extends Contestant, M extends Match<T, ?>> void simulatePhase(AbstractTournamentPhase<T, M> phase) {


		((AbstractTournamentPhase<T, M>) phase).runPhase();
	}

	
	public static void testFullTennisTournament() {
		System.out.println("\n===== Scenariusz testowy 1: Pełny turniej tenisowy =====");

		List<TennisPlayer> players = Arrays.asList(
				new TennisPlayer("Novak Djokovic", 35, "SRB"),
				new TennisPlayer("Rafael Nadal", 36, "ESP"),
				new TennisPlayer("Roger Federer", 41, "SUI"),
				new TennisPlayer("Andy Murray", 35, "GBR"),
				new TennisPlayer("Carlos Alcaraz", 20, "ESP"),
				new TennisPlayer("Jannik Sinner", 21, "ITA"),
				new TennisPlayer("Daniil Medvedev", 27, "RUS"),
				new TennisPlayer("Casper Ruud", 24, "NOR")
		);

		List<DrawStrategy<TennisPlayer>> tennisDrawStrategies = Arrays.asList(
				new HeadToHeadDrawStrategy(),
				new TennisSetRatioDrawStrategy(),
				new GameRatioDrawStrategy()
		);

		TennisPhaseFactory tennisPhaseFactory = new TennisPhaseFactory(tennisDrawStrategies);

		System.out.println("\n--- ROZPOCZYNANIE FAZ GRUPOWYCH ---");

		List<TournamentPhase<TennisPlayer, TennisMatch>> groupAPhaseDef = new ArrayList<>();
		groupAPhaseDef.add(new TennisGroupPhase("Grupa A", players.subList(0, 4), tennisDrawStrategies));
		Tournament<TennisPlayer, TennisMatch> groupATournament = new com.yourcompany.game.tennis.TennisTournament(
				"Grupa A Turniej", players.subList(0, 4), groupAPhaseDef, tennisPhaseFactory
		);
		groupATournament.startTournament();
		simulatePhase((AbstractTournamentPhase<TennisPlayer, TennisMatch>) groupATournament.getCurrentPhase());

		List<TennisPlayer> groupAQualifiers = groupATournament.getCurrentPhase().getQualifiers(); // Get qualifiers from the finished group A phase
		System.out.println("Kwalifikanci z Grupy A: " + groupAQualifiers);

		List<TournamentPhase<TennisPlayer, TennisMatch>> groupBPhaseDef = new ArrayList<>();
		groupBPhaseDef.add(new TennisGroupPhase("Grupa B", players.subList(4, 8), tennisDrawStrategies));
		Tournament<TennisPlayer, TennisMatch> groupBTournament = new com.yourcompany.game.tennis.TennisTournament(
				"Grupa B Turniej", players.subList(4, 8), groupBPhaseDef, tennisPhaseFactory
		);
		groupBTournament.startTournament();
		simulatePhase((AbstractTournamentPhase<TennisPlayer, TennisMatch>) groupBTournament.getCurrentPhase());
		List<TennisPlayer> groupBQualifiers = groupBTournament.getCurrentPhase().getQualifiers(); // Get qualifiers from the finished group B phase
		System.out.println("Kwalifikanci z Grupy B: " + groupBQualifiers);

		List<TennisPlayer> allKnockoutParticipants = new ArrayList<>();
		allKnockoutParticipants.addAll(groupAQualifiers);
		allKnockoutParticipants.addAll(groupBQualifiers);
		System.out.println("\nWszyscy kwalifikanci do fazy pucharowej: " + allKnockoutParticipants);

		System.out.println("\n--- ROZPOCZYNANIE FAZ PUCHAROWYCH ---");
		List<TournamentPhase<TennisPlayer, TennisMatch>> knockoutPhaseDefinitions = new ArrayList<>();



		knockoutPhaseDefinitions.add(new TennisKnockoutPhase("Półfinały", Collections.emptyList(), 3)); // Participants will be passed by advanceToNextPhase
		knockoutPhaseDefinitions.add(new TennisKnockoutPhase("Finał", Collections.emptyList(), 5)); // Participants will be passed by advanceToNextPhase

		Tournament<TennisPlayer, TennisMatch> mainTennisTournament = new com.yourcompany.game.tennis.TennisTournament(
				"Wielki Szlem Tenis", allKnockoutParticipants, knockoutPhaseDefinitions, tennisPhaseFactory
		);

		mainTennisTournament.startTournament(); // This will start the Ćwierćfinały phase

		while (!mainTennisTournament.isFinished()) {
			simulatePhase((AbstractTournamentPhase<TennisPlayer, TennisMatch>) mainTennisTournament.getCurrentPhase());
			mainTennisTournament.advanceToNextPhase();
		}

		System.out.println("\n===== Końcowy status turnieju tenisowego =====");
		System.out.println(mainTennisTournament);
	}

	
	public static void testFullPickupSticksTournament() {
		System.out.println("\n===== Scenariusz testowy 2: Pełny turniej bierek =====");

		List<PickupSticksPlayer> players = Arrays.asList(
				new PickupSticksPlayer("Mistrz Patyczków 1", 30, "USA"),
				new PickupSticksPlayer("Mistrz Patyczków 2", 25, "CAN"),
				new PickupSticksPlayer("Mistrz Patyczków 3", 35, "GBR"),
				new PickupSticksPlayer("Mistrz Patyczków 4", 28, "GER"),
				new PickupSticksPlayer("Mistrz Patyczków 5", 22, "FRA"),
				new PickupSticksPlayer("Mistrz Patyczków 6", 40, "JPN"),
				new PickupSticksPlayer("Mistrz Patyczków 7", 33, "AUS"),
				new PickupSticksPlayer("Mistrz Patyczków 8", 29, "BRA")
		);

		List<DrawStrategy<PickupSticksPlayer>> pickupSticksDrawStrategies = Arrays.asList(
				new PickupSticksMostWinsDrawStrategy(),
				new PickupSticksHeadToHeadDrawStrategy()
		);

		PickupSticksPhaseFactory pickupSticksPhaseFactory = new PickupSticksPhaseFactory(pickupSticksDrawStrategies);

		System.out.println("\n--- ROZPOCZYNANIE FAZ GRUPOWYCH ---");

		List<TournamentPhase<PickupSticksPlayer, PickupSticksMatch>> groupAlfaPhaseDef = new ArrayList<>();
		groupAlfaPhaseDef.add(new PickupSticksGroupPhase("Grupa Alfa", players.subList(0, 4), pickupSticksDrawStrategies));
		Tournament<PickupSticksPlayer, PickupSticksMatch> groupAlfaTournament = new com.yourcompany.game.pickupsticks.PickupSticksTournament(
				"Grupa Alfa Turniej", players.subList(0, 4), groupAlfaPhaseDef, pickupSticksPhaseFactory
		);
		groupAlfaTournament.startTournament();
		simulatePhase((AbstractTournamentPhase<PickupSticksPlayer, PickupSticksMatch>) groupAlfaTournament.getCurrentPhase());
		List<PickupSticksPlayer> groupAlfaQualifiers = groupAlfaTournament.getCurrentPhase().getQualifiers();
		System.out.println("Kwalifikanci z Grupy Alfa: " + groupAlfaQualifiers);

		List<TournamentPhase<PickupSticksPlayer, PickupSticksMatch>> groupBetaPhaseDef = new ArrayList<>();
		groupBetaPhaseDef.add(new PickupSticksGroupPhase("Grupa Beta", players.subList(4, 8), pickupSticksDrawStrategies));
		Tournament<PickupSticksPlayer, PickupSticksMatch> groupBetaTournament = new com.yourcompany.game.pickupsticks.PickupSticksTournament(
				"Grupa Beta Turniej", players.subList(4, 8), groupBetaPhaseDef, pickupSticksPhaseFactory
		);
		groupBetaTournament.startTournament();
		simulatePhase((AbstractTournamentPhase<PickupSticksPlayer, PickupSticksMatch>) groupBetaTournament.getCurrentPhase());
		List<PickupSticksPlayer> groupBetaQualifiers = groupBetaTournament.getCurrentPhase().getQualifiers();
		System.out.println("Kwalifikanci z Grupy Beta: " + groupBetaQualifiers);

		List<PickupSticksPlayer> allKnockoutParticipants = new ArrayList<>();
		allKnockoutParticipants.addAll(groupAlfaQualifiers);
		allKnockoutParticipants.addAll(groupBetaQualifiers);
		System.out.println("\nWszyscy kwalifikanci do fazy pucharowej: " + allKnockoutParticipants);

		System.out.println("\n--- ROZPOCZYNANIE FAZ PUCHAROWYCH ---");
		List<TournamentPhase<PickupSticksPlayer, PickupSticksMatch>> knockoutPhaseDefinitions = new ArrayList<>();


		knockoutPhaseDefinitions.add(new PickupSticksKnockoutPhase("Półfinały", allKnockoutParticipants));
		knockoutPhaseDefinitions.add(new PickupSticksKnockoutPhase("Finał", Collections.emptyList()));

		Tournament<PickupSticksPlayer, PickupSticksMatch> mainPickupSticksTournament = new com.yourcompany.game.pickupsticks.PickupSticksTournament(
				"Puchar Świata w Bierkach", allKnockoutParticipants, knockoutPhaseDefinitions, pickupSticksPhaseFactory
		);

		mainPickupSticksTournament.startTournament();

		while (!mainPickupSticksTournament.isFinished()) {
			simulatePhase((AbstractTournamentPhase<PickupSticksPlayer, PickupSticksMatch>) mainPickupSticksTournament.getCurrentPhase());
			mainPickupSticksTournament.advanceToNextPhase();
		}

		System.out.println("\n===== Końcowy status turnieju bierek =====");
		System.out.println(mainPickupSticksTournament);
	}

	
	public static void testPickupSticksTournamentWithTie() {
		System.out.println("\n===== Scenariusz testowy 4: Turniej bierek z remisem =====");


	}


	public static void main(String[] args) {
		testFullTennisTournament();



	}
}
