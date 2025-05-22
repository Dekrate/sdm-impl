package com.yourcompany.game;

import com.yourcompany.game.Match;
import com.yourcompany.game.Contestant;
import com.yourcompany.game.Result;


public class EndMatchCommand<M extends Match<? extends Contestant, ? extends Result>> implements MatchCommand<M> {


	@Override
	public void execute(M match) {
		if (match != null) {
			match.endMatch();
		} else {
			System.err.println("Error executing EndMatchCommand: Match is null.");
		}
	}
}
