package neco.negotiation_plan.plans;

import neco.negotiation_plan.NegotiationPlan;
import neco.negotiation_plan.constraints.MinimumUtilityConstraint;
import neco.offer.Offer;
import neco.offer.evaluator.OfferEvaluator;

/**
 * Esta estrategia va cambiando los valores de la oferta que se va enviando para 
 * que sea mas favorable a la contraparte y tratar de llegar a un acuerdo.
 * La oferta se va generando a partir de una utilidad la cual va disminuyendo en una
 * cantidad de unidades determinada por <code>utilityDecrement</code> hasta que se llega a un
 * acuerdo. 
 * 
 * @author Camilo Báez Aneiros
 */
public class FlexibleNegotiation extends NegotiationPlan{
    /**
     * Oferta ideal de la cual se obtiene la utiliad incial que se encuentra en el 
     * atributo <code>desiredOffer</code>, y sobre la cual se agustan la funciones de evaluacion
     * y de generacion.
     */
    protected Offer desiredOffer;
    
    /**
     * Utilidad inicial
     */
    protected float expectedUtility;
    /**
     * Minimo de utilidad aceotada de una oferta
     */
    protected float minimumUtility;
    /**
     * Decremento de la utilidad en cada ciclo
     */
    protected float utilityDecrement;
    
    /**
     * Indica si es la primera oferta que se lanza
     */
    protected boolean isFirstOffer;

    public FlexibleNegotiation(OfferEvaluator offerEvaluator, Offer desiredOffer, float utilityDecrement) {
        super(offerEvaluator);
        this.desiredOffer = desiredOffer;
        this.expectedUtility = offerEvaluator.evaluate(desiredOffer);
        this.minimumUtility = this.expectedUtility / 2;
        this.utilityDecrement = utilityDecrement;
        this.isFirstOffer = true;
        
        this.offerGenerator = this.offerEvaluator.createOfferGenerator();
        addConstraint(new MinimumUtilityConstraint(minimumUtility));
    }
    
    @Override
    public Offer generateCounterOffer() {
        if(isFirstOffer){
            isFirstOffer = false;
            lastSentOffer = desiredOffer;
            return desiredOffer;
        }
        
        //Se decrementa la utilida y se pasa a generar una nueva oferta a partir de este nuevo valor.
        expectedUtility -= utilityDecrement;
        Offer newOffer = offerGenerator.generate(expectedUtility, desiredOffer);
        lastSentOffer = newOffer;
        return newOffer;    
    }
    
}
