package com.yourcompany.game.pickupsticks;

import com.yourcompany.game.pickupsticks.PickupSticksResult;
import com.yourcompany.game.pickupsticks.PickupSticksPlayer;

public class SimplePickupSticksReportVisitor implements PickupSticksResultVisitor { // Implement PickupSticksResultVisitor

	private StringBuilder report;

	public SimplePickupSticksReportVisitor() {
		this.report = new StringBuilder();
	}

	
	public String getReport() {
		return report.toString();
	}

	@Override
	public void visit(PickupSticksResult result) {
		report.append("--- PickupSticks Match Result Report ---\n");
		report.append("Match Score: ").append(result.getScoreString()).append("\n");
		report.append("Winner: ").append(result.getWinner() != null ? result.getWinner().getName() : "N/A").append("\n");

		for (PickupSticksPlayer player : result.getContestants()) {
			report.append(player.getName()).append(": ").append(result.getScoreForPlayer(player)).append(" points\n");
		}
		report.append("--------------------------------------\n");
	}
}
