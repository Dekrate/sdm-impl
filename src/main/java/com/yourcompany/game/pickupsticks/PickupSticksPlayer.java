package com.yourcompany.game.pickupsticks;

import com.yourcompany.game.Contestant;


public class PickupSticksPlayer implements Contestant {

	private String name;
	private int age;
	private String country;


	
	public PickupSticksPlayer(String name, int age, String country) {
		this.name = name;
		this.age = age;
		this.country = country;
	}

	@Override
	public String getName() {
		return name;
	}


	@Override
	public String toString() {
		return name + " (" + country + ")";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		PickupSticksPlayer that = (PickupSticksPlayer) obj;
		return name.equals(that.name);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
