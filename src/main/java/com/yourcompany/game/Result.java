package com.yourcompany.game;

public interface Result {
	Boolean isMatchFinished();
	String getScoreString();
	Contestant getWinner();
}