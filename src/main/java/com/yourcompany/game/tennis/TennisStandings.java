package com.yourcompany.game.tennis;

import com.yourcompany.game.Standings;
import com.yourcompany.game.Match;
import com.yourcompany.game.DrawStrategy;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class TennisStandings implements Standings<TennisPlayer> {

	private Map<TennisPlayer, Map<String, Integer>> standingsData;
	private List<TennisPlayer> rankedContestants;
	private List<DrawStrategy<TennisPlayer>> drawStrategies;
	private List<? extends Match<TennisPlayer, ?>> allMatches;

	public TennisStandings(List<TennisPlayer> contestants, List<DrawStrategy<TennisPlayer>> drawStrategies) {
		this.standingsData = new HashMap<>();
		this.rankedContestants = new ArrayList<>(contestants);
		this.drawStrategies = drawStrategies;
		this.allMatches = new ArrayList<>();
		initializeStandings(contestants);
	}

	private void initializeStandings(List<TennisPlayer> contestants) {
		for (TennisPlayer contestant : contestants) {
			Map<String, Integer> data = new HashMap<>();
			data.put("matchesPlayed", 0);
			data.put("matchesWon", 0);
			data.put("matchesLost", 0);
			data.put("setsWon", 0);
			data.put("setsLost", 0);
			data.put("gamesWon", 0);
			data.put("gamesLost", 0);
			standingsData.put(contestant, data);
		}
	}

	@Override
	public void updateStandings(List<? extends Match<TennisPlayer, ?>> matches) {
		this.allMatches = matches;
		for (Match<TennisPlayer, ?> match : matches) {
			if (match.isFinished()) {
				TennisResult result = (TennisResult) match.getResult();
				TennisPlayer winner = result.getWinner();
				List<TennisPlayer> matchContestants = match.getContestants();

				if (matchContestants.size() == 2) {
					TennisPlayer player1 = matchContestants.get(0);
					TennisPlayer player2 = matchContestants.get(1);

					updateContestantStats(player1, result, player1.equals(winner));
					updateContestantStats(player2, result, player2.equals(winner));
				}
			}
		}
		recalculateRanking();
	}

	private void updateContestantStats(TennisPlayer contestant, TennisResult result, boolean isWinner) {
		Map<String, Integer> data = standingsData.get(contestant);
		if (data != null) {
			data.put("matchesPlayed", data.getOrDefault("matchesPlayed", 0) + 1);
			if (isWinner) {
				data.put("matchesWon", data.getOrDefault("matchesWon", 0) + 1);
				data.put("matchesLost", data.getOrDefault("matchesLost", 0));
			} else {
				data.put("matchesWon", data.getOrDefault("matchesWon", 0));
				data.put("matchesLost", data.getOrDefault("matchesLost", 0) + 1);
			}

			int currentSetsWon = data.getOrDefault("setsWon", 0);
			int currentSetsLost = data.getOrDefault("setsLost", 0);
			int currentGamesWon = data.getOrDefault("gamesWon", 0);
			int currentGamesLost = data.getOrDefault("gamesLost", 0);


			if (contestant.equals(result.getPlayer1())) {
				data.put("setsWon", currentSetsWon + result.getSetsWonPlayer1());
				data.put("setsLost", currentSetsLost + result.getSetsWonPlayer2());

				int totalGamesWonInMatch = 0;
				int totalGamesLostInMatch = 0;
				for (TennisSet set : result.getSets()) {
					if (set.getWinner() != null) {
						if (set.getWinner().equals(contestant)) {
							totalGamesWonInMatch += set.getGamesWonPlayer1();
							totalGamesLostInMatch += set.getGamesWonPlayer2();
						} else {
							totalGamesWonInMatch += set.getGamesWonPlayer2();
							totalGamesLostInMatch += set.getGamesWonPlayer1();
						}
					}
				}
				data.put("gamesWon", currentGamesWon + totalGamesWonInMatch);
				data.put("gamesLost", currentGamesLost + totalGamesLostInMatch);

			} else {
				data.put("setsWon", currentSetsWon + result.getSetsWonPlayer2());
				data.put("setsLost", currentSetsLost + result.getSetsWonPlayer1());
				int totalGamesWonInMatch = 0;
				int totalGamesLostInMatch = 0;
				for (TennisSet set : result.getSets()) {
					if (set.getWinner() != null) {
						if (set.getWinner().equals(contestant)) {
							totalGamesWonInMatch += set.getGamesWonPlayer2();
							totalGamesLostInMatch += set.getGamesWonPlayer1();
						} else {
							totalGamesWonInMatch += set.getGamesWonPlayer1();
							totalGamesLostInMatch += set.getGamesWonPlayer2();
						}
					}
				}
				data.put("gamesWon", currentGamesWon + totalGamesWonInMatch);
				data.put("gamesLost", currentGamesLost + totalGamesLostInMatch);
			}
		}
	}


	private void recalculateRanking() {
		List<TennisPlayer> sortedContestants = new ArrayList<>(standingsData.keySet());

		sortedContestants.sort((p1, p2) -> {
			Map<String, Integer> data1 = standingsData.get(p1);
			Map<String, Integer> data2 = standingsData.get(p2);

			return data2.getOrDefault("matchesWon", 0).compareTo(data1.getOrDefault("matchesWon", 0));
		});

		List<TennisPlayer> finalRankedList = new ArrayList<>();
		int i = 0;
		while (i < sortedContestants.size()) {
			List<TennisPlayer> tiedGroup = new ArrayList<>();
			tiedGroup.add(sortedContestants.get(i));

			int j = i + 1;
			while (j < sortedContestants.size()) {
				Map<String, Integer> data1 = standingsData.get(sortedContestants.get(i));
				Map<String, Integer> data2 = standingsData.get(sortedContestants.get(j));

				if (data1.getOrDefault("matchesWon", 0).equals(data2.getOrDefault("matchesWon", 0))) {
					tiedGroup.add(sortedContestants.get(j));
					j++;
				} else {
					break;
				}
			}

			if (tiedGroup.size() > 1) {
				System.out.println("\nTie detected among: " + tiedGroup);
				List<TennisPlayer> resolvedTie = new ArrayList<>(tiedGroup);

				for (DrawStrategy<TennisPlayer> strategy : drawStrategies) {
					List<TennisPlayer> resultOfStrategy = strategy.resolveTie(resolvedTie, standingsData, allMatches);

					boolean orderChanged = !resolvedTie.equals(resultOfStrategy);
					resolvedTie = resultOfStrategy;

					if (orderChanged) {
						System.out.println("Draw strategy '" + strategy.getClass().getSimpleName() + "' applied. Result: " + resolvedTie);
					} else {
						System.out.println("Draw strategy '" + strategy.getClass().getSimpleName() + "' did not change the order for this tie group.");
					}

				}
				finalRankedList.addAll(resolvedTie);
			} else {
				finalRankedList.add(tiedGroup.getFirst());
			}
			i = j;
		}

		this.rankedContestants = finalRankedList;
	}


	@Override
	public List<TennisPlayer> getRankedContestants() {
		return rankedContestants;
	}

	@Override
	public Map<TennisPlayer, Map<String, Integer>> getContestantData() {
		return standingsData;
	}

	public String getStandingsTable() {
		StringBuilder table = new StringBuilder();
		table.append("Pozycja | Zawodnik         | Mecze W | Mecze P | Sety W | Sety P | Gemy W | Gemy P\n");
		table.append("--------|------------------|---------|---------|--------|--------|--------|--------\n");

		int position = 1;
		for (TennisPlayer contestant : rankedContestants) {
			Map<String, Integer> data = standingsData.get(contestant);
			if (data != null) {
				table.append(String.format("%-8d| %-17s| %-8d| %-8d| %-7d| %-7d| %-7d| %-7d\n",
						position,
						contestant.getName(),
						data.getOrDefault("matchesWon", 0),
						data.getOrDefault("matchesLost", 0),
						data.getOrDefault("setsWon", 0),
						data.getOrDefault("setsLost", 0),
						data.getOrDefault("gamesWon", 0),
						data.getOrDefault("gamesLost", 0)));
				position++;
			}
		}
		return table.toString();
	}

	@Override
	public String toString() {
		return getStandingsTable();
	}
}
