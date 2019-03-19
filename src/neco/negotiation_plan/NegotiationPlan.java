package neco.negotiation_plan;

import neco.negotiation_plan.constraints.NegotiationConstraint;
import java.util.ArrayList;
import java.util.List;
import neco.tradeagents.agents.TradeAgent;
import neco.offer.Offer;
import neco.offer.evaluator.OfferEvaluator;
import neco.offer.generator.OfferGenerator;
import java.io.Serializable;

/**
 * Esta clase representa la estrategia de negociacion que seguira el agente para
 * evaluar las ofertas y generar contraofertas.
 * @author Camilo Báez Aneiros
 */
public abstract class NegotiationPlan implements Serializable{
    /**
     * Cantidad de veces que se ha realizado un ciclo de negociacion
     */
    protected int negotiationTimes;
    
    /**
     * Ultima oferta enviada
     */
    protected Offer lastSentOffer;
    /**
     * Ultima oferta recibida
     */
    protected Offer lastReceivedOffer;
    
    /**
     * Agente negociador que posee esta estrategia de negociacion
     */
    protected TradeAgent tradeAgent;
    /**
     * Generadore de oferta
     */
    protected OfferGenerator offerGenerator;
    /**
     * Evalaudor de oferta
     */
    protected OfferEvaluator offerEvaluator;
    
    /**
     * Lista de restricicones de la negociacion
     */
    protected List<NegotiationConstraint> negotiationConstraints;
    
    
    public NegotiationPlan(OfferEvaluator offerEvaluator) {
        negotiationConstraints = new ArrayList<NegotiationConstraint>();
        this.offerEvaluator = offerEvaluator;
    }
    
    /**
     * Se evalua la oferta haciendola pasar por una lista de NegotiationConstraints. Si pasa 
     * todas las Constraints se toma como una buena oferta. A la primera restriccion que no
     * pase se detiene el proceso de evaluacion y se devuelve el resultado de mala oferta.
     * @see NegotiationConstraint
     * @param offer La oferta a evaluar
     * @return El resultado de evaluar la oferta. Uno de los valores del enumerador OfferEvaluationResult
     */
    public OfferEvaluationResult evaluate(Offer offer){
        setLastReceivedOffer(offer);
        
        for (NegotiationConstraint constraint : negotiationConstraints) {
            OfferEvaluationResult constraintResult = constraint.action(offer);
            switch(constraintResult){
                case TERMINATE_NEGOTIATION:
                    return OfferEvaluationResult.TERMINATE_NEGOTIATION;
                case BAD_OFFER:
                    return OfferEvaluationResult.BAD_OFFER;
                case GOOD_OFFER:
                    break;
            }
        }
        return OfferEvaluationResult.GOOD_OFFER;
    }
    
    /**
     * Este metodo es el encargado de generar una contraoferta.
     * @return La oferta generada
     */
    public abstract Offer generateCounterOffer();
    
    /**
     * Limpia el estado del NegotiationPlan y lo resetea. Hace lo mismo con 
     * cada una de sus restricciones. Esto sucede cuando un agente termino de negociar
     * y decide resetar su estado con respecto a la negociacion.
     */
    public void cleanState(){
        this.negotiationTimes = 0;
        this.lastReceivedOffer = null;
        this.lastSentOffer = null;
        
        for (NegotiationConstraint negotiationConstraint : negotiationConstraints)
            negotiationConstraint.cleanState();
        
    }
    
    
    /**
     * Añade una restriccion a la lista de restricciones.
     * @param constraint Restriccion a añadir
     */
    public void addConstraint(NegotiationConstraint constraint){
        constraint.setNegotiationPlan(this);
        this.negotiationConstraints.add(constraint);
    }
    

    public TradeAgent getTradeAgent() {
        return tradeAgent;
    }

    public void setTradeAgent(TradeAgent tradeAgent) {
        this.tradeAgent = tradeAgent;
    }

    public OfferEvaluator getOfferEvaluator() {
        return offerEvaluator;
    }

    public void setOfferEvaluator(OfferEvaluator offerEvaluator) {
        this.offerEvaluator = offerEvaluator;
    }


    public void setNegotiationTimes(int negotiationTimes){
        this.negotiationTimes = negotiationTimes;
    }
    
    public int getNegotiationTimes(){
        return negotiationTimes;
    }

    public Offer getLastSentOffer() {
        return lastSentOffer;
    }

    public void setLastSentOffer(Offer lastSentOffer) {
        this.lastSentOffer = lastSentOffer;
    }

    public Offer getLastReceivedOffer() {
        return lastReceivedOffer;
    }

    public void setLastReceivedOffer(Offer lastReceivedOffer) {
        this.lastReceivedOffer = lastReceivedOffer;
    }
    
    

}
