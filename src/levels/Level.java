package levels;

import java.io.Serializable;

/**
 * A Meerkat Challenge level
 * @author John Casson
 *
 */
public class Level implements Serializable {
	private static final long serialVersionUID = 32551L;
	private int meerkats;
	private int targetScore;
	private int timeLimit;
	private String title;
	private String description;
	private int number;
	
	public Level (int number, int popUpMeerkats, int targetScore, int timeLimit, String title, String description) {
		this.setMeerkats(popUpMeerkats);
		this.setTargetScore(targetScore);
		this.setTimeLimit(timeLimit);
		this.setTitle(title);
		this.setDescription(description);
		this.setNumber(number);
	}

	/**
	 * Gets the number of meerkats in the level
	 * @return
	 */
	public int getMeerkats() {
		return meerkats;
	}

	/**
	 * Sets the number of meerkats in the level
	 * @param meerkats
	 */
	public void setMeerkats(int meerkats) {
		this.meerkats = meerkats;
	}

	/**
	 * Gets the target score
	 * @return
	 */
	public int getTargetScore() {
		return targetScore;
	}

	
	/**
	 * Sets the target score
	 * @param targetScore
	 */
	public void setTargetScore(int targetScore) {
		this.targetScore = targetScore;
	}

	/**
	 * Gets the time limit
	 * @return
	 */
	public int getTimeLimit() {
		return timeLimit;
	}

	/**
	 * Sets the time limit
	 * @param timeLimit
	 */
	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}

	/**
	 * Gets the title
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the description
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the level number
	 * @return
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Sets the level number
	 * @param number
	 */
	public void setNumber(int number) {
		this.number = number;
	}
}
