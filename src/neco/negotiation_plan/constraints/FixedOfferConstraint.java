package neco.negotiation_plan.constraints;

import neco.negotiation_plan.OfferEvaluationResult;
import neco.offer.Offer;

/**
 * Esta restriccion solo permite aquellas ofertas cuyas dimensiones sean iguales
 * a las de la oferta deseada.
 * @author Camilo Báez Aneiros
 */
public class FixedOfferConstraint extends NegotiationConstraint{
    protected Offer desiredOffer;
    public FixedOfferConstraint(Offer offer){
        desiredOffer = offer;
    }
    @Override
    public OfferEvaluationResult action(Offer offer) {
        return desiredOffer.equals(offer) ? OfferEvaluationResult.GOOD_OFFER :
                                            OfferEvaluationResult.BAD_OFFER;            
    }

    @Override
    public void cleanState() {
        
    }

}
