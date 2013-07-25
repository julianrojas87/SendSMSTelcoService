package org.telcomp.events;

import java.util.HashMap;
import java.util.Random;
import java.io.Serializable;

public final class EndSendSMSTelcoServiceEvent implements Serializable {

	private final long id;
	private static final long serialVersionUID = 1L;
	boolean smsSent;

	public EndSendSMSTelcoServiceEvent(HashMap<String, ?> hashMap) {
		id = new Random().nextLong() ^ System.currentTimeMillis();
		this.smsSent = (boolean) Boolean.parseBoolean((String) hashMap.get("smsSent"));
	}

	public boolean equals(Object o) {
		if (o == this) return true;
		if (o == null) return false;
		return (o instanceof EndSendSMSTelcoServiceEvent) && ((EndSendSMSTelcoServiceEvent)o).id == id;
	}
	
	public int hashCode() {
		return (int) id;
	}
	
	public boolean getSmsSent(){
		return this.smsSent;
	}
	
	public String toString() {
		return "endSendSMSEvent[" + hashCode() + "]";
	}
}
