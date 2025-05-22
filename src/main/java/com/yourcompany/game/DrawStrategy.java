package com.yourcompany.game;

import java.util.List;
import java.util.Map;

public interface DrawStrategy<T extends Contestant> {

	List<T> resolveTie(List<T> tiedContestants, Map<T, Map<String, Integer>> standingsData, List<? extends Match<T, ?>> allMatches);
}
