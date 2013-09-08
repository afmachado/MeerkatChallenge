package levels;

import java.io.Serializable;

public class Level implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 32551L;
	private int popUpMeerkats;
	private int targetScore;
	private int timeLimit;
	private String title;
	private String description;
	private int number;
	
	public Level (int number, int popUpMeerkats, int targetScore, int timeLimit, String title, String description) {
		this.setPopUpMeerkats(popUpMeerkats);
		this.setTargetScore(targetScore);
		this.setTimeLimit(timeLimit);
		this.setTitle(title);
		this.setDescription(description);
		this.setNumber(number);
	}

	public int getPopUpMeerkats() {
		return popUpMeerkats;
	}

	public void setPopUpMeerkats(int popUpMeerkats) {
		this.popUpMeerkats = popUpMeerkats;
	}

	public int getTargetScore() {
		return targetScore;
	}

	public void setTargetScore(int targetScore) {
		this.targetScore = targetScore;
	}

	public int getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
}
