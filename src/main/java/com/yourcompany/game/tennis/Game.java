package com.yourcompany.game.tennis;

import com.yourcompany.game.Contestant;
import java.util.List;
import java.util.ArrayList;


public class Game {
	private TennisPlayer player1;
	private TennisPlayer player2;
	private TennisPoint player1Points;
	private TennisPoint player2Points;
	private TennisPlayer winner;
	private boolean finished;

	
	public Game(TennisPlayer player1, TennisPlayer player2) {
		this.player1 = player1;
		this.player2 = player2;
		this.player1Points = new TennisPoint();
		this.player2Points = new TennisPoint();
		this.winner = null;
		this.finished = false;
	}

	
	public void pointWonBy(TennisPlayer scoringPlayer) {
		if (finished) {

			return;
		}

		TennisPoint scorerPoints;
		TennisPoint opponentPoints;

		if (scoringPlayer.equals(player1)) {
			scorerPoints = player1Points;
			opponentPoints = player2Points;
		} else if (scoringPlayer.equals(player2)) {
			scorerPoints = player2Points;
			opponentPoints = player1Points;
		} else {
			System.err.println("Nieprawidłowy zawodnik dla zdobytego punktu: " + scoringPlayer.getName());
			return;
		}

		scorerPoints.increment();

		if (player1Points.getValue() >= 40 && player2Points.getValue() >= 40) {

			if (scorerPoints.getValue() - opponentPoints.getValue() >= 2) {

				winner = scoringPlayer;
				finished = true;
			} else if (opponentPoints.getValue() - scorerPoints.getValue() >= 1 && opponentPoints.isAdvantage()) {

				opponentPoints.resetAdvantage();
				scorerPoints.resetAdvantage(); // Upewnij się, że obie strony są "normalne" 40
				System.out.println("Deuce!");
			} else if (scorerPoints.getValue() - opponentPoints.getValue() == 1) {

				scorerPoints.setAdvantage();
				opponentPoints.resetAdvantage(); // Upewnij się, że przeciwnik nie ma Advantage
				System.out.println(scoringPlayer.getName() + " Advantage!");
			}

		} else if (scorerPoints.getValue() >= 40 && scorerPoints.getValue() - opponentPoints.getValue() >= 2) {

			winner = scoringPlayer;
			finished = true;
		}
	}

	
	public TennisPoint getPlayer1Points() {
		return player1Points;
	}

	
	public TennisPoint getPlayer2Points() {
		return player2Points;
	}

	
	public TennisPlayer getWinner() {
		return winner;
	}

	
	public boolean isFinished() {
		return finished;
	}

	@Override
	public String toString() {
		String score1 = player1Points.toString();
		String score2 = player2Points.toString();

		if (player1Points.isAdvantage()) {
			score1 = "AD";
			score2 = "40"; // Jeśli P1 ma AD, P2 musi mieć 40
		} else if (player2Points.isAdvantage()) {
			score1 = "40"; // Jeśli P2 ma AD, P1 musi mieć 40
			score2 = "AD";
		} else if (player1Points.getValue() == 40 && player2Points.getValue() == 40) {
			score1 = "40";
			score2 = "40";
		}

		return "Game Score: " + score1 + "-" + score2 + (finished ? " (Winner: " + winner.getName() + ")" : "");
	}

	public String getScoreString() {
		return player1Points.getValue() + "-" + player2Points.getValue();
	}
}
