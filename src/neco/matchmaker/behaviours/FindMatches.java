package neco.matchmaker.behaviours;

import neco.matchmaker.AgentRegistration;
import neco.matchmaker.Blackboard;
import neco.matchmaker.MatchmakerAgent;
import neco.onto.actions.MatchNotifyAction;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Behaviour del Matchmaker que se encarga de encontrar los matcheos de los
 * agentes. Se ejecuta el ciclo cada cierta cantidad de milisegundos. Cada vez
 * que encuentra un Matcheo notifica al consumidor y le envia el AID del
 * proveedor que coicide con el. Luego borra el registro del consumidor y el proveedor que matcheo
 * para simplificar el procedimiento.
 *
 * @author Camilo Báez Aneiros
 */
public class FindMatches extends TickerBehaviour {

    //TODO Mejorar el behaviour para no tener que eliminar los registros que matcheen, sino ponerlos en ocupado.
    /**
     * Lista de registros de consumidores y proveedores que han matcheado y deben ser quitados del registro.
     */
    protected List<AgentRegistration> consumerToDelete = new ArrayList<AgentRegistration>(),
                                      providersToDelete = new ArrayList<AgentRegistration>();

    public FindMatches(Agent a, long period) {
        super(a, period);
    }

    @Override
    protected void onTick() {
        //Se obtiene la lista de registros de consumidor y de proveedor del Blackboard
        Blackboard blackboard = ((MatchmakerAgent) myAgent).getBlackboard();
        List<AgentRegistration> consumers = blackboard.getConsumersList();
        List<AgentRegistration> providers = blackboard.getProvidersList();

        //Ciclo de revision de matcheo. Para cada consumidor buscara en cada proveedor uno q coincida
        for (AgentRegistration consumer : consumers) {
            //Si el consumidor esta ocupado no revisarlo
            if (consumer.getState() == AgentRegistration.BUSSY) {
                continue;
            }
            for (AgentRegistration provider : providers) {
                //Si coincide el tipo de recurso
                if (consumer.getResourceType().equals(provider.getResourceType())) {
                    //Si se trato sin errores el matcheo continuar con el proximo consumidor.
                    if (handleMatching(consumer, provider)) {
                        break;
                    }
                }
            }
        }

        //Se eliminan los registros de consumidres y proeveedores que hayan matcheado.
        consumers.removeAll(consumerToDelete);
        providers.removeAll(providersToDelete);

    }

    /**
     * Maneja la situacion cuando se encuentra un matcheo. Envia al consumidor
     * el AID del proveedor que le coincide
     *
     * @param consumer Registro del consumidor
     * @param provider Registro del proveedor
     * @return Si la operacion fue exitosa o no
     */
    public boolean handleMatching(AgentRegistration consumer, AgentRegistration provider) {
        try {
            AID consumerAID = consumer.getAID();
            AID providerAID = provider.getAID();

            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.setOntology(((MatchmakerAgent) myAgent).getOntology().getName());
            msg.setLanguage(((MatchmakerAgent) myAgent).getCodec().getName());

            msg.addReceiver(consumerAID);
            msg.setSender(myAgent.getAID());
            msg.setContent(STATE_READY);

            MatchNotifyAction match = new MatchNotifyAction();
            match.setMatchingAgent(providerAID);
            Action action = new Action(myAgent.getAID(), match);

            myAgent.getContentManager().fillContent(msg, action);
            myAgent.send(msg);
            
            
            consumerToDelete.add(consumer);
            providersToDelete.add(provider);

            //TODO Implementar un sistema de estados (DISPONIBLE, OCUPADO, ETC..). Revisar recomendaciones del Matchmaker en el documento tecnico del componente.
            System.out.println("    Matchmaker Information");
            System.out.println("Match Found: " + consumerAID.getName() + " <=> " + providerAID.getName() + " (Type:" + consumer.getResourceType() + ")");
            return true;
        } catch (Codec.CodecException ex) {
            Logger.getLogger(FindMatches.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OntologyException ex) {
            Logger.getLogger(FindMatches.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }
}
