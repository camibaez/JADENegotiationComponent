package neco.onto;

import neco.onto.concepts.OfferConcept;
import neco.onto.concepts.DimensionConcept;
import neco.onto.actions.ProposeNegotiationAction;
import neco.onto.actions.ProposeOfferAction;
import neco.onto.actions.ProposeRegistrationAction;
import neco.onto.actions.MatchNotifyAction;
import neco.onto.concepts.RegistrationConcept;
import jade.content.onto.BasicOntology;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.schema.AgentActionSchema;
import jade.content.schema.ConceptSchema;
import jade.content.schema.ObjectSchema;
import jade.content.schema.PrimitiveSchema;
import java.util.logging.Level;
import java.util.logging.Logger;
import neco.onto.actions.ProposeUnregistrationAction;

/**
 * Ontologia creada para la negociacion. Implementa la intefaz NegotiationVocabulary
 * en la cual se encuentra el vocabulario de la ontologia.
 * @author Camilo Báez Aneiros
 */
public class NegotiationOntology extends Ontology implements NegotiationVocabulary{

    public static final String ONTOLOGY_NAME = "NegotiationOntology";
    
    protected static NegotiationOntology instance = new NegotiationOntology();

    public static NegotiationOntology getInstance() {
        return instance;
    }

    private NegotiationOntology() {
       super(ONTOLOGY_NAME, BasicOntology.getInstance());

        try {
            add(new ConceptSchema(OFFER), OfferConcept.class);
            add(new ConceptSchema(DIMENSION), DimensionConcept.class);
            add(new ConceptSchema(REGISTRATION), RegistrationConcept.class);
            
            add(new AgentActionSchema(PROPOSE_REGISTRATION), ProposeRegistrationAction.class);
            add(new AgentActionSchema(PROPOSE_UNREGISTRATION), ProposeUnregistrationAction.class);
            add(new AgentActionSchema(PROPOSE_NEGOTIATION),ProposeNegotiationAction.class);
            add(new AgentActionSchema(MATCHING), MatchNotifyAction.class);
            add(new AgentActionSchema(PROPOSE_OFFER), ProposeOfferAction.class);
            
            
            // Funciones de incializacion y configuracion de conceptos
            initOfferSchema();
            initDimensionSchema();
            initRegistrationSchema();
            
            //Funciones de incializacion y configuracion de acciones de agentes
            initProposeRegistrationAction();
            initProposeUnregistrationAction();
            initProposeNegotiationAction();
            initMatchNotifyAction();
            initProposeOfferAction();
            
        } catch (OntologyException ex) {
            Logger.getLogger(NegotiationOntology.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /* INIT CONCEPTS */
    protected void initOfferSchema() throws OntologyException{
        ConceptSchema offer = (ConceptSchema) getSchema(OFFER);
        ConceptSchema dimension = (ConceptSchema) getSchema(DIMENSION);
        offer.add(DIMENSIONS, dimension, 1, ObjectSchema.UNLIMITED);
    }
    
    protected void initDimensionSchema() throws OntologyException{
        ConceptSchema dimension = (ConceptSchema) getSchema(DIMENSION);
        dimension.add(DIMENSION_NAME, (PrimitiveSchema) getSchema(BasicOntology.STRING));
        dimension.add(DIMENSION_TYPE, (PrimitiveSchema) getSchema(BasicOntology.STRING));
        dimension.add(DIMENSION_VALUE, (PrimitiveSchema) getSchema(BasicOntology.STRING));
    }
    
    protected void initRegistrationSchema() throws OntologyException {
        ConceptSchema registration = (ConceptSchema) getSchema(REGISTRATION);
        registration.add(REGISTRATION_AID, (ConceptSchema) getSchema(BasicOntology.AID));
        registration.add(REGISTRATION_AGENT_TYPE, (PrimitiveSchema) getSchema(BasicOntology.STRING));
        registration.add(REGISTRATION_RESOURCE_TYPE, (PrimitiveSchema) getSchema(BasicOntology.STRING));
    }
    
    /* INIT AGENT ACTIONS */  
    protected void initProposeRegistrationAction() throws OntologyException{
        AgentActionSchema action = (AgentActionSchema) getSchema(PROPOSE_REGISTRATION);
        action.add(PROPOSE_REGISTRATION_REGISTRATION,  (ConceptSchema) getSchema(REGISTRATION));
    }
    
    protected void initProposeNegotiationAction() throws OntologyException{
        AgentActionSchema action = (AgentActionSchema) getSchema(PROPOSE_NEGOTIATION);
        action.add(PROPOSER_AID,  (ConceptSchema) getSchema(BasicOntology.AID));
        
    }
    
     protected void initMatchNotifyAction() throws OntologyException{
        AgentActionSchema match = (AgentActionSchema) getSchema(MATCHING);
        match.add(MATCHING_AGENT, (ConceptSchema) getSchema(BasicOntology.AID));
    }

    protected void initProposeOfferAction() throws OntologyException{
          AgentActionSchema action = (AgentActionSchema) getSchema(PROPOSE_OFFER);
          action.add(PROPOSE_OFFER_PROPOSER, (ConceptSchema) getSchema(BasicOntology.AID));
          action.add(PROPOSE_OFFER_RECEIVER, (ConceptSchema) getSchema(BasicOntology.AID));
          action.add(PROPOSE_OFFER_OFFER, (ConceptSchema) getSchema(OFFER));
          
    }
    protected void initProposeUnregistrationAction() throws OntologyException {
        AgentActionSchema action = (AgentActionSchema) getSchema(PROPOSE_UNREGISTRATION);
        action.add(PROPOSE_UREGISTRATION_PROPOSER,  (ConceptSchema) getSchema(BasicOntology.AID));
    }
     
}
