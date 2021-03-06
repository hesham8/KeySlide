package views.game;

import instruction.InstructionController;
import instruction.InstructionStatus;
import utilities.GameLog;
import views.Window;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.io.File;
import java.util.logging.Level;

/**
 * GamePanel class: Contains all game elements
 *
 * @author heshamsalman
 */
public class GamePanel extends JPanel implements KeyListener {
    private static final long serialVersionUID = 1L;
    DirectionPanel directionPanel;
    Clip clip;
    InstructionController controller = InstructionController.getInstance();
    Timer timer;
    JLabel scoreLabel;
    private JPanel timePanel;
    private JProgressBar timeBar;
    private int timePosition = 0;
    private int score = 0;
    /**
     * Constructor
     */
    public GamePanel() {
        setupGui();
    }

    /**
     * Sets up GUI
     */
    private void setupGui() {
        setSize(1280, 720);
        setFocusable(true);
        setLayout(new BorderLayout());
        scoreLabel = new JLabel("0");
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 40));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setOpaque(true);
        scoreLabel.setBorder(BorderFactory.createEmptyBorder());
        timePanel = new JPanel();
        timeBar = new JProgressBar();
        timer = new Timer(20, e -> {
            timeBar.setMaximum(setTime());
            timeBar.setValue(timePosition);
            timePosition += 20;
            if (controller.getStatus() == InstructionStatus.STOP && timeBar.getValue() >= setTime()) {
                nextRound();
            } else if (timeBar.getValue() >= setTime()) {
                gameOver();
            }
        });
        timeBar.setPreferredSize(new Dimension(1280, 50));
        timeBar.setMaximum(setTime());
        timeBar.setMinimum(0);
        timePanel.add(timeBar);
        add(scoreLabel, BorderLayout.NORTH);
        add(timePanel, BorderLayout.SOUTH);
        addKeyListener(this);
        directionPanel = DirectionPanelFactory.getNextPanel();
        add(directionPanel, BorderLayout.CENTER);
        updateGUI();
        initializeAudio(new File("Assets/Audio/Chiptune.wav"));
    }

    /**
     * Starts instance of game
     */
    public void start() {
    	startAudio();
        score = 0;
        timePosition = 0;
        timer.start();
        updateGUI();
    }

    /**
     * Plays audio.
     *
     * @param url Location of the file
     */
    private void initializeAudio(final File url) {
        GameLog.log.entering(getClass().getName(), "initializeAudio");
        try {
            stopPlay();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(inputStream);
            GameLog.log.log(Level.INFO, "Audio Initialized");
        } catch (Exception e) {
            stopPlay();
            System.err.println(e.getMessage());
        }
        GameLog.log.exiting(getClass().getName(), "initializeAudio");
    }

    /**
     * Starts audio
     */
    private void startAudio() {
    	try {
            GameLog.log.log(Level.INFO, "Rewinding Audio");
    		clip.setFramePosition(0);
    		clip.start();
            GameLog.log.log(Level.INFO, "Playing Audio");
    	} catch (Exception e) {
            System.err.println(e.getMessage());
    	}
    }

    /**
     * Stops audio.
     */
    private void stopPlay() {
        GameLog.log.entering(getClass().getName(), "stopPlay");
        if (clip != null) {
            clip.stop();
            GameLog.log.log(Level.INFO, "Stopping Audio");
        }
        GameLog.log.exiting(getClass().getName(), "stopPlay");
    }


    /**
     * Ends game. Disposes of all game resources and performs operations to setup for next game.
     */
    private void gameOver() {
        GameLog.log.entering(getClass().getName(), "gameOver");
        stopPlay();
        timePosition = 0;
        timer.stop();
        timeBar.setValue(timePosition);
        Window.switchToGameOver();
        GameLog.log.exiting(getClass().getName(), "gameOver");
        updateUI();
    }

    /**
     * The actions to be carried out for next round
     */
    private synchronized void nextRound() {
        restartTimer();
        score += 1;
        updateGUI();
        updateUI();
        System.gc();
    }

    /**
     * Restarts timer.
     */
    private synchronized void restartTimer() {
        GameLog.log.entering(getClass().getName(), "restartTimer");
        timePosition = 0;
        timeBar.setValue(timePosition);
        timer.restart();
        GameLog.log.exiting(getClass().getName(), "restartTimer");
    }


    /**
     * Sets time per round
     *
     * @return time, the maximum value of time for that round
     */
    private int setTime() {
        int time = 1500 - (30 * score);
        if (time < 500) {
            return 500;
        } else {
            return time;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Handles key presses
     *
     * @param e caller
     */
    @Override
    public void keyPressed(KeyEvent e) {
        GameLog.log.entering(getClass().getName(), "keyPressed");
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT
                || e.getKeyCode() == KeyEvent.VK_UP
                || e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (controller.getStatus() == InstructionStatus.STOP)
                gameOver();
            else if (e.getKeyCode() == directionPanel.getInternalKey()) {
                nextRound();
            } else {
                gameOver();
            }
        }
        GameLog.log.exiting(getClass().getName(), "keyPressed");
    }

    /**
     * Updates the gui between rounds
     */
    private synchronized void updateGUI() {
        remove(directionPanel);
        directionPanel = DirectionPanelFactory.getNextPanel();
        directionPanel.updateSelf();
        add(directionPanel);
        timeBar.setForeground(directionPanel.getColors()[0]);
        timePanel.setBackground(directionPanel.getColors()[2]);
        scoreLabel.setBackground(directionPanel.getColors()[2]);
        scoreLabel.setText("" + score);
    }

    /**
     * @return score -- value of points
     */
    public int getScore() {
        return score;
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocus();
    }
}
