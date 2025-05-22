package com.yourcompany.game.tennis;

import com.yourcompany.game.MatchCommand;

public class TennisStopMatchCommand implements MatchCommand<TennisMatch> {

	
	@Override
	public void execute(TennisMatch match) {
		if (match != null) {
			match.endMatch();
		} else {
			System.err.println("Error executing EndMatchCommand: Match is null.");
		}
	}
}
