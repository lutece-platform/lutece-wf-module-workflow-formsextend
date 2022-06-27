package fr.paris.lutece.plugins.workflow.modules.formsextend.service;

import java.util.List;

import fr.paris.lutece.plugins.workflow.modules.formsextend.business.MassNotificationHistory;

public interface IMassNotificationHistoryService {

	/**
	 * {@inheritDoc}
	 */
	void create(MassNotificationHistory massNotificationHistory);

	/**
	 * {@inheritDoc}
	 */
	void remove(int nIdHistory);

	/**
	 * {@inheritDoc}
	 */
	void removeByIdTask(int nIdTask);

	/**
	 * {@inheritDoc}
	 */
	void update(MassNotificationHistory massNotificationHistory);

	/**
	 * {@inheritDoc}
	 */
	MassNotificationHistory find(int nIdTask, int nIdResourceHistory);

	/**
	 * {@inheritDoc}
	 */
	List<MassNotificationHistory> findAll();

}
