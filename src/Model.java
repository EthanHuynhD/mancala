
import java.util.ArrayList;
import java.util.Random;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Model class is the Model of MVC pattern. This class contains collections,
 * accessors, mutators, and attach method for the listeners.
 * 
 * @author Tripod - Ethan Huynh, Raza Ahmad, Ching Tsoi
 */
public class Model {
	MainFrame input;
	private ArrayList<Integer> pits;
	private ArrayList<ChangeListener> listeners;
	private Style style = new Circular();
	// backup last move
	private ArrayList<Integer> pitBackUp;
	// count who's turn
	private int turn;
	private int undoturn;
	private int undoCount;
	private String win;
	private boolean doubleUndo;

	/**
	 * Constructs a Model object that takes in an integer and initialize the number
	 * of stones in each pit.
	 * 
	 * @param j
	 */
	public Model(int j) {
		doubleUndo = false;
		win = "N";
		turn = 1;
		pits = new ArrayList<>();
		pitBackUp = new ArrayList<>();
		listeners = new ArrayList<>();
		for (int i = 0; i < 14; i++) {
			if (i == 13 || i == 0) {
				pits.add(0);
			} else {
				pits.add(j);
			}
		}
	}

	/**
	 * Start the game using a random assignment/priority.
	 */
	public void start() {
		Random ran = new Random();
		turn = (int) ran.nextInt(2) + 1;
		System.out.println(turn);
	}

	/**
	 * Retrieve the Style of the board.
	 * 
	 * @return
	 */
	public Style getStyle() {
		return style;
	}

	/**
	 * Change the style of the game.
	 * @param style
	 */
	public void setStyle(Style style) {
		this.style = style;
		for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}

