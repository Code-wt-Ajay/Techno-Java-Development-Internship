
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TicTacToe {
    int boardWidth = 600;
    int boardHeight = 650; // Adjust height for score panel

    JFrame frame = new JFrame("Tic-Tac-Toe");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanal = new JPanel();
    JPanel scorePanel = new JPanel();

    JButton[][] board = new JButton[3][3];
    String playerX = "X";
    String playerO = "O";
    String Currentplayer = playerX;
    String playerMarker = playerX; // The player's chosen marker (X or O)
    boolean gameOver = false;
    int turns = 0;

    // Score variables
    int playerXScore = 0;
    int playerOScore = 0;
    JLabel scoreLabel = new JLabel();

    // Reset button
    JButton resetButton = new JButton("Reset Game");

    TicTacToe() {
        // Choose player marker
        choosePlayerMarker();

        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setBackground(Color.white);
        textLabel.setForeground(Color.black);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Tic-Tac-Toe");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        // Score Panel
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 30));
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        updateScoreLabel();
        scorePanel.add(scoreLabel);
        frame.add(scorePanel, BorderLayout.SOUTH);

        boardPanal.setLayout(new GridLayout(3, 3));
        boardPanal.setBackground(Color.darkGray);
        frame.add(boardPanal, BorderLayout.CENTER);

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanal.add(tile);

                tile.setBackground(Color.white);
                tile.setForeground(Color.black);
                tile.setFont(new Font("Arial", Font.BOLD, 100));
                tile.setFocusable(false);

                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (((JButton) e.getSource()).getText() == "" && !gameOver) {
                            tile.setText(Currentplayer);
                            turns++;
                            checkWinner();
                            togglePlayer();
                        }
                    }
                });
            }
        }

        // Reset button functionality
        resetButton.setFont(new Font("Arial", Font.BOLD, 20));
        resetButton.setFocusable(false);
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });
        scorePanel.add(resetButton);
    }

    // Method to choose player marker (X or O) at the beginning
    void choosePlayerMarker() {
        String[] options = { playerX, playerO };
        int choice = JOptionPane.showOptionDialog(frame, "Choose your marker", "Player Choice",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (choice == 1) {
            playerMarker = playerO;
            Currentplayer = playerO; // The player starts with O
        } else {
            playerMarker = playerX;
            Currentplayer = playerX; // Default to X if no choice is made
        }
    }

    void checkWinner() {
        // Horizontal check
        for (int r = 0; r < 3; r++) {
            if (board[r][0].getText() == "")
                continue;

            if (board[r][0].getText() == board[r][1].getText() &&
                    board[r][1].getText() == board[r][2].getText()) {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[r][i]);
                }
                updateScore();
                gameOver = true;
                return;
            }
        }
        // Vertical check
        for (int c = 0; c < 3; c++) {
            if (board[0][c].getText() == "")
                continue;

            if (board[0][c].getText() == board[1][c].getText() &&
                    board[1][c].getText() == board[2][c].getText()) {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[i][c]);
                }
                updateScore();
                gameOver = true;
                return;
            }
        }
        // Diagonal checks
        if (board[0][0].getText() == board[1][1].getText() &&
                board[1][1].getText() == board[2][2].getText() &&
                board[0][0].getText() != "") {
            for (int i = 0; i < 3; i++) {
                setWinner(board[i][i]);
            }
            updateScore();
            gameOver = true;
            return;
        }
        if (board[0][2].getText() == board[1][1].getText() &&
                board[1][1].getText() == board[2][0].getText() &&
                board[0][2].getText() != "") {
            setWinner(board[0][2]);
            setWinner(board[1][1]);
            setWinner(board[2][0]);
            updateScore();
            gameOver = true;
            return;
        }
        if (turns == 9) {
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    setTie(board[r][c]);
                }
            }
            gameOver = true;
        }
    }

    void setWinner(JButton tile) {
        tile.setForeground(Color.green);
        tile.setBackground(Color.white);
        textLabel.setText(Currentplayer + " is the Winner");
    }

    void setTie(JButton tile) {
        tile.setForeground(Color.red);
        tile.setBackground(Color.white);
        textLabel.setText("<<< Tie >>>!");
    }

    void togglePlayer() {
        Currentplayer = (Currentplayer == playerX) ? playerO : playerX;
        textLabel.setText(Currentplayer + "'s turn");
    }

    void updateScore() {
        if (Currentplayer == playerX) {
            playerXScore++;
        } else {
            playerOScore++;
        }
        updateScoreLabel();
    }

    void updateScoreLabel() {
        scoreLabel.setText("Score - X: " + playerXScore + " | O: " + playerOScore);
    }

    void resetGame() {
        turns = 0;
        gameOver = false;
        Currentplayer = playerMarker; // Start again with the player's chosen marker
        textLabel.setText("Tic-Tac-Toe");
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c].setText("");
                board[r][c].setBackground(Color.white);
            }
        }
    }
}
