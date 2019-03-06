package ca.riskgamet31.maincomps;

import java.util.ArrayList;

/**
 * basic element of the graph.
 *
 *
 * @author Fareed Tayar
 * @version 1.0
 * @since 1.0
 */
public class GraphNode
  {
	
	/**
	 * the country element forming node's data.
	 */
	private Country nodeData;
	/**
	 * distance from root node in the graph.
	 */
	private int distance;
	/**
	 * parent node in the graph.
	 */
	private GraphNode parentNode;
	/**
	 * list of neighboring nodes
	 */
	private ArrayList<GraphNode> neighbors;
	
	/**
	 * Instantiates new node object
	 * 
	 * @param nodeData the country data of this node.
	 */
	public GraphNode(Country nodeData)
	  {
		this.nodeData = nodeData;
		this.distance = -1;
		this.parentNode = null;
		this.neighbors = new ArrayList<GraphNode>();
	  }
	  
	/**
	 * returns the country component of the node.
	 * 
	 * @return country component of the node.
	 */
	public Country getNodeData()
	  {
		return nodeData;
	  }
	  
	/**
	 * returns array list of neighboring nodes.
	 * 
	 * @return array list of neighboring nodes.
	 */
	public ArrayList<GraphNode> getNodeNeighbors()
	  {
		return this.neighbors;
	  }
	  
	/**
	 * returns the distance from parent node in the graph.
	 * 
	 * @return the distance from parent node in the graph.
	 */
	public int getDistance()
	  {
		return distance;
	  }
	  
	/**
	 * sets the distance from parent node in the graph.
	 * 
	 * @param distance distance from parent node in the graph.
	 */
	public void setDistance(int distance)
	  {
		this.distance = distance;
	  }
	  
	/**
	 * returns its parent node.
	 * 
	 * @return its parent node.
	 */
	public GraphNode getParentNode()
	  {
		return parentNode;
	  }
	  
	/**
	 * sets parent node
	 * 
	 * @param parentNode node's parent.
	 */
	public void setParentNode(GraphNode parentNode)
	  {
		this.parentNode = parentNode;
	  }
	  
	/**
	 * adds new neighbor
	 * 
	 * @param neighbor new neighbor
	 */
	public void addNeighbor(GraphNode neighbor)
	  {
		
		this.getNodeNeighbors().add(neighbor);
		
	  }
	  
	/**
	 * remove a neighbor from list of neighbors.
	 * 
	 * @param neighbor the neighbor to be removed.
	 */
	public void removeNeighbor(GraphNode neighbor)
	  {
		
		this.getNodeNeighbors().remove(neighbor);
		
	  }
	  
	/**
	 * returns if two nodes are equals or not based on country's equal method.
	 * 
	 * @return true if both nodes have country with the same name.
	 * @see Country#equals(Object)
	 */
	@Override
	public boolean equals(Object otherNode)
	  {
		
		if (otherNode == null)
		  return false;
		if (!(otherNode instanceof GraphNode))
		  return false;
		if (otherNode == this)
		  return true;
		return (this.getNodeData().equals(((GraphNode) otherNode)
		    .getNodeData()));
		
	  }
	  
	/**
	 * returns a string representation of graph node data object
	 */
	@Override
	public String toString()
	  {
		return this.getNodeData().toString();
	  }
  }
