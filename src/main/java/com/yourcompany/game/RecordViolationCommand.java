package com.yourcompany.game;

import com.yourcompany.game.Match;
import com.yourcompany.game.Violation;
import com.yourcompany.game.Contestant;
import com.yourcompany.game.Result;


public class RecordViolationCommand<M extends Match<? extends Contestant, ? extends Result>> implements MatchCommand<M> {

	private Violation<? extends Contestant, ? extends Result> violation;

	
	public RecordViolationCommand(Violation<? extends Contestant, ? extends Result> violation) {
		this.violation = violation;
	}

	
	@Override
	public void execute(M match) {
		if (match != null && violation != null) {



			match.recordViolation(violation); // Delegate the action to the Receiver
		} else {
			System.err.println("Error executing RecordViolationCommand: Match or violation is null.");
		}
	}
}
