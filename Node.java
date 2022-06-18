package functions;

import java.util.*;
import java.io.IOException;
import java.math.*;

// VERSION 7/5/22

public class Node {


	// TODO: fields
	Integer nodeID, e, d, K, lastID;
	boolean useBI, transmitted, corrupt;
	Map<Integer, Node> m;
	Message msg;
	
	public Node(Integer n, Integer e, Integer d, Integer K, Boolean useBI, Map<Integer,Node> m) {
		// PRE: n is node ID,
		//      e is the exponent for the function f()
		//      d is the exponent for the function g()
		//      K is the divisor in f() and g()
		//      useBI is true if BigInteger should be used for NodeTransitionFunction, false otherwise
		//      m is a non-null map of node IDs to node objects
		// TODO
		nodeID = n;
		this.e = e;
		this.d = d;
		this.K = K;
		this.useBI = useBI;
		this.m = m;
		msg = null;
		transmitted = false;
		corrupt = false;
		lastID = -1;
	}
	


	public Boolean isDestinationNode() {
		// PRE: Based on msg received
		// POST: Returns true if this is the destination node, false otherwise

		// TODO
		
		return msg.isDestination();
	}

	
	public Message getMessage() {
		// PRE: -
		// POST: Returns the current received message, null if no received message
		
		// TODO
		
		return msg;
	}
	

	

	public Boolean transmittedMessage() {
		// PRE: -
		// POST: Return true if this node has transmitted a message, false otherwise

		// TODO
		
		return transmitted;
	}


	public void sendMsgToNodeByPath(Node n, Message msg, Vector<Integer> path) {
		// PRE: n is a non-null node that the message is being sent to,
		//      msg is a message,
		//      path is a list of valid node IDs that the message still has to traverse,
		//        after being transmitted to Node n
		// POST: invokes receiveMsgFromNodeByPath on node n
		
		// TODO
		n.receiveMsgFromNodeByPath(msg,nodeID,path);
		
	}
	
	public void sendMsgToNodeAnon(Node n, Message msg, Integer r, NodeTransitionFunction ntf) {
		// PRE: n is a non-null node that the message is being sent to,
		//      msg is a message,
		//      r is the current value of r from the forward transition function,
		//      ntf is the forward transition function
		// POST: invokes receiveMsgFromNodeAnon on node n

		// TODO
		n.receiveMsgFromNodeAnon(msg, nodeID, r, ntf);
	}

	public void sendMsgToNodeAnon(Node n, Message msg, BigInteger r, NodeTransitionFunction ntf) {
		// PRE: n is a non-null node,
		//      msg is a message,
		//      r is the current value of r from the forward transition function.
		//      ntf is the forward transition function
		// POST: invokes receiveMsgFromNodeAnon on node n

		// TODO
		n.receiveMsgFromNodeAnon(msg, nodeID, r, ntf);
	}
	
	public void receiveMsgFromNodeByPath(Message msg, Integer sendID, Vector<Integer> path) {
		// PRE: msg is a message, 
		//      sendID is the ID of the sending node,
		//      r is the current value of r from the forward transition function,
		//      ntf is the forward transition function
		// POST: If the path is empty, stop;
		//       otherwise, record in the message that the node has been visited and
		//                  send the message onwards.

		// TODO
		if(!path.isEmpty()) {
			msg.arrivedAt(nodeID);
			this.msg = msg;
			lastID = sendID;
			this.sendMsgToNodeByPath(this, msg, path);
		}
	}
	
	public void receiveMsgFromNodeAnon(Message msg, Integer sendID, Integer r, NodeTransitionFunction ntf) {
		// PRE: msg is a message, 
		//      sendID is the ID of the sending node,
		//      r is the current value of r from the forward transition function,
		//      ntf is the forward transition function
		// POST: If this is the destination node, stop;
		//       otherwise, record in the message that the node has been visited and
		//                  send the message onwards.

		// TODO
		if(!this.isDestinationNode()) {
			msg.arrivedAt(nodeID);
			this.msg = msg;
			lastID = sendID;
			this.sendMsgToNodeAnon(this, msg, r, ntf);
		}
	}

