package com.yourcompany.game;

import java.util.List;
import java.util.Map;

public interface Standings<T extends Contestant> {

	void updateStandings(List<? extends Match<T, ?>> matches);

	List<T> getRankedContestants();

	Map<T, ?> getContestantData();
}
