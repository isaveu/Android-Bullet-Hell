package edu.rosehulman.bullethell;

public class Score implements Comparable<Score> {
	public int score;
	public String name;

	public Score(String name, int score) {
		this.name = name;
		this.score = score;
	}

	public int compareTo(Score another) {
		return another.score-score;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name + " - " + score;
	}
}
