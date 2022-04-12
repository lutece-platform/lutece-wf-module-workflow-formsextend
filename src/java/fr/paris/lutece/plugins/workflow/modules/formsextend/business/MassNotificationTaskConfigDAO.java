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

import org.apache.commons.lang3.StringUtils;

import fr.paris.lutece.plugins.workflow.modules.formsextend.service.WorkflowFormsextendPlugin;
import fr.paris.lutece.plugins.workflowcore.business.config.ITaskConfigDAO;
import fr.paris.lutece.util.sql.DAOUtil;

/**
 * 
 * MassNotificationTaskConfigDAO
 *
 */
public class MassNotificationTaskConfigDAO implements ITaskConfigDAO<MassNotificationTaskConfig>
{

    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id_task, email_message, email_subject, email_sender_name, email_sender_email, email_extender_types, is_email_config, dashboard_message, dashboard_subject, dashboard_sender, dashboard_extender_types, is_dashboard_config FROM task_forms_extend_mass_notification_cf WHERE id_task = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO task_forms_extend_mass_notification_cf ( id_task, email_message, email_subject, email_sender_name, email_sender_email, email_extender_types, is_email_config, dashboard_message, dashboard_subject, dashboard_sender, dashboard_extender_types, is_dashboard_config ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM task_forms_extend_mass_notification_cf WHERE id_task = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE task_forms_extend_mass_notification_cf SET id_task = ?, email_message = ?, email_subject = ?, email_sender_name = ?, email_sender_email = ?, email_extender_types = ?, is_email_config = ?, dashboard_message = ?, dashboard_subject = ?, dashboard_sender = ?, dashboard_extender_types = ?, is_dashboard_config = ? WHERE id_task = ?";

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert( MassNotificationTaskConfig config )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, WorkflowFormsextendPlugin.getPlugin( ) ) )
        {
            int nIndex = 0;
            daoUtil.setInt( ++nIndex, config.getIdTask( ) );
            // Email config
            daoUtil.setString( ++nIndex, config.getMessageForEmail( ) );
            daoUtil.setString( ++nIndex, config.getSubjectForEmail( ) );
            daoUtil.setString( ++nIndex, config.getSenderNameForEmail( ) );
            daoUtil.setString( ++nIndex, config.getSenderEmailForEmail( ) );
            daoUtil.setString( ++nIndex, StringUtils.join( config.getListExtenderTypesForEmail( ), "," ) );
            daoUtil.setBoolean( ++nIndex, config.isEmailConfig( ) );

            // Dashboard config
            daoUtil.setString( ++nIndex, config.getMessageForDashboard( ) );
            daoUtil.setString( ++nIndex, config.getSubjectForDashboard( ) );
            daoUtil.setString( ++nIndex, config.getSenderForDashboard( ) );
            daoUtil.setString( ++nIndex, StringUtils.join( config.getListExtenderTypesForDashboard( ), "," ) );
            daoUtil.setBoolean( ++nIndex, config.isDashboardConfig( ) );

            daoUtil.executeUpdate( );
            daoUtil.free( );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void store( MassNotificationTaskConfig config )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, WorkflowFormsextendPlugin.getPlugin( ) ) )
        {
            int nIndex = 0;
            daoUtil.setInt( ++nIndex, config.getIdTask( ) );
            // Email config
            daoUtil.setString( ++nIndex, config.getMessageForEmail( ) );
            daoUtil.setString( ++nIndex, config.getSubjectForEmail( ) );
            daoUtil.setString( ++nIndex, config.getSenderNameForEmail( ) );
            daoUtil.setString( ++nIndex, config.getSenderEmailForEmail( ) );
            daoUtil.setString( ++nIndex, StringUtils.join( config.getListExtenderTypesForEmail( ), "," ) );
            daoUtil.setBoolean( ++nIndex, config.isEmailConfig( ) );

            // Dashboard config
            daoUtil.setString( ++nIndex, config.getMessageForDashboard( ) );
            daoUtil.setString( ++nIndex, config.getSubjectForDashboard( ) );
            daoUtil.setString( ++nIndex, config.getSenderForDashboard( ) );
            daoUtil.setString( ++nIndex, StringUtils.join( config.getListExtenderTypesForDashboard( ), "," ) );
            daoUtil.setBoolean( ++nIndex, config.isDashboardConfig( ) );

            daoUtil.setInt( ++nIndex, config.getIdTask( ) );

            daoUtil.executeUpdate( );
            daoUtil.free( );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MassNotificationTaskConfig load( int nIdTask )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, WorkflowFormsextendPlugin.getPlugin( ) ) )
        {
            daoUtil.setInt( 1, nIdTask );
            daoUtil.executeQuery( );

            MassNotificationTaskConfig massNotificationTaskConfig = null;

            if ( daoUtil.next( ) )
            {
                massNotificationTaskConfig = new MassNotificationTaskConfig( );
                int nIndex = 0;

                // Email config
                massNotificationTaskConfig.setIdTask( daoUtil.getInt( ++nIndex ) );
                massNotificationTaskConfig.setMessageForEmail( daoUtil.getString( ++nIndex ) );
                massNotificationTaskConfig.setSubjectForEmail( daoUtil.getString( ++nIndex ) );
                massNotificationTaskConfig.setSenderNameForEmail( daoUtil.getString( ++nIndex ) );
                massNotificationTaskConfig.setSenderEmailForEmail( daoUtil.getString( ++nIndex ) );

                String strListExtenderTypes = daoUtil.getString( ++nIndex );
                if ( StringUtils.isNotEmpty( strListExtenderTypes ) )
                {
                    massNotificationTaskConfig.setListExtenderTypesForEmail( strListExtenderTypes.split( "," ) );
                }
                massNotificationTaskConfig.setEmailConfig( daoUtil.getBoolean( ++nIndex ) );

                // Dashboard config
                massNotificationTaskConfig.setMessageForDashboard( daoUtil.getString( ++nIndex ) );
                massNotificationTaskConfig.setSubjectForDashboard( daoUtil.getString( ++nIndex ) );
                massNotificationTaskConfig.setSenderForDashboard( daoUtil.getString( ++nIndex ) );

                String strListExtenderTypesForDashboard = daoUtil.getString( ++nIndex );
                if ( StringUtils.isNotEmpty( strListExtenderTypesForDashboard ) )
                {
                    massNotificationTaskConfig.setListExtenderTypesForDashboard( strListExtenderTypesForDashboard.split( "," ) );
                }
                massNotificationTaskConfig.setDashboardConfig( daoUtil.getBoolean( ++nIndex ) );
            }

            daoUtil.free( );

            return massNotificationTaskConfig;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete( int nIdTask )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, WorkflowFormsextendPlugin.getPlugin( ) ) )
        {
            daoUtil.setInt( 1, nIdTask );
            daoUtil.executeUpdate( );
            daoUtil.free( );
        }
    }

}
