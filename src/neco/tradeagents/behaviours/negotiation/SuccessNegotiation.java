/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neco.tradeagents.behaviours.negotiation;

import neco.tradeagents.agents.TradeAgent;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * En este behaviour se le indica al agente con el que se esta negociando que se 
 * hacepta la oferta que ha enviado, terminando la negociacion de manera satisfactoria.
 * @author Camilo Báez Aneiros
 */
public class SuccessNegotiation extends OneShotBehaviour{

    public SuccessNegotiation(Agent a) {
        super(a);
    }

    @Override
    public void action() {
         System.out.println("Closing the deal with " + ((TradeAgent)myAgent).getNegotiationAgent().getLocalName());

        
        ACLMessage acceptMessage = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
        acceptMessage.setSender(myAgent.getAID());
        acceptMessage.addReceiver(((TradeAgent)myAgent).getNegotiationAgent());

        myAgent.send(acceptMessage);
        
        ((TradeAgent)myAgent).setNegotiationState(NegotiateBehaviour.NEGOTIATION_SUCCESS_STATE);
    }

}
