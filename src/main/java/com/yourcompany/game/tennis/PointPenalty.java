package com.yourcompany.game.tennis;

import com.yourcompany.game.Match;
import com.yourcompany.game.Contestant;
import com.yourcompany.game.Result;

public class PointPenalty extends TennisPenalty {

	public PointPenalty() {
		super("Utrata punktu");
	}

	@Override
	public void applyPenalty(Match<TennisPlayer, TennisResult> match, TennisPlayer contestant) {
		if (match == null || contestant == null) {
			System.err.println("Nie można zastosować kary punktowej: brak meczu lub zawodnika.");
			return;
		}

		if (!(match instanceof TennisMatch)) {
			System.err.println("Nie można zastosować kary punktowej: przekazany obiekt Match nie jest TennisMatch.");
			return;
		}
		TennisMatch tennisMatch = (TennisMatch) match;


		System.out.println("Zastosowano karę punktową na " + contestant.getName());

		TennisPlayer opponent = tennisMatch.getContestants().stream()
				.filter(p -> !p.equals(contestant))
				.findFirst()
				.orElse(null);

		if (opponent != null) {
			tennisMatch.pointWonBy(opponent);
		} else {
			System.err.println("Nie można zastosować kary punktowej: nie znaleziono przeciwnika.");
		}
	}
}
