package com.yourcompany.game;

import com.yourcompany.game.Match; // Import the general Match interface
import com.yourcompany.game.Contestant; // Import Contestant as Match uses it
import com.yourcompany.game.Result; // Import Result as Match uses it


public interface MatchCommand<M extends Match<? extends Contestant, ? extends Result>> {

	
	void execute(M match);


}
