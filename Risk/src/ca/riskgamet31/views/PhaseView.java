package ca.riskgamet31.views;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import ca.riskgamet31.controllers.GameMainDriver;
import ca.riskgamet31.maincomps.Player;

/**
 * phase view using Observer pattern
 * @author Fareed Tayar
 *@version 1.2
 */
public class PhaseView extends Observable implements Observer 
  {

	/**
	 * name of the phase
	 */
	private String phaseName;
	/**
	 * name of current player
	 */
	private String playerName;
	/**
	 * general info regarding actions during this phase
	 */
	private String phaseActions;
	
		
  /**
   * constructor for the phase view class
   */
  public PhaseView()
	  {
		super();
		this.phaseName = "";
		this.playerName = "";
		this.phaseActions = "";
	  }


  /**
   * to get phase name
   * @return the name of current phase
   */
  public String getPhaseName()
    {
  	return phaseName;
    }


  /**
   * to set the current name of the phase
   * @param phaseName current phase name
   */
  public void setPhaseName(String phaseName)
    {
  	this.phaseName = phaseName;
    }




  /**
   * to get current player name
   * @return name of current player
   */
  public String getPlayerName()
    {
  	return playerName;
    }




  /**
   * to set current player name
   * @param playerName name of current player
   */
  public void setPlayerName(String playerName)
    {
  	this.playerName = playerName;
    }




  /**
   * to get phase actions
   * @return general info of phase actions
   */
  public String getPhaseActions()
    {
  	return phaseActions;
    }


  /**
   * @param phaseActions to set general info of phase actions
   */
  public void setPhaseActions(String phaseActions)
    {
  	this.phaseActions = phaseActions;
    }


  /**
   * to display phase information, this method is called inside the update method.
   */
  public void displayViewInfo() {
	System.out.println("");
	System.out.println("---------------------- "+this.getPhaseName()+" -- "+this.getPlayerName()+" ----------------------");
	System.out.println(this.getPhaseActions()+"\n");
  }

/**
 * overrides the update method of observer
 * @param o the observable object
 * @param arg information object will be passed from observable object.
 */
  @SuppressWarnings("unchecked")
  @Override
  public void update(Observable o, Object arg)
	{
	  
	  this.phaseName = ((ArrayList<String>)arg).remove(0);
	  this.playerName = ((ArrayList<String>)arg).remove(0);
	  this.phaseActions = ((ArrayList<String>)arg).remove(0);
	  this.displayViewInfo();
	  
	  if (phaseName.trim().startsWith("Reinforcement Phase"))
		{
		  Optional<Player> currentPlayerOptional = ((GameMainDriver) o).getPlayerList().getPlayerList().stream().filter(x -> x.getplayerName().equals(this.playerName)).findFirst();
		  Player currentPlayer = currentPlayerOptional.isPresent() ? currentPlayerOptional.get(): null;
		  if (currentPlayer == null)
			try
			  {
				throw (new Exception());
			  } catch (Exception e)
			  {
				e.printStackTrace();
			  }
		  
		  CardExchangeView cardExchangeView = new CardExchangeView();
		  this.addObserver(cardExchangeView);
		  setChanged();
		  notifyObservers(currentPlayer);
		  this.deleteObserver(cardExchangeView);
		}
	}
	
  }
