package implementationJADE;

import java.util.LinkedList;
import java.util.List;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

/**
 * Esta clase contiene todo el trabajo que se realiza en coordinación con el DF.
 * 
 * @author Ing. Inf. Alternán Carrasco Bustamante
 * @enterprise Complejo de Investigaciones Tecnológicas Integradas - CITI
 * @email acarrasco@udio.cujae.edu.cu
 * 
 */
public class WorkDF
{
	/**
	 * Registra en el DF a un agente con un tipo de servicio.
	 * 
	 * @param agent
	 *            Agente que se desea registrar.
	 * @param type
	 *            Tipo de servicio que ofrece.
	 * @return True si se registró correctamente.
	 */
	public static boolean registerByType(Agent agent, String type)
	{
		boolean register = false;

		DFAgentDescription[] df_ad_old = get_AgentDescription_ByName(agent, agent.getName());
		if (df_ad_old.length == 0)
		{
			DFAgentDescription df_ad_new = new DFAgentDescription();
			ServiceDescription sd = new ServiceDescription();
			sd.setType(type);
			sd.setName(agent.getName());
			df_ad_new.addServices(sd);
			df_ad_new.setName(agent.getAID());
			try
			{
				DFService.register(agent, df_ad_new);
				register = true;
			}
			catch (FIPAException e)
			{
				agent.doDelete();
				register = false;
			}
		}
		else
		{
			DFAgentDescription df_ad_new = df_ad_old[0];
			ServiceDescription sd = new ServiceDescription();
			sd.setType(type);
			sd.setName(agent.getName());
			df_ad_new.addServices(sd);
			df_ad_new.setName(agent.getAID());
			try
			{
				DFService.deregister(agent);
				DFService.register(agent, df_ad_new);
				register = true;
			}
			catch (FIPAException e)
			{
				agent.doDelete();
				register = false;
			}
		}
		return register;
	}

	/**
	 * Registra en el DF a un agente con varios tipos de servicio.
	 * 
	 * @param agent
	 *            Agente que se desea registrar.
	 * @param types
	 *            Tipos de servicio que ofrece.
	 * @return True si se registró correctamente.
	 */
	public static boolean registerByTypes(Agent agent, String[] types)
	{
		boolean register = true;
		for (int i = 0; i < types.length; i++)
		{
			if (!registerByType(agent, types[0]))
				register = false;
		}
		return register;
	}

	/**
	 * Toma los AIDs de los agentes según el tipo de servicio que proveen.
	 * 
	 * @param agent
	 *            Agente que realizará la búsqueda en el DF.
	 * @param type
	 *            Tipo de servicio que se quiere buscar.
	 * @return Devuelve un arreglo con los AID de los agentes que cumplen con el tipo pasado por
	 *         parámetro.
	 */
	public static AID[] getAIDsByType(Agent agent, String type)
	{
		DFAgentDescription[] result = WorkDF.get_AgentDescription_ByType(agent, type);

		AID[] agentAID = new AID[0];
		int length = result.length;
		if (result != null && length > 0)
		{
			agentAID = new AID[length];
			for (int i = 0; i < length; i++)
			{
				agentAID[i] = result[i].getName();
			}
		}

		return agentAID;
	}

	/**
	 * Toma los AIDs de los agentes según el tipo de servicio que proveen.
	 * 
	 * @param agent
	 *            Agente que realizará la búsqueda en el DF.
	 * @param types
	 *            Tipos de servicio que se quiere buscar.
	 * @return Devuelve un arreglo con los AID de los agentes que cumplen con el tipo pasado por
	 *         parámetro.
	 */
	public static AID[] getAIDsByTypes(Agent agent, String[] types)
	{
		DFAgentDescription[] result = WorkDF.get_AgentDescription_ByTypes(agent, types);

		AID[] agentAID = new AID[0];
		int length = result.length;
		if (result != null && length > 0)
		{
			agentAID = new AID[length];
			for (int i = 0; i < length; i++)
			{
				agentAID[i] = result[i].getName();
			}
		}

		return agentAID;
	}

	/**
	 * Toma los AIDs de los agentes según el tipo de servicio que proveen, pero quita de esa lista
	 * al agente que realiza la petición de búsqueda (parámetro agent). Es factible usarlo cuando se
	 * desea tener los AIDs de todos los agentes que son del mismo tipo que el agente que realiza la
	 * petición de búsqueda.
	 * 
	 * @param agent
	 *            Agente que realiza la petición de búsqueda.
	 * @param type
	 *            Tipo de agente que se quiere buscar.
	 * @return Devuelve un arreglo de AIDs de los agentes del tipo pasado por parámetro, excepto el
	 *         agente pasado por parámetro.
	 */
	public static AID[] get_AIDsAgent_ExceptOne_ByType(Agent agent, String type)
	{
		List<AID> listAIDs = new LinkedList<AID>();
		AID[] allAIDs = WorkDF.getAIDsByType(agent, type);
		int length = allAIDs.length;
		if (allAIDs != null && length > 0)
		{
			for (AID aid : allAIDs)
			{
				if (aid.getName().compareTo(agent.getAID().getName()) != 0)
					listAIDs.add(aid);
			}
		}

		AID[] arrAIDs = new AID[listAIDs.size()];
		for (int i = 0; i < listAIDs.size(); i++)
		{
			arrAIDs[i] = listAIDs.get(i);
		}
		return arrAIDs;
	}

