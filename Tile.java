package application;

import javafx.scene.paint.Color;

import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

class Tile extends StackPane {

	Button btn = new Button();
	int x, y = 0;
	boolean hasBomb;
	Text text = new Text();
	int numBombs = 0;
	Color color = null;
	boolean flagged = false;
	ArrayList<Tile> neighbours = new ArrayList<Tile>();
	boolean active = true;

	public Tile(int x, int y, boolean hasBomb) {
		this.x = x;
		this.y = y;
		this.hasBomb = hasBomb;

		if (hasBomb) {
			Main.numBombs++;
		}

		btn.setMinHeight(40);
		btn.setMinWidth(40);

		btn.setOnMouseClicked(e -> {
			onClick(e);
		});

		getChildren().addAll(btn, text);

		setTranslateX(x * 40);
		setTranslateY(y * 40);

	}

	private void onClick(MouseEvent e) {

		// Left Click
		if (e.getButton() == MouseButton.PRIMARY) {
			
			if(!flagged) {

			btn.setBackground(null);
			btn.setDisable(true);
			active = false;

			if (hasBomb) {
				Main.gameOver();
			} else {
				// Blank
				if (this.numBombs == 0) {
					blankClick(this);
				} else {
					text.setFont(Font.font(null, FontWeight.EXTRA_BOLD, 20));
					text.setText(Integer.toString(numBombs));
					text.setFill(color);
				}
			}
			}
		}
		// Right Click
		else {
			if (flagged == false) {
				flagged = true;
				text.setFont(Font.font(null, FontWeight.EXTRA_BOLD, 10));
				text.setText("FLAG");
				if (this.hasBomb) {
					Main.foundBombs++;
					if (Main.foundBombs == Main.numBombs) {
						Main.win();
					}
				}
			} else {
				if (hasBomb) {
					Main.foundBombs--;
				}
				text.setText("");
				flagged = false;
			}
		}
	}

	private void blankClick(Tile tile) {

		for (int i = 0; i < tile.neighbours.size(); i++) {
			if (tile.neighbours.get(i).active) {
				tile.neighbours.get(i).btn.setBackground(null);
				tile.neighbours.get(i).btn.setDisable(true);
				tile.neighbours.get(i).text.setText(Integer.toString(tile.neighbours.get(i).numBombs));
				tile.neighbours.get(i).text.setFont(Font.font(null, FontWeight.EXTRA_BOLD, 20));
				tile.neighbours.get(i).text.setFill(tile.neighbours.get(i).color);
				tile.neighbours.get(i).active = false;
				if (tile.neighbours.get(i).numBombs == 0) {
					blankClick(tile.neighbours.get(i));
				}

			}
		}
		return;
	}

}