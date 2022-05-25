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

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.extend.service.extender.IResourceExtenderService;
import fr.paris.lutece.plugins.extend.service.extender.ResourceExtenderService;
import fr.paris.lutece.plugins.workflow.modules.formsextend.service.IMassNotificationService;
import fr.paris.lutece.plugins.workflow.modules.formsextend.service.WorkflowFormsextendPlugin;
import fr.paris.lutece.plugins.workflow.web.task.NoFormTaskComponent;
import fr.paris.lutece.plugins.workflowcore.service.config.ITaskConfigService;
import fr.paris.lutece.plugins.workflowcore.service.task.ITask;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.util.html.HtmlTemplate;

/**
 * 
 * EmailMassNotificationTaskComponent
 *
 */
public class MassNotificationTaskComponent extends NoFormTaskComponent
{

    // TEMPLATES
    private static final String TEMPLATE_CONFIG = "admin/plugins/workflow/modules/formsextend/mass_notification_config_task_form.html";

    // MARKS
    private static final String MARK_CONFIG = "config";
    private static final String MARK_EXTENDER_TYPES = "extenderTypes";
    private static final String MARK_EMAIL_MARKERS = "email_markers";

    @Inject
    @Named( WorkflowFormsextendPlugin.BEAN_CONFIG )
    private ITaskConfigService _config;

    @Inject
    @Named( ResourceExtenderService.BEAN_SERVICE )
    private IResourceExtenderService _resourceExtenderService;

    @Inject
    private IMassNotificationService _massNotificationService;

    @Override
    public String getDisplayConfigForm( HttpServletRequest request, Locale locale, ITask task )
    {
        Map<String, Object> model = new HashMap<>( );

        model.put( MARK_CONFIG, _config.findByPrimaryKey( task.getId( ) ) );
        model.put( MARK_EXTENDER_TYPES, _resourceExtenderService.getExtenderTypes( locale ) );
        model.put( MARK_EMAIL_MARKERS, _massNotificationService.getAvailableMarkers( task ) );

        HtmlTemplate html = AppTemplateService.getTemplate( TEMPLATE_CONFIG, locale, model );

        return html.getHtml( );
    }

    @Override
    public String getDisplayTaskInformation( int nIdHistory, HttpServletRequest request, Locale locale, ITask task )
    {
        return null;
    }
}
