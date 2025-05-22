package com.yourcompany.game;

public abstract class ResultDecorator implements Result {

	protected Result wrappedResult; // Reference to the decorated Result object

	
	public ResultDecorator(Result wrappedResult) {
		this.wrappedResult = wrappedResult;
	}



	@Override
	public Contestant getWinner() {
		return wrappedResult.getWinner();
	}

	@Override
	public Boolean isMatchFinished() {
		return wrappedResult.isMatchFinished();
	}

	@Override
	public String getScoreString() {
		return wrappedResult.getScoreString(); // This will be commonly overridden
	}


}
