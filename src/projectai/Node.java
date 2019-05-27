package projectai;

import java.util.ArrayList;

public class Node {
	private final State state;
	private final ArrayList<Integer> visitNum = new ArrayList<>();
	private final boolean isInitial;
	private final boolean isFinal;
	
	Node(State stt, int num, boolean i, boolean f) {
		state = stt;
		if (num > 0) {
			visitNum.add(num);
		}
		isInitial = i;
		isFinal = f;
	}
	
	public State getState() {
		return state;
	}
	
	public void addVisitNumber(int num) {
		visitNum.add(num);
	}
	
	public ArrayList<Integer> getVisitNumber() {
		return visitNum;
	}
	
	public String getVisitNumberStr() {
		final String [] str = {""};
		visitNum.forEach((i) -> {str[0] += ", " + i;});
		return str[0];
	}
	
	public boolean isInitial() {
		return isInitial;
	}
	
	public boolean isFinal() {
		return isFinal;
	}
	
	@Override
	public String toString() {
		return state.toString() + getVisitNumberStr() + (isInitial ? ", Initial" : "") + (isFinal ? ", Final" : "");
	}
	
	@Override
	public boolean equals(Object obj) {
		return state.equals(((Node) obj).state);
	}
}
