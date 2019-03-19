package implementationJADE;

import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.behaviours.TickerBehaviour;

import java.net.Socket;

/**
 * Esta clase implementa la reconección de un agente a una plataforma que se detuvo y se volvió a
 * iniciar luego.
 * 
 * @author Martica (DTS), Marye (DTS) y Alternán
 */
public abstract class ReconnectToPlatformBehaviour extends TickerBehaviour
{
	private static final long serialVersionUID = 1L;
	private boolean connected = true;
	private String hostName;
	private String hostPort;
	private String agentContainer;

	private TickerBehaviour tickerBehaviour;

	/**
	 * Este es el constructor de la clase.
	 * 
	 * @param a
	 *            Agente que se volverá a subscribir a la plataforma.
	 * @param period
	 *            Tiempo en milisegundos para esperar por la plataforma.
	 * @param _hostName
	 *            Nombre de la PC donde está la plataforma.
	 * @param _hostPort
	 *            Puerto de la plataforma.
	 * @param _agentContainer
	 *            Nombre del contenedor donde se colocará el parámetro a.
	 */
	public ReconnectToPlatformBehaviour(Agent a, long period, String _hostName, String _hostPort, String _agentContainer)
	{
		super(a, period);

		hostName = _hostName;
		hostPort = _hostPort;
		agentContainer = _agentContainer;

		tickerBehaviour = null;

		setBehaviourName("Behaviour_ReconnectToPlatform");
	}

	@Override
	protected void onTick()
	{
		try
		{
			System.out.println("Probando conexión con la Plataforma JADE");
			new Socket(hostName, Integer.parseInt(hostPort));
			System.out.println("Conexión con la Plataforma JADE: OK");
		}
		catch (Exception e)
		{
			System.err.println("Conexión con la Plataforma JADE: ERROR");
			if (tickerBehaviour == null)
			{
				tickerBehaviour = new TickerBehaviour(myAgent, 60000)
				{
					private static final long serialVersionUID = 1L;

					@Override
					protected void onTick()
					{
						try
						{
							System.out.println("Tratando reconectarse con la Plataforma JADE");
							new Socket(hostName, Integer.parseInt(hostPort));

							if (!connected)
							{
								Profile profile = new ProfileImpl();
								profile.setParameter(Profile.CONTAINER_NAME, agentContainer);
								profile.setParameter(Profile.MAIN_HOST, hostName);

								jade.core.Runtime rt = jade.core.Runtime.instance();
								jade.wrapper.AgentContainer agents_Container = rt.createAgentContainer(profile);
								agents_Container.acceptNewAgent(myAgent.getName(), myAgent);
								connected = true;

								doAction();
								this.stop();
								myAgent.removeBehaviour(tickerBehaviour);
								tickerBehaviour = null;
							}
						}
						catch (Exception e)
						{
							connected = false;
							System.err.println("Error: " + e.getMessage());
							System.err.println("Probando reconectarse a la Plataforma JADE en 1 minuto");
						}
					}
				};
				myAgent.addBehaviour(tickerBehaviour);
			}
		}
		block(500);
	}

	public abstract void doAction();
}