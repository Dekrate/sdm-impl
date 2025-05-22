package com.yourcompany.game.tennis;

import com.yourcompany.game.tennis.TennisResult;
import com.yourcompany.game.tennis.TennisSet;
import com.yourcompany.game.tennis.Game;
import com.yourcompany.game.tennis.TennisPoint;

public class TennisSimpleScoreReportVisitor implements TennisResultVisitor {

	private StringBuilder report;

	public TennisSimpleScoreReportVisitor() {
		this.report = new StringBuilder();
	}

	
	public String getReport() {
		return report.toString();
	}

	@Override
	public void visit(TennisResult result) {
		report.append("--- Match Result Report ---\n");
		report.append("Match Score: ").append(result.getScoreString()).append("\n");
		report.append("Winner: ").append(result.getWinner() != null ? result.getWinner().getName() : "N/A").append("\n");
		report.append("Sets Won by Player 1: ").append(result.getSetsWonPlayer1()).append("\n");
		report.append("Sets Won by Player 2: ").append(result.getSetsWonPlayer2()).append("\n");
		report.append("Sets:\n");

	}

	@Override
	public void visit(TennisSet set) {
		report.append("  Set Score: ").append(set.getGamesWonPlayer1()).append("-").append(set.getGamesWonPlayer2());
		if (set.isFinished()) {
			report.append(" (Winner: ").append(set.getWinner() != null ? set.getWinner().getName() : "N/A").append(")");
		}
		report.append("\n");
		report.append("    Games:\n");

	}

	@Override
	public void visit(Game game) {
		report.append("      Game Score: ").append(game.getScoreString());
		if (game.isFinished()) {
			report.append(" (Winner: ").append(game.getWinner() != null ? game.getWinner().getName() : "N/A").append(")");
		}
		report.append("\n");

	}

	@Override
	public void visit(TennisPoint point) {
		 report.append("        Point: ").append(point.toString()).append("\n");
	}
}
