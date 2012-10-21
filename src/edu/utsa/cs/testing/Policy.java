package edu.utsa.cs.testing;

public class Policy {

	private boolean decisionType;
	private Request userRequest;
	private String decisionRule;
	
	public Policy(){
		decisionType = true;
		userRequest = new Request();
		decisionRule = "";
	}
	
}
