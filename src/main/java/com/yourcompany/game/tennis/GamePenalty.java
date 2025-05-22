package com.yourcompany.game.tennis;

import com.yourcompany.game.Match;

public class GamePenalty extends TennisPenalty {

	public GamePenalty() {
		super("Utrata gema");
	}

	@Override
	public void applyPenalty(Match<TennisPlayer, TennisResult> match, TennisPlayer contestant) {
		if (match == null || contestant == null) {
			System.err.println("Cannot apply game penalty: match or contestant is null.");
			return;
		}

		if (!(match instanceof TennisMatch)) {
			System.err.println("Cannot apply game penalty: the provided Match object is not a TennisMatch.");
			return;
		}
		TennisMatch tennisMatch = (TennisMatch) match;

		System.out.println("Applied game penalty on " + contestant.getName());

		TennisPlayer opponent = tennisMatch.getContestants().stream()
				.filter(p -> !p.equals(contestant))
				.findFirst()
				.orElse(null);

		if (opponent != null) {

			TennisResult result = tennisMatch.getResult();
			TennisSet currentSet = result.getCurrentSet();
			if (currentSet != null && !currentSet.isFinished()) {
				Game currentGame = currentSet.getCurrentGame();
				if (currentGame != null && !currentGame.isFinished()) {
					while (!currentGame.isFinished()) {
						currentGame.pointWonBy(opponent);
					}
					System.out.println(opponent.getName() + " wins the game as a result of the penalty.");
				} else {
					System.err.println("Cannot apply game penalty: the current game is finished or no game exists.");
				}
			} else {
				System.err.println("Cannot apply game penalty: the current set is finished or no set exists.");
			}

		} else {
			System.err.println("Cannot apply game penalty: opponent not found.");
		}
	}
}
