/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neco.tradeagents.behaviours.negotiation;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import neco.offer.Offer;
import neco.tradeagents.agents.TradeAgent;
import neco.negotiation_plan.OfferEvaluationResult;
import neco.tools.TestLoader;

/**
 * Este Behaviour es el encargado de evaluar la oferta que le ha llegado al agente.
 * @author Camilo Báez Aneiros
 */
public class EvaluateOffer extends OneShotBehaviour{
    protected int exitState;

    public EvaluateOffer(Agent a){
        super(a);
    }
    
    @Override
    public void action() {
        Offer offer =  ((TradeAgent) myAgent).getActualOffer();
        if(offer == null)
            return;
        ((TradeAgent)myAgent).informGuiController("Evaluating Offer...");
        myAgent.doWait(TestLoader.WAIT_TIME);
        
        OfferEvaluationResult evaluationResponse = ((TradeAgent)myAgent).getNegotiationPlan().evaluate(offer);
        
        
        
        if(evaluationResponse == OfferEvaluationResult.GOOD_OFFER){

            ((TradeAgent)myAgent).informGuiController("Good Offer");
            
            exitState = NegotiateBehaviour.GOOD_OFFER_TRANS;
        }
        else if(evaluationResponse == OfferEvaluationResult.BAD_OFFER){

            ((TradeAgent)myAgent).informGuiController("Bad Offer");
            ((TradeAgent)myAgent).informGuiController("Creating New Offer");

            
            exitState = NegotiateBehaviour.BAD_OFFER_TRANS;
        }
        
        else{

            ((TradeAgent)myAgent).informGuiController("Exceded negotiation times");

            exitState = NegotiateBehaviour.ABORT_NEGOTIATION_TRANS;
        }
            
        
    }

    @Override
    public int onEnd() {
        return exitState;
    }
    
    

}
