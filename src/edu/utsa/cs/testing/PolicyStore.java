package edu.utsa.cs.testing;

import java.util.ArrayList;

public class PolicyStore {
	private ArrayList<Policy> policyList;
	
	public PolicyStore(){
		policyList = new ArrayList<Policy>();
	}
	
	public void addPolicy(Policy pol){
		policyList.add(pol);
	}
}
