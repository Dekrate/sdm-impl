package com.yourcompany.game.tennis;

import com.yourcompany.game.Contestant;

public class TennisPlayer implements Contestant {
	private String name;
	private Integer age;
	private String country;

	public TennisPlayer(String name, Integer age, String country) {
		this.name = name;
		this.age = age;
		this.country = country;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "TennisPlayer{" +
				"name='" + name + '\'' +
				", age=" + age +
				", country='" + country + '\'' +
				'}';
	}
}
