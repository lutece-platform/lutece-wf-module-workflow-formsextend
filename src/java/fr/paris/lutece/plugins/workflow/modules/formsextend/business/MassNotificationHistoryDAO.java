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

import java.util.ArrayList;
import java.util.List;

import fr.paris.lutece.plugins.workflow.modules.formsextend.service.WorkflowFormsextendPlugin;
import fr.paris.lutece.util.sql.DAOUtil;

/**
 * 
 * NotifyConsultationHistoryDAO
 *
 */
public class MassNotificationHistoryDAO implements IMassNotificationHistoryDAO
{

    // Constants
    private static final String SQL_QUERY_SELECT_BY_ID_HISTORY_AND_ID_TASK = "SELECT id_history, id_task, id_resource, id_workflow, email_notification, dashboard_notification FROM task_forms_extend_mass_notification_history WHERE id_history = ? AND id_task = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_history, id_task, id_resource, id_workflow, email_notification, dashboard_notification FROM task_forms_extend_mass_notification_history";
    private static final String SQL_QUERY_INSERT = "INSERT INTO task_forms_extend_mass_notification_history ( id_history, id_task, id_resource, id_workflow, email_notification, dashboard_notification ) VALUES ( ?, ?, ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM task_forms_extend_mass_notification_history WHERE id_history = ? ";
    private static final String SQL_QUERY_DELETE_BY_ID_TASK = "DELETE FROM task_forms_extend_mass_notification_history WHERE id_task = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE task_forms_extend_mass_notification_history SET id_history = ?, id_task = ?, id_resource = ?, id_workflow = ?, email_notification = ?, dashboard_notification = ? WHERE id_history = ? ";

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert( MassNotificationHistory massConsultationHistory )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, WorkflowFormsextendPlugin.getPlugin( ) ) )
        {
            int nIndex = 0;
            daoUtil.setInt( ++nIndex, massConsultationHistory.getIdHistory( ) );
            daoUtil.setInt( ++nIndex, massConsultationHistory.getIdTask( ) );
            daoUtil.setInt( ++nIndex, massConsultationHistory.getIdResource( ) );
            daoUtil.setInt( ++nIndex, massConsultationHistory.getIdWorkflow( ) );
            daoUtil.setBoolean( ++nIndex, massConsultationHistory.isEmailNotification( ) );
            daoUtil.setBoolean( ++nIndex, massConsultationHistory.isDashboardNotification( ) );

            daoUtil.executeUpdate( );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void store( MassNotificationHistory massConsultationHistory )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, WorkflowFormsextendPlugin.getPlugin( ) ) )
        {
            int nIndex = 0;
            daoUtil.setInt( ++nIndex, massConsultationHistory.getIdHistory( ) );
            daoUtil.setInt( ++nIndex, massConsultationHistory.getIdTask( ) );
            daoUtil.setInt( ++nIndex, massConsultationHistory.getIdResource( ) );
            daoUtil.setInt( ++nIndex, massConsultationHistory.getIdWorkflow( ) );
            daoUtil.setBoolean( ++nIndex, massConsultationHistory.isEmailNotification( ) );
            daoUtil.setBoolean( ++nIndex, massConsultationHistory.isDashboardNotification( ) );

            daoUtil.setInt( ++nIndex, massConsultationHistory.getIdHistory( ) );

            daoUtil.executeUpdate( );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete( int nIdHistory )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, WorkflowFormsextendPlugin.getPlugin( ) ) )
        {
            daoUtil.setInt( 1, nIdHistory );
            daoUtil.executeUpdate( );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete_by_idTask( int nIdTask )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_BY_ID_TASK, WorkflowFormsextendPlugin.getPlugin( ) ) )
        {
            daoUtil.setInt( 1, nIdTask );
            daoUtil.executeUpdate( );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MassNotificationHistory load( int nIdResource, int nIdHistory )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_ID_HISTORY_AND_ID_TASK ) )
        {
            daoUtil.setInt( 1, nIdHistory );
            daoUtil.setInt( 2, nIdResource );

            daoUtil.executeQuery( );

            MassNotificationHistory massConsultationHistory = null;

            if ( daoUtil.next( ) )
            {
                int nIndex = 0;
                massConsultationHistory = new MassNotificationHistory( );
                massConsultationHistory.setIdHistory( daoUtil.getInt( ++nIndex ) );
                massConsultationHistory.setIdTask( daoUtil.getInt( ++nIndex ) );
                massConsultationHistory.setIdResource( daoUtil.getInt( ++nIndex ) );
                massConsultationHistory.setIdWorkflow( daoUtil.getInt( ++nIndex ) );
                massConsultationHistory.setEmailNotification( daoUtil.getBoolean( ++nIndex ) );
                massConsultationHistory.setDashboardNotification( daoUtil.getBoolean( ++nIndex ) );

            }

            return massConsultationHistory;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MassNotificationHistory> selectAll( )
    {
        List<MassNotificationHistory> listMassConsultationHistory = new ArrayList<>( );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL ) )
        {
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                int nIndex = 0;
                MassNotificationHistory massConsultationHistory = new MassNotificationHistory( );
                massConsultationHistory.setIdHistory( daoUtil.getInt( ++nIndex ) );
                massConsultationHistory.setIdTask( daoUtil.getInt( ++nIndex ) );
                massConsultationHistory.setIdResource( daoUtil.getInt( ++nIndex ) );
                massConsultationHistory.setIdWorkflow( daoUtil.getInt( ++nIndex ) );
                massConsultationHistory.setEmailNotification( daoUtil.getBoolean( ++nIndex ) );
                massConsultationHistory.setDashboardNotification( daoUtil.getBoolean( ++nIndex ) );

                listMassConsultationHistory.add( massConsultationHistory );
            }

            return listMassConsultationHistory;
        }
    }
}
