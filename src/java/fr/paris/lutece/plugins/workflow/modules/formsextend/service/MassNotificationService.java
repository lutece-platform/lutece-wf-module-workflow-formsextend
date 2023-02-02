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
package fr.paris.lutece.plugins.workflow.modules.formsextend.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;

import com.google.common.collect.Lists;

import fr.paris.lutece.plugins.extend.business.extender.history.ResourceExtenderHistory;
import fr.paris.lutece.plugins.extend.business.extender.history.ResourceExtenderHistoryFilter;
import fr.paris.lutece.plugins.extend.service.extender.history.IResourceExtenderHistoryService;
import fr.paris.lutece.plugins.extend.service.extender.history.ResourceExtenderHistoryService;
import fr.paris.lutece.plugins.forms.business.Form;
import fr.paris.lutece.plugins.forms.business.FormHome;
import fr.paris.lutece.plugins.forms.business.FormQuestionResponse;
import fr.paris.lutece.plugins.forms.business.FormResponse;
import fr.paris.lutece.plugins.forms.business.FormResponseHome;
import fr.paris.lutece.plugins.forms.business.FormResponseStep;
import fr.paris.lutece.plugins.forms.business.Question;
import fr.paris.lutece.plugins.forms.business.QuestionHome;
import fr.paris.lutece.plugins.forms.util.FormsResponseUtils;
import fr.paris.lutece.plugins.genericattributes.business.Response;
import fr.paris.lutece.plugins.mylutece.modules.cacheuserattribute.service.CacheUserAttributeService;
import fr.paris.lutece.plugins.workflow.modules.formsextend.business.MassNotificationTaskConfig;
import fr.paris.lutece.plugins.workflow.modules.formsextend.util.FormsExtendConstants;
import fr.paris.lutece.plugins.workflowcore.business.action.Action;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.plugins.workflowcore.business.workflow.Workflow;
import fr.paris.lutece.plugins.workflowcore.service.action.ActionService;
import fr.paris.lutece.plugins.workflowcore.service.action.IActionService;
import fr.paris.lutece.plugins.workflowcore.service.resource.IResourceWorkflowService;
import fr.paris.lutece.plugins.workflowcore.service.resource.ResourceWorkflowService;
import fr.paris.lutece.plugins.workflowcore.service.task.ITask;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.mail.MailService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.html.HtmlTemplate;

/**
 * 
 * MassNotificationService
 *
 */
public class MassNotificationService implements IMassNotificationService
{
    private static final String EMAIL_SENDING_LIMIT = "workflow-formsextend.task.mass.notification.email.sending.limit";
    private static final String DASHBOARD_SENDING_LIMIT = "workflow-formsextend.task.mass.notification.dashboard.sending.limit";
    private static final String PROPERTY_ID_EMAIL_ATTRIBUTE = "workflow-formsextend.cacheuserattribute.attributeId.email";
    private static final String PROPERTY_FORMS_RESPONSE_URL_FO = "module.workflow.formsextend.marker.forms_response_url_fo";
    private static final String MARK_RESPONSE_URL = "view_response_url";
    
    @Inject
    @Named( ResourceExtenderHistoryService.BEAN_SERVICE )
    private IResourceExtenderHistoryService _resourceExtenderHistoryService;

    @Inject
    @Named( ActionService.BEAN_SERVICE )
    private IActionService _actionService;
    
    @Inject
    @Named( ResourceWorkflowService.BEAN_SERVICE )
    IResourceWorkflowService _resourceWorkflowService;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ReferenceList getAvailableMarkers( ITask task, Locale locale  )
    {
        ReferenceList referenceList = new ReferenceList( );

        referenceList.addItem( MARK_RESPONSE_URL, I18nService.getLocalizedString( PROPERTY_FORMS_RESPONSE_URL_FO, locale ) );
        
        Action action = _actionService.findByPrimaryKey( task.getAction( ).getId( ) );      
        Workflow workflow = action.getWorkflow( );
                
        for( Form form : FormHome.getFormList( ) )
        {
            if( form.getIdWorkflow( ) == workflow.getId( ) )
            {
                List<Question> questionList = QuestionHome.getListQuestionByIdForm( form.getId( ) ).stream( )
                        .filter( distinctByKey( Question::getCode ) )
                        .collect( Collectors.toList( ) );
                questionList.stream( ).forEach( q -> referenceList.addItem( q.getCode( ), q.getTitle( ) ) );
            }
        }

        return referenceList;
    }

