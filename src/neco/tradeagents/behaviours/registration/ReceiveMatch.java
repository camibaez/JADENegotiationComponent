/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neco.tradeagents.behaviours.registration;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import neco.tradeagents.agents.TradeAgent;
import neco.tradeagents.behaviours.ProposeNegotiationIntialization;
import neco.onto.actions.MatchNotifyAction;
import jade.content.lang.Codec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A traves de este Behaviour un agente consumidor espera por un aviso de un Matchmaker
 * de que aparecio un proveedor que le puede interesar. En el mensaje se envia el AID del proveedor.
 *
 * @author Camilo Báez Aneiros
 */
public class ReceiveMatch extends SimpleBehaviour {
    protected boolean matchFound;
    public ReceiveMatch(Agent a) {
        super(a);
    }
    

    @Override
    public void action() {
        ((TradeAgent)myAgent).informGuiController("Waiting for Matching");
        
        
        Ontology ontology = ((TradeAgent) myAgent).getOntology();
        Codec codec = ((TradeAgent) myAgent).getCodec();

        MessageTemplate performativeMatch = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
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
        
        if (msg == null) {
            block();
            return;
        }
        
        processMsg(msg);
    }
    
    
    public void processMsg(ACLMessage msg){
        try {
            /* Se obtiene el AID del proveedor enviado por el Matchmaker */
            Action ce = (Action) myAgent.getContentManager().extractContent(msg);
            MatchNotifyAction match = (MatchNotifyAction) ce.getAction();
            AID provider = match.getMatchingAgent();
            ((TradeAgent)myAgent).setNegotiationAgent(provider);
            
            ((TradeAgent)myAgent).informGuiController("Provider Found. Name : " + provider.getName());
            myAgent.addBehaviour(new ProposeNegotiationIntialization(myAgent));
            
            matchFound = true;
            
            
        } catch (Codec.CodecException ex) {
            Logger.getLogger(ReceiveMatch.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OntologyException ex) {
            Logger.getLogger(ReceiveMatch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean done() {
        return matchFound;
    }

}
