package projectai;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Queue;
import java.util.Stack;
import java.util.stream.Stream;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jesus
 */
public class Tree<E_Leaf extends Object> {
    
    public class Leaf implements Comparable<Leaf> {
        
        private Integer level;
    	private Double weight_h;
    	private Double weight_g;
        private String label;
        private E_Leaf leaf;
        private Leaf father;
        private ArrayList<Leaf> children;
        
        Leaf(Leaf l) {
        	level = l.level;
        	weight_h = l.weight_h;
        	weight_g = l.weight_g;
        	label = l.label;
            leaf = l.leaf;
            father = l;
            children = new ArrayList<>(l.children);
        }
        
        Leaf(E_Leaf l, Leaf fath, Double g, Double h, String lbl) {
        	level = fath != null ? fath.level + 1 : 0;
        	weight_h = h == null ? 0 : (double) ((int) (h * 100)) / 100;
        	if (fath != null && fath.weight_g != null) {
        		if (g != null) {
        			weight_g = fath.weight_g + (g == null ? 0 : (double) ((int) (g * 100)) / 100);
        		} else {
        			weight_g = fath.weight_g;
        		}
        	} else {
        		weight_g = g == null ? 0 : (double) ((int) (g * 100)) / 100;
        	}
        	label = lbl;
            leaf = l;
            father = fath;
            children = new ArrayList<>();
        }
        
        public E_Leaf getLeaf() {
            return leaf;
        }
        
        public void setLabel(String lbl) {
            label = lbl;
        }
        
        public String getLabel() {
            return label;
        }
        
        public Double getCost() {
        	return weight_g != null ? weight_h != null ? weight_g + weight_h : weight_g : weight_h;
        }
        
        public Leaf addChild(E_Leaf newChild, Double g, Double h, String lbl) {
        	Double wg;
        	Double wf;
        	if (weight_g != null) {
        		if (g != null) {
        			wg = weight_g + g;
        		} else {
            		wg = weight_g;
        		}
        	} else {
        		wg = g;
        	}
        	if (wg != null) {
        		if (h != null) {
        			wf = wg + h;
        		} else {
        			wf = wg;
        		}
        	} else {
        		wf = h;
        	}
        	/*
        	Leaf l = findLeaf(newChild);
        	if (l != null) {
        		if ((wf != null && l.getCost() != null && wf < l.getCost()) || ((level + 1) < l.getLevel())) {
        			removeLeaf(l.getLeaf());
        		} else {
        			return;
        		}
        	}
        	*/
        	for (Leaf child : children) {
        		if (child.leaf.equals(newChild)) {
        			child.setWeightG(g);
        			child.setWeightH(h);
        			child.setLabel(lbl);
        			return child;
        		}
        	}
        	
        	Leaf child = new Leaf(newChild, this, g, h, lbl);
        	children.add(child);
        	return child;
        }
        
        public void removeChild(E_Leaf child) {
            ArrayList<Leaf> aux = new ArrayList<>();
            children.stream().filter((Leaf l) -> (l.getLeaf().equals(child))).forEach(aux::add);
            aux.forEach(children::remove);
        }

        
        public void removeChild(String child) {
            children.stream().filter((Leaf l) -> {
            	return l.getLabel().equals(child);
            }).forEach(children::remove);
        }
        
        public Leaf getChild(E_Leaf child) {
        	return children.stream().filter((Leaf l) -> {
        		return l.getLeaf().equals(child);
        	}).findFirst().get();
        }
        
        public void setWeightG(Double g) {
        	weight_g = g == null ? 0 : (double) ((int) (g * 100)) / 100;
        }
        
        public void setWeightH(Double h) {
        	weight_h = h == null ? 0 : (double) ((int) (h * 100)) / 100;
        }
        
        public double getWeightG() {
        	return weight_g == null ? 0 : weight_g;
        }
        
        public double getWeightH() {
        	return weight_h == null ? 0 : weight_h;
        }
        
        public Leaf getFather() {
        	return father;
        }
        
        public Integer getLevel() {
        	return level;
        }
        
        public ArrayList<Leaf> getChildren() {
        	return children;
        }