    /**
     * distinctByKey
     * 
     * @param <T>
     * @param keyExtractor
     * @return
     */
    private static <T> Predicate<T> distinctByKey( Function<? super T, Object> keyExtractor )
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>( );
        return t -> map.putIfAbsent( keyExtractor.apply( t ), Boolean.TRUE ) == null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Object> getAvailableMarkersValues( FormResponse formResponse )
    {
        Map<String, Object> model = new HashMap<>( );
        
        model.put( MARK_RESPONSE_URL, FormsResponseUtils.buildFormsResponseFOUrl(formResponse.getId( )) );

        if ( formResponse != null )
        {
            for ( FormResponseStep formResponseStep : formResponse.getSteps( ) )
            {
                for ( FormQuestionResponse formQuestionResponse : formResponseStep.getQuestions( ) )
                {
                    for ( Response response : formQuestionResponse.getEntryResponse( ) )
                    {
                        model.put( formQuestionResponse.getQuestion( ).getCode( ), response.getResponseValue( ) );
                    }
                }
            }
        }
        return model;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyUsersByEmail( ResourceHistory resourceHistory, MassNotificationTaskConfig config )
    {
        FormResponse formResponse = FormResponseHome.findByPrimaryKey( resourceHistory.getIdResource( ) );
        Map<String, Object> model = getAvailableMarkersValues( formResponse );
        List<ResourceExtenderHistory> listResourceExtenderHistory = getResourceExtenderHistoryListForEmail( resourceHistory, config, formResponse.getFormId( ) );

        //Remove duplicated GUID
        listResourceExtenderHistory = listResourceExtenderHistory.stream( )
                .filter( distinctByKey( ResourceExtenderHistory::getUserGuid ) )
                .collect( Collectors.toList( ) );
        
        List<List<ResourceExtenderHistory>> partitionedList = Lists.partition( listResourceExtenderHistory,
                AppPropertiesService.getPropertyInt( EMAIL_SENDING_LIMIT, 100 ) );

        for ( List<ResourceExtenderHistory> listResourceExtenderHistoryPart : partitionedList )
        {
            List<String> listGuids = new ArrayList< >( );

            for ( ResourceExtenderHistory resourceExtenderHistory : listResourceExtenderHistoryPart )
            {
                listGuids.add( resourceExtenderHistory.getUserGuid( ) );
            }            
            sendEmail( getEmailListByUsersCachedAttribute( listGuids ), config, model );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyUsersDashboard( ResourceHistory resourceHistory, MassNotificationTaskConfig config, HttpServletRequest request )
    {
        List<JSONObject> listNotificationJson = new ArrayList<>( );
        
        FormResponse formResponse = FormResponseHome.findByPrimaryKey( resourceHistory.getIdResource( ) );
        Map<String, Object> model = getAvailableMarkersValues( formResponse );

        List<ResourceExtenderHistory>  listResourceExtenderHistory = getResourceExtenderHistoryListForDashboard( resourceHistory, config, formResponse.getFormId( ) );
        
        //Remove duplicated GUID
        listResourceExtenderHistory = listResourceExtenderHistory.stream( )
                .filter( distinctByKey( ResourceExtenderHistory::getUserGuid ) )
                .collect( Collectors.toList( ) );
        
        List<List<ResourceExtenderHistory>> partitionedList = Lists.partition( listResourceExtenderHistory,
                AppPropertiesService.getPropertyInt( DASHBOARD_SENDING_LIMIT, 100 ) );

        HtmlTemplate html = AppTemplateService.getTemplateFromStringFtl( config.getMessageForDashboard( ), null, model );

        for ( List<ResourceExtenderHistory> listResourceExtenderHistoryPart : partitionedList )
        {
            for ( ResourceExtenderHistory resourceExtenderHistory : listResourceExtenderHistoryPart )
            {
                listNotificationJson.add( constructJsonNotification( config, html, resourceExtenderHistory, model ) );
            }
            DashboardNotificationService.sendMassNotification( listNotificationJson.toString( ), request );
        }

    }

    /**
     * Construct json for notify the API Rest Mydashboard notification
     * @param config
     * @param html
     * @param resourceExtenderHistory
     * @param model 
     * @return json
     */
    private JSONObject constructJsonNotification( MassNotificationTaskConfig config, HtmlTemplate html, ResourceExtenderHistory resourceExtenderHistory, Map<String, Object> model )
    {
        Map<String, Object> map = new HashMap< >( );
        
        map.put( FormsExtendConstants.JSON_OBJECT, AppTemplateService.getTemplateFromStringFtl( config.getSubjectForDashboard( ), null, model ).getHtml( ) );
        map.put( FormsExtendConstants.JSON_SENDER, config.getSenderForDashboard( ) );
        map.put( FormsExtendConstants.JSON_MESSAGE, html.getHtml( ) );
        map.put( FormsExtendConstants.JSON_ID_USER, resourceExtenderHistory.getUserGuid( ) );

        return new JSONObject( map );
    }

    /**
     * Send email
     * @param strEmails
     * @param config
     * @param model
     */
    private void sendEmail ( String strEmails, MassNotificationTaskConfig config, Map<String,Object> model )
    {
        if ( !strEmails.isEmpty( ) )
        {
            HtmlTemplate html = AppTemplateService.getTemplateFromStringFtl( config.getMessageForEmail( ), null, model );

            MailService.sendMailHtml( StringUtils.EMPTY, StringUtils.EMPTY, strEmails, config.getSenderNameForEmail( ), config.getSenderEmailForEmail( ),
            		AppTemplateService.getTemplateFromStringFtl( config.getSubjectForEmail( ), null, model ).getHtml( ), html.getHtml( ) );
        }
        else
        {
            AppLogService.error( "Email list is empty", new Exception( ) );
        }
    }
    
    /**
     * Populate email list by users cached attribute
     * @param listCachedAttributes
     * @param listGuid
     */
    public static String getEmailListByUsersCachedAttribute ( List<String> listGuid )
    {
        Map<String, String> listCachedAttributes = new HashMap< >( );
        List<String> listEmail = new ArrayList< >( );
        int nIdEmailAttribute = AppPropertiesService.getPropertyInt( PROPERTY_ID_EMAIL_ATTRIBUTE, 1 );
        
        listCachedAttributes.putAll( CacheUserAttributeService.getCachedAttributesByListUserIdsAndAttributeId( listGuid, nIdEmailAttribute ) );
        
        for ( Map.Entry<String, String> cachedAttribute : listCachedAttributes.entrySet( ) )
        {               
            listEmail.add( cachedAttribute.getValue( ) );
        }
        return StringUtils.join( listEmail, ";");
    }

    /**
     * Get resource extender history filtered list for email notification
     * 
     * @param resourceHistory
     * @param config
     * @param nIdForm
     * @return list resource extender history filtered
     */
    private List<ResourceExtenderHistory> getResourceExtenderHistoryListForEmail( ResourceHistory resourceHistory, MassNotificationTaskConfig config, int nIdForm )
    {
        List<ResourceExtenderHistory> listResourceExtenderHistory = new ArrayList<>( );
        
        for ( String extenderType : config.getListExtenderTypesForEmail( ) )
        {
            ResourceExtenderHistoryFilter filter = new ResourceExtenderHistoryFilter( );
            filter.setExtenderType( extenderType );
            filter.setIdExtendableResource( String.valueOf( resourceHistory.getIdResource( ) ) );
            filter.setExtendableResourceType( getResourceType( resourceHistory.getResourceType( ), nIdForm ) );
            
            listResourceExtenderHistory.addAll( _resourceExtenderHistoryService.findByFilter( filter ) );
        }
        
        return listResourceExtenderHistory;
    }

    /**
     * Get resource extender history filtered list for dashboard notification
     * 
     * @param resourceHistory
     * @param config
     * @param nIdForm
     * @return list resource extender history filtered
     */
    private List<ResourceExtenderHistory> getResourceExtenderHistoryListForDashboard( ResourceHistory resourceHistory, MassNotificationTaskConfig config, int nIdForm )
    {
        List<ResourceExtenderHistory> listResourceExtenderHistory = new ArrayList<>( );

        for ( String extenderType : config.getListExtenderTypesForDashboard( ) )
        {
            ResourceExtenderHistoryFilter filter = new ResourceExtenderHistoryFilter( );
            filter.setExtenderType( extenderType );
            filter.setIdExtendableResource( String.valueOf( resourceHistory.getIdResource( ) ) );
            filter.setExtendableResourceType( getResourceType( resourceHistory.getResourceType( ), nIdForm ) );
            
            listResourceExtenderHistory.addAll( _resourceExtenderHistoryService.findByFilter( filter ) );
        }
        return listResourceExtenderHistory;
    }
    
    /**
     * Get resource type
     * @param strResourceType
     * @param nIdForm
     * @return resource type
     */
    private String getResourceType( String strResourceType, int nIdForm )
    {        
        return strResourceType + "_" + nIdForm;
    }
}
