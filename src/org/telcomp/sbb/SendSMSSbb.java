package org.telcomp.sbb;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.util.HashMap;

import javax.slee.ActivityContextInterface;
import javax.slee.Address;
import javax.slee.RolledBackContext;
import javax.slee.SbbContext;

import org.telcomp.events.EndSendSMSTelcoServiceEvent;
import org.telcomp.events.StartSendSMSTelcoServiceEvent;

public abstract class SendSMSSbb implements javax.slee.Sbb {
	

	public void onStartSendSMSTelcoServiceEvent(StartSendSMSTelcoServiceEvent event, ActivityContextInterface aci) {
		String message = event.getMessage();
		String phone = event.getPhone();
		if(this.sendSMS(message, phone)){
			HashMap<String, Object> operationInputs = new HashMap<String, Object>();
			operationInputs.put("smsSent", (String) "true");
			EndSendSMSTelcoServiceEvent endSMS = new EndSendSMSTelcoServiceEvent(operationInputs);
			this.fireEndSendSMSTelcoServiceEvent(endSMS, aci, null);
			aci.detach(this.sbbContext.getSbbLocalObject());
		} else{
			HashMap<String, Object> operationInputs = new HashMap<String, Object>();
			operationInputs.put("smsSent", (String) "false");
			EndSendSMSTelcoServiceEvent endSMS = new EndSendSMSTelcoServiceEvent(operationInputs);
			this.fireEndSendSMSTelcoServiceEvent(endSMS, aci, null);
			aci.detach(this.sbbContext.getSbbLocalObject());
		}
	}
	
	public boolean sendSMS(String message, String phone){
		SocketChannel sc = null;
        try {
            sc = SocketChannel.open();
            InetAddress addr = null;
            addr = InetAddress.getByName("192.168.190.71");
            int port = 2500;
            SocketAddress sockaddr = new InetSocketAddress(addr, port);              
            java.net.Socket socket = sc.socket();

            String sms = message+"&"+phone;
            socket.connect(sockaddr, 5000);
            socket.setKeepAlive(true);
            socket.setTcpNoDelay(true);    
            OutputStream os = socket.getOutputStream();
            os.write(sms.getBytes());
            os.flush();
            os.close();
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
	}
	

	
	// TODO: Perform further operations if required in these methods.
	public void setSbbContext(SbbContext context) { 
		this.sbbContext = context;
	}
    public void unsetSbbContext() { this.sbbContext = null; }
    
    // TODO: Implement the lifecycle methods if required
    public void sbbCreate() throws javax.slee.CreateException {}
    public void sbbPostCreate() throws javax.slee.CreateException {}
    public void sbbActivate() {}
    public void sbbPassivate() {}
    public void sbbRemove() {}
    public void sbbLoad() {}
    public void sbbStore() {}
    public void sbbExceptionThrown(Exception exception, Object event, ActivityContextInterface activity) {}
    public void sbbRolledBack(RolledBackContext context) {}
	
	public abstract void fireEndSendSMSTelcoServiceEvent (EndSendSMSTelcoServiceEvent event, ActivityContextInterface aci, Address address);

	
	/**
	 * Convenience method to retrieve the SbbContext object stored in setSbbContext.
	 * 
	 * TODO: If your SBB doesn't require the SbbContext object you may remove this 
	 * method, the sbbContext variable and the variable assignment in setSbbContext().
	 *
	 * @return this SBB's SbbContext object
	 */
	
	protected SbbContext getSbbContext() {
		return sbbContext;
	}

	private SbbContext sbbContext; // This SBB's SbbContext

}
