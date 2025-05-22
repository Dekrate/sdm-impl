package com.yourcompany.game.pickupsticks;

import com.yourcompany.game.DrawStrategy;
import com.yourcompany.game.Match;
import com.yourcompany.game.pickupsticks.PickupSticksPlayer;
import com.yourcompany.game.pickupsticks.PickupSticksResult;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;


public class PickupSticksHeadToHeadDrawStrategy implements DrawStrategy<PickupSticksPlayer> {

	@Override
	public List<PickupSticksPlayer> resolveTie(List<PickupSticksPlayer> tiedContestants,
	                                           Map<PickupSticksPlayer, Map<String, Integer>> standingsData,
	                                           List<? extends Match<PickupSticksPlayer, ?>> allMatches) {

		if (tiedContestants.size() != 2) {
			System.out.println("  Head-to-Head Strategy: Not applicable for " + tiedContestants.size() + " players. Returning original order.");
			return new ArrayList<>(tiedContestants);
		}

		PickupSticksPlayer p1 = tiedContestants.get(0);
		PickupSticksPlayer p2 = tiedContestants.get(1);

		int p1WinsAgainstP2 = 0;
		int p2WinsAgainstP1 = 0;

		for (Match<PickupSticksPlayer, ?> match : allMatches) {
			if (match.isFinished() && match.getContestants().contains(p1) && match.getContestants().contains(p2) && match.getContestants().size() == 2 &&
						((match.getContestants().get(0).equals(p1) && match.getContestants().get(1).equals(p2)) ||
								(match.getContestants().get(0).equals(p2) && match.getContestants().get(1).equals(p1)))) {

					PickupSticksResult result = (PickupSticksResult) match.getResult();
					if (result.getWinner() != null) {
						if (result.getWinner().equals(p1)) {
							p1WinsAgainstP2++;
						} else if (result.getWinner().equals(p2)) {
							p2WinsAgainstP1++;
						}
					}
				}

		}

		List<PickupSticksPlayer> resolvedOrder = new ArrayList<>(tiedContestants);
		if (p1WinsAgainstP2 > p2WinsAgainstP1) {
			resolvedOrder.set(0, p1);
			resolvedOrder.set(1, p2);
			System.out.println("  Head-to-Head Strategy: " + p1.getName() + " wins against " + p2.getName() + " (" + p1WinsAgainstP2 + "-" + p2WinsAgainstP1 + ")");
		} else if (p2WinsAgainstP1 > p1WinsAgainstP2) {
			resolvedOrder.set(0, p2);
			resolvedOrder.set(1, p1);
			System.out.println("  Head-to-Head Strategy: " + p2.getName() + " wins against " + p1.getName() + " (" + p2WinsAgainstP1 + "-" + p1WinsAgainstP2 + ")");
		} else {
			System.out.println("  Head-to-Head Strategy: No clear winner or tie in head-to-head for " + p1.getName() + " and " + p2.getName() + ". Returning original order.");
		}
		return resolvedOrder;
	}
}
