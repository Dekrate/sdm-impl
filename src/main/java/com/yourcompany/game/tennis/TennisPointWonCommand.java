package com.yourcompany.game.tennis;

import com.yourcompany.game.MatchCommand;
import com.yourcompany.game.tennis.TennisMatch;
import com.yourcompany.game.tennis.TennisPlayer;
import com.yourcompany.game.Match; // Import general Match interface
import com.yourcompany.game.Contestant; // Import Contestant
import com.yourcompany.game.Result; // Import Result

public class TennisPointWonCommand implements MatchCommand<TennisMatch> { // Implement MatchCommand for TennisMatch

	private TennisPlayer scoringPlayer; // The player who won the point

	
	public TennisPointWonCommand(TennisPlayer scoringPlayer) {
		this.scoringPlayer = scoringPlayer;
	}

	
	@Override
	public void execute(TennisMatch match) { // Signature updated to TennisMatch
		if (match != null && scoringPlayer != null) {
			match.pointWonBy(scoringPlayer); // Delegate the action to the Receiver
		} else {
			System.err.println("Error executing PointWonCommand: Match or scoring player is null.");
		}
	}
}
