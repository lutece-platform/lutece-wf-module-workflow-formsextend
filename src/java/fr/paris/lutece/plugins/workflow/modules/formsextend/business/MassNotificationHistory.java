package fr.paris.lutece.plugins.workflow.modules.formsextend.business;

public class MassNotificationHistory 
{
	private int _nidHistory;
	private int _idTask;
	private int _idResource;
	private int _idWorkflow;
	private boolean _bEmailNotification;
	private boolean _bDashboardNotification;

    /**
	 * @return the _nidHistory
	 */
	public int getIdHistory() {
		return _nidHistory;
	}
	
	/**
	 * @param _nidHistory the _nidHistory to set
	 */
	public void setIdHistory(int _nidHistory) {
		this._nidHistory = _nidHistory;
	}
    
	/**
	 * @return the _idTask
	 */
	public int getIdTask() {
		return _idTask;
	}
	
	/**
	 * @param _idTask the _idTask to set
	 */
	public void setIdTask(int _idTask) {
		this._idTask = _idTask;
	}
    
	/**
	 * @return the _idResource
	 */
	public int getIdResource() {
		return _idResource;
	}
	
	/**
	 * @param _idResource the _idResource to set
	 */
	public void setIdResource(int _idResource) {
		this._idResource = _idResource;
	}

	/**
	 * @return the _idWorkflow
	 */
	public int getIdWorkflow() {
		return _idWorkflow;
	}

	/**
	 * @param _idWorkflow the _idWorkflow to set
	 */
	public void setIdWorkflow(int _idWorkflow) {
		this._idWorkflow = _idWorkflow;
	}

	/**
	 * @return the _bEmailNotification
	 */
	public boolean isEmailNotification() {
		return _bEmailNotification;
	}

	/**
	 * @param _bEmailNotification the _bEmailNotification to set
	 */
	public void setEmailNotification(boolean _bEmailNotification) {
		this._bEmailNotification = _bEmailNotification;
	}

	/**
	 * @return the _bDashboardNotification
	 */
	public boolean isDashboardNotification() {
		return _bDashboardNotification;
	}

	/**
	 * @param _bDashboardNotification the _bDashboardNotification to set
	 */
	public void setDashboardNotification(boolean _bDashboardNotification) {
		this._bDashboardNotification = _bDashboardNotification;
	}
}
