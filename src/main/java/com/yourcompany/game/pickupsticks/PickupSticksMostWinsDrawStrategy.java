package com.yourcompany.game.pickupsticks;

import com.yourcompany.game.DrawStrategy;
import com.yourcompany.game.Match;
import com.yourcompany.game.pickupsticks.PickupSticksPlayer;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;


public class PickupSticksMostWinsDrawStrategy implements DrawStrategy<PickupSticksPlayer> {

	@Override
	public List<PickupSticksPlayer> resolveTie(List<PickupSticksPlayer> tiedContestants,
	                                           Map<PickupSticksPlayer, Map<String, Integer>> standingsData,
	                                           List<? extends Match<PickupSticksPlayer, ?>> allMatches) {

		List<PickupSticksPlayer> sortedPlayers = new ArrayList<>(tiedContestants);


		sortedPlayers.sort(Comparator.comparingInt(p -> {
			Map<String, Integer> playerData = standingsData.get(p);

			return playerData != null ? playerData.getOrDefault("matchesWon", 0) : 0;
		}).reversed());

		System.out.println("  Zastosowano strategię 'Najwięcej Zwycięstw' do rozwiązywania remisu. Posortowane: " + sortedPlayers);
		return sortedPlayers;
	}
}
