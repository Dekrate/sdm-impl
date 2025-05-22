package com.yourcompany.game.tennis;

import com.yourcompany.game.Result;

import java.util.List;
import java.util.ArrayList;


public class TennisResult implements Result {
	private TennisPlayer player1;
	private TennisPlayer player2;
	private List<TennisSet> sets;
	private int setsWonPlayer1;
	private int setsWonPlayer2;
	private TennisPlayer winner;
	private int totalSetsToWin;

	public TennisResult(TennisPlayer player1, TennisPlayer player2, int totalSetsToWin) {
		this.player1 = player1;
		this.player2 = player2;
		this.sets = new ArrayList<>();
		this.setsWonPlayer1 = 0;
		this.setsWonPlayer2 = 0;
		this.winner = null;
		this.totalSetsToWin = totalSetsToWin;
		startNewSet();
	}

	public List<TennisSet> getSets() {
		return sets;
	}

	public int getSetsWonPlayer1() {
		return setsWonPlayer1;
	}

	public int getSetsWonPlayer2() {
		return setsWonPlayer2;
	}

	public TennisPlayer getWinner() {
		return winner;
	}

	public void startNewSet() {
		if (isMatchFinished()) {
			return;
		}
		TennisSet newSet = new TennisSet(player1, player2);
		sets.add(newSet);
	}

	public void pointWonBy(TennisPlayer scoringPlayer) {
		if (isMatchFinished()) {
			return;
		}

		TennisSet currentSet = getCurrentSet();
		if (currentSet == null) {
			return;
		}

		currentSet.pointWonBy(scoringPlayer);

		if (currentSet.isFinished()) {
			if (currentSet.getWinner().equals(player1)) {
				setsWonPlayer1++;
			} else {
				setsWonPlayer2++;
			}
			checkMatchEnd();
			if (!isMatchFinished()) {
				startNewSet();
			}
		}
	}

	public TennisSet getCurrentSet() {
		if (isMatchFinished() || sets.isEmpty()) {
			return null;
		}
		return sets.get(sets.size() - 1);
	}

	private void checkMatchEnd() {
		if (setsWonPlayer1 == totalSetsToWin) {
			winner = player1;
		} else if (setsWonPlayer2 == totalSetsToWin) {
			winner = player2;
		}
	}

	@Override
	public Boolean isMatchFinished() {
		return winner != null;
	}

	@Override
	public String getScoreString() {
		StringBuilder score = new StringBuilder();
		for (int i = 0; i < sets.size(); i++) {
			score.append(sets.get(i).getScoreString());
			if (i < sets.size() - 1) {
				score.append(", ");
			}
		}
		return score.toString();
	}

	@Override
	public String toString() {
		if (isMatchFinished()) {
			return "Match won by " + winner.getName() + " with score " + getScoreString();
		}
		return "Current match score: " + getScoreString();
	}

	
	public void accept(TennisResultVisitor visitor) {
		visitor.visit(this);

		for (TennisSet set : sets) {
			set.accept(visitor);
		}
	}

	public TennisPlayer getPlayer1() {
		return player1;
	}

	public TennisPlayer getPlayer2() {
		return player2;
	}
}