		@Override
		public int compareTo(Tree<E_Leaf>.Leaf o) {
			int c = getCost().compareTo(o.getCost());
			return c == 0 ? ((c = getLevel().compareTo(o.getLevel())) == 0 ? 1 : c) : c;
		}
    }
    
    //**************************************************************************
    
    protected Leaf root;
    protected Leaf finalLeaf;
    
    Tree() { }
    
    Tree(E_Leaf l, Double h, String lbl) {
    	root = new Leaf(l, null, 0.0, h, lbl);
    }
    
    public void setFinal(E_Leaf l) {
    	finalLeaf = findLeaf(l);
    }
    
    public Leaf getFinal() {
    	return finalLeaf;
    }
    
    public Leaf addLeaf(E_Leaf l, E_Leaf fath, Double g, Double h, String lbl) {
    	if (root == null) {
    		return root = new Leaf(l, null, 0.0, h, lbl);
    	}
    	Leaf f = findLeaf(fath);
    	//if (l.equals(fath) || f == null || (f != null && f.getFather() != null && l.equals(f.getFather().getLeaf()))) {
    	if (f == null) {
    		return null;
    	}
    	return findLeaf(fath).addChild(l, g, h, lbl);
    }
    
    public Leaf findLeaf(E_Leaf l) {
    	return findLeaf(l, root);
    }
    
    public Leaf findLeaf(E_Leaf l, Leaf r) {
    	ArrayList<Leaf> queue = new ArrayList<>();
    	Leaf current;
    	
    	queue.add(r);
    	
    	while (!queue.isEmpty()) {
    		if ((current = queue.get(0)).getLeaf().equals(l)) {
    			return current;
    		}
    		queue.remove(0);
    		current.children.forEach((Leaf child) -> {
    			queue.add(child);
    		});
    	}
    	return null;
    }
    
    public void removeLeaf(E_Leaf l) {
    	ArrayList<Leaf> queue = new ArrayList<>();
    	Leaf current;
    	
    	queue.add(root);
    	
    	while (!queue.isEmpty()) {
    		if ((current = queue.get(0)).getLeaf().equals(l)) {
    			current.getFather().removeChild(current.getLeaf());
    			return;
    		}
    		queue.remove(0);
    		current.children.forEach((Leaf child) -> {
    			queue.add(child);
    		});
    	}
    }
    
    public int getDeep(Leaf r) {
    	r = r == null ? root : r;
    	
    	int deep = 0;
    	Leaf current;
    	Stack<Leaf> stack = new Stack<>();
    	ArrayList<E_Leaf> closed = new ArrayList<>();
    	
    	stack.push(r);
    	
    	while (!stack.isEmpty()) {
    		deep = stack.size() > deep ? stack.size() : deep;
    		current = stack.peek();
    		
    		if (current.children.isEmpty() ||
    				current.children.stream().allMatch((Leaf child) -> {
    					return closed.stream().anyMatch((E_Leaf l) -> {
    						return l.equals(child.getLeaf());
    					});
    				})) {
    			
    			closed.add(current.getLeaf());
    			stack.pop();
    			continue;
    		}
    		
    		for (Leaf child : current.children) {
    			if (closed.stream().noneMatch((E_Leaf l) -> (l.equals(child.getLeaf())))) {
    				stack.push(child);
    				break;
    			}
    		}
    	}
    	
    	return deep;
    }
    
    public int getMaxLevelSize(Leaf r) {
    	r = r == null ? root : r;
    	
    	int size = 0;
    	ArrayList<Leaf> fathers = new ArrayList<>();
    	ArrayList<Leaf> children = new ArrayList<>();
    	
    	children.add(r);
    	
    	while (!children.isEmpty()) {
    		size = children.size() > size ? children.size() : size;
    		fathers = new ArrayList<>(children);
    		children.clear();
    		
    		fathers.forEach((Leaf f) -> {
    			f.children.forEach(children::add);
    		});
    	}
    	
    	return size;
    }
    
    public int getWidth(Leaf r) {
    	r = r == null ? root : r;
    	int width = 0;
    	if (r.getChildren().isEmpty()) {
    		return 1;
    	} else {
    		for (Leaf l : r.getChildren()) {
    			width += getWidth(l);
    		}
    	}
    	return width != 1 ? width : width + 1;
    }
    