	/**
	 * Toma los AIDs de los agentes según el tipo de servicio que proveen, pero quita de esa lista
	 * al agente que tiene el AID pasado por parámetro.
	 * 
	 * @param agent
	 *            Agente que realiza la petición de búsqueda.
	 * @param except
	 *            AID que no se quiere que aparezca en la lista devuelta.
	 * @param type
	 *            Tipo de agente que se quiere buscar.
	 * @return Devuelve un arreglo de AIDs de los agentes del tipo pasado por parámetro, excepto el
	 *         AID pasado por parámetro.
	 */
	public static AID[] get_AIDsAgent_ExceptOne_ByType(Agent agent, AID except, String type)
	{
		List<AID> listAIDs = new LinkedList<AID>();
		AID[] allAIDs = WorkDF.getAIDsByType(agent, type);
		int length = allAIDs.length;
		if (allAIDs != null && length > 0)
		{
			for (AID aid : allAIDs)
			{
				if (!aid.getName().equals((except.getName())))
					listAIDs.add(aid);
			}
		}

		AID[] arrAIDs = new AID[listAIDs.size()];
		for (int i = 0; i < listAIDs.size(); i++)
		{
			arrAIDs[i] = listAIDs.get(i);
		}
		return arrAIDs;
	}

	/**
	 * Toma el AID del primer agente que cumpla con el tipo de servicio dado.
	 * 
	 * @param agent
	 *            Agente que realizará la búsqueda en el DF.
	 * @param type
	 *            Tipo de servicio que se quiere buscar.
	 * @return Devuelve el AID del primer agente que cumpla con el tipo de servicio dado.
	 */
	public static AID getAIDAgentByType(Agent agent, String type)
	{
		DFAgentDescription[] result = WorkDF.get_AgentDescription_ByType(agent, type);

		AID aidAgent = null;
		int length = result.length;
		if (result != null && length > 0)
		{
			aidAgent = result[0].getName();
		}

		return aidAgent;
	}

	/**
	 * Toma el DFAgentDescription de los agentes según el tipo de servicio que proveen.
	 * 
	 * @param agent
	 *            Agente que realizará la búsqueda en el DF.
	 * @param type
	 *            Tipo de servicio que se quiere buscar.
	 * @return Devuelve un arreglo con los DFAgentDescription de los agentes que cumplen con el tipo
	 *         pasado por parámetro.
	 */
	public static DFAgentDescription[] get_AgentDescription_ByType(Agent agent, String type)
	{
		DFAgentDescription df_ad = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType(type);
		df_ad.addServices(sd);

		DFAgentDescription[] result = new DFAgentDescription[0];
		try
		{
			result = DFService.search(agent, df_ad);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * Toma el DFAgentDescription de los agentes según el tipo de servicio que proveen. En este caso
	 * se quieren buscar los agentes que cumplan con todos los tipos de servicios del parámetro
	 * types.
	 * 
	 * @param agent
	 *            Agente que realizará la búsqueda en el DF.
	 * @param types
	 *            Tipos de servicio que se quiere buscar.
	 * @return Devuelve un arreglo con los DFAgentDescription de los agentes que cumplen con el tipo
	 *         pasado por parámetro.
	 */
	public static DFAgentDescription[] get_AgentDescription_ByTypes(Agent agent, String[] types)
	{
		DFAgentDescription df_ad = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		for (String type : types)
		{
			sd.setType(type);
			df_ad.addServices(sd);
		}

		DFAgentDescription[] result = new DFAgentDescription[0];
		try
		{
			result = DFService.search(agent, df_ad);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * Toma el DFAgentDescription de los agentes según el nombre del agente.
	 * 
	 * @param agent
	 *            Agente que realizará la búsqueda en el DF.
	 * @param name
	 *            Nombre del agente que se quiere buscar.
	 * @return Devuelve un arreglo con los DFAgentDescription de los agentes que cumplen con el
	 *         nombre pasado por parámetro.
	 */
	public static DFAgentDescription[] get_AgentDescription_ByName(Agent agent, String name)
	{
		DFAgentDescription df_ad = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setName(name);
		df_ad.addServices(sd);

		DFAgentDescription[] result = new DFAgentDescription[0];
		try
		{
			result = DFService.search(agent, df_ad);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * Toma los nombres de los agentes según el tipo de servicio que proveen.
	 * 
	 * @param agent
	 *            Agente que realizará la búsqueda en el DF.
	 * @param type
	 *            Tipo de servicio que se quiere buscar.
	 * @return Devuelve un arreglo con los nombres de los agentes que cumplen con el tipo pasado por
	 *         parámetro.
	 */
	public static String[] get_NamesAgent_ByType(Agent agent, String type)
	{
		AID[] agentAID = WorkDF.getAIDsByType(agent, type);

		String[] namesAgents = new String[0];
		int length = agentAID.length;
		if (namesAgents != null && length > 0)
		{
			namesAgents = new String[length];
			for (int i = 0; i < length; i++)
				namesAgents[i] = agentAID[i].getLocalName();
		}

		return namesAgents;
	}
}
