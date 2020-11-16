package ru.fbtw.cipher_pro.core.haffmen;

import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import ru.fbtw.cipher_pro.core.Symbol;

import java.util.LinkedList;
import java.util.logging.Level;

public class HaffmenVizualazer {
	private HaffmenNode node;
	private int depth;

	private static double objWidth =20;
	private static double objHeight = 120;

	public HaffmenVizualazer(HaffmenNode node, int depth) {
		this.node = node;
		if(node == null){
			node = new HaffmenNode(new Symbol(1));
		}
		this.depth = depth;
	}

	public StackPane getView(){
		Pane main = new Pane();
		Pane back = new Pane();

		double width = Math.pow(2,depth) * 2*objWidth;
		double heigth = objHeight * depth;

		main.setMinWidth(width);
		main.setMinHeight(heigth);

		back.setMinWidth(width);
		back.setMinHeight(heigth);

		double rootX = width  ;
		double rooty = objHeight/4;

		node.setX(rootX);
		node.setY(rooty);
		Label label2 = new Label(node.toString());
		label2.setLayoutX(rootX);
		label2.setLayoutY(rooty);
		label2.setBackground(new Background(new BackgroundFill(Color.AQUA,null,null)));
		main.getChildren().addAll(label2);

		LinkedList<HaffmenNode> queue = new LinkedList<>();
		if(!node.isLeaf())
		queue.add(node);
		int level = 2;
		while (!queue.isEmpty()){
			HaffmenNode tmpRoot = queue.pollFirst();
			HaffmenNode left = tmpRoot.getLeft();
			double v = width / (Math.pow(2,left.getCode().length())) ;
			left.setX(tmpRoot.getX() - v);
			left.setY(tmpRoot.getY() + objHeight);
			Label label = new Label(left.toString());
			label.setBackground(new Background(new BackgroundFill(Color.AQUA,null,null)));
			label.setLayoutX(left.getX());
			label.setLayoutY(left.getY());
			main.getChildren().addAll(label);
			back.getChildren().addAll(new Line(tmpRoot.getX(),tmpRoot.getY(),left.getX(),left.getY()));

			Label code0 = new Label(left.getCode());
			code0.setBackground(new Background(new BackgroundFill(Color.CORAL,null,null)));
			code0.setLayoutX((tmpRoot.getX()+left.getX())/2-v/4);
			code0.setLayoutY((tmpRoot.getY()+left.getY())/2);
			main.getChildren().add(code0);

			if(!left.isLeaf()){
				queue.add(left);
			}

			HaffmenNode rigth = tmpRoot.getRigth();
			double v1 = width / (Math.pow(2,rigth.getCode().length()));
			rigth.setX(tmpRoot.getX() + v1);
			rigth.setY(tmpRoot.getY() + objHeight);
			Label label1 = new Label(rigth.toString());
			label1.setLayoutX(rigth.getX());
			label1.setLayoutY(rigth.getY());
			label1.setBackground(new Background(new BackgroundFill(Color.AQUA,null,null)));
			main.getChildren().addAll(label1);
			back.getChildren().addAll(new Line(tmpRoot.getX(),tmpRoot.getY(),rigth.getX(),rigth.getY()));

			if(!rigth.isLeaf()){
				queue.add(rigth);
			}

			Label code1 = new Label(rigth.getCode());
			code1.setLayoutX((tmpRoot.getX()+rigth.getX())/2+v1/4);
			code1.setLayoutY((tmpRoot.getY()+rigth.getY())/2);
			code1.setBackground(new Background(new BackgroundFill(Color.CORAL,null,null)));
			main.getChildren().add(code1);
			level++;
		}

		StackPane stackPane = new StackPane();
		stackPane.getChildren().addAll(main);
		stackPane.getChildren().addAll(back);

		return stackPane;
	}
}
