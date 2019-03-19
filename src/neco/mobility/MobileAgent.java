package neco.mobility;

import jade.core.Agent;
import jade.core.Location;
import jade.core.PlatformID;
import jade.core.behaviours.Behaviour;
import java.util.HashMap;

/**
 * La clase <code>MobileAgent</code> representa a un agente movil. 
 * Provee la abstraccion de origen y destino del agente.Su implementación esta 
 * muy realacionada con el concepto de agente negociador porque esta deiseñada para satisfacer
 * las necesidades de movildad de este.
 * 
 * @author Camilo Báez Aneiros
 */
public abstract class MobileAgent extends Agent{
    //Claves de los atributos del diccionario de configuracion que se recibe en la inicalizacion del agente
    public static final String HOME_CONTAINER = "HomeHost", //Cotendor de origen
                               HOME_PLATFORM = "HomePlatform", //Plataforma de origen
                               TRADE_CONTAINER = "TradeHost", //Contendor de negociacion (destino)
                               TRADE_PLATFORM = "TradePlatform"; //Plataforma de negociacion (destino)
    
    /**
     * Identificador de la plataforma de origen
     */
    protected PlatformID homePlatform;
    /**
     * Identificador de la plataforma de negociacion (destino)
     */
    protected PlatformID tradePlatform;
    /**
     * Identificador del contendor de origen
     */
    protected Location homeHost;
    /**
     * Identificador del contenedor de negociacion (destino)
     */
    protected Location tradeHost;
    
    /**
     * Behvaiour que sera cargado cuando el agente migre. Es cargado por el metodo 
     * afterMove()
     */
    protected Behaviour afterMoveAction;

   
    
    @Override
    protected void setup(){
        super.setup();
        HashMap<String, Object> arguments = (HashMap<String, Object>) getArguments()[0];
        
        homeHost = (Location) arguments.get(HOME_CONTAINER);
        tradeHost = (Location) arguments.get(TRADE_CONTAINER);
        
        if(arguments.containsKey(HOME_PLATFORM))
              setHomePlatform((PlatformID) arguments.get(HOME_PLATFORM));
        if(arguments.containsKey(TRADE_PLATFORM))
            setTradePlatform((PlatformID) arguments.get(TRADE_PLATFORM));
        
        //getContentManager().registerOntology(MobilityOntology.getInstance());
    }
    
    protected abstract void loadAfterMove();
    
    @Override
    protected void afterMove() {
        loadAfterMove();
        if(afterMoveAction != null){
            addBehaviour(afterMoveAction);
            afterMoveAction = null;
        }
    }
    
    
    
    
     public Location getHomeHost() {
        return homeHost;
    }

    public void setHomeHost(Location homeHost) {
        this.homeHost = homeHost;
    }

    public Location getTradeHost() {
        return tradeHost;
    }

    public void setTradeHost(Location tradeHost) {
        this.tradeHost = tradeHost;
    }

    public PlatformID getHomePlatform() {
        return homePlatform;
    }

    public void setHomePlatform(PlatformID homePlatform) {
        this.homePlatform = homePlatform;
    }

    public PlatformID getTradePlatform() {
        return tradePlatform;
    }

    public void setTradePlatform(PlatformID tradePlatform) {
        this.tradePlatform = tradePlatform;
    }
    
     public Behaviour getAfterMoveAction() {
        return afterMoveAction;
    }

    public void setAfterMoveAction(Behaviour afterMoveAction) {
        this.afterMoveAction = afterMoveAction;
    }
}
