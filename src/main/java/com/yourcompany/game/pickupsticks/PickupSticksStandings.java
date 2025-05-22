package com.yourcompany.game.pickupsticks;

import com.yourcompany.game.Standings;
import com.yourcompany.game.Match;
import com.yourcompany.game.DrawStrategy;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;



public class PickupSticksStandings implements Standings<PickupSticksPlayer> {


	private Map<PickupSticksPlayer, Map<String, Integer>> standingsData;
	private List<PickupSticksPlayer> rankedContestants;
	private List<DrawStrategy<PickupSticksPlayer>> drawStrategies;
	private List<? extends Match<PickupSticksPlayer, ?>> allMatchesInPhase;

	
	public PickupSticksStandings(List<PickupSticksPlayer> players, List<DrawStrategy<PickupSticksPlayer>> drawStrategies) {
		this.standingsData = new HashMap<>();
		this.rankedContestants = new ArrayList<>(players);
		this.drawStrategies = drawStrategies;
		this.allMatchesInPhase = new ArrayList<>();
		initializeStandings(players);
	}

	private void initializeStandings(List<PickupSticksPlayer> players) {
		for (PickupSticksPlayer player : players) {
			Map<String, Integer> data = new HashMap<>();
			data.put("totalPoints", 0);
			data.put("matchesPlayed", 0);
			data.put("matchesWon", 0);
			data.put("matchesLost", 0);
			standingsData.put(player, data);
		}
	}

	@Override
	public void updateStandings(List<? extends Match<PickupSticksPlayer, ?>> matches) {
		this.allMatchesInPhase = matches;

		for (Map<String, Integer> data : standingsData.values()) {
			data.put("totalPoints", 0);
			data.put("matchesPlayed", 0);
			data.put("matchesWon", 0);
			data.put("matchesLost", 0);
		}

		for (Match<PickupSticksPlayer, ?> match : matches) {
			if (match.isFinished()) {
				PickupSticksResult result = (PickupSticksResult) match.getResult();

				PickupSticksPlayer player1 = match.getContestants().get(0);
				PickupSticksPlayer player2 = match.getContestants().get(1);

				Map<String, Integer> data1 = standingsData.get(player1);
				Map<String, Integer> data2 = standingsData.get(player2);

				if (data1 == null || data2 == null) {
					System.err.println("Błąd: Mecz dotyczy graczy spoza klasyfikacji. Pomijanie meczu: " + match.getContestants());
					continue;
				}

				data1.put("matchesPlayed", data1.get("matchesPlayed") + 1);
				data2.put("matchesPlayed", data2.get("matchesPlayed") + 1);

				data1.put("totalPoints", data1.get("totalPoints") + result.getScoreForPlayer(player1));
				data2.put("totalPoints", data2.get("totalPoints") + result.getScoreForPlayer(player2));

				PickupSticksPlayer winner = result.getWinner();
				if (winner != null) {
					if (winner.equals(player1)) {
						data1.put("matchesWon", data1.get("matchesWon") + 1);
						data2.put("matchesLost", data2.get("matchesLost") + 1);
					} else {
						data2.put("matchesWon", data2.get("matchesWon") + 1);
						data1.put("matchesLost", data1.get("matchesLost") + 1);
					}
				} else {
					System.out.println("Mecz między " + player1.getName() + " a " + player2.getName() + " zakończył się remisem.");
				}
			}
		}
		recalculateRanking();
	}

	@Override
	public List<PickupSticksPlayer> getRankedContestants() {

		return new ArrayList<>(rankedContestants);
	}

	
	private void recalculateRanking() {
		List<PickupSticksPlayer> sortedContestants = new ArrayList<>(standingsData.keySet());

		Collections.sort(sortedContestants, Comparator.comparingInt(p -> standingsData.get(p).getOrDefault("totalPoints", 0)).reversed());


		List<PickupSticksPlayer> finalRankedList = new ArrayList<>();
		int i = 0;
		while (i < sortedContestants.size()) {
			List<PickupSticksPlayer> tiedGroup = new ArrayList<>();
			tiedGroup.add(sortedContestants.get(i));

			int j = i + 1;
			while (j < sortedContestants.size()) {
				Map<String, Integer> data1 = standingsData.get(sortedContestants.get(i));
				Map<String, Integer> data2 = standingsData.get(sortedContestants.get(j));

				if (data1.getOrDefault("totalPoints", 0).equals(data2.getOrDefault("totalPoints", 0))) {
					tiedGroup.add(sortedContestants.get(j));
					j++;
				} else {
					break;
				}
			}

			if (tiedGroup.size() > 1) {
				System.out.println("\nWykryto remis wśród: " + tiedGroup);
				List<PickupSticksPlayer> resolvedTie = new ArrayList<>(tiedGroup);

				for (DrawStrategy<PickupSticksPlayer> strategy : drawStrategies) {

					List<PickupSticksPlayer> resultOfStrategy = strategy.resolveTie(resolvedTie, standingsData, allMatchesInPhase);

					boolean orderChanged = !resolvedTie.equals(resultOfStrategy);
					resolvedTie = resultOfStrategy;

					if (orderChanged) {
						System.out.println("Zastosowano strategię '" + strategy.getClass().getSimpleName() + "'. Wynik: " + resolvedTie);
					} else {
						System.out.println("Strategia '" + strategy.getClass().getSimpleName() + "' nie zmieniła kolejności dla tej grupy remisu.");
					}
				}
				finalRankedList.addAll(resolvedTie);
			} else {
				finalRankedList.add(tiedGroup.get(0));
			}
			i = j;
		}
		this.rankedContestants = finalRankedList;
	}

	@Override
	public Map<PickupSticksPlayer, Map<String, Integer>> getContestantData() {
		return standingsData;
	}

	
	public String getStandingsTable() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%-20s %-10s %-5s %-5s %-5s\n", "Gracz", "Punkty", "W", "P", "Rozegrane"));
		sb.append("--------------------------------------------------\n");

		List<PickupSticksPlayer> rankedPlayers = getRankedContestants();
		for (int i = 0; i < rankedPlayers.size(); i++) {
			PickupSticksPlayer player = rankedPlayers.get(i);
			Map<String, Integer> data = standingsData.get(player);
			sb.append(String.format("%-20s %-10d %-5d %-5d %-5d\n",
					player.getName(),
					data.getOrDefault("totalPoints", 0),
					data.getOrDefault("matchesWon", 0),
					data.getOrDefault("matchesLost", 0),
					data.getOrDefault("matchesPlayed", 0)));
		}
		return sb.toString();
	}
}
