package com.autentia.tutorials.test.fsm;

import java.net.URL;
import java.util.Collection;

import org.apache.commons.scxml.ErrorReporter;
import org.apache.commons.scxml.EventDispatcher;
import org.apache.commons.scxml.SCInstance;
import org.apache.commons.scxml.TriggerEvent;
import org.apache.commons.scxml.model.Action;
import org.apache.commons.logging.Log;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Matchers;
import org.mockito.Mockito;
import static org.mockito.Mockito.spy;

import com.autentia.tutorials.fsm.CustomAction;
import com.autentia.tutorials.fsm.FSM;

public class FSMTest {
	
	public static final String STATE_A = "A";
	public static final String STATE_B = "B";
	public static final String STATE_C = "C";
	
	public static final String TRANSITION_AB = "Transicion_AB";
	public static final String TRANSITION_BC = "Transicion_BC";
	
	public static final URL urlXML = FSM.class.getClassLoader().getResource("fsm.scxml");
	
	public static TriggerEvent transicionAB = null;
	public static TriggerEvent transicionBC = null;
	
	public static boolean eventdata;
	
	private static Action actionTransitionCB1;
	private static Action actionTransitionCB2;
	
	private static CustomAction actionEntryB;
	private static CustomAction actionExitB1;
	private static CustomAction actionExitB2;
	private static CustomAction actionEntryC;
	
	public static FSM fsm = null;
	
	
		@BeforeClass
		public static void init(){
			fsm = spy(new FSM(urlXML));
			
			actionEntryB = spy(new CustomAction("Action onEntry B"));
			actionExitB1 = spy(new CustomAction("Action onExit B1"));
			actionExitB2 = spy(new CustomAction("Action onExit B2"));
			actionEntryC = spy(new CustomAction("Action onEntry C"));
			
			Action [] actionOnEntryB = {actionEntryB};
			Action [] actionOnExitB = {actionExitB1, actionExitB2};
			Action [] actionOnEntryC = {actionEntryC};
			
			actionTransitionCB1 = spy(new CustomAction("Action Transition CB1"));
			actionTransitionCB2 = spy(new CustomAction("Action Transition CB2"));
			
			fsm.setActionInOnEntryByState(STATE_B, actionOnEntryB);
			fsm.setActionInOnExitByState(STATE_B, actionOnExitB);
			
			fsm.setActionInOnEntryByState(STATE_C, actionOnEntryC);
			
			fsm.addActionToTransitionByStateAndEvent(STATE_B, TRANSITION_BC , actionTransitionCB1);
			fsm.addActionToTransitionByStateAndEvent(STATE_B, TRANSITION_BC , actionTransitionCB2);
			
			eventdata = true;
			
			fsm.addConditionToTransitionByStateAndEvent(STATE_B, TRANSITION_BC, "_eventdata");
			
			transicionAB = new TriggerEvent(TRANSITION_AB,TriggerEvent.SIGNAL_EVENT, eventdata);
			transicionBC = new TriggerEvent(TRANSITION_BC,TriggerEvent.SIGNAL_EVENT,eventdata);
			
		}
		
		@Test
		public void shouldCallOnEntryAndOnExitInOrder(){
			
			try {
				
				InOrder inOrder = Mockito.inOrder(actionEntryB,actionExitB1,actionExitB2,actionEntryC);

				fsm.getEngine().triggerEvent(transicionAB);
				fsm.getEngine().triggerEvent(transicionBC);

				inOrder.verify(actionEntryB, Mockito.atLeastOnce()).execute(Matchers.any(EventDispatcher.class), Matchers.any(ErrorReporter.class), Matchers.any(SCInstance.class), Matchers.any(Log.class), Matchers.any(Collection.class));
				inOrder.verify(actionExitB1, Mockito.atLeastOnce()).execute(Matchers.any(EventDispatcher.class), Matchers.any(ErrorReporter.class), Matchers.any(SCInstance.class), Matchers.any(Log.class), Matchers.any(Collection.class));
				inOrder.verify(actionExitB2, Mockito.atLeastOnce()).execute(Matchers.any(EventDispatcher.class), Matchers.any(ErrorReporter.class), Matchers.any(SCInstance.class), Matchers.any(Log.class), Matchers.any(Collection.class));
				inOrder.verify(actionEntryC, Mockito.atLeastOnce()).execute(Matchers.any(EventDispatcher.class), Matchers.any(ErrorReporter.class), Matchers.any(SCInstance.class), Matchers.any(Log.class), Matchers.any(Collection.class));
				
			} catch (Exception e) {
				System.err.println("Error");
				e.printStackTrace();
			}
		}
		
		@Test
		public void shoulGetTransitions(){
			assert(fsm.getTransitionsByStateAndEvent("A", TRANSITION_AB) == transicionAB);
			assert(fsm.getTransitionsByStateAndEvent("B", TRANSITION_BC) == transicionBC);
		}
		
		@Test
		public void shouldCallActionsInOrderInTransitionsCB(){
	
			InOrder inOrder = Mockito.inOrder(actionTransitionCB1, actionTransitionCB2);
			
			try {
				
				fsm.getEngine().triggerEvent(transicionAB);
				
				fsm.getEngine().triggerEvent(transicionBC);
				
				inOrder.verify(actionTransitionCB1, Mockito.atLeastOnce()).execute(Matchers.any(EventDispatcher.class), Matchers.any(ErrorReporter.class), Matchers.any(SCInstance.class), Matchers.any(Log.class), Matchers.any(Collection.class));
				inOrder.verify(actionTransitionCB2, Mockito.atLeastOnce()).execute(Matchers.any(EventDispatcher.class), Matchers.any(ErrorReporter.class), Matchers.any(SCInstance.class), Matchers.any(Log.class), Matchers.any(Collection.class));
				
			} catch (Exception e) {
				System.err.println("Error");
				e.printStackTrace();
			}
		}
		
		@Test
		public void runInAllStates(){
			
			fsm.addConditionToTransitionByStateAndEvent(STATE_B, TRANSITION_BC, "_eventdata");
			
			try {
				
				assert(fsm.getEngine().getCurrentStatus().equals("A"));
				fsm.getEngine().triggerEvent(transicionAB);
			
				assert(fsm.getEngine().getCurrentStatus().equals("B"));
	
				fsm.getEngine().triggerEvent(transicionBC);
				assert(fsm.getEngine().getCurrentStatus().equals("C"));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}
