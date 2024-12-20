import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ScoreReader {
    private static ScoreReader instance;

    private ScoreReader() {


    }

    public static ScoreReader getInstance() {
        if (instance == null) {
            instance = new ScoreReader();
        }
        return instance;
    }

    public List<String> read(Path p) {
        List<String> fileToText;

        try {
            if (!Files.exists(p)) {
                Files.createFile(p);
            }
            fileToText = Files.readAllLines(p);
        } catch (IOException e) {
            System.out.println("Något gick fel när filen skulle läsas eller skapas");
            throw new RuntimeException(e);
        }
        return fileToText;
    }

    public void write(Path p, String userName, int score) {
        List<String> lines;

        try {
            if (!Files.exists(p)) {
                Files.createFile(p);
            }
            lines = Files.readAllLines(p);
        } catch (IOException e) {
            System.out.println("Något gick fel när filen skulle läsas eller skapas");
            throw new RuntimeException(e);
        }

        lines.add(userName + "," + score);

        lines.sort((line1, line2) -> {
            int score1 = Integer.parseInt(line1.split(",")[1].trim());
            int score2 = Integer.parseInt(line2.split(",")[1].trim());
            return Integer.compare(score1, score2);
        });

        if (lines.size() > 5) {
            lines = lines.subList(0, 5);
        }

        try {
            Files.write(p, lines);
        } catch (IOException e) {
            System.out.println("Något gick fel när filen skulle skrivas");
        }
    }

}
