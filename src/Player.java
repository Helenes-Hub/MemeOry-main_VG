import javax.swing.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Player {
    private int moves;
    private String name;
    ScoreReader scoreReader = ScoreReader.getInstance();
    private final Path easy = Paths.get("src/Score/Easy.txt");
    private final Path hard = Paths.get("src/Score/Hard.txt");
    private List<String> scores;
    // Constructor
    public Player() {
        this.moves = 0;
    }


    // Increment score when player makes a match
    public void incrementMoves() {
        moves++;
    }

    // Get current score
    public int getMoves() {
        return moves;
    }

    // Get player name
    public void setName(String name) {

    }
    public void nullMoves(){
        this.moves = 0;
    }

    public void compareHighscore(String difficulty){
        Path p = null;
        if (difficulty.equals("Easy".toLowerCase())){
            p=easy;
        }
        else if (difficulty.equals("Hard".toLowerCase())){
            p=hard;
        }
        scores=scoreReader.read(p);

        if (scores.isEmpty()) {
            String name = JOptionPane.showInputDialog(null, "Enter username: ");
            scoreReader.write(p, name, moves);
            return;
        }
        for(int i=0; i<scores.size(); i++){
            String[] parts = scores.get(i).split(",");
            int currentScore = Integer.parseInt(parts[1]);
            if(this.moves<currentScore || scores.size()<5){
                String name=JOptionPane.showInputDialog(null, "New highscore!\n" +
                        "enter your username: ");
                scoreReader.write(p, name, moves);
                break;
            }
        }

    }
}