package com.yourcompany.game.pickupsticks;

import com.yourcompany.game.MatchCommand;
import com.yourcompany.game.pickupsticks.PickupSticksMatch;
import com.yourcompany.game.pickupsticks.PickupSticksPlayer;
import com.yourcompany.game.Match;
import com.yourcompany.game.Contestant;
import com.yourcompany.game.Result;

public class EndTurnCommand implements MatchCommand<PickupSticksMatch> {

	private PickupSticksPlayer player;

	
	public EndTurnCommand(PickupSticksPlayer player) {
		this.player = player;
	}

	
	@Override
	public void execute(PickupSticksMatch match) {
		if (match != null && player != null) {
			match.recordAction(player, "Ended turn");
		} else {
			System.err.println("Error executing EndTurnCommand: Match or player is null.");
		}
	}
}
