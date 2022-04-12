/*
 * Copyright (c) 2002-2022, City of Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.workflow.modules.formsextend.business;

import fr.paris.lutece.plugins.workflowcore.business.config.TaskConfig;

/**
 * 
 * MassNotificationTaskConfig
 *
 */
public class MassNotificationTaskConfig extends TaskConfig
{
    /* Email config */
    private String _strMessageForEmail;
    private String _strSubjectForEmail;
    private String _strSenderEmailForEmail;
    private String _strSenderNameForEmail;
    private String [ ] _listExtenderTypesForEmail;
    private boolean _bEmailConfig;

    /* Dashboard config */
    private String _strMessageForDashboard;
    private String _strSubjectForDashboard;
    private String _strSenderForDashboard;
    private String [ ] _listExtenderTypesForDashboard;
    private boolean _bDashboardConfig;

    /**
     * @return the _strMessageForEmail
     */
    public String getMessageForEmail( )
    {
        return _strMessageForEmail;
    }

    /**
     * @param strMessageForEmail
     *            the _strMessageForEmail to set
     */
    public void setMessageForEmail( String strMessageForEmail )
    {
        this._strMessageForEmail = strMessageForEmail;
    }

    /**
     * @return the _strSubjectForEmail
     */
    public String getSubjectForEmail( )
    {
        return _strSubjectForEmail;
    }

    /**
     * @param strSubjectForEmail
     *            the _strSubjectForEmail to set
     */
    public void setSubjectForEmail( String strSubjectForEmail )
    {
        this._strSubjectForEmail = strSubjectForEmail;
    }

    /**
     * @return the _strSenderEmailForEmail
     */
    public String getSenderEmailForEmail( )
    {
        return _strSenderEmailForEmail;
    }

    /**
     * @param strSenderEmailForEmail
     *            the _strSenderEmailForEmail to set
     */
    public void setSenderEmailForEmail( String strSenderEmailForEmail )
    {
        this._strSenderEmailForEmail = strSenderEmailForEmail;
    }

    /**
     * @return the _strSenderNameForEmail
     */
    public String getSenderNameForEmail( )
    {
        return _strSenderNameForEmail;
    }

    /**
     * @param strSenderNameForEmail
     *            the _strSenderNameForEmail to set
     */
    public void setSenderNameForEmail( String strSenderNameForEmail )
    {
        this._strSenderNameForEmail = strSenderNameForEmail;
    }

    /**
     * @return the _listExtenderTypesForEmail
     */
    public String [ ] getListExtenderTypesForEmail( )
    {
        return _listExtenderTypesForEmail;
    }

    /**
     * @param listExtenderTypesForEmail
     *            the _listExtenderTypesForEmail to set
     */
    public void setListExtenderTypesForEmail( String [ ] listExtenderTypesForEmail )
    {
        this._listExtenderTypesForEmail = listExtenderTypesForEmail;
    }

    /**
     * @return the _bEmailConfig
     */
    public boolean isEmailConfig( )
    {
        return _bEmailConfig;
    }

    /**
     * @param bEmailConfig
     *            the _bEmailConfig to set
     */
    public void setEmailConfig( boolean bEmailConfig )
    {
        this._bEmailConfig = bEmailConfig;
    }

    /**
     * @return the _strMessageForDashboard
     */
    public String getMessageForDashboard( )
    {
        return _strMessageForDashboard;
    }

    /**
     * @param strMessageForDashboard
     *            the _strMessageForDashboard to set
     */
    public void setMessageForDashboard( String strMessageForDashboard )
    {
        this._strMessageForDashboard = strMessageForDashboard;
    }

    /**
     * @return the _strSubjectForDashboard
     */
    public String getSubjectForDashboard( )
    {
        return _strSubjectForDashboard;
    }

    /**
     * @param strSubjectForDashboard
     *            the _strSubjectForDashboard to set
     */
    public void setSubjectForDashboard( String strSubjectForDashboard )
    {
        this._strSubjectForDashboard = strSubjectForDashboard;
    }

    /**
     * @return the _strSenderForDashboard
     */
    public String getSenderForDashboard( )
    {
        return _strSenderForDashboard;
    }

    /**
     * @param strSenderForDashboard
     *            the _strSenderForDashboard to set
     */
    public void setSenderForDashboard( String strSenderForDashboard )
    {
        this._strSenderForDashboard = strSenderForDashboard;
    }

    /**
     * @return the _listExtenderTypesForDashboard
     */
    public String [ ] getListExtenderTypesForDashboard( )
    {
        return _listExtenderTypesForDashboard;
    }

    /**
     * @param listExtenderTypesForDashboard
     *            the _listExtenderTypesForDashboard to set
     */
    public void setListExtenderTypesForDashboard( String [ ] listExtenderTypesForDashboard )
    {
        this._listExtenderTypesForDashboard = listExtenderTypesForDashboard;
    }

    /**
     * @return the _bDashboardConfig
     */
    public boolean isDashboardConfig( )
    {
        return _bDashboardConfig;
    }

    /**
     * @param bDashboardConfig
     *            the _bDashboardConfig to set
     */
    public void setDashboardConfig( boolean bDashboardConfig )
    {
        this._bDashboardConfig = bDashboardConfig;
    }

}
