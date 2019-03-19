package neco.negotiation_plan.constraints;

import neco.negotiation_plan.OfferEvaluationResult;
import neco.offer.Offer;

/**
 * Esta restriccion solo permite que los intentos de negociacion sean solo de una
 * cantidad determinada de intentos.
 * @author Camilo Báez Aneiros
 */
public class MaxNegotiationTimesConstraint extends NegotiationConstraint{
    protected int maxNegotiationTimes;
    public MaxNegotiationTimesConstraint(int maxNegotiationTimes){
        this.maxNegotiationTimes = maxNegotiationTimes;
    }
    @Override
    public OfferEvaluationResult action(Offer offer) {
        int negotiationTimes = getNegotiationPlan().getNegotiationTimes();
                
        if(negotiationTimes >= maxNegotiationTimes)
            return OfferEvaluationResult.TERMINATE_NEGOTIATION;
        
        getNegotiationPlan().setNegotiationTimes(negotiationTimes + 1);
        return OfferEvaluationResult.GOOD_OFFER;        
    }

    @Override
    public void cleanState() {
        
    }

}
