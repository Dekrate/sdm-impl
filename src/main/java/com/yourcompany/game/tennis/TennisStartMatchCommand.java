package com.yourcompany.game.tennis;

import com.yourcompany.game.MatchCommand;
import com.yourcompany.game.tennis.TennisMatch;
import com.yourcompany.game.Match; // Import general Match interface
import com.yourcompany.game.Contestant; // Import Contestant
import com.yourcompany.game.Result; // Import Result

public class TennisStartMatchCommand implements MatchCommand<TennisMatch> { // Implement MatchCommand for TennisMatch

	
	@Override
	public void execute(TennisMatch match) { // Signature updated to TennisMatch
		if (match != null) {
			match.startMatch(); // Delegate the action to the Receiver
		} else {
			System.err.println("Error executing StartMatchCommand: Match is null.");
		}
	}
}
