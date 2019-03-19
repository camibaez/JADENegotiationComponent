package neco.onto;

/**
 * Vocabulario de la ontologia
 * @author Camilo Báez Aneiros
 */
public interface NegotiationVocabulary {
    //Offer
    public static final String OFFER = "Offer";
    public static final String DIMENSIONS = "Dimensions";
    
    //Dimension
    public static final String DIMENSION = "Dimension";
    public static final String DIMENSION_NAME = "Name";
    public static final String DIMENSION_TYPE = "Type";
    public static final String DIMENSION_VALUE = "Value";
    
   
                               
    
    //Registration
    public static final String REGISTRATION = "Registration",
                               REGISTRATION_AID = "AID", 
                               REGISTRATION_AGENT_TYPE = "AgentType",
                               REGISTRATION_RESOURCE_TYPE = "ResourceType";    
    
    //Propose Registration Aaction
    public static final String PROPOSE_REGISTRATION = "ProposeRegistration",
                               PROPOSE_REGISTRATION_REGISTRATION = "Registration"; 
    
    public static final String PROPOSE_UNREGISTRATION = "ProposeUnregistration";
    public static final String PROPOSE_UREGISTRATION_PROPOSER = "Proposer";
    
    
    //Propose Negoatiation Action
    public static final String PROPOSE_NEGOTIATION = "ProposeNegotiation";
    public static final String PROPOSER_AID = "Proposer";
    
    
    
    
     //Matching Notify Action
    public static final String MATCHING = "MatchNotifyAction",
                               MATCHING_AGENT = "MatchingAgent"; 
    
    //Propose OFfer Action
    public static final String PROPOSE_OFFER = "ProposeOffer",
                               PROPOSE_OFFER_PROPOSER = "Proposer",
                               PROPOSE_OFFER_RECEIVER = "Receiver",
                               PROPOSE_OFFER_OFFER = "Offer";
}
