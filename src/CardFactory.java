import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CardFactory {
    private final Path animal = Paths.get("src/MemoryImg/animals");
    private final Path character = Paths.get("src/MemoryImg/characters");
    private final Path backOfCard = Paths.get("src/memoryImg/BackOfCard");
    private final Icon[] characters;
    private final Icon[] animals;
    private final Icon[] backOfCards;
    private JButton[] guiButtons;
    private Card[] cards;
    private String categoryChoice;


    public CardFactory() {
        FileHandler files = new FileHandler();
        characters = files.iconReader(character, 12);
        animals = files.iconReader(animal, 12);
        backOfCards = files.iconReader(backOfCard,1);


    }

    public Card[] getMemoryCards(int amount, String category) {

        this.categoryChoice = category;
        this.guiButtons = new JButton[amount];

        createCards();
        setIcons();
        return cards;
    }

    private void createCards() {
        cards = new Card[guiButtons.length];
        int buttonID = 0;
        for (int i = 0; i < guiButtons.length; i++) {
            guiButtons[i] = new JButton();
            guiButtons[i].setPreferredSize(new Dimension(140, 140));
            Card card = new Card(guiButtons[i], buttonID);
            this.cards[i] = card;
            if (i % 2 == 1) {
                buttonID++;
            }
        }
        shuffleCards((cards));
    }

    private void setIcons() {

        Icon[] tempIconHolder = new Icon[cards.length];
        switch (this.categoryChoice.toUpperCase()) {
            case "CHARACTERS":
                tempIconHolder = characters;
                break;
            case "ANIMALS":
                tempIconHolder = animals;
                break;
        }
        shuffleIcons((tempIconHolder));
        for (int i = 0; i < cards.length; i++) {
            cards[i].setFront(tempIconHolder[cards[i].getID()]);
            cards[i].setBack(backOfCards[0]);
        }
    }


    public void shuffleCards(Card[] lists) {
        Collections.shuffle(Arrays.asList(lists));
    }

    public void shuffleIcons(Icon[] icons) {
        Collections.shuffle(Arrays.asList(icons));
    }

}
