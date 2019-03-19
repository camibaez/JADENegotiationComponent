package neco.negotiation_plan.constraints;

import neco.negotiation_plan.OfferEvaluationResult;
import neco.offer.Offer;

/**
 * Esta restriccion permite solamente aquellas ofertas cuya utilidad sea mayor o igual
 * que una utilidad deseada.
 * @author Camilo Báez Aneiros
 */
public class MinimumUtilityConstraint extends NegotiationConstraint{
    protected float minimumUtility;
    
    public MinimumUtilityConstraint(float minimumUtility) {
        this.minimumUtility = minimumUtility;
    }
    
    @Override
    public OfferEvaluationResult action(Offer offer) {                                                                                    
        if(getNegotiationPlan().getOfferEvaluator().evaluate(offer) >= minimumUtility)
            return OfferEvaluationResult.GOOD_OFFER;
        else
            return OfferEvaluationResult.BAD_OFFER;
    }

    @Override
    public void cleanState() {
        
    }

}
