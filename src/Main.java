import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ru.fbtw.cipher_pro.core.Symbol;
import ru.fbtw.cipher_pro.core.haffmen.HaffmenEncoder;
import ru.fbtw.cipher_pro.core.haffmen.HaffmenNode;
import ru.fbtw.cipher_pro.core.haffmen.HaffmenVizualazer;
import ru.fbtw.cipher_pro.core.shenon.ShanonFanoEncoder;
import ru.fbtw.cipher_pro.core.shenon.ShenonVisualazer;
import utils.EntropyBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class Main extends Application {

	Pane[] lay;

	int alg;
	private ToggleButton fano;
	private ToggleButton haffmen;

	public static void main(String[] args) {
		Locale.setDefault(Locale.ROOT);
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {

        /*symbols.add(new Symbol(0.3));
        symbols.add(new Symbol(0.2));
        symbols.add(new Symbol(0.3));
        symbols.add(new Symbol(0.2));*/
		// 0.25 0.2 0.2 0.15 0.05 0.05 0.04 0.03 0.02 0.01
        /*Scanner in = new Scanner(System.in);
        double[] syms = Arrays.stream(in.nextLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
        for(double d : syms){
            symbols.add(new Symbol(d));
        }*/
        /*ShanonFanoEncoder encoder = new ShanonFanoEncoder(symbols);

        String[][] res = encoder.encodeAsString();

        for (String[] r : res) {
            for(String s: r){
                if(s == null) s ="";
                System.out.print(s+" ");
            }
            System.out.println();
        }

        System.out.println(encoder.getCodeLen());
        System.out.println(EntropyBuilder.getEntropy(symbols));*/

       /* HaffmenEncoder encoder = new HaffmenEncoder(symbols);

        System.out.println(encoder.getCodeLen());

        HaffmenNode encode = encoder.encode();
        int depth = encoder.getDepth();
        HaffmenVizualazer vizualazer = new HaffmenVizualazer(encode, depth);


        StackPane stackPane = vizualazer.getView();
      ;*/
		VBox mainLayout = new VBox(20);
		Label label = new Label("Ввведите через пробел вероятности");
		TextField input = new TextField();
		HBox alg = new HBox(30);
		ToggleGroup toggleGroup = new ToggleGroup();
		fano = new ToggleButton("Фано");
		haffmen = new ToggleButton("Хаффмен");
		fano.setToggleGroup(toggleGroup);
		haffmen.setToggleGroup(toggleGroup);
		fano.setOnAction(this::onAlgChenged);
		haffmen.setOnAction(this::onAlgChenged);

		alg.getChildren().addAll(fano, haffmen);


		Label freqs = new Label();
		Label eentropyInfo = new Label();
		ScrollPane mainRes = new ScrollPane();

		Button button = new Button("Посчитать");
		button.setOnAction((event -> {
			ArrayList<Symbol> symbols = new ArrayList<>();
			StringBuilder builder = new StringBuilder();

				try {


				double[] syms = Arrays.stream(input.getText()
						.replaceAll(",",".")
						.replaceAll(";","")
						.split(" ")).mapToDouble(Double::parseDouble).toArray();

				for (double d : syms) {
					Symbol e = new Symbol(d);
					symbols.add(e);
					builder.append(e.toString());
					builder.append(" ");
				}
				freqs.setText(builder.toString());

				switch (this.alg) {
					case 1:
						Symbol.restNames();
						ShanonFanoEncoder encoder = new ShanonFanoEncoder(symbols);
						String[][] res = encoder.encodeAsString();
						String entropy = String
								.format("(Энтропия) = %f, (Средняя длина кода) = %f, (Избыточность) = %f ",
										EntropyBuilder.getEntropy(symbols),
										encoder.getCodeLen(),
										encoder.getCodeLen() - EntropyBuilder.getEntropy(symbols));
						eentropyInfo.setText(entropy);

						mainRes.setContent(new ShenonVisualazer(res).visualise());
						break;
					case 2:
						Symbol.restNames();
						HaffmenEncoder encoderHaf = new HaffmenEncoder(symbols);
						HaffmenNode node = encoderHaf.encode();
						entropy = String
								.format("(Энтропия) = %f, (Средняя длина кода) = %f, (Избыточность) = %f ",
										EntropyBuilder.getEntropy(symbols),
										encoderHaf.getCodeLen(),
										encoderHaf.getCodeLen() - EntropyBuilder.getEntropy(symbols));
						eentropyInfo.setText(entropy);

						mainRes.setContent(new HaffmenVizualazer(node, encoderHaf.getDepth()).getView());
						break;
				}


			}catch (Exception ignored){}
		}));
		mainLayout.getChildren().addAll(label, input, alg, button, freqs, eentropyInfo, mainRes);
		Scene scene = new Scene(mainLayout, 800, 600);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	void onAlgChenged(ActionEvent event) {
		if (((ToggleButton) event.getSource()).getText().equals("Фано")) {
			alg = 1;
			return;
		}

		if (((ToggleButton) event.getSource()).getText().equals("Хаффмен")) {
			alg = 2;
			return;
		}
		alg = 0;
	}
}