package com.autentia.tutorials.test.fsm;

import org.apache.commons.scxml.TriggerEvent;
import org.apache.commons.scxml.model.Action;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.autentia.tutorials.fsm.CustomAction;
import com.autentia.tutorials.fsm.FSM;

public class FSMTest {
	
	public static final String STATE_A = "A";
	public static final String STATE_B = "B";
	public static final String STATE_C = "C";
	
	public static final String TRANSITION_AB = "Transicion_AB";
	public static final String TRANSITION_BC = "Transicion_BC";
	FSM fsm = Mockito.mock(FSM.class);

		
		@Test
		public void init(){
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
			
			boolean eventdata = true;
			try {
				TriggerEvent transicionAB = new TriggerEvent(TRANSITION_AB,TriggerEvent.SIGNAL_EVENT, eventdata);

				fsm.getEngine().triggerEvent(transicionAB);
				
				TriggerEvent transicionBC = new TriggerEvent(TRANSITION_BC,TriggerEvent.SIGNAL_EVENT,eventdata);
				
				fsm.getEngine().triggerEvent(transicionBC);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			Mockito.verify(fsm).A();
			Mockito.verify(fsm).B();
			Mockito.verify(fsm).C();
		
		}

	//	@Test
		public void test(){
			
			Mockito.verify(fsm).A();
			Mockito.verify(fsm).B();
			Mockito.verify(fsm).C();
			
		}
}
