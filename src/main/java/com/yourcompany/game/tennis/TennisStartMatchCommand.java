package com.yourcompany.game.tennis;

import com.yourcompany.game.MatchCommand;
import com.yourcompany.game.tennis.TennisMatch;
import com.yourcompany.game.Match;
import com.yourcompany.game.Contestant;
import com.yourcompany.game.Result;

public class TennisStartMatchCommand implements MatchCommand<TennisMatch> {

	
	@Override
	public void execute(TennisMatch match) {
		if (match != null) {
			match.startMatch();
		} else {
			System.err.println("Error executing StartMatchCommand: Match is null.");
		}
	}
}
