package utils;

import ru.fbtw.cipher_pro.core.Symbol;

import java.util.ArrayList;

public class EntropyBuilder {
	public static double getEntropy(ArrayList<Symbol> symbols){
		double entropy = 0;
		for(Symbol s : symbols){
			entropy -= MathUtils.log2(s.getFrec()) * s.getFrec();
		}
		return entropy;
	}


}
