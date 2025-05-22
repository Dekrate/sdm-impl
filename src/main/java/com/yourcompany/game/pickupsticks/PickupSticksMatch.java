package com.yourcompany.game.pickupsticks;

import com.yourcompany.game.*;


import java.util.List;
import java.util.ArrayList;


public class PickupSticksMatch implements Match<PickupSticksPlayer, PickupSticksResult> {

	private List<PickupSticksPlayer> contestants;
	private PickupSticksResult matchResult;
	private List<Event> eventLog;
	private boolean isStarted;
	private boolean isFinished;
	private List<MatchObserver<PickupSticksPlayer, PickupSticksMatch>> observers;


	public PickupSticksMatch(PickupSticksPlayer player1, PickupSticksPlayer player2) {
		this.contestants = new ArrayList<>();
		this.contestants.add(player1);
		this.contestants.add(player2);
		this.matchResult = new PickupSticksResult(player1, player2);
		this.eventLog = new ArrayList<>();
		this.isStarted = false;
		this.isFinished = false;
		this.observers = new ArrayList<>();
	}


	public void addObserver(MatchObserver<PickupSticksPlayer, PickupSticksMatch> observer) {
		observers.add(observer);
	}


	public void removeObserver(MatchObserver<PickupSticksPlayer, PickupSticksMatch> observer) {
		observers.remove(observer);
	}


	private void notifyObservers(Event event) {
		for (MatchObserver<PickupSticksPlayer, PickupSticksMatch> observer : observers) {
			observer.update(this, event);
		}
	}

	
	public void executeCommand(MatchCommand<PickupSticksMatch> command) {
		if (command != null) {
			command.execute(this);
		} else {
			System.err.println("Cannot execute null command.");
		}
	}

	@Override
	public void startMatch() {
		if (!isStarted) {
			isStarted = true;
			System.out.println("PickupSticks match started!");
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
			matchResult.finishMatch();
			System.out.println("PickupSticks match finished!");
			Event endEvent = new Event("Match finished", matchResult.getWinner());
			eventLog.add(endEvent);
			notifyObservers(endEvent);
		} else if (!isStarted) {
			System.out.println("Match has not started yet.");
		} else {
			System.out.println("Match is already finished.");
		}
	}

	
	public void recordStickCollection(PickupSticksPlayer player, StickType stickType) {
		if (!isStarted || isFinished) {
			System.out.println("Cannot record stick collection: Match is not in progress.");
			return;
		}

		matchResult.addStick(player, stickType);
		String description = player.getName() + " collected a " + stickType.name() + " stick (" + stickType.getPoints() + " points).";
		Event stickEvent = new Event(description, player);
		eventLog.add(stickEvent);
		notifyObservers(stickEvent);
		System.out.println(description + " Current score: " + matchResult.getScoreString());
	}

	@Override
	public void recordAction(PickupSticksPlayer contestant, Object action) {
		if (!isStarted || isFinished) {
			System.out.println("Cannot record actions: Match is not in progress.");
			return;
		}

		String description = "Action by " + contestant.getName() + ": " + action.toString();
		Event actionEvent = new Event(description, contestant);
		eventLog.add(actionEvent);
		notifyObservers(actionEvent);
		System.out.println("Action recorded: " + description);
	}

	@Override
	public void recordViolation(Violation<? extends Contestant, ? extends Result> violation) {
		if (!isStarted || isFinished) {
			System.out.println("Cannot record violations: Match is not in progress.");
			return;
		}
		String description = "Violation: " + violation.getDescription();
		Contestant violatingPlayerForEvent = violation.getViolatingContestant();
		Event violationEvent = new Event(description, violatingPlayerForEvent);
		eventLog.add(violationEvent);
		notifyObservers(violationEvent);

		System.out.println("Violation recorded: " + description);

		System.out.println("No specific penalty applied for PickupSticks violation in this simplified model.");
	}

	@Override
	public PickupSticksResult getResult() {
		return matchResult;
	}

	@Override
	public List<PickupSticksPlayer> getContestants() {
		return contestants;
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
			return "PickupSticks match has not started yet.";
		} else if (isFinished) {
			return "PickupSticks match finished. Result: " + matchResult.getScoreString() +
					". Winner: " + (matchResult.getWinner() != null ? matchResult.getWinner().getName() : "N/A");
		} else {
			return "PickupSticks match in progress. Current score: " + matchResult.getScoreString();
		}
	}
}