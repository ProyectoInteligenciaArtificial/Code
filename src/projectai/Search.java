package projectai;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import static projectai.Principal.map;

public class Search {
	protected static Operator [] operators = {new Operator(Operator.MOVE_INT_UP), new Operator(Operator.MOVE_INT_RIGHT), new Operator(Operator.MOVE_INT_DOWN), new Operator(Operator.MOVE_INT_LEFT)};
	protected static boolean orderChanged = false;
	private static Tree<Node> backTrackingTree;
	private static ArrayList<State> backTrackingRoute;
	private static ArrayList<State> backTrackingSolution;
	private static Tree<Node> AStarTree;
	private static ArrayList<State> AStarRoute;
	private static ArrayList<State> AStarSolution;
	private static State is;
	private static State fs;
	
	
	public static ArrayList<State> getBackTrackingRoute(State initialState, State finalState) {
		if (backTrackingRoute == null || (!is.equals(initialState) || !fs.equals(finalState)) || orderChanged) {
			try {
				backTracking(initialState, finalState);
			} catch (RuntimeException e) {
				new RuntimeException(e.getMessage());
			}
		}
		return backTrackingRoute;
	}
	
	public static Tree<Node> getBackTrackingTree(State initialState, State finalState) {
		if (backTrackingTree == null || (!is.equals(initialState) || !fs.equals(finalState)) || orderChanged) {
			try {
				backTracking(initialState, finalState);
			} catch (RuntimeException e) {
				new RuntimeException(e.getMessage());
			}
		}
		return backTrackingTree;
	}
	
	public static ArrayList<State> getBackTrackingSolution(State initialState, State finalState) {
		if (backTrackingSolution == null || (!is.equals(initialState) || !fs.equals(finalState)) || orderChanged) {
			try {
				backTracking(initialState, finalState);
			} catch (RuntimeException e) {
				new RuntimeException(e.getMessage());
			}
		}
		
		backTrackingSolution = new ArrayList<>();
		Tree<Node>.Leaf l = backTrackingTree.findLeaf(new Node(fs, 0, false, false));
		
		while(l != null) {
			backTrackingSolution.add(l.getLeaf().getState());
			l = l.getFather();
		}
		
		return backTrackingSolution;
	}
	
	private static void backTracking(State initialState, State finalState) {
		is = initialState;
		fs = finalState;
		Stack<State> stack = new Stack<>();
		ArrayList<State> expanded = new ArrayList<>();
		backTrackingRoute = new ArrayList<>();
		backTrackingTree = new Tree<>();
		Node node = null;
		Tree<Node>.Leaf fath = null;
		int visitCounter = 1;
		
		State stt;
		
		if (initialState == null) {
			throw new RuntimeException("InitialState had not been set.");
		}
		
		stack.push(initialState);
		expanded.add(initialState);
		
		while (!stack.isEmpty()) {
			backTrackingRoute.add(stt = stack.peek());
			if (fath != null && fath.getLeaf().getState().equals(stt)) {
				fath.getLeaf().addVisitNumber(visitCounter++);
				fath.setLabel(fath.getLeaf().toString());
			}
			if (fath == null || (fath != null && !fath.getLeaf().getState().equals(stt))) {
				node = new Node(stt, visitCounter++, stt.equals(initialState), stt.equals(finalState));
				fath = backTrackingTree.addLeaf(node, fath != null ? fath.getLeaf() : null, null, null, node.toString());
			}
			
			if (stt.equals(finalState)) {
				backTrackingTree.setFinal(node);
				return;
			}
			
			stt = expandFirstChild(stt, expanded);
			if (stt == null) {
				fath = fath.getFather();
				stack.pop();
				continue;
			}
			
			stack.push(stt);
			expanded.add(stt);
		}
		
		backTrackingRoute = null;
		backTrackingTree = null;
		 orderChanged = false;
		
		throw new RuntimeException("FinalState is not reachable.");
	}
	
	private static boolean isAplicable(Operator op, State stt) {
		return op.isAplicable(map.getSelectedPlayer(), op.getNextState(stt));
	}
	
	private static boolean isExpanded(Operator op, State stt, ArrayList<State> expanded) {
		return expanded.stream().anyMatch((s) -> (s.equals(op.getNextState(stt))));
	}
	
	private static State expandFirstChild(final State stt, ArrayList<State> expanded) {
		for (Operator op : operators) {
			if (isAplicable(op, stt) && !isExpanded(op, stt, expanded)) {
				return op.getNextState(stt);
			}
		}
		return null;
	}
	
