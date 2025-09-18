package fr.paris.lutece.plugins.workflow.modules.formsextend.business;

import java.util.List;

public interface IMassNotificationHistoryDAO {

	/**
	 * {@inheritDoc}
	 */
	void insert(MassNotificationHistory massConsultationHistory);

	/**
	 * {@inheritDoc}
	 */
	void store(MassNotificationHistory massConsultationHistory);

	/**
	 * {@inheritDoc}
	 */
	void delete(int nIdHistory);

	/**
	 * {@inheritDoc}
	 */
	void delete_by_idTask(int nIdTask);

	/**
	 * {@inheritDoc}
	 */
	MassNotificationHistory load(int nIdResource, int nIdHistory);

	/**
	 * {@inheritDoc}
	 */
	List<MassNotificationHistory> selectAll();

}
