package com.yourcompany.game.tennis;

import com.yourcompany.game.DrawStrategy;
import com.yourcompany.game.Match;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class GameRatioDrawStrategy implements DrawStrategy<TennisPlayer> {

	@Override
	public List<TennisPlayer> resolveTie(List<TennisPlayer> tiedContestants, Map<TennisPlayer, Map<String, Integer>> standingsData, List<? extends Match<TennisPlayer, ?>> allMatches) {
		System.out.println("Applying Game Ratio Draw Strategy for tied players: " + tiedContestants);
		List<TennisPlayer> sortedList = new ArrayList<>(tiedContestants);
		sortedList.sort((p1, p2) -> {
			Map<String, Integer> data1 = standingsData.get(p1);
			Map<String, Integer> data2 = standingsData.get(p2);

			int gamesDifference1 = data1.getOrDefault("gamesWon", 0) - data1.getOrDefault("gamesLost", 0);
			int gamesDifference2 = data2.getOrDefault("gamesWon", 0) - data2.getOrDefault("gamesLost", 0);

			int compareResult = Integer.compare(gamesDifference2, gamesDifference1);

			if (compareResult != 0) {
				System.out.println(p1.getName() + " (Games Diff: " + gamesDifference1 + ") vs " + p2.getName() + " (Games Diff: " + gamesDifference2 + ") -> Compare: " + compareResult);
			} else {
				System.out.println(p1.getName() + " vs " + p2.getName() + " - Game Differences are equal (" + gamesDifference1 + ").");
			}

			return compareResult;
		});

		return sortedList;
	}
}