	public static ArrayList<State> getAStarRoute(State initialState, State finalState) {
		if (AStarRoute == null || (!is.equals(initialState) || !fs.equals(finalState)) || orderChanged) {
			try {
				AStar(initialState, finalState);
			} catch (RuntimeException e) {
				new RuntimeException(e.getMessage());
			}
		}
		return AStarRoute;
	}
	
	public static Tree<Node> getAStarTree(State initialState, State finalState) {
		if (AStarTree == null || (!is.equals(initialState) || !fs.equals(finalState)) || orderChanged) {
			try {
				AStar(initialState, finalState);
			} catch (RuntimeException e) {
				new RuntimeException(e.getMessage());
			}
		}
		return AStarTree;
	}
	
	public static ArrayList<State> getAStarSolution(State initialState, State finalState) {
		if (AStarSolution == null || (!is.equals(initialState) || !fs.equals(finalState)) || orderChanged) {
			try {
				AStar(initialState, finalState);
			} catch (RuntimeException e) {
				new RuntimeException(e.getMessage());
			}
		}
		
		AStarSolution = new ArrayList<>();
		Tree<Node>.Leaf l = AStarTree.findLeaf(new Node(fs, 0, false, false));
		
		while(l != null) {
			AStarSolution.add(l.getLeaf().getState());
			l = l.getFather();
		}
		
		return AStarSolution;
	}
	
	public static void AStar(State initialState, State finalState) {
		is = initialState;
		fs = finalState;
		
		Queue<Tree<Node>.Leaf> pQ = new PriorityQueue<>();
		AStarRoute = new ArrayList<>();
		AStarTree = new Tree<>();
		Node node;
		ArrayList<Tree<Node>.Leaf> leaf = new ArrayList<>();
		Integer visitCounter = 1;
		
		State stt;
		if (initialState == null) {
			throw new RuntimeException("InitialState had not been set.");
		}
		stt = initialState;
		node = new Node(stt, -1, stt.equals(initialState), stt.equals(finalState));
		leaf.clear();
		leaf.add(new Tree<Node>().new Leaf(node, null, 0.0, (double) map.manhatan(stt), node.toString()));
		AStarTree.addLeaf(node, null, 0.0, (double) map.manhatan(stt), node.toString());
		pQ.add(leaf.get(0));
		while (!pQ.isEmpty()) {
			leaf.clear();
			leaf.add(pQ.poll());
			AStarRoute.add(leaf.get(0).getLeaf().getState());
			leaf.get(0).getLeaf().addVisitNumber(visitCounter++);
			AStarTree.findLeaf(leaf.get(0).getLeaf()).setLabel(leaf.get(0).getLeaf().toString());
			if (finalState.equals(leaf.get(0).getLeaf().getState())) {
				AStarTree.setFinal(leaf.get(0).getLeaf());
				return;
			}
			addAll(leaf.get(0), pQ);
		}
		AStarRoute = null;
		AStarTree = null;
		orderChanged = false;
		
		throw new RuntimeException("FinalState is not reachable.");
	}
	
	private static void addAll(final Tree<Node>.Leaf leaf, Queue<Tree<Node>.Leaf> pQ) {
		final Node [] aux = {null};
		Tree<Node>.Leaf l;
		final State [] stt = {null};
		double w;
		double g;
		double h;
		double f;
		for (Operator op : operators) {
			if (op.isAplicable(map.getSelectedPlayer(), stt[0] = op.getNextState(leaf.getLeaf().getState()))) {
				aux[0] = new Node(stt[0], -1, stt[0].equals(map.getInitialState()), stt[0].equals(map.getFinalState()));
				
				w = map.getSelectedPlayer().getWeights().stream().filter((weight) -> {
					return map.getTerrain(stt[0]).getId() == weight.getTerrainID();
				}).findFirst().get().getWeight();
				g = leaf.getWeightG() + w;
				h = map.manhatan(stt[0]);
				f = g + h;
				l = AStarTree.findLeaf(aux[0]);
				
				if (l != null && f < l.getCost()) {
					l.getLeaf().getVisitNumber().forEach((n) -> {aux[0].addVisitNumber(n);});
					AStarTree.removeLeaf(l.getLeaf());
					l = null;
				}
				if (l == null) {
					pQ.add(AStarTree.addLeaf(aux[0], leaf.getLeaf(), w, h, aux[0].toString()));
				}							
			}
		}
	}
}
