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
package fr.paris.lutece.plugins.workflow.modules.formsextend.web;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import fr.paris.lutece.plugins.extend.service.extender.IResourceExtenderService;
import fr.paris.lutece.plugins.workflow.modules.formsextend.business.MassNotificationTaskConfig;
import fr.paris.lutece.plugins.workflow.modules.formsextend.service.IMassNotificationService;
import fr.paris.lutece.plugins.workflow.modules.formsextend.service.WorkflowFormsextendPlugin;
import fr.paris.lutece.plugins.workflow.web.task.NoFormTaskComponent;
import fr.paris.lutece.plugins.workflowcore.business.config.ITaskConfig;
import fr.paris.lutece.plugins.workflowcore.business.task.ITaskType;
import fr.paris.lutece.plugins.workflowcore.service.config.ITaskConfigService;
import fr.paris.lutece.plugins.workflowcore.service.task.ITask;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.web.constants.Messages;
import fr.paris.lutece.util.html.HtmlTemplate;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 
 * EmailMassNotificationTaskComponent
 *
 */
@ApplicationScoped
@Named( "workflow-formsextend.massNotificationTaskComponent" )
public class MassNotificationTaskComponent extends NoFormTaskComponent
{

    // TEMPLATES
    private static final String TEMPLATE_CONFIG = "admin/plugins/workflow/modules/formsextend/mass_notification_config_task_form.html";

    // MARKS
    private static final String MARK_CONFIG = "config";
    private static final String MARK_EXTENDER_TYPES = "extenderTypes";
    private static final String MARK_EMAIL_MARKERS = "email_markers";

    @Inject
    @Named( "extend.resourceExtenderService" )
    private IResourceExtenderService _resourceExtenderService;

    @Inject
    private IMassNotificationService _massNotificationService;

    /**
     * Constructor for the CDI proxy
     */
    MassNotificationTaskComponent( )
    {
    }

    /**
     * Constructor
     * 
     * @param taskType
     *            the mass notification task type
     * @param taskConfigService
     *            the configuration service
     */
    @Inject
    public MassNotificationTaskComponent( @Named( WorkflowFormsextendPlugin.BEAN_TASK_TYPE ) ITaskType taskType,
            @Named( WorkflowFormsextendPlugin.BEAN_CONFIG ) ITaskConfigService taskConfigService )
    {
        setTaskType( taskType );
        setTaskConfigService( taskConfigService );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplayConfigForm( HttpServletRequest request, Locale locale, ITask task )
    {
        Map<String, Object> model = new HashMap<>( );

        model.put( MARK_CONFIG, getTaskConfigService( ).findByPrimaryKey( task.getId( ) ) );
        model.put( MARK_EXTENDER_TYPES, _resourceExtenderService.getExtenderTypes( locale ) );
        model.put( MARK_EMAIL_MARKERS, _massNotificationService.getAvailableMarkers( task, locale ) );

        HtmlTemplate html = AppTemplateService.getTemplate( TEMPLATE_CONFIG, locale, model );

        return html.getHtml( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String validateConfig( ITaskConfig config, HttpServletRequest request )
    {
        String strError = super.validateConfig( config, request );

        if ( StringUtils.isNotBlank( strError ) )
        {
            return strError;
        }

        MassNotificationTaskConfig massNotificationConfig = (MassNotificationTaskConfig) config;

        if ( massNotificationConfig.isEmailConfig( )
                && isAnyBlank( massNotificationConfig.getSubjectForEmail( ), massNotificationConfig.getSenderNameForEmail( ),
                        massNotificationConfig.getSenderEmailForEmail( ), massNotificationConfig.getMessageForEmail( ) )
                || massNotificationConfig.isEmailConfig( ) && ArrayUtils.isEmpty( massNotificationConfig.getListExtenderTypesForEmail( ) )
                || massNotificationConfig.isDashboardConfig( )
                        && isAnyBlank( massNotificationConfig.getSubjectForDashboard( ), massNotificationConfig.getSenderForDashboard( ),
                                massNotificationConfig.getMessageForDashboard( ) )
                || massNotificationConfig.isDashboardConfig( ) && ArrayUtils.isEmpty( massNotificationConfig.getListExtenderTypesForDashboard( ) ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        return StringUtils.EMPTY;
    }

    /**
     * Tells whether one of the values is blank once its HTML tags are removed
     *
     * @param values
     *            the values
     * @return true if one value is blank
     */
    private static boolean isAnyBlank( String... values )
    {
        for ( String strValue : values )
        {
            if ( StringUtils.isBlank( strValue ) || StringUtils.isBlank( strValue.replaceAll( "<[^>]*>|&nbsp;", "" ) ) )
            {
                return true;
            }
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplayTaskInformation( int nIdHistory, HttpServletRequest request, Locale locale, ITask task )
    {
        return null;
    }
}
