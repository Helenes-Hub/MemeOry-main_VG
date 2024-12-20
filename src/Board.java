import javax.swing.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Board extends JFrame implements ActionListener {
//.idea/vcs.xml
    private final Player player = new Player();
    private DifficultyLevel difficulty;
    private CardTheme theme;

    private final JPanel startPanel = new JPanel();
    private final JPanel chooseDifficultyPanel = new JPanel();
    private final JPanel chooseThemePanel = new JPanel();
    private final JPanel boardPanel = new JPanel();
    private final JPanel rulesPanel = new JPanel();
    private final JPanel aboutPanel = new JPanel();
    private final JPanel gameOverPanel = new JPanel();

    private final JLabel startLabel = new JLabel("MemeOry");
    private final JButton startButton = new JButton("Start new game");
    private final JButton viewHighScoreButton = new JButton("View high scores");
    private final JButton aboutButton = new JButton("About the game");
    private final JButton rulesButton = new JButton("Rules");

    private final JButton levelEasy = new JButton("Easy");
    private final JButton levelHard = new JButton("Hard");

    private final JButton themeAnimals = new JButton("Animals");
    private final JButton themeCharacters = new JButton("Characters");
    private Card[] cards;
    private Card cardToCheck1;
    private Card cardToCheck2;
    private boolean gameFinished = false;
    private boolean checkingMatch=false;
    private CardFactory factory = new CardFactory();

    private final JTextArea rulesTArea = new JTextArea(20,40);
    private final JButton backButton = new JButton("Back");
    private final JTextArea aboutTArea = new JTextArea(20, 40);
    private final JPanel viewHighScorePanel=new JPanel();
    private final JTextArea [] highScoreAreas=new JTextArea[2];

    private final Path easy = Paths.get("src/Score/Easy.txt");
    private final Path hard = Paths.get("src/Score/Hard.txt");
    private final JLabel thankYouLabel = new JLabel("Thanks for playing!");
    private final JLabel movesLabel = new JLabel("Moves: " + player.getMoves(), SwingConstants.CENTER);
    private final JButton playAgainButton = new JButton("Play Again");
    private final JButton exitButton = new JButton("Exit game");
    private int width=1000;
    private int height=800;
    int buttonWidth=200;
    ScoreReader scoreReader = ScoreReader.getInstance();


    public Board(){
        setTitle("MemeOry");
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        getContentPane().setBackground(new Color(255, 222, 222));
        displayStartPanel();

        startButton.addActionListener(l -> {
            remove(startPanel);
            displayChooseDifficulty();
            revalidate();
            repaint();

        });
        rulesButton.addActionListener(l -> {
            remove(startPanel);
            displayRulesPanel();
            revalidate();
            repaint();
        });
        aboutButton.addActionListener(l -> {
            remove(startPanel);
            displayAboutPanel();
            revalidate();
            repaint();
        });
        themeAnimals.addActionListener(l -> {theme = CardTheme.ANIMALS;
            remove(chooseThemePanel);
            setBoard(difficulty, theme);
            revalidate();
            repaint();});
        themeCharacters.addActionListener(l -> {theme = CardTheme.CHARACTERS;
            remove(chooseThemePanel);
            setBoard(difficulty, theme);
            revalidate();
            repaint();});

        viewHighScoreButton.addActionListener(l -> {
            remove(startPanel);
            displayHighScore();
            revalidate();
            repaint();

        });

        backButton.addActionListener(l -> {
            remove(aboutPanel);
            displayStartPanel();
            revalidate();
            repaint();
        });
    }

    public enum DifficultyLevel {
        EASY("easy", 12),
        HARD("hard", 24);

        public final String difficulty;
        public final int value;

        DifficultyLevel(String d, int v) {
            difficulty = d;
            value = v;
        }
    }

    public enum CardTheme {
        ANIMALS("animals"),
        CHARACTERS("characters");

        public final String theme;

        CardTheme(String t) {
            theme = t;
        }
    }

    public void displayStartPanel() {
        startPanel.setBounds(0, 0, width, height);
        startPanel.setLayout(null);
        startPanel.setBackground(new Color(255, 222, 222));
        add(startPanel);
        startLabel.setBounds((width-250)/2, 100, 400, 100);
        startPanel.add(startLabel);
        startLabel.setFont(new Font("Magneto", Font.BOLD, 50 ));
        startButton.setBounds((width-buttonWidth)/2, 250, 200, 100);
        startButton.setBackground(new Color(255, 240, 240));
        viewHighScoreButton.setBounds((width-buttonWidth)/2, 375, 200, 100);
        aboutButton.setBounds((width-buttonWidth)/2, 625, 200, 100);
        aboutButton.setBackground(new Color(255, 240, 240));
        rulesButton.setBounds((width-buttonWidth)/2, 500, 200, 100);
        rulesButton.setBackground(new Color(255, 240, 240));
        startPanel.add(startButton);
        startPanel.add(viewHighScoreButton);
        startPanel.add(aboutButton);
        startPanel.add(rulesButton);
        try {
            ImageIcon santaGif = new ImageIcon("src/MemoryImg/christmas/santa.gif");
            Image scaledImage = santaGif.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT);
            ImageIcon scaledSanta = new ImageIcon(scaledImage);

            JLabel santaLabel = new JLabel(scaledSanta);
            santaLabel.setBounds(width/2, 190, 80, 80);
            startPanel.add(santaLabel);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void displayChooseDifficulty() {
        chooseDifficultyPanel.setBounds(0, 0, 700, 700);
        chooseDifficultyPanel.setLayout(null);
        chooseDifficultyPanel.setBackground(new Color(255, 222, 222));
        add(chooseDifficultyPanel);
        levelEasy.setBounds((width-buttonWidth)/2, 200, 200, 100);
        levelEasy.setBackground(new Color(255, 240, 240));
        levelHard.setBounds((width-buttonWidth)/2, 350, 200, 100);
        levelHard.setBackground(new Color(255, 240, 240));
        chooseDifficultyPanel.add(levelEasy); chooseDifficultyPanel.add(levelHard);

        levelEasy.addActionListener(l -> {difficulty = DifficultyLevel.EASY;
        remove(chooseDifficultyPanel);
        displayChooseTheme();
        revalidate();
        repaint();});

        levelHard.addActionListener(l -> {difficulty = DifficultyLevel.HARD;
            remove(chooseDifficultyPanel);
            displayChooseTheme();
            revalidate();
            repaint();});
    }

    public void displayChooseTheme() {
        chooseThemePanel.setBounds(0, 0, 700, 700);
        chooseThemePanel.setLayout(null);
        chooseThemePanel.setBackground(new Color(255, 222, 222));
        add(chooseThemePanel);
        themeAnimals.setBounds((width-buttonWidth)/2, 200, 200, 100);
        themeAnimals.setBackground(new Color(255, 240, 240));
        themeCharacters.setBounds((width-buttonWidth)/2, 350, 200, 100);
        themeCharacters.setBackground(new Color(255, 240, 240));
        chooseThemePanel.add(themeAnimals); chooseThemePanel.add(themeCharacters);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (checkingMatch) {
            return;
        }
        JButton button = (JButton) e.getSource();
        Card clickedCard = (Card) button.getClientProperty("card");
        updateMoves();
        if (clickedCard == cardToCheck1 || clickedCard == cardToCheck2 || (clickedCard.getMatched())) {
            return;
        }
        clickedCard.setFlipped(!clickedCard.getFlipped());
        if (cardToCheck1 == null) {
            cardToCheck1 = clickedCard;
        } else if (cardToCheck2 == null) {
            cardToCheck2 = clickedCard;
            checkIfMatched(cardToCheck1, cardToCheck2);
        }
    }

    public void setBoard(DifficultyLevel difficulty, CardTheme theme) {
        player.nullMoves();
        boardPanel.removeAll();
        cards = factory.getMemoryCards(difficulty.value, theme.theme);

        boardPanel.setBounds(0, 0, width, height);
        boardPanel.setLayout(new BorderLayout());
        boardPanel.setBackground(new Color(255, 222, 222));

        int topPadding;
        if (difficulty == DifficultyLevel.EASY) {
            topPadding = 175;
        } else {
            topPadding = 20;
        }

        JPanel cardsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        cardsPanel.setBackground(new Color(255, 222, 222));
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(topPadding, 0, 0, 0));
        for (Card card : cards) {
            JButton button = card.getButton();
            card.getButton().setIcon(card.getBack());
            cardsPanel.add(button);
            button.addActionListener(this);
        }
        movesLabel.setPreferredSize(new Dimension(width, 50));
        boardPanel.add(movesLabel, BorderLayout.NORTH);

        enableButtons(true);
        boardPanel.add(cardsPanel, BorderLayout.CENTER);

        add(boardPanel);
    }

    public void displayRulesPanel() {
        rulesPanel.setBounds(0, 0, 700, 700);
        rulesPanel.setLayout(null);
        rulesPanel.setBackground(new Color(255, 222, 222));
        add(rulesPanel);
        rulesTArea.setBounds((width-400)/2, 80, 400, 400);
        rulesTArea.setText("Memory Rules\n\nA classic memory card game where you test your memory by matching pairs of cards. A move" +
                " consists of turning over 2 cards. If the cards match, you get to keep the cards and get a point added to your score. " +
                "If the cards do not match, the cards are turned over again. You must try to remember where you have seen cards, so you" +
                " can use them to make a match.");
        rulesTArea.setBackground(new Color(255, 190, 190));
        rulesTArea.setFont(new Font("Arial", Font.BOLD, 24));
        rulesTArea.setEditable(false);
        rulesTArea.setLineWrap(true);
        rulesTArea.setWrapStyleWord(true);
        rulesPanel.add(rulesTArea);
        backButton.setBounds((width-buttonWidth)/2, 500, 200, 100);
        backButton.setBackground(new Color(255, 240, 240));
        rulesPanel.add(backButton);

        backButton.addActionListener(l -> {
            remove(rulesPanel);
            displayStartPanel();
            revalidate();
            repaint();
        });
    }

    public void updateMoves(){
        player.incrementMoves();
        movesLabel.setText("Moves: "+player.getMoves());


    }

    public void displayAboutPanel() {
        aboutPanel.setBounds(0, 0, 700, 700);
        aboutPanel.setLayout(null);
        aboutPanel.setBackground(new Color(255, 222, 222));
        add(aboutPanel);
        aboutTArea.setBounds((width-400)/2, 80, 400, 400);
        aboutTArea.setText("\nMemeOry Game v.1.0\n\nDeveloped by: Christer, Hannes, Helene, Jennifer & Paulina\n\nRelease date: December 2024\n\nBuilt with: Java and love");
        aboutTArea.setBackground(new Color(255, 190, 190));
        aboutTArea.setFont(new Font("Arial", Font.BOLD, 24));
        aboutTArea.setEditable(false);
        aboutTArea.setLineWrap(true);
        aboutTArea.setWrapStyleWord(true);
        aboutPanel.add(aboutTArea);
        backButton.setBounds((width-buttonWidth)/2, 500, 200, 100);
        backButton.setBackground(new Color(255, 240, 240));
        aboutPanel.add(backButton);


    }
    private void checkIfMatched (Card card1, Card card2) {
        enableButtons(false);
        if (card1.getID() == card2.getID()) {
            card1.setMatched(true);
            card2.setMatched(true);
            cardToCheck1=null;
            cardToCheck2=null;
            winChecker();
        }
        else{
            Timer timer = new Timer(1000, evt -> {
                card1.setFlipped(false);
                card2.setFlipped(false);
                enableButtons(true);
                cardToCheck1=null;
                cardToCheck2=null;

                ((Timer) evt.getSource()).stop();
            });

            timer.setRepeats(false);
            timer.start();
        }
    }

    private void winChecker() {
        gameFinished = true;
        for (Card card : cards) {
            if (!card.getMatched()) {
                gameFinished = false;
                enableButtons(true);
            }
        }
        if (gameFinished) {
            player.compareHighscore(difficulty.difficulty);
            Timer timer = new Timer(2000, evt -> {
                remove(boardPanel);
                displayGameOverPanel(player.getMoves()); // or whatever score calculation you have
                revalidate();
                repaint();

                ((Timer) evt.getSource()).stop();
            });

            timer.setRepeats(false);
            timer.start();
        }
    }

    private void enableButtons(Boolean trueOrFalse) {
        if (trueOrFalse) {
            checkingMatch=false;
        }
        else{
            checkingMatch=true;
        }
    }

    private void displayHighScore(){
        viewHighScorePanel.removeAll();
        for(int i=0; i<highScoreAreas.length;i++){
            highScoreAreas[i]=new JTextArea();

            highScoreAreas[i].setBackground(new Color(255, 190*i+10, 190));
            highScoreAreas[i].setFont(new Font("Arial", Font.BOLD, 20));
            highScoreAreas[i].setEditable(false);
            highScoreAreas[i].setLineWrap(true);
            highScoreAreas[i].setWrapStyleWord(true);
            viewHighScorePanel.add(highScoreAreas[i]);

        }
        List<String> scores=new ArrayList<>();
        scores=scoreReader.read(easy);
        System.out.println(scores.toString());
        highScoreAreas[0].setText("\nHighscore easy: \n");

        for (String score : scores) {
            String[] parts = score.split(",");
            highScoreAreas[0].append("\n" + parts[1]+" Moves by User: "+ parts[0]);
        }
        scores=scoreReader.read(hard);
        highScoreAreas[1].setText("\nHighscore hard: \n");
        for (String score : scores) {
            String[] parts = score.split(",");
            highScoreAreas[1].append("\n" + parts[1]+" Moves by User: "+ parts[0]);
        }
        highScoreAreas[0].revalidate();
        highScoreAreas[0].repaint();
        highScoreAreas[1].revalidate();
        highScoreAreas[1].repaint();
        viewHighScorePanel.setBounds(0, 0, width, 700);
        viewHighScorePanel.setLayout(null);
        viewHighScorePanel.setBackground(new Color(255, 222, 222));
        highScoreAreas[0].setBounds(0, 0, width/2, 500);
        highScoreAreas[1].setBounds(500, 0, width/2, 500);
        viewHighScorePanel.add(highScoreAreas[0]);
        viewHighScorePanel.add(highScoreAreas[1]);
        backButton.setBounds((width-buttonWidth)/2, 500, 200, 100);
        viewHighScorePanel.add(backButton);
        backButton.setBackground(new Color(255, 240, 240));
        //viewHighScorePanel.add(backButton);
        this.add(viewHighScorePanel);
        revalidate();
        repaint();
        backButton.addActionListener(l -> {
            remove(viewHighScorePanel);
            displayStartPanel();
            revalidate();
            repaint();
        });
    };

    public void displayGameOverPanel(int score) {
        for (Card card : cards) {
            boardPanel.remove(card.getButton());
            boardPanel.revalidate();
            }

        gameOverPanel.setBounds(0, 0, 700, 950);
        gameOverPanel.setLayout(null);
        gameOverPanel.setBackground(new Color(255, 222, 222));
        add(gameOverPanel);

        thankYouLabel.setBounds((width-250)/2, 100, 400, 200);
        thankYouLabel.setFont(new Font("Magneto", Font.PLAIN, 25));

        playAgainButton.setBounds((width-buttonWidth)/2, 250, 200, 100);// Same position as startButton
        playAgainButton.setBackground(new Color(255, 240, 240));
        exitButton.setBounds((width-buttonWidth)/2, 375, 200, 100);       // Same position as rulesButton
        exitButton.setBackground(new Color(255, 240, 240));


        gameOverPanel.add(thankYouLabel);

        gameOverPanel.add(playAgainButton);
        gameOverPanel.add(exitButton);

        playAgainButton.addActionListener(l -> {
            remove(gameOverPanel);
            displayStartPanel();
            revalidate();
            repaint();
        });

        exitButton.addActionListener(l -> {
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        new Board();
    }
}