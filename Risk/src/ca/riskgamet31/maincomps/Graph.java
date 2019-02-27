package ca.riskgamet31.maincomps;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * generic graph class to be used in all graph related construction
 * and validations (map , player map ...etc)
 * 
 * @author Fareed Tayar
 * @version 1.0
 * @since 1.0 
 */
public class Graph
{
  
  /**
   * list of nodes constituting this graph.
   */
  private ArrayList<GraphNode> graphNodes;
  /**
   * queue item to host nodes while processing.
   */
  private LinkedList<GraphNode> processingList;
  
  /**
   * construct new graph object.
   * 
   * @param graphNodes <code>ArrayList</code> of graph nodes
   */
  public Graph(ArrayList<GraphNode> graphNodes)
	{
	  this.graphNodes = graphNodes;
	  this.processingList = new LinkedList<GraphNode>();
	}
  
  /**
   * empty constructor , generates empty nodes and processing lists.
   */
  public Graph()
	{
	  
	  this.graphNodes = new ArrayList<>();
	  this.processingList = new LinkedList<GraphNode>();
	  
	}
  
  /**
   * provides reference to graph nodes <code>ArrayList</code>
   * @return reference to graph nodes <code>ArrayList</code>
   */
  public ArrayList<GraphNode> getGraphNodes()
	{
	  return graphNodes;
	}
  
  /**
   * sets the graph nodes reference
   * 
   * @param graphNodes graph nodes array list reference.
   */
  public void setGraphNodes(ArrayList<GraphNode> graphNodes)
	{
	  this.graphNodes = graphNodes;
	}
  
  /**
   * provides a reference to processing list queue.
   * 
   * @return processing list queue.
   */
  public LinkedList<GraphNode> getProcessingList()
	{
	  return processingList;
	}
  
  /**
   * adds new node to graph nodes.
   * 
   * @param newNode new node to be added.
   */
  public void addNode(GraphNode newNode)
	{
	  this.getGraphNodes().add(newNode);
	}
  
  /**
   * removes a node from nodes list.
   * 
   * @param node node to be removed
   * @return true if the provided node is found and removed.
   */
  public boolean removeNode(GraphNode node)
	{
	  
	  return this.getGraphNodes().remove(node);
	  
	}
  
  /**
   * clean up the processing queue structure.
   */
  public void cleanProcessingData()
	{
	  
	  this.getProcessingList().clear();
	  
	  for (GraphNode node : this.getGraphNodes())
		{
		  node.setDistance(-1);
		  node.setParentNode(null);
		}
	}
  
  /**
   * starts from a node and process the graph using DFS 
   * 
   */
  public void processGraph()
	{
	  int tempdistance = 0;
	  cleanProcessingData();
	  GraphNode rootNode = this.getGraphNodes().get(0);
	  rootNode.setDistance(tempdistance++);
	  this.getProcessingList().add(rootNode);
	  
	  while (!this.getProcessingList().isEmpty())
		{
		  GraphNode node = this.getProcessingList().getFirst();
		  ArrayList<GraphNode> nodeNeighbors = node.getNodeNeighbors();
		  
		  for (GraphNode neighborC : nodeNeighbors)
			{
			  if (neighborC.getDistance() < 0)
				{
				  neighborC.setDistance(tempdistance);
				  neighborC.setParentNode(node);
				  this.getProcessingList().add(neighborC);
				}
			}
		  
		}
	  
	}
  
  /**
   * checks if the graph is connected or not
   * @return true if not a single node is isolated.
   */
  public boolean isConnected() {
	
	this.processGraph();
	
	boolean connected = this.getGraphNodes().stream().anyMatch((E) -> {return E.getDistance() < 0;});
	
	return !connected;
	
	
  }
  
  
  
}