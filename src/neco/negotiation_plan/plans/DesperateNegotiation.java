package neco.negotiation_plan.plans;

import neco.negotiation_plan.NegotiationPlan;
import neco.negotiation_plan.constraints.MinimumUtilityConstraint;
import neco.offer.Offer;
import neco.offer.evaluator.OfferEvaluator;

/**
 * En este plan de negociacion se acepta una oferta si su utilidad es mayor o igual que 
 * la utilidad deseada, derivada de la oferta deseada.
 * @author Camilo Báez Aneiros
 */
public class DesperateNegotiation extends NegotiationPlan{
    /**
     * Oferta deseada para obtener la utilidad mínima
     */
    protected Offer desiredOffer;
    /**
     * Minima utilidad que se acepta de una oferta que se reciba
     */
    protected float minimumUtility;
    
    public DesperateNegotiation(OfferEvaluator offerEvaluator, Offer desiredOffer){
        super(offerEvaluator);
        this.desiredOffer = desiredOffer;
        this.minimumUtility = offerEvaluator.evaluate(desiredOffer);
        
        addConstraint(new MinimumUtilityConstraint(minimumUtility));   
    }
    
    @Override
    public Offer generateCounterOffer() {
        return desiredOffer;
    }

}
