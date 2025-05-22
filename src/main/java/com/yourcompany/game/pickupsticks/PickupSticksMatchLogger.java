package com.yourcompany.game.pickupsticks   ;

import com.yourcompany.game.MatchObserver;
import com.yourcompany.game.Match;
import com.yourcompany.game.Event;
import com.yourcompany.game.pickupsticks.PickupSticksPlayer;
import com.yourcompany.game.pickupsticks.PickupSticksMatch;

public class PickupSticksMatchLogger implements MatchObserver<PickupSticksPlayer, PickupSticksMatch> {

	private String loggerName;

	
	public PickupSticksMatchLogger(String loggerName) {
		this.loggerName = loggerName;
	}

	
	@Override
	public void update(PickupSticksMatch match, Event event) {
		System.out.println("[" + loggerName + "] Event: '" + event.getDescription() +
				"' (Match: " + match.getContestants().get(0).getName() + " vs " + match.getContestants().get(1).getName() + ")");
	}
}
