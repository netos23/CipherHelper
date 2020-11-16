package ru.fbtw.cipher_pro.core.haffmen;

import ru.fbtw.cipher_pro.core.Symbol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class HaffmenEncoder {
	private ArrayList<Symbol> symbols;
	private double codeLen =0;
	private int depth = 0;


	public HaffmenEncoder(ArrayList<Symbol> symbols) {
		this.symbols = symbols;
	}

	public HaffmenNode encode(){
		HaffmenNode root = buildTree();
		if(root == null){
			return null;
		}

		if(root.isLeaf()){
			root.setCode("0");
			codeLen = root.getFreq();
			return root;
		}

		LinkedList<HaffmenNode> queue = new LinkedList<>();
		root.setCode("");
		queue.add(root);
		while (!queue.isEmpty()){
			HaffmenNode tmpRoot = queue.pollFirst();
			HaffmenNode left = tmpRoot.getLeft();
			left.setCode(tmpRoot.getCode() + "0");
			if(!left.isLeaf()){
				queue.add(left);
			}else{
				codeLen += left.getFreq() * left.getCode().length();
				depth = Math.max(left.getCode().length(),depth);
			}

			HaffmenNode rigth = tmpRoot.getRigth();
			rigth.setCode(tmpRoot.getCode() + "1");
			if(!rigth.isLeaf()){
				queue.add(rigth);
			}else {
				codeLen += rigth.getFreq() * rigth.getCode().length();
				depth = Math.max(rigth.getCode().length(),depth);
			}
		}
		return root;
	}

	public HaffmenNode buildTree(){
		LinkedList<HaffmenNode> nodes = new LinkedList<>();
		for(Symbol symbol : symbols){
			nodes.add(new HaffmenNode(symbol));
		}

		while (nodes.size() > 1){
			Collections.sort(nodes);
			nodes.add(new HaffmenNode(nodes.get(0),nodes.get(1)));
			nodes.removeFirst();
			nodes.removeFirst();
		}
		return (nodes.size() > 0) ? nodes.get(0) : null;
	}

	public int getDepth() {
		return depth;
	}

	public double getCodeLen() {
		return codeLen;
	}
}
