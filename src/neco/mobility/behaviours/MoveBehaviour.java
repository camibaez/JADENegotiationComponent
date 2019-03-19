package neco.mobility.behaviours;

import jade.core.Agent;
import jade.core.Location;
import jade.core.behaviours.OneShotBehaviour;

/**
 * Behaviour que se encarga de hacer migrar al agente a partir de la locacion
 * asignada en su atributo destination.
 * @author Camilo Báez Aneiros
 */
public class MoveBehaviour extends OneShotBehaviour{
    /**
     * Locacion hacia donde el agente va a migrar
     */
    protected Location destination;

    protected MoveBehaviour(){
        super();
    }

    public MoveBehaviour(Agent agent, Location destination){
        super(agent);
        this.destination = destination;
    }
    
    @Override
    public void action() {
        myAgent.doMove(destination);
    }
}
