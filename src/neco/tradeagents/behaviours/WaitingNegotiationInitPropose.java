/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neco.tradeagents.behaviours;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import neco.tradeagents.agents.TradeAgent;
import neco.onto.actions.ProposeNegotiationAction;
import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Mediante este behviour un agente (generalmente un proveedor de servicios)
 * espera ser contactado por un consumidor interesado en los servicios que
 * provee para iniciar una negociacion.
 *
 * @author Camilo Báez Aneiros
 */
public class WaitingNegotiationInitPropose extends SimpleBehaviour {
    protected boolean negotiationInitialized;
    public WaitingNegotiationInitPropose(Agent a) {
        super(a);
    }
    

    @Override
    public void action() {

        Ontology ontology = ((TradeAgent) myAgent).getOntology();
        Codec codec = ((TradeAgent) myAgent).getCodec();
        
        MessageTemplate performativeMatch = MessageTemplate.MatchPerformative(ACLMessage.PROPOSE);
        MessageTemplate codecMatch = MessageTemplate.MatchLanguage(codec.getName());
        MessageTemplate ontoMatch = MessageTemplate.MatchOntology(ontology.getName());
        MessageTemplate mt = MessageTemplate.and(
                performativeMatch,
                MessageTemplate.and(
                        codecMatch,
                        ontoMatch
                )
        );
        ACLMessage msg = myAgent.receive(mt);
        
        ((TradeAgent) myAgent).informGuiController("Waiting For Negotiation proposition....");
        if (msg == null) {
            block();
            return;
        }
        
        try {
            ContentElement ce = myAgent.getContentManager().extractContent(msg);
            if (ce instanceof Action && ((Action) ce).getAction() instanceof ProposeNegotiationAction) {
                ProposeNegotiationAction initiateAction = (ProposeNegotiationAction) ((Action) ce).getAction();
                AID proposer = initiateAction.getProposer();
                ((TradeAgent) myAgent).setNegotiationAgent(proposer);
                negotiationInitialized = true;
                
                confirmNegotiatioInit();
                ((TradeAgent) myAgent).informGuiController("Negotiation Initialized with: " + proposer.getName() + "\n------------------");
                myAgent.addBehaviour(new ProposeOffer(myAgent));
            }else{
                
            }
        } catch (Codec.CodecException ex) {
            Logger.getLogger(WaitingNegotiationInitPropose.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OntologyException ex) {
            Logger.getLogger(WaitingNegotiationInitPropose.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
    
    
    protected void confirmNegotiatioInit(){
        ((TradeAgent) myAgent).informGuiController("Accepted Negotiation Invitation");
        ACLMessage msg = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
        msg.addReceiver(((TradeAgent)myAgent).getNegotiationAgent());
        msg.setSender(myAgent.getAID());
        
        myAgent.send(msg);
    }

    @Override
    public boolean done() {
        return negotiationInitialized;
    }

}
