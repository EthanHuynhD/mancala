
import java.util.ArrayList;
import java.util.Random;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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

	public void start() {
		Random ran = new Random();
		turn = (int) ran.nextInt(2) + 1;
		System.out.println(turn);
	}

	public Style getStyle() {
		return style;
	}

	// Plugging in strategy to the context program
	public void setStyle(Style style) {
		this.style = style;
		for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}

	// undo to last move
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
			undoturn=turn;
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

	public void update(int i) {
		if(pits.get(i)==0) {
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

	public ArrayList<Integer> getData() {
		return pits;
	}

	public int getStone(int i) {
		int stone = (int) pits.get(i);
		return stone;
	}

	public int getTurn() {
		return turn;
	}

	public int getUndoCount() {
		return undoCount;
	}

	public void attach(ChangeListener l) {
		listeners.add(l);
	}

	public String getWin() {
		return win;
	}
}