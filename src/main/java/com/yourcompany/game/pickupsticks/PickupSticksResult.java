package com.yourcompany.game.pickupsticks;

import com.yourcompany.game.Result;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class PickupSticksResult implements Result {

	private Map<PickupSticksPlayer, Integer> playerScores;
	private PickupSticksPlayer winner;
	private boolean matchFinished;
	private List<PickupSticksPlayer> contestants;

	
	public PickupSticksResult(PickupSticksPlayer player1, PickupSticksPlayer player2) {
		this.playerScores = new HashMap<>();
		this.playerScores.put(player1, 0);
		this.playerScores.put(player2, 0);
		this.winner = null;
		this.matchFinished = false;
		this.contestants = new ArrayList<>();
		this.contestants.add(player1);
		this.contestants.add(player2);
	}

	
	public void addStick(PickupSticksPlayer player, StickType stickType) {
		if (playerScores.containsKey(player)) {
			playerScores.put(player, playerScores.get(player) + stickType.getPoints());
		} else {
			System.err.println("Error: Player " + player.getName() + " not found in this match result.");
		}
	}

	
	public void finishMatch() {
		if (matchFinished) {
			System.out.println("Match is already finished.");
			return;
		}
		matchFinished = true;
		determineWinner();
	}

	
	private void determineWinner() {
		if (contestants.size() != 2) {
			System.err.println("Error: PickupSticksResult expects exactly two players for winner determination.");
			return;
		}

		PickupSticksPlayer player1 = contestants.get(0);
		PickupSticksPlayer player2 = contestants.get(1);

		int score1 = playerScores.getOrDefault(player1, 0);
		int score2 = playerScores.getOrDefault(player2, 0);

		if (score1 > score2) {
			winner = player1;
		} else if (score2 > score1) {
			winner = player2;
		} else {


			winner = null;
			System.out.println("Match ended in a tie!");
		}
	}

	public PickupSticksPlayer getWinner() {
		return winner;
	}

	@Override
	public Boolean isMatchFinished() {
		return matchFinished;
	}

	
	@Override
	public String getScoreString() {
		if (contestants.size() != 2) {
			return "Invalid score (number of contestants not 2)";
		}
		PickupSticksPlayer player1 = contestants.get(0);
		PickupSticksPlayer player2 = contestants.get(1);
		return player1.getName() + ": " + playerScores.getOrDefault(player1, 0) + " points - " +
				player2.getName() + ": " + playerScores.getOrDefault(player2, 0) + " points";
	}

	public int getScoreForPlayer(PickupSticksPlayer player) {
		return playerScores.getOrDefault(player, 0);
	}

	
	public void accept(PickupSticksResultVisitor visitor) {
		visitor.visit(this);


	}

	public List<PickupSticksPlayer> getContestants() {
		return contestants;
	}
}
