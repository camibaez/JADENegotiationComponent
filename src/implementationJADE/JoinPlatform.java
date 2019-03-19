package implementationJADE;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.behaviours.Behaviour;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

import java.util.HashMap;

/**
 * Esta clase contiene la creación de contenedores y agentes en una plataforma que anteriormente ha
 * sido inicializada.
 * 
 * @author Ing. Inf. Alternán Carrasco Bustamante
 * @enterprise Complejo de Investigaciones Tecnológicas Integradas - CITI
 * @email acarrasco@udio.cujae.edu.cu
 * 
 */
public class JoinPlatform
{
	private static String hostName;
	private static String port;
	private static jade.core.Runtime rt;
	private static HashMap<String, AgentContainer> agentContainers;
	private static HashMap<String, StandardAgent> listAgent;

	/**
	 * Inicializa los datos necesarios para que se pueda trabajar en una plataforma existente.
	 * 
	 * @param _rt
	 */
	public static void init(String _hostName, String _port)
	{
		hostName = _hostName;
		if (_port == null || _port.isEmpty())
		{
			port = "1099";
		}
		else
		{
			port = _port;
		}
		rt = jade.core.Runtime.instance();
		agentContainers = new HashMap<String, AgentContainer>();
		listAgent = new HashMap<String, StandardAgent>();
	}

	/**
	 * Crea un contenedor en la plataforma existente.
	 * 
	 * @param containerName
	 *            Nombre del contenedor que se desa crear.
	 */
	public static void createAgentContainer(String containerName)
	{
		// Create a default profile
		Profile profile = new ProfileImpl();
		profile.setParameter(Profile.CONTAINER_NAME, containerName);
		profile.setParameter(Profile.MAIN_HOST, hostName);
		profile.setParameter(Profile.MAIN_PORT, port);

		AgentContainer agentContainer = rt.createAgentContainer(profile);

		agentContainers.put(containerName, agentContainer);
	}

	public static void createAgentContainer(String containerName, String localPort, String urlForMTP)
	{
		// Create a default profile
		Profile profile = new ProfileImpl();
		profile.setParameter(Profile.CONTAINER_NAME, containerName);
		profile.setParameter(Profile.MAIN_HOST, hostName);
		profile.setParameter(Profile.MAIN_PORT, port);
		profile.setParameter(Profile.LOCAL_PORT, localPort);
		// profile.setParameter("jade_mtp_http_port", "5010");
		// profile.setParameter("jade_mtp_http_outPort", "5012");

		// profile.setParameter(Profile.MTPS, "jade.mtp.http.MessageTransportProtocol("+ urlForMTP
		// +")");

		jade.wrapper.AgentContainer agentContainer = rt.createAgentContainer(profile);

		agentContainers.put(containerName, agentContainer);
	}

	/**
	 * Crea un agente dentro de un contenedor previamente creado.
	 * 
	 * @param containerName
	 *            Contenedor donde se ejecutará el agente.
	 * @param agentName
	 *            Nombre del agente que se quiere ejecutar.
	 * @param agentClass
	 *            Clase que implementa el agente que se quiere ejecutar. Ej:
	 *            packageSystem.packageAgents.classMyAgent
	 * @param agentArgs
	 *            Arreglo de tipo Object que se le pasará al agente como argumentos para su
	 *            ejecución.
	 */
	public static void addAgent_ToContainer(final String containerName, final String agentName, final String agentClass, final Object[] agentArgs)
	{
		if (agentName.equals("TemporalProxyAgent"))
		{
			// Para este nombre de agente hay que realizar otro tipo de comportamiento.
			// El problema radica en la emisión de varios comandos seguidos a través
			// de este agente, dando error cuando se quiere crear un agente con el mismo nombre.
			// Lo que se quiere es crear un agente con un nombre diferente cada vez
			// de forma que no de error aún cuando el agente se demore en morir
			// y llegue otro a realizar el mismo trabajo.
			new Thread()
			{
				@Override
				public void run()
				{
					try
					{
						AgentContainer agentContainer = agentContainers.get(containerName);
						String nickname = agentName + System.currentTimeMillis();
						AgentController agentController = agentContainer.createNewAgent(nickname, agentClass, agentArgs);
						System.out.println("Starting up " + nickname + "...");
						agentController.start();
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}.start();
		}
		else
		{
			new Thread()
			{
				@Override
				public void run()
				{
					try
					{
						AgentContainer agentContainer = agentContainers.get(containerName);
						AgentController agentController = agentContainer.createNewAgent(agentName, agentClass, agentArgs);
						System.out.println("Starting up " + agentName + "...");
						agentController.start();
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}.start();
		}
	}

	public static StandardAgent getAgent(String nombre)
	{
		return listAgent.get(nombre);
	}

	public static void addBehaviourToAgent(String agentName, Behaviour behaviour)
	{
		listAgent.get(agentName).addBehaviour(behaviour);
	}

	public static void removeBehaviourInAgent(String agentName, Behaviour behaviour)
	{
		listAgent.get(agentName).removeBehaviour(behaviour);
	}

	public static void putStandardAgent(StandardAgent standardAgent)
	{
		if (listAgent != null)
		{
			listAgent.put(standardAgent.getLocalName(), standardAgent);
		}
	}
}
