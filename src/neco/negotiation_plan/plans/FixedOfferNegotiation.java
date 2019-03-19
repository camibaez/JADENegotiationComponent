package neco.negotiation_plan.plans;

import neco.negotiation_plan.NegotiationPlan;
import neco.negotiation_plan.constraints.FixedOfferConstraint;
import neco.negotiation_plan.constraints.MaxNegotiationTimesConstraint;
import neco.offer.Offer;

/**
 * Con este plan de Negociacion se acepta solo las ofertas cuyas dimensiones sean 
 * iguales a las de la oferta deseada.
 * @author Camilo Báez Aneiros
 */
public class FixedOfferNegotiation extends NegotiationPlan{
    Offer desiredOffer;
    
    public FixedOfferNegotiation(Offer offer, int maxTimes){
        super(null);
        this.desiredOffer = offer;
        addConstraint(new MaxNegotiationTimesConstraint(maxTimes));
        addConstraint(new FixedOfferConstraint(offer));
    }
    
    public FixedOfferNegotiation(Offer offer) {
        this(offer, 3);
    }

    @Override
    public Offer generateCounterOffer() {
        return desiredOffer;
    }

}
