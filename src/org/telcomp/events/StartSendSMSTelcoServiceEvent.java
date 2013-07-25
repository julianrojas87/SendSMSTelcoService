package org.telcomp.events;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Random;

public final class StartSendSMSTelcoServiceEvent implements Serializable {

	private final long id;
	private static final long serialVersionUID = 1L;
	private String message;
	private String phone;

	public StartSendSMSTelcoServiceEvent(HashMap<String, ?> hashMap) {
		id = new Random().nextLong() ^ System.currentTimeMillis();
		this.message = (String) hashMap.get("message");
		this.phone = (String) hashMap.get("phone");
	}

	public boolean equals(Object o) {
		if (o == this) return true;
		if (o == null) return false;
		return (o instanceof StartSendSMSTelcoServiceEvent) && ((StartSendSMSTelcoServiceEvent)o).id == id;
	}
	
	public int hashCode() {
		return (int) id;
	}
	
	public String getMessage(){
		return this.message;
	}
	
	public String getPhone(){
		return this.phone;
	}
	
	public String toString() {
		return "startSendSMSEvent[" + hashCode() + "]";
	}
}
