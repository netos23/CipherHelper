package ru.fbtw.cipher_pro.core.shenon;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ShenonVisualazer {

	String[][] matrix;

	public ShenonVisualazer(String[][] matrix) {
		this.matrix = matrix;
		if(matrix == null){
			this.matrix = new String[1][];
			this.matrix[0] = new String[]{"A","1","0"};
		}
	}

	public GridPane visualise(){
		GridPane main = new GridPane();

		main.setGridLinesVisible(true);
		for(int i = matrix.length - 1,row = 0; i >= 0;row++, i--){
			main.getRowConstraints().add(new RowConstraints(50,0,100));
			for(int col = 0; col < matrix[0].length;col++){
				/*if(row == 0)
					main.getColumnConstraints().add(
							new ColumnConstraints(50,400,
									Double.MAX_VALUE, Priority.NEVER, HPos.CENTER,false));
*/
				Label child = new Label(matrix[i][col]);
				if(matrix[i][col] != null)
				child.setPadding(new Insets(10,10,10,10));
				child.setAlignment(Pos.CENTER);
				child.setFont(Font.font("Arial", FontWeight.BOLD,20));
				main.add(child,col,row);
			}
		}
		return main;
	}
}