	/**
	 * Undo the game to previous state/step up to three times per independent move.
	 * The method also updates ChangeListeners.
	 */
	public void undo() {
		boolean test = false;
		if (pitBackUp.isEmpty()) {
			return;
		}
		for (int i = 0; i < pits.size(); i++) {
			if (pits.get(i) - pitBackUp.get(i) != 0) {
				test = true;
			}
		}
		if (!test) // if test is false
		{
			return;
		}
		if (undoCount % 4 == 3) {
			undoCount = 0;
			return;
		} else {
			pits = new ArrayList<>(pitBackUp);
			undoturn = turn;
			undoCount++;
			if (doubleUndo == true) {
				turn -= 2;
			} else {
				turn--;
			}
			for (ChangeListener l : listeners) {
				l.stateChanged(new ChangeEvent(this));
			}
		}
	}
	/**
	 * Decide the winner of the game depending the conditions. When one side runs
	 * out of stones to click, the following conditions are taken into calculation:
	 * 	1. Number of stones still on boards go to the mancala of the respective player
	 *  depending on the side where the stones are located (bottom belongs to
	 *  Player A and top belongs to Player B).
	 *  2. If the number of stones from both sides are equal, it is a tie game.
	 */
	public void win() {
		int A;
		int B;
		if (pits.get(1) == 0 && pits.get(2) == 0 && pits.get(3) == 0 && pits.get(4) == 0 && pits.get(5) == 0
				&& pits.get(6) == 0) {
			A = pits.get(7) + pits.get(8) + pits.get(9) + pits.get(10) + pits.get(11) + pits.get(12);
			B = pits.get(13);
			pits.set(13, A + B);
			if (pits.get(0) > pits.get(13))
				win = "Player B wins";
			else if (pits.get(13) > pits.get(0))
				win = "Player A wins";
			else
				win = "Tie Game";
		} else if (pits.get(7) == 0 && pits.get(8) == 0 && pits.get(9) == 0 && pits.get(10) == 0 && pits.get(11) == 0
				&& pits.get(12) == 0) {
			A = pits.get(6) + pits.get(1) + pits.get(2) + pits.get(3) + pits.get(4) + pits.get(5);
			B = pits.get(0);
			pits.set(0, A + B);
			if (pits.get(0) > pits.get(13))
				win = "Player B wins";
			else if (pits.get(13) > pits.get(0))
				win = "Player A wins";
			else
				win = "Tie Game";
		}
	}
	/**
	 * Decide whether or how to update the game board depending on where
	 * the pit is clicked. If the pit is clicked on an empty pit, nothing happens.
	 * @param i
	 */
	public void update(int i) {
		if (pits.get(i) == 0) {
			return;
		}
		pitBackUp = new ArrayList<>(pits); // backup last move before update
		Integer x = pits.get(i);
		if (x != 0) {
			doubleUndo = false;
			turn++;
			pits.set(i, 0);
			updatePit(i, x);
			if (undoturn < turn)
				undoCount = 0;
		}
		win();
		for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}
	/**
	 * Decide whether or how to update the game board depending on where
	 * the pit is clicked. This is a continuation of the update(int) method.
	 * The flows of the stones are pointed to the correct direction. This method
	 * also aids the undo() method in keeping track of the number of undo. In addition,
	 * the method decides where stones go according to some special rules.
	 * Special Rules:
	 * 	1. During gameplay, if the last stone from a player's hand ends up on an empty pit on
	 *  on the same player's side, the player gets to hijack all stones in the pit across the
	 *  other side of the opposing player.
	 *  2. If the last stone from a player's hand ends up on the said player's mancala, the player
	 *  gets an extra turn. There is no limit to how many extra turn this player gets as long
	 *  as the player manage to get the last stone on the player's hand from a pit to fall into the
	 *  player's own mancala.
	 * @param i
	 * @param x
	 */
	public void updatePit(int i, int x) {
		int temp = 0;
		int index = i;
		if (i < 7) {
			if ((i - x) % 13 == 0) {
				turn++;
				doubleUndo = true;
			}
			for (int j = x; j > 0; j--) {
				if (i < 7 && i > 0) {
					i--;
					pits.set(i, pits.get(i) + 1);
				} else if (i == 0) {
					i = 7;
					j++;
				} else if (i == 13) {
					i = 6;
					pits.set(i, pits.get(i) + 1);
				} else if (i >= 7) {
					pits.set(i, pits.get(i) + 1);
					i++;
				}
			}
			if (index - x > 0 && pits.get(index - x) == 1) {
				temp = pits.get(index - x + 6);
				pits.set(index - x + 6, 0);
				pits.set(0, temp + pits.get(0));
			}
		} else {
			if ((i + x) % 13 == 0) {
				turn++;
				doubleUndo = true;
			}
			for (int j = x; j > 0; j--) {
				if (i < 13 && i >= 7) {
					i++;
					pits.set(i, pits.get(i) + 1);
				} else if (i == 13) {
					i = 6;
					j++;
				} else if (i == 0) {
					i = 7;
					pits.set(i, pits.get(i) + 1);
				} else if (i <= 6) {
					pits.set(i, pits.get(i) + 1);
					i--;
				}
			}
			if (index + x < 13 && pits.get(index + x) == 1) {
				temp = pits.get(index + x - 6);
				pits.set(index + x - 6, 0);
				pits.set(13, temp + pits.get(13));
			}
		}
	}
	/**
	 * Retrieve the collection of pits and mancalas.
	 * @return
	 */
	public ArrayList<Integer> getData() {
		return pits;
	}
	/**
	 * Retrieve the stone(s) in a specified pit.
	 * @param i
	 * @return stone
	 */
	public int getStone(int i) {
		int stone = (int) pits.get(i);
		return stone;
	}
	/**
	 * Retrieve the number representing the turn. This number is not yet %2.
	 * @return turn
	 */
	public int getTurn() {
		return turn;
	}
	/**
	 * Retrieve the number representing the undo's count. This number is not yet %2.
	 * @return undoCount
	 */
	public int getUndoCount() {
		return undoCount;
	}
	/**
	 * Attach a ChangeListener to the collection of ChangeListeners in the Model.
	 * @param l
	 */
	public void attach(ChangeListener l) {
		listeners.add(l);
	}
	/**
	 * Retrieve a String referring to a winner or a tie game.
	 * @return win
	 */
	public String getWin() {
		return win;
	}
}