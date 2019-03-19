package neco.negotiation_plan.constraints;

import neco.negotiation_plan.NegotiationPlan;
import neco.negotiation_plan.OfferEvaluationResult;
import neco.offer.Offer;
import java.io.Serializable;

/**
 * Esta clase represnta una restriccion a la hora de evaluar la oferta. La evaluacion
 * se ejecuta en su metodo action
 * @author Camilo Báez Aneiros
 */
public abstract class NegotiationConstraint implements Serializable{
    protected NegotiationPlan negotiationPlan;
    
    /**
     * Evalua la oferta segun un criterio y devuelve el resultado de esa evaluacion.
     * @param offer La oferta a evaluar
     * @return El resultado de la evalaucion
     */
    public abstract OfferEvaluationResult action(Offer offer);

    public NegotiationPlan getNegotiationPlan() {
        return negotiationPlan;
    }

    public void setNegotiationPlan(NegotiationPlan negotiationPlan) {
        this.negotiationPlan = negotiationPlan;
    }
    
    /**
     * Limpia el estado de la restriccion
     */
    public abstract void cleanState();
        
    
}