	public void receiveMsgFromNodeAnon(Message msg, Integer sendID, BigInteger r, NodeTransitionFunction ntf) {
		// PRE: msg is a message, 
		//      id is the ID of the sending node,
		//      r is the current value of r from the forward transition function,
		//      ntf is the forward transition function
		// POST: If this is the destination node, stop;
		//       otherwise, record in the message that the node has been visited and
		//                  send the message onwards.

		// TODO
		if(!this.isDestinationNode()) {
			msg.arrivedAt(nodeID);
			this.msg = msg;
			lastID = sendID;
			this.sendMsgToNodeAnon(this, msg, r, ntf);
		}
	}
	
	public String getMsg() {
		// PRE: -
		// POST: Returns the current received message, null if no received message

		// TODO
		
		return msg.getMessage();
	}

	public NodeTransitionFunction createForwardNodeTransitionFunction() {
		// PRE: -
		// POST: Creates a NodeTransitionFunction using this node's public function f() with parameters e, K

		// TODO
		
		return new NodeTransitionFunction(e, K);
	}
	
	public Integer firstRForInitiatingMessage(Integer k, Integer v) {
		// PRE: v is destination node ID, k is number of steps
		// POST: Uses the trapdoor function inverse, applied to destination node v with number of steps k, to calculate the node path;
		//       returns value of r that determines first step on node path

		// TODO
		NodeTransitionFunction fin = new NodeTransitionFunction(d, K);
		Integer r = v;
		for(int i = 1; i<=k;i++) {
			r = fin.f(r);
		}
		return r;
	}

	public BigInteger firstRForInitiatingMessage(Integer k, BigInteger v) {
		// PRE: v is destination node ID, k is number of steps as a BigInteger
		// POST: Uses the trapdoor function inverse, applied to destination node v with number of steps k, to calculate the node path;
		//       returns value of r that determines first step on node path

		// TODO
		NodeTransitionFunction fin = new NodeTransitionFunction(d, K);
		BigInteger r = v;
		for(int i = 1; i<=k;i++) {
			r = fin.f(r);
		}
		return r;
	}
	
	public void initiateMessageAnon(String msgStr, Integer k, Integer v) {
		// PRE: msg is an original message, v is destination node ID, k is number of steps
		// POST: Adds destination ID to msg; 
		//       creates Message to send to next node, as determined by firstRForInitiatingMessage(k, v),
		//       along with forward transition function
		
		// TODO
		Message msg = new Message(msgStr, v);
		Integer nextNode = this.firstRForInitiatingMessage(k, v);
		this.sendMsgToNodeAnon(this, msg, nextNode, new NodeTransitionFunction(e, K));
	}
	
	public Integer getID() {
		// PRE: -
		// POST: Returns node ID

		// TODO
		
		return nodeID;
	}
	
	public Integer getE() {
		// PRE: -
		// POST: Returns value of e in this node's function f()

		// TODO
		
		return e;
	}
	
	public Integer getK() {
		// PRE: -
		// POST: Returns value of K in this node's function f()

		// TODO
		
		return K;
	}
	

	// (HIGH) DISTINCTION: guess initiator
	
	public Integer guessInitiator() {
		// PRE: -
		// POST: Guesses a node to be the initiator if it can track back through corrupted nodes
		//       along two separate paths
		//      returns this node ID, or -1 if no guess

		// TODO
		
		return -1;
	}

	public void setCorrupt() {
		// PRE: -
		// POST: Sets a node to be corrupt

		// TODO
		corrupt = true;
	}
	
	public Integer lastSender() {
		// PRE: -
		// POST: If a node is not corrupt, returns -1;
		//       if a node is corrupt, returns ID of node that last sent it a message,
		//       -1 if it has not been sent any messages

		// TODO
		if(!corrupt) {
			return -1;
		}
		else {
			return lastID;
		}
	}
	

	// DISTINCTION: visits
	
	
	
	public Vector<Integer> allVisitableNodes(Integer v) {
		// PRE: v is destination node ID
		// POST: Returns set of all possible nodes that can be visited on the way to v,
		//         using the trapdoor method, for all possible lengths of path
		
		// TODO
		Vector<Integer> path = new Vector<Integer>();
		Integer r = 1;
		NodeTransitionFunction fin = new NodeTransitionFunction(d, K);
		while(r != v) {
			path.add(r);
			r = fin.f(r);
		}
		
		return path;		
	}
	

	public static void main(String[] args) {

	}

}
