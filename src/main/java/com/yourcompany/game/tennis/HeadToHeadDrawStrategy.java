package com.yourcompany.game.tennis;

import com.yourcompany.game.DrawStrategy;
import com.yourcompany.game.Match;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
public class HeadToHeadDrawStrategy implements DrawStrategy<TennisPlayer> {

	@Override
	public List<TennisPlayer> resolveTie(List<TennisPlayer> tiedContestants, Map<TennisPlayer, Map<String, Integer>> standingsData, List<? extends Match<TennisPlayer, ?>> allMatches) {
		if (tiedContestants.size() != 2) {
			return new ArrayList<>(tiedContestants);
		}

		TennisPlayer player1 = tiedContestants.get(0);
		TennisPlayer player2 = tiedContestants.get(1);

		for (Match<TennisPlayer, ?> match : allMatches) {
			List<TennisPlayer> matchContestants = match.getContestants();
			if (matchContestants.contains(player1) && matchContestants.contains(player2) && match.isFinished()) {
				TennisResult result = (TennisResult) match.getResult();
				if (result.getWinner() != null) {
					List<TennisPlayer> sortedList = new ArrayList<>();
					if (result.getWinner().equals(player1)) {
						sortedList.add(player1);
						sortedList.add(player2);
					} else {
						sortedList.add(player2);
						sortedList.add(player1);
					}
					System.out.println("Tie resolved by Head-to-Head: " + sortedList.get(0).getName() + " ahead of " + sortedList.get(1).getName());
					return sortedList;
				}
			}
		}

		System.out.println("Head-to-Head draw strategy could not resolve the tie between " + player1.getName() + " and " + player2.getName());
		return new ArrayList<>(tiedContestants);
	}
}
