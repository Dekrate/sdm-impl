package com.yourcompany.game.tennis;

import com.yourcompany.game.MatchObserver;
import com.yourcompany.game.Event;
import com.yourcompany.game.Result;

public class SimpleTennisMatchResultDisplay implements MatchObserver<TennisPlayer, TennisMatch> {

	
	@Override
	public void update(TennisMatch match, Event event) {

		String eventDescription = event.getDescription();

		if (eventDescription.contains("Point won") || eventDescription.contains("Violation") ||
				eventDescription.contains("Match started") || eventDescription.contains("Match finished")) {

			TennisResult currentResult = match.getResult();

			Result simplifiedResult = new TennisSimpleScoreDecorator(currentResult);
			System.out.println("[SCOREBOARD] Current Score (Simplified): " + simplifiedResult.getScoreString());

			if (match.isFinished()) {
				System.out.println("[SCOREBOARD] Match Status: Finished. Winner: " + (currentResult.getWinner() != null ? currentResult.getWinner().getName() : "N/A"));
			} else {
				System.out.println("[SCOREBOARD] Match Status: In Progress.");
			}
		}
	}
}
