package neco.matchmaker.behaviours;

import neco.matchmaker.MatchmakerAgent;
import neco.onto.actions.ProposeRegistrationAction;
import neco.onto.concepts.RegistrationConcept;
import jade.content.lang.Codec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.logging.Level;
import java.util.logging.Logger;
import neco.onto.actions.ProposeUnregistrationAction;

/**
 * Behaviour que se encarga de recibir los pedidos de registro y desregistro de los agentes negociadores.
 * @author Camilo Báez Aneiros
 */
public class ReceiveRegistrations extends CyclicBehaviour{

    
    @Override
    public void action() {
        Ontology ontology = ((MatchmakerAgent) myAgent).getOntology();
        Codec codec = ((MatchmakerAgent) myAgent).getCodec();

        //Filtrado del tipo de mensaje
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
        
        if (msg == null) {
            block();
            return;
        }
        processMsg(msg);
        
    }
    
    protected void processMsg(ACLMessage msg){
        try {
            /*
            Se extrae el contenido de el mensaje que se espera sea un ProposeNegotiationAction.
            De este a su vez se extrae el RegistrationConcept que tiene la inforamacion de registro
            del agente.
            */         
            Action ce = (Action) myAgent.getContentManager().extractContent(msg);
            if (ce.getAction() instanceof ProposeRegistrationAction) {
                ProposeRegistrationAction registrationAction = (ProposeRegistrationAction) ((Action) ce).getAction();
                RegistrationConcept registration = registrationAction.getRegistration();
                
                //TODO Por ahora el Matchmaker siempre te registra. Se pueden implementar comprobaciones para decidir si registrar o no al agente.
                acceptRegistrationProposal(registration);
                
            //Si es un pedido de desregistro por parte de un agente
            }else if(ce.getAction() instanceof ProposeUnregistrationAction){
                AID agent = ((ProposeUnregistrationAction) ce.getAction()).getProposer();

               //TODO El MM siempre te deregistras si se lo pides. Hay que hacer comprobaciones para saber si el q pide el desregistro
               //es el propio agente que se va a desregistrar. Implementar en el futuro un TOKEN.
                acceptUnregistrationProposal(agent);
            }
        } catch (Codec.CodecException ex) {
            Logger.getLogger(ReceiveRegistrations.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OntologyException ex) {
            Logger.getLogger(ReceiveRegistrations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Aceptar pedido de registro
     * @param registration Pedido de registro del agente
     */
    protected void acceptRegistrationProposal(RegistrationConcept registration){
        ((MatchmakerAgent)myAgent).register(registration);
    }
    
    /**
     * Rechazar pedido de registro
     */
    protected void rejectRegistrationProposal(){
        
    }

    /**
     * Aceptar pedido de desregistro
     * @param agent Identificador del agente que hace el pedido de desregistro
     */
    protected void acceptUnregistrationProposal(AID agent) {
        ((MatchmakerAgent)myAgent).unregister(agent);
    }
}
