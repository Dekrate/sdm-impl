package com.yourcompany.game.tennis;

import com.yourcompany.game.Contestant;
import java.util.List;
import java.util.ArrayList;

public class TennisSet {
	private TennisPlayer player1;
	private TennisPlayer player2;
	private int player1Games;
	private int player2Games;
	private TennisPlayer winner;
	private boolean finished;
	private List<Game> games;
	private Game currentGame;
	private boolean isTieBreak;

	
	public TennisSet(TennisPlayer player1, TennisPlayer player2) {
		this.player1 = player1;
		this.player2 = player2;
		this.player1Games = 0;
		this.player2Games = 0;
		this.winner = null;
		this.finished = false;
		this.games = new ArrayList<>();
		this.currentGame = new Game(player1, player2);
		this.games.add(currentGame);
		this.isTieBreak = false;
	}

	
	public void pointWonBy(TennisPlayer scoringPlayer) {
		if (finished) {

			return;
		}

		if (isTieBreak) {

			if (scoringPlayer.equals(player1)) {
				player1Games++;
			} else {
				player2Games++;
			}
			checkTieBreakFinished();
		} else {

			currentGame.pointWonBy(scoringPlayer);

			if (currentGame.isFinished()) {
				if (currentGame.getWinner().equals(player1)) {
					player1Games++;
				} else {
					player2Games++;
				}
				checkSetFinished();
				if (!finished) {

					currentGame = new Game(player1, player2);
					games.add(currentGame);
				}
			}
		}
	}

	
	private void checkSetFinished() {
		if (finished) return;

		if (player1Games >= 6 && player1Games - player2Games >= 2) {
			winner = player1;
			finished = true;
		} else if (player2Games >= 6 && player2Games - player1Games >= 2) {
			winner = player2;
			finished = true;
		} else if (player1Games == 6 && player2Games == 6) {

			System.out.println("Set osiągnął 6-6. Rozpoczynanie Tie-breaku!");
			isTieBreak = true;


			player1Games = 0;
			player2Games = 0;

		}
	}

	
	private void checkTieBreakFinished() {
		if (!isTieBreak) return;

		if (player1Games >= 7 && player1Games - player2Games >= 2) {
			winner = player1;
			finished = true;
			System.out.println("Tie-break zakończony! Zwycięzca: " + winner.getName());
		} else if (player2Games >= 7 && player2Games - player1Games >= 2) {
			winner = player2;
			finished = true;
			System.out.println("Tie-break zakończony! Zwycięzca: " + winner.getName());
		}
	}

	
	public int getPlayer1Games() {
		return player1Games;
	}

	
	public int getPlayer2Games() {
		return player2Games;
	}

	
	public TennisPlayer getWinner() {
		return winner;
	}

	
	public boolean isFinished() {
		return finished;
	}

	
	public List<Game> getGames() {
		return games;
	}

	
	public Game getCurrentGame() {
		return currentGame;
	}

	
	public int getGamesWonPlayer1() {
		return player1Games;
	}

	
	public int getGamesWonPlayer2() {
		return player2Games;
	}

	@Override
	public String toString() {
		String score = player1Games + "-" + player2Games;
		if (isTieBreak && !finished) {

			score = player1Games + "-" + player2Games + " (Tie-break)";
		} else if (finished) {
			score += " (Zwycięzca: " + (winner != null ? winner.getName() : "N/A") + ")";
		}
		return "Wynik Seta: " + score;
	}

	public void accept(TennisResultVisitor visitor) {
		visitor.visit(this);
	}

	public String getScoreString() {
		return player1.getName() + " " + player1Games + " - " + player2.getName() + " " + player2Games;
	}
}
