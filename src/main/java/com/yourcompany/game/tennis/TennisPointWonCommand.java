package com.yourcompany.game.tennis;

import com.yourcompany.game.MatchCommand;
import com.yourcompany.game.tennis.TennisMatch;
import com.yourcompany.game.tennis.TennisPlayer;
import com.yourcompany.game.Match;
import com.yourcompany.game.Contestant;
import com.yourcompany.game.Result;

public class TennisPointWonCommand implements MatchCommand<TennisMatch> {

	private TennisPlayer scoringPlayer;

	
	public TennisPointWonCommand(TennisPlayer scoringPlayer) {
		this.scoringPlayer = scoringPlayer;
	}

	
	@Override
	public void execute(TennisMatch match) {
		if (match != null && scoringPlayer != null) {
			match.pointWonBy(scoringPlayer);
		} else {
			System.err.println("Error executing PointWonCommand: Match or scoring player is null.");
		}
	}
}
