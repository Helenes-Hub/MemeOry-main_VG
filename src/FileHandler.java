import javax.swing.*;
import java.nio.file.Path;


public class FileHandler {
    public FileHandler() {

    }

    public Icon[] iconReader(Path p, int amount) {
        Icon[] icons = new Icon[amount];
        for (int i = 0; i < amount; i++) {
            String imagePath = p.toString() + "/" + (i + 1) + ".png";
            try {
                Icon originalIcon = new ImageIcon(imagePath);
                icons[i] = originalIcon;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return icons;
    }

}
