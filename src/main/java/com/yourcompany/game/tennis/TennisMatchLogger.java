package com.yourcompany.game.tennis;

import com.yourcompany.game.MatchObserver;
import com.yourcompany.game.Match;
import com.yourcompany.game.Event;
import com.yourcompany.game.tennis.TennisPlayer; // Assuming TennisPlayer for context
import com.yourcompany.game.tennis.TennisMatch; // Assuming TennisMatch for context

public class TennisMatchLogger implements MatchObserver<TennisPlayer, TennisMatch> { // Renamed to TennisMatchLogger

	private String loggerName;

	
	public TennisMatchLogger(String loggerName) {
		this.loggerName = loggerName;
	}

	
	@Override
	public void update(TennisMatch match, Event event) {

		System.out.println("[" + loggerName + "] Event: '" + event.getDescription() +
				"' (Source Player: " + (event.getContestant() != null ? event.getContestant().getName() : "N/A") +
				", Match: " + match.getContestants().get(0).getName() + " vs " + match.getContestants().get(1).getName() + ")");
	}
}
