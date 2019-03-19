package neco.mobility.behaviours;

import jade.core.AID;
import neco.mobility.MobileAgent;
import neco.tradeagents.behaviours.registration.IdentifyEnviorment;
import jade.core.Location;
import jade.lang.acl.ACLMessage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import neco.mobility.InterAgentCreator;
import neco.mobility.TradeAgentDescription;
import neco.tradeagents.agents.TradeAgent;

/**
 * Behaviour que facilita la tarea de hacer migrar al agente hacia el contenedor
 * de negociacion. Asigna al valor de su atributo destination el valor que tiene
 * el agente como contenedor de negociacion.
 *
 * @author Camilo Báez Aneiros
 */
public class MigrateTradeHost extends MoveBehaviour {

    @Override
    public void action() {
        MobileAgent agent = (MobileAgent) myAgent;
        //Si es movilidad interplataforma primero va a migrar hacia el contendor principal y luego se le
        //orderna que migre hacia el contenedor de destino.
        if (agent.getTradePlatform() != null) {

                //myAgent.doWait(20000);

            
            sendInterPlatformAgent();
            //myAgent.doDelete();
            return;
                   
        } else {
            destination = agent.getTradeHost();
            agent.setAfterMoveAction(new IdentifyEnviorment());
        }

        super.action();

    }

    
    protected void sendInterPlatformAgent(){
        TradeAgentDescription desc = new TradeAgentDescription((TradeAgent)myAgent);
        ACLMessage msg = new ACLMessage(ACLMessage.SUBSCRIBE);
        
        String address = desc.tradePlatform.getAddress();
        String creatorFullName = InterAgentCreator.CREATOR_NAME + "@" + desc.tradePlatform.getAmsAID().getHap();
        System.err.println(creatorFullName);
        System.err.println(address);
        AID creatorAID = new AID(creatorFullName, AID.ISGUID);
        creatorAID.addAddresses(address);
        
        msg.addReceiver(creatorAID);
        
        try {
            msg.setContentObject(desc);
            myAgent.send(msg);
            System.err.println("Sent Message Migration ");
        } catch (IOException ex) {
            Logger.getLogger(MigrateTradeHost.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
