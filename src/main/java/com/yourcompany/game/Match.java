package com.yourcompany.game;

import java.util.List;

public interface Match<T extends Contestant, R extends Result> {
	void startMatch();
	void endMatch();
	void recordAction(T contestant, Object action);
	void recordViolation(Violation<? extends Contestant, ? extends Result> violation);
	R getResult();
	List<T> getContestants();
	boolean isStarted();
	boolean isFinished();
}