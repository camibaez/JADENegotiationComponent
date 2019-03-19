package neco.tradeagents.agents;

import jade.core.AID;
import java.util.HashMap;
import neco.offer.evaluator.OfferEvaluator;
import neco.negotiation_plan.NegotiationPlan;
import neco.offer.Offer;
import neco.mobility.MobileAgent;
import neco.mobility.behaviours.MigrateTradeHost;
import neco.onto.NegotiationOntology;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.lang.acl.ACLMessage;

/**
 * Esta clase representa al agente de negociación. Cada TradeAgent se crea para una 
 * negociacion en especifico.
 * @author Camilo Báez Aneiros
 */
public abstract class TradeAgent extends MobileAgent{
    public static final String OFFER_EVALUATOR = "OfferEvaluator";
    public static final String RESOURCE_TYPE = "ResourceType";
    public static final String NEGOTIATION_PLAN = "NegotiationPlan";
    public static final String NEGOTIATION_AGENT = "NegotiationAgent";
    public static final String MATCHMAKER_AGENT = "MatchmakerAgent";
    
    /**
     * Agente controloador con el cual el TradeAgent se comunica y al cual le entrega la informacion
     * referente al estado de la negociacion, asi como el resultado de las misma.
     */
    public AID controllerAgent;
    public static final String G_CONTROLLER =  "GController";
    
    /**
     * OfferEvaluator para evluar las ofertas de la negociacion en la que se encuentra
     * el agente.
     * @see OfferEvaluator
     */
    protected OfferEvaluator offerEvaluator;
    
    /**
     * Plan de negociacion que usara el agente en la negociacion
     * @see NegotiationPlan
     */
    protected NegotiationPlan negotiationPlan;
    
    /**
     * Agente con el cual se esta negociando.
     */
    protected AID negotiationAgent;
    
    /**
     * Agente matchmaker con el que el negociador se comunica
     */
    protected AID matchmakerAgent;
    
    /**
     * Oferta actual que esta analizando el agente
     * 
     */
    protected Offer actualOffer;
    protected Offer lastOffer;
    protected String negotiationState;
    /**
     * Tipo de agente (Consumer, Provider)
     */
    protected String agentType;
    
    /**
     * Tipo de recurso que se va a negociar.
     */
    protected String resourceType;
    
    /**
     * Codec del leguaje usado para el uso de la ontologia en los mensajes.
     */
    protected Codec codec = new SLCodec();
    /**
     * Ontologia usada para la negociacion.
     */
    protected NegotiationOntology ontology = NegotiationOntology.getInstance();

    @Override
    protected void setup() {
        super.setup();
        HashMap<String, Object> arguments = (HashMap<String, Object>) getArguments()[0];
        
        setNegotiationPlan((NegotiationPlan) arguments.get(NEGOTIATION_PLAN));
        setResourceType((String) arguments.get(RESOURCE_TYPE));
        
        //addBehaviour(new ProposeRegistration());
        addBehaviour(new MigrateTradeHost());
    }
    
    protected void loadAfterMove(){
        getContentManager().registerLanguage(codec);
        getContentManager().registerOntology(ontology);
    }
    
    /**
     * Esta funcion se invoca una vez que el registro ha sido realizado con exito en el matchmaker.
     * Cada tipo de agente debera implementarla, a partir del protocolo de negociacion.
     */
    public abstract void registrationDone();
    
    /**
     * Limbia el estado del agente y de su NegotiationPlan. Esto sucede cuando el agente 
     * termino de negociar y debe resetear su estado para inciar una nueva negociacion.
     */
    public void cleanState(){
        negotiationAgent = null;
        actualOffer =null;
        negotiationState = null;
        negotiationPlan.cleanState();
    }

    public NegotiationPlan getNegotiationPlan() {
        return negotiationPlan;
    }
    public void setNegotiationPlan(NegotiationPlan negotiationPlan) {
        this.negotiationPlan = negotiationPlan;
        if(negotiationPlan != null)
            this.negotiationPlan.setTradeAgent(this);
    }
    
   

    public AID getNegotiationAgent() {
        return negotiationAgent;
    }
    public void setNegotiationAgent(AID negotiationAgent) {
        this.negotiationAgent = negotiationAgent;
    }

    public Offer getActualOffer() {
        return actualOffer;
    }
    public void setActualOffer(Offer actualOffer) {
        this.actualOffer = actualOffer;
        this.negotiationPlan.setLastReceivedOffer(lastOffer);
       
    }

    public Offer getLastOffer() {
        return lastOffer;
    }

    public void setLastOffer(Offer lastOffer) {
        this.lastOffer = lastOffer;
    }
    
    public Codec getCodec(){
        return codec;
    }
    
    public Ontology getOntology(){
        return ontology;
    }
    
    public AID getMatchmaker(){
        return matchmakerAgent;
    }
    public void setMatchmaker(AID matchmaker){
        this.matchmakerAgent = matchmaker;
    }
    
    public String getResourceType(){
        return resourceType;
    }
    
    public void setResourceType(String resourceType){
        this.resourceType = resourceType;
    }
    
    public String getAngentType() {
       return getClass().getName();
    }

    public String getNegotiationState() {
        return negotiationState;
    }

    public void setNegotiationState(String negotiationState) {
        this.negotiationState = negotiationState;
    }
    
    public void informGuiController(String information) {
        if(this instanceof ConsumerAgent)
            System.err.println(this.getLocalName() + ": " + information);
        else
            System.out.println("                                        " + this.getLocalName() + ": " + information);
    }

}
