package com.yourcompany.game;

import com.yourcompany.game.Match;
import com.yourcompany.game.Contestant;
import com.yourcompany.game.Result;


public class StartMatchCommand<M extends Match<? extends Contestant, ? extends Result>> implements MatchCommand<M> {


	@Override
	public void execute(M match) {
		if (match != null) {
			match.startMatch(); // Delegate the action to the Receiver
		} else {
			System.err.println("Error executing StartMatchCommand: Match is null.");
		}
	}
}
