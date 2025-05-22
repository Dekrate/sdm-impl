package com.yourcompany.game.tennis;

import com.yourcompany.game.DrawStrategy;
import com.yourcompany.game.Match;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

public class TennisSetRatioDrawStrategy implements DrawStrategy<TennisPlayer> {

	@Override
	public List<TennisPlayer> resolveTie(List<TennisPlayer> tiedContestants, Map<TennisPlayer, Map<String, Integer>> standingsData, List<? extends Match<TennisPlayer, ?>> allMatches) {
		System.out.println("Applying Set Ratio Draw Strategy for tied players: " + tiedContestants);

		List<TennisPlayer> sortedList = new ArrayList<>(tiedContestants);
		sortedList.sort((p1, p2) -> {
			Map<String, Integer> data1 = standingsData.get(p1);
			Map<String, Integer> data2 = standingsData.get(p2);

			int setsDifference1 = data1.getOrDefault("setsWon", 0) - data1.getOrDefault("setsLost", 0);
			int setsDifference2 = data2.getOrDefault("setsWon", 0) - data2.getOrDefault("setsLost", 0);

			int compareResult = Integer.compare(setsDifference2, setsDifference1);

			if (compareResult != 0) {
				System.out.println(p1.getName() + " (Sets Diff: " + setsDifference1 + ") vs " + p2.getName() + " (Sets Diff: " + setsDifference2 + ") -> Compare: " + compareResult);
			} else {
				System.out.println(p1.getName() + " vs " + p2.getName() + " - Set Differences are equal (" + setsDifference1 + ").");
			}

			return compareResult;
		});

		return sortedList;
	}
}
