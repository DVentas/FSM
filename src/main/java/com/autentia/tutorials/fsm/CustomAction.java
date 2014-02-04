package com.autentia.tutorials.fsm;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.scxml.ErrorReporter;
import org.apache.commons.scxml.EventDispatcher;
import org.apache.commons.scxml.SCInstance;
import org.apache.commons.scxml.SCXMLExpressionException;
import org.apache.commons.scxml.model.Action;
import org.apache.commons.scxml.model.ModelException;

public class CustomAction extends Action{
	
	private static final Log LOGGER = LogFactory.getLog(CustomAction.class);

	private static final long serialVersionUID = 1L;
	String action;
	
	public CustomAction(String action){
		super();
		this.action = action;
	}

	@Override
	public void execute(EventDispatcher evtDispatcher, ErrorReporter errRep,
			SCInstance scInstance, Log appLog, Collection derivedEvents)
			throws ModelException, SCXMLExpressionException {
		LOGGER.info(action);
		
	}
	
	public void setAction(String action){
		this.action = action;
	}
	
	

}
