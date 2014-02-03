package com.autentia.tutorials.fsm;

import java.net.URL;
import java.util.List;

import org.apache.commons.scxml.env.AbstractStateMachine;
import org.apache.commons.scxml.model.Action;
import org.apache.commons.scxml.model.OnEntry;
import org.apache.commons.scxml.model.OnExit;
import org.apache.commons.scxml.model.State;
import org.apache.commons.scxml.model.Transition;


public class FSM extends AbstractStateMachine{
	 
	 // Constructor
	 public FSM(URL xmlPath) {
		   super(xmlPath);
	 }
	 
	 // A–ade una accion a una transaccion de un estado (stateName) y nombre del evento que la lanza (event)
	 public void addActionToTransitionByStateAndEvent(String stateName, String event, Action action){
		 State state = getState(stateName);
		 for(Transition t : (List<Transition>) state.getTransitionsList()){
			 if (t.getEvent().equals(event)){
				 t.addAction(action);
			 }
		 }
	 }
	// A–ade una condicion a una transaccion de un estado (stateName) y nombre del evento que la lanza (event)
	 public void addConditionToTransitionByStateAndEvent(String stateName, String event, String condition){
		 State state = getState(stateName);
		 for(Transition t : (List<Transition>) state.getTransitionsList()){
			 if (t.getEvent().equals(event)){
				 t.setCond(condition);
			 }
		 }
	 }
	 // devuelve State a partir de nombre en String
	 public State getState(String stateName){
		 return (State) getEngine().getStateMachine().getTargets().get(stateName);
	 }
	 // Introduce acciones a OnEntry de un estado pasado por su nombre en String
	 public void setActionInOnEntryByState(String stateName, Action [] actionsToAdd){
		 State state = getState(stateName);
		 OnEntry oe = state.getOnEntry();
		 for(Action action : actionsToAdd){
		 	oe.addAction(action);
		 }
		 state.setOnEntry(oe);
	 }
	// Introduce acciones a OnExit de un estado pasado por su nombre en String
	 public void setActionInOnExitByState(String stateName, Action [] actionsToAdd){
		 State state = getState(stateName);
		 OnExit oex = state.getOnExit();
		 for(Action action : actionsToAdd){
			 	oex.addAction(action);
		}
		 state.setOnExit(oex);
	 }
	 // Introduce una nueva transacci—n a un estado por su nombre en String
	 public void setTransitionInState(String stateName, Transition transitionToAdd){
		 State state = getState(stateName);
		 state.addTransition(transitionToAdd);
	 }
	 // Crea una transacci—n con los datos que se le pasan y la introduce en el estado stateName
	 // event --> nombre del evento que har‡ saltar la transacci—n (si la condici—n es true)
	 // cond --> Condici—n para que la transacci—n tenga lugar
	 // targetState --> estado destino de la transacci—n
	 // actionsToAdd --> Acciones que ejecuta la transacci—n 
	 public void addCustomTransition(String stateName ,String event, String cond, String targetState, Action [] actionsToAdd){
		 Transition t = new Transition();
		 t.setEvent(event);
		 t.setCond(cond);
		 t.setTarget(getState(targetState));
		 
		 for (Action a : actionsToAdd){
			 t.addAction(a);
	 	 }
		 setTransitionInState(stateName, t);
	 }
	 // Devuelve la lista de transacciones de un estado stateName que tienen como evento eventName
	 public List<Transition> getTransitionsByStateAndEvent(String stateName, String eventName){
		 State s = getState(stateName);
		 return  s.getTransitionsList(eventName);
		 
	 }
	 // Callback del estado A, es llamado cuando entra en este estado
	 public void A() {
		 System.out.println("STATE: A");
	  }
	// Callback del estado B
	 public void B() {
	     System.out.println("STATE: B");
	 }
	// Callback del estado C
	 public void C() {
	     System.out.println("STATE: C");
	 }
	  
}