    public ArrayList<Leaf> getLeafs(Leaf r) {
    	r = r == null ? root : r;
    	
    	int size = 0;
    	ArrayList<Leaf> fathers = new ArrayList<>();
    	ArrayList<Leaf> children = new ArrayList<>();
    	ArrayList<Leaf> leafs = new ArrayList<>();
    	
    	children.add(r);
    	leafs.add(r);
    	
    	while (!children.isEmpty()) {
    		size = children.size() > size ? children.size() : size;
    		fathers = new ArrayList<>(children);
    		children.clear();
    		
    		fathers.forEach((Leaf f) -> {
    			f.children.forEach(children::add);
    		});
    		
    		children.forEach(leafs::add);
    	}
    	
    	return leafs;
    }
    
    public String toString() {
    	final String [] str = {""};
    	getLeafs(null).forEach((Leaf l) -> {
    		str[0] += "\nLeaf: " + l.getLabel() + "\nChildren: ";
    		l.getChildren().forEach((Leaf child) -> {
    			str[0] += child.getLabel() + " :: ";
    		});
    	});
    	return str[0];
    }
    
    public BufferedImage paint() {
    	int size = 50;
    	BufferedImage img = new BufferedImage((getWidth(null) * 4 + 1) * size * 2, (getDeep(null) * 2 + 1) * size, BufferedImage.TYPE_INT_RGB);
    	
    	for (int y = 0; y < img.getHeight(); y++) {
    		for (int x = 0; x < img.getWidth(); x++) {
    			img.setRGB(x, y, Color.WHITE.getRGB());
    		}
    	}
    	
    	Graphics2D g2d = img.createGraphics();
    	int fontSize = size / 4;
    	g2d.setFont(new Font("TimesRoman", Font.BOLD, fontSize));
    	final Point fatherPosition = new Point();
    	final Point childPosition = new Point();
    	getLeafs(null).forEach((Leaf l) -> {
    		childPosition.setLocation(((getWidth(root) + 1) / 2 + getPositionX(l)) * size, (l.getLevel() * 2 + 1) * size);
    		if (!l.equals(root)) {
        		fatherPosition.setLocation(((getWidth(root) + 1) / 2 + getPositionX(l.getFather())) * size, (l.getFather().getLevel() * 2 + 1) * size);
        		if (findLeaf(finalLeaf.getLeaf(), l) != null) {
        			g2d.setColor(Color.green);
        			g2d.setStroke(new BasicStroke(5));
        		} else {
        			g2d.setColor(Color.black);
        			g2d.setStroke(new BasicStroke(1));
        		}
        		g2d.drawLine(fatherPosition.x + (size / 2), fatherPosition.y + size, childPosition.x + (size / 2), childPosition.y + (size / 2));
    		}
        	g2d.setColor(Color.blue);
        	g2d.fill(new Ellipse2D.Float((float) childPosition.getX(), (float) childPosition.getY(), size, size));
    		g2d.setColor(Color.black);
    		g2d.drawString(l.getLabel(), (float) childPosition.getX(), (float) childPosition.getY());
    		g2d.drawString("g = " + (l.weight_g != null ? l.weight_g : "null"), (float) childPosition.getX(), (float) childPosition.getY() + fontSize);
    		g2d.drawString("h = " + (l.weight_h != null ? l.weight_h : "null"), (float) childPosition.getX(), (float) childPosition.getY() + 2 * fontSize);
    		g2d.drawString("f = " + (l.getCost() != null ? l.getCost() : "null"), (float) childPosition.getX(), (float) childPosition.getY() + 3 * fontSize);
    		
    	});
    	g2d.dispose();
    	return img;
    }
    
    private Float getPositionX(Leaf l) {
    	final float [] position = {0};
    	int d = 0;
    	ArrayList<Leaf> fatherChildren = new ArrayList<>();
    	if (l.getFather() != null) {
    		fatherChildren = l.getFather().children;
    		position[0] = getPositionX(l.getFather()) - (!fatherChildren.isEmpty() ? (fatherChildren.size() - 1) : 0) + fatherChildren.indexOf(l);
    		for (int i = 0; !fatherChildren.get(i).getLabel().equals(l.getLabel()); i++) {
    			position[0] += getWidth(fatherChildren.get(i)) + 1;
    		}
    	}
    	return position[0];
    }
}