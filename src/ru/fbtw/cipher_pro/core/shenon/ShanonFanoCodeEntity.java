package ru.fbtw.cipher_pro.core.shenon;

import ru.fbtw.cipher_pro.core.Symbol;

import java.util.ArrayList;
import java.util.Collections;

public class ShanonFanoCodeEntity {

	private SubEntity left;
	private SubEntity right;
	private int level;
	private String baseCode;
	private ArrayList<Symbol> symbols;
	private ArrayList<String> codes;

	public ShanonFanoCodeEntity(int level,String baseCode) {
		this.level = level;
		this.baseCode = baseCode;

		if(level != -1) {
			left = new SubEntity(baseCode + '1');
			right = new SubEntity(baseCode + '0');
		}else{
			left = new SubEntity(baseCode);
			right = new SubEntity(baseCode);
		}

	}

	public SubEntity getLeft() {
		return left;
	}

	public SubEntity getRight() {
		return right;
	}

	public int getLevel() {
		return level;
	}

	public String getBaseCode() {
		return baseCode;
	}

	public class SubEntity{
		private ArrayList<Symbol> symbols;
		private double sum;
		private String code;

		public SubEntity(String code) {
			this.code = code;
			symbols = new ArrayList<>();
			sum = 0;

		}

		public void prepare(){
			Collections.sort(symbols);
		}


		public void push(Symbol s){
			sum+= s.getFrec();
			symbols.add(s);
		}

		public boolean isReady(){
			return symbols.size() <= 1;
		}

		public ArrayList<Symbol> getSymbols() {
			return symbols;
		}

		public double getSum() {
			return sum;
		}

		public String getCode() {
			return code;
		}
	}

	private void merge(){
		symbols = new ArrayList<>();
		if(left.getSymbols().size()>=right.getSymbols().size()) {
			symbols.addAll(left.symbols);
			symbols.addAll(right.symbols);
		}else {
			symbols.addAll(right.symbols);
			symbols.addAll(left.symbols);
		}

	}

	public String[] mergeCodes(){
		if (symbols == null) {
			merge();
		}
		String[] arr = new String[symbols.size()];
		if(left.getSymbols().size()>=right.getSymbols().size()) {
			for (int i = 0; i < left.getSymbols().size(); i++) {
				arr[i] = left.code;
			}
			for (int i = 0; i < right.getSymbols().size(); i++) {
				arr[i + left.getSymbols().size()] = right.code;
			}
		}else{
			for (int i = 0; i < right.getSymbols().size(); i++) {
				arr[i] = right.code;
			}
			for (int i = 0; i < left.getSymbols().size(); i++) {
				arr[i + right.getSymbols().size()] = left.code;
			}
		}
		return arr;
	}
	public String[] mergeSyms(){
		if (symbols == null) {
			merge();
		}
		String[] arr = new String[symbols.size()];
		for (int i = 0; i < symbols.size(); i++) {
			Symbol s = symbols.get(i);
			arr[i] = s.getName();
		}
		return arr;
	}
	public String[] mergeFreqs(){
		if (symbols == null) {
			merge();
		}
		String[] arr = new String[symbols.size()];
		for (int i = 0; i < symbols.size(); i++) {
			Symbol s = symbols.get(i);
			arr[i] = Double.toString(s.getFrec());
		}
		return arr;
	}

	@Override
	public String toString() {
		String res = "level " + level+"\n";
		res+= !left.symbols.isEmpty() ? left.code : "-\n";
		res+="\n";
		res+= !right.symbols.isEmpty() ? right.code : "-\n";
		return res;
	}
}
