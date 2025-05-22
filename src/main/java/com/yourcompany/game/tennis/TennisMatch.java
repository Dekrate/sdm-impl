package com.yourcompany.game.tennis;

import com.yourcompany.game.*;
import com.yourcompany.game.MatchCommand;
import java.util.List;
import java.util.ArrayList;


public class TennisMatch implements Match<TennisPlayer, TennisResult> {

	private List<TennisPlayer> contestants;
	private TennisResult matchResult;
	private List<Event> eventLog;
	private boolean isStarted;
	private boolean isFinished;
	private List<MatchObserver<TennisPlayer, TennisMatch>> observers;

	
	public TennisMatch(TennisPlayer player1, TennisPlayer player2, int totalSetsToWin) {
		this.contestants = new ArrayList<>();
		this.contestants.add(player1);
		this.contestants.add(player2);
		this.matchResult = new TennisResult(player1, player2, totalSetsToWin);
		this.eventLog = new ArrayList<>();
		this.isStarted = false;
		this.isFinished = false;
		this.observers = new ArrayList<>();
	}

	
	public void addObserver(MatchObserver<TennisPlayer, TennisMatch> observer) {
		observers.add(observer);
	}

	
	public void removeObserver(MatchObserver<TennisPlayer, TennisMatch> observer) {
		observers.remove(observer);
	}

	
	private void notifyObservers(Event event) {
		for (MatchObserver<TennisPlayer, TennisMatch> observer : observers) {
			observer.update(this, event);
		}
	}

	
	public void executeCommand(MatchCommand<TennisMatch> command) {
		if (command != null) {
			command.execute(this); // The command executes itself on this match (the Receiver)
		} else {
			System.err.println("Cannot execute null command.");
		}
	}


	@Override
	public void startMatch() {
		if (!isStarted) {
			isStarted = true;
			System.out.println("Tennis match started!");
			Event startEvent = new Event("Match started", null);
			eventLog.add(startEvent);
			notifyObservers(startEvent);
		} else {
			System.out.println("Match is already in progress.");
		}
	}

	@Override
	public void endMatch() {
		if (isStarted && !isFinished) {
			isFinished = true;
			System.out.println("Tennis match finished!");
			Event endEvent = new Event("Match finished", matchResult.getWinner());
			eventLog.add(endEvent);
			notifyObservers(endEvent);
		} else if (!isStarted) {
			System.out.println("Match has not started yet.");
		} else {
			System.out.println("Match is already finished.");
		}
	}

	@Override
	public void recordAction(TennisPlayer contestant, Object action) {


		if (!isStarted || isFinished) {
			System.out.println("Cannot record actions: Match is not in progress.");
			return;
		}

		if (action instanceof String && ((String) action).equalsIgnoreCase("point won")) {
			pointWonBy(contestant); // Still delegate to the internal method
		} else {
			String description = "Action by " + contestant.getName() + ": " + action.toString();
			Event actionEvent = new Event(description, contestant);
			eventLog.add(actionEvent);
			notifyObservers(actionEvent);
			System.out.println("Action recorded: " + description);
		}
	}

	@Override
	public void recordViolation(Violation<? extends Contestant, ? extends Result> violation) {
		if (!isStarted || isFinished) {
			System.out.println("Cannot record violations: Match is not in progress.");
			return;
		}
		String description = "Violation: " + violation.getDescription();
		TennisPlayer violatingPlayerForEvent = (TennisPlayer) violation.getViolatingContestant();
		Event violationEvent = new Event(description, violatingPlayerForEvent);
		eventLog.add(violationEvent);
		notifyObservers(violationEvent);

		System.out.println("Violation recorded: " + description);

		Penalty<TennisPlayer, TennisResult> penalty = (Penalty<TennisPlayer, TennisResult>) violation.getPenalty();
		if (penalty != null) {
			TennisPlayer violatingPlayer = (TennisPlayer) violation.getViolatingContestant();
			penalty.applyPenalty(this, violatingPlayer);
		} else {
			System.out.println("Violation has no associated penalty.");
		}

		if (matchResult.isMatchFinished()) {
			endMatch();
		}
	}

	@Override
	public TennisResult getResult() {
		return matchResult;
	}

	@Override
	public List<TennisPlayer> getContestants() {
		return contestants;
	}

	public void pointWonBy(TennisPlayer scoringPlayer) {
		if (!isStarted || isFinished) {
			System.out.println("Cannot score point: Match is not in progress.");
			return;
		}

		matchResult.pointWonBy(scoringPlayer);

		String description = "Point won by " + scoringPlayer.getName();
		Event pointEvent = new Event(description, scoringPlayer);
		eventLog.add(pointEvent);
		notifyObservers(pointEvent);
		System.out.println(description + ". Current score: " + matchResult.getScoreString());

		if (matchResult.isMatchFinished()) {
			endMatch();
		}
	}

	public boolean isStarted() {
		return isStarted;
	}

	public boolean isFinished() {
		return isFinished;
	}

	public List<Event> getEventLog() {
		return eventLog;
	}

	@Override
	public String toString() {
		if (!isStarted) {
			return "Tennis match has not started yet.";
		} else if (isFinished) {
			Result simplifiedResult = new TennisSimpleScoreDecorator(matchResult);
			return "Tennis match finished. Result: " + simplifiedResult.getScoreString() +
					". Winner: " + (matchResult.getWinner() != null ? matchResult.getWinner().getName() : "N/A");
		} else {
			return "Tennis match in progress. Current score: " + matchResult.getScoreString();
		}
	}

	public TennisSet getCurrentSet() {
		return matchResult.getCurrentSet();
	}
}
