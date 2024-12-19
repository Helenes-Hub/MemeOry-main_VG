import javax.swing.*;

public class Card {
    private Boolean isFlipped = false;
    private Boolean isMatched = false;
    private Icon front;
    private Icon back;
    final JButton button;
    final int cardID;

    public Card(JButton b, int id) {
        this.button = b;
        this.cardID = id;
        this.button.putClientProperty("card", this);


    }

    public Icon getFront() {
        return front;
    }

    public void setFront(Icon front) {
        this.front = front;
    }

    public Icon getBack() {
        return back;
    }

    public void setBack(Icon back) {
        this.back = back;
    }

    public Boolean getFlipped() {
        return isFlipped;
    }

    public void setFlipped(Boolean Flipped) {
        isFlipped = Flipped;
        if (isFlipped) {
            button.setIcon(front);
        } else {
            button.setIcon(back);
        }
    }

    public Boolean getMatched() {
        return isMatched;
    }

    public void setMatched(Boolean matched) {
        isMatched = matched;
    }

    public JButton getButton() {
        return button;
    }


    public int getID() {
        return cardID;
    }
}
