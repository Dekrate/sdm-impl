package com.yourcompany.game.tennis;

import com.yourcompany.game.Result;
import com.yourcompany.game.ResultDecorator;
import com.yourcompany.game.tennis.TennisResult;

public class TennisSimpleScoreDecorator extends ResultDecorator {

	
	public TennisSimpleScoreDecorator(TennisResult wrappedResult) {
		super(wrappedResult);
	}


	@Override
	public String getScoreString() {


		if (wrappedResult instanceof TennisResult) {
			TennisResult tennisResult = (TennisResult) wrappedResult;
			return tennisResult.getSetsWonPlayer1() + "-" + tennisResult.getSetsWonPlayer2();
		}
		return wrappedResult.getScoreString();
	}
}
