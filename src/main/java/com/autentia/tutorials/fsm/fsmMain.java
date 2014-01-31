package com.autentia.tutorials.fsm;

import org.apache.commons.scxml.TriggerEvent;
import org.apache.commons.scxml.model.Action;

public class fsmMain {
	
	static FSM fsm = new FSM();
	
	public static final String STATE_A = "A";
	public static final String STATE_B = "B";
	public static final String STATE_C = "C";
	
	public static final String TRANSITION_AB = "Transicion_AB";
	public static final String TRANSITION_BC = "Transicion_BC";
	
	public static void main(String [] args){
		
		init();
		run();
	}
	
	private static void init(){
		
		Action [] actionOnEntryB = {new CustomAction("Action onEntry B")};
		Action [] actionOnExitB = {new CustomAction("Action onExit B1"), new CustomAction("Action onExit B2")};
		Action [] actionOnEntryC = {new CustomAction("Action onEntry C")};
		Action actionTransitionCB1 = new CustomAction("Action Transition CB1");
		Action actionTransitionCB2 = new CustomAction("Action Transition CB2");
		
		fsm.setActionInOnEntryByState(STATE_B, actionOnEntryB);
		fsm.setActionInOnExitByState(STATE_B, actionOnExitB);
		
		fsm.setActionInOnEntryByState(STATE_C, actionOnEntryC);
		
		fsm.addActionToTransitionByStateAndEvent(STATE_B, TRANSITION_BC , actionTransitionCB1);
		fsm.addActionToTransitionByStateAndEvent(STATE_B, TRANSITION_BC , actionTransitionCB2);
		
		fsm.addConditionToTransitionByStateAndEvent(STATE_B, TRANSITION_BC, "_eventdata");
		
	}
	private static void run(){
		// Dato que se introduce en el evento y que ser‡ recogido en la transacci—n como _eventdata
		//  En este caso se agrega al atributo cond de la transacci—n el eventdata, y como es
		// un booleano true, podr‡ realizarse la transacci—n (Mirar fsm.scxml) para m‡s info
		boolean eventdata = true;
		try {
			TriggerEvent transicionAB = new TriggerEvent(TRANSITION_AB,TriggerEvent.SIGNAL_EVENT, null);

			fsm.getEngine().triggerEvent(transicionAB);
			
			TriggerEvent transicionBC = new TriggerEvent(TRANSITION_BC,TriggerEvent.SIGNAL_EVENT,eventdata);
			
			fsm.getEngine().triggerEvent(transicionBC);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
