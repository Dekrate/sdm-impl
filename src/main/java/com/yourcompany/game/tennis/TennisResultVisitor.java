package com.yourcompany.game.tennis;

import com.yourcompany.game.tennis.TennisResult;
import com.yourcompany.game.tennis.TennisSet;
import com.yourcompany.game.tennis.Game;
import com.yourcompany.game.tennis.TennisPoint;

public interface TennisResultVisitor {

	
	void visit(TennisResult result);

	
	void visit(TennisSet set);

	
	void visit(Game game);

	
	void visit(TennisPoint point);

}
