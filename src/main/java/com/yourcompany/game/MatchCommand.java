package com.yourcompany.game;

import com.yourcompany.game.Match;
import com.yourcompany.game.Contestant;
import com.yourcompany.game.Result;


public interface MatchCommand<M extends Match<? extends Contestant, ? extends Result>> {

	
	void execute(M match);


}
