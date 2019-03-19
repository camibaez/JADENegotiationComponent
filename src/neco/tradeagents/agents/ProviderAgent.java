package neco.tradeagents.agents;

import neco.tradeagents.behaviours.WaitingNegotiationInitPropose;

/**
 * Este agente representa a un agente proveedor
 * @author Camilo B�ez Aneiros
 */
public class ProviderAgent extends TradeAgent{
    @Override
    protected void setup() {
        super.setup();
        
    }

    @Override
    public void registrationDone() {
        addBehaviour(new WaitingNegotiationInitPropose(this));
    }
    
}
