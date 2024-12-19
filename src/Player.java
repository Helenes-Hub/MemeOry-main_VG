public class Player {
    private int score;
    private String name;

    // Constructor
    public Player() {}

    public Player(String name) {
        this.name = name;
        this.score = 0;  // Initialize score to 0
    }

    // Increment score when player makes a match
    public void incrementScore() {
        score++;
    }

    // Get current score
    public int getScore() {
        return score;
    }

    // Get player name
    public String getName() {
    return name;
    }
}