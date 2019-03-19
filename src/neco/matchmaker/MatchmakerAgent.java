package neco.matchmaker;

import neco.matchmaker.behaviours.FindMatches;
import implementationJADE.WorkDF;
import neco.matchmaker.behaviours.ReceiveRegistrations;
import neco.onto.NegotiationOntology;
import neco.onto.concepts.RegistrationConcept;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.ReceiverBehaviour;
import java.io.Console;
import java.util.logging.ConsoleHandler;

/**
 * Esta clase representa al agente Matchmaker. Este agente es el encargado de
 * realizar las busquedas de coincidencias entre los agentes negociadores.
 *
 * @author Camilo Báez Aneiros
 */
public class MatchmakerAgent extends Agent {

    public static final String MATCHMAKER_SERVICE = "MatchmakerService",
            TRADING_CONTAINER_NAME = "TradingContainer";

    protected Codec codec = new SLCodec();
    protected NegotiationOntology ontology = NegotiationOntology.getInstance();

    protected Blackboard blackboard;

    @Override
    protected void setup() {
        super.setup();

        //Registro de ontologia y codec del lenguaje
        getContentManager().registerLanguage(codec);
        getContentManager().registerOntology(ontology);

        WorkDF.registerByType(this, MATCHMAKER_SERVICE);

        blackboard = new Blackboard();

        addBehaviour(new ReceiveRegistrations());
        addBehaviour(new FindMatches(this, 3000));
    }

    /**
     * En esta funcion se preprocesa la registracion para que cumpla con los
     * estandares del Matchmaker. Se manipula el objeto, transformando sus
     * valores segun sea necesario para que todos los registrso cumplan las
     * reglas establecidas.
     *
     * @param registration Representa el objeto de registro
     */
    protected void normalizeRegistration(AgentRegistration registration) {
        String resourceType = registration.getResourceType();
        resourceType = resourceType.toUpperCase().trim();
        registration.setResourceType(resourceType);
    }

    /**
     * Añade el registro de agente al balckboard del Matchmaker.
     *
     * @param registration El objeto que representa el registro del agente
     *
     * @see AgentRegistration
     * @see Blackboard
     */
    public void register(AgentRegistration registration) {
        normalizeRegistration(registration);
        System.out.println("");
        System.out.println("    Matchmaker Information");
        if (registration.getAgentType().equals(AgentRegistration.CONSUMER_AGENT)) {
            blackboard.consumersList.add(registration);
            System.out.println("Consumer " + registration.getAID().getLocalName() + " Added To Register");
        }
        if (registration.getAgentType().equals(AgentRegistration.PROVIDER_AGENT)) {
            blackboard.providersList.add(registration);
            System.out.println("Provider " + registration.getAID().getLocalName() + " Added To Register");
        }
        
        this.blackboard.showRegistersState();
    }

    /**
     * Registra al agente en el blackboard a partir de un RegistrationConcept
     * que se obtuvo a partir del mensaje ACL enviado por el agente que desea
     * registrarse.
     *
     * @param registration
     */
    public void register(RegistrationConcept registration) {
        AgentRegistration agentRegistration = new AgentRegistration(registration.getAID(),
                                                                    registration.getAgentType(), 
                                                                    registration.getResourceType()
                                                    );
        register(agentRegistration);
    }

    public void unregister(AID aid) {

    }

    public NegotiationOntology getOntology() {
        return ontology;
    }

    public Codec getCodec() {
        return codec;
    }

    public Blackboard getBlackboard() {
        return blackboard;
    }
}
