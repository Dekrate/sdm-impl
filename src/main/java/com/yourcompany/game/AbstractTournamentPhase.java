package com.yourcompany.game;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;


public abstract class AbstractTournamentPhase<T extends Contestant, M extends Match<T, ?>> implements TournamentPhase<T, M> {

	protected String name;
	protected List<T> participants;
	protected List<M> matches; // Mecze w tej fazie
	protected List<T> qualifiers; // Zawodnicy, którzy awansowali
	protected boolean isStarted;
	protected boolean isFinished;


	public AbstractTournamentPhase(String name, List<T> participants) {
		this.name = name;
		this.participants = new ArrayList<>(participants);
		this.matches = new ArrayList<>();
		this.qualifiers = new ArrayList<>();
		this.isStarted = false;
		this.isFinished = false;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void startPhase() {
		if (!isStarted) {
			isStarted = true;
			System.out.println("Faza '" + name + "' rozpoczęta!");
		} else {
			System.out.println("Faza '" + name + "' jest już w toku.");
		}
	}

	@Override
	public void endPhase() {
		if (isStarted && !isFinished) {

			boolean allMatchesFinished = true;
			for (M match : matches) {

				if (!match.getResult().isMatchFinished()) {
					allMatchesFinished = false;
					break;
				}
			}

			if (allMatchesFinished) {
				isFinished = true;
				System.out.println("Faza '" + name + "' zakończona!");
				determineQualifiers(); // Określ kwalifikantów po zakończeniu wszystkich meczów
			} else {
				System.out.println("Nie można zakończyć fazy '" + name + "'. Nie wszystkie mecze są zakończone.");
			}
		} else if (!isStarted) {
			System.out.println("Faza '" + name + "' jeszcze się nie rozpoczęła.");
		} else {
			System.out.println("Faza '" + name + "' jest już zakończona.");
		}
	}

	@Override
	public boolean isStarted() {
		return isStarted;
	}

	@Override
	public boolean isFinished() {
		return isFinished;
	}

	@Override
	public List<M> getMatches() {
		return Collections.unmodifiableList(matches); // Zwróć niemodyfikowalną listę
	}

	@Override
	public List<T> getQualifiers() {
		return Collections.unmodifiableList(qualifiers); // Zwróć niemodyfikowalną listę
	}


	public final void runPhase() {
		startPhase();
		generateMatches(); // Abstrakcyjna metoda, implementowana przez podklasy
		playAllMatchesInPhase(); // NOWA abstrakcyjna metoda do odtworzenia meczów
		endPhase(); // Sprawdza i kończy fazę
	}


	protected abstract void generateMatches();


	protected abstract void playAllMatchesInPhase();



	protected abstract void determineQualifiers();
}
