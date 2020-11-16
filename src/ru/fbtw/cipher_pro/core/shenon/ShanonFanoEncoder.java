package ru.fbtw.cipher_pro.core.shenon;

import ru.fbtw.cipher_pro.core.Symbol;

import java.util.ArrayList;
import java.util.LinkedList;

public class ShanonFanoEncoder {
	private ArrayList<Symbol> symbols;
	private int maxLevel;
	private double codeLen =0;

	public ShanonFanoEncoder(ArrayList<Symbol> symbols) {
		this.symbols = symbols;
	}


	public ArrayList<ShanonFanoCodeEntity> encode(){

		ArrayList<ShanonFanoCodeEntity> res = new ArrayList<>();
		ShanonFanoCodeEntity tmp = new ShanonFanoCodeEntity(-1,"");
		for(Symbol symbol: symbols){
			tmp.getLeft().push(symbol);
		}
		tmp.getLeft().prepare();
		LinkedList<ShanonFanoCodeEntity> queue =  new LinkedList<>();
		queue.add(tmp);


		while (!queue.isEmpty()){
			ShanonFanoCodeEntity entity = queue.pollFirst();

			if(!entity.getLeft().isReady()){
				ShanonFanoCodeEntity e = encode(entity.getLeft(), entity.getLevel() + 1);
				maxLevel = Math.max(maxLevel,entity.getLevel() + 1);
				res.add(e);
				queue.add(e);
			}else{
				for(Symbol s : entity.getLeft().getSymbols()){
					codeLen += s.getFrec() * entity.getLeft().getCode().length();
				}
			}

			if(!entity.getRight().isReady()){
				ShanonFanoCodeEntity e = encode(entity.getRight(), entity.getLevel() + 1);
				maxLevel = Math.max(maxLevel,entity.getLevel() + 1);
				res.add(e);
				queue.add(e);
			}else{
				for(Symbol s : entity.getRight().getSymbols()){
					codeLen += s.getFrec() * entity.getRight().getCode().length();
				}
			}
		}

		return res;
	}

	public double getCodeLen() {
		return codeLen;
	}

	public ShanonFanoCodeEntity encode(ShanonFanoCodeEntity.SubEntity entity, int level){
		double minDelta = entity.getSum();
		double sum = 0;
		maxLevel++;

		ShanonFanoCodeEntity  nextEntity =  new ShanonFanoCodeEntity(level,entity.getCode());
		entity.prepare();

		for(Symbol symbol : entity.getSymbols()){
			sum += symbol.getFrec();
			if(Math.abs(entity.getSum() - 2.0 * sum) <= minDelta){
				minDelta = entity.getSum() - 2.0*sum;

				nextEntity.getLeft().push(symbol);
			}else{
				nextEntity.getRight().push(symbol);
			}
		}

		return nextEntity;
	}

	public String[][] encodeAsString(){
		ArrayList<ShanonFanoCodeEntity> entities = encode();

		int size = entities != null && entities.size() != 0 ? symbols.size() : 0;
		String [][] matrix = new String[1][];
		matrix[0] = new String[]{"A",""+symbols.get(0),"0"};
		if(size>0) {
			int i1 = maxLevel + 2;
			String[][] res = new String[size][i1];
			int [] registred = new int[i1];
			String[] names = entities.get(0).mergeSyms();
			String[] freq = entities.get(0).mergeFreqs();
			for(int i =0; i < res.length;i++){
				res[i][0] = names[i];
				res[i][1] = freq[i];
			}

			for( int i = 0; i< entities.size();i++){
				String [] codes = entities.get(i).mergeCodes();
				for(int j =0; j<codes.length;j++){
					int column = 2 + entities.get(i).getLevel();
					res[registred[column]][column] = codes[j];
					registred[column] +=1;
				}
			}


			return res;
		}else{
			return matrix;
		}
	}

}
