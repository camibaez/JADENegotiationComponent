package neco.tradeagents.agents;

import neco.tradeagents.behaviours.registration.ReceiveMatch;

/**
 * Este agente representa a un agente consumidor.
 * @author Camilo Báez Aneiros
 */
public class ConsumerAgent extends TradeAgent{
    @Override
    protected void setup() {
        super.setup();
        
    }

    @Override
    public void registrationDone() {
        addBehaviour(new ReceiveMatch(this));
        
    }

   
    
}
