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

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.httpaccess.HttpAccess;
import fr.paris.lutece.util.httpaccess.HttpAccessException;

/**
 * 
 * NotificationApiService
 *
 */
public class DashboardNotificationService
{
    private static final String NOTIFICATION_API_ENDPOINT_ADDLIST = "workflow-formsextend.notification_api.endpoint.addList";
    private static final String PROPERTY_REST_URL = "workflow-formsextend.notification_api.rest.webapp.url";
    

    /**
     * Private constructor
     */
    private DashboardNotificationService( )
    {

    }

    /**
     * Send list notification
     * 
     * @param strJson
     * @param request
     * @return true if success
     */
    public static boolean sendMassNotification( String strJson, HttpServletRequest request )
    {
        String strRelativeUri = AppPropertiesService.getProperty( NOTIFICATION_API_ENDPOINT_ADDLIST, "rest/notification-api/addList" );

        String strJsonResult = doPostJson( strRelativeUri, strJson, request );

        return strJsonResult != null && strJsonResult.contains( "success" );
    }

    /**
     * Do Post json
     * 
     * @param strRelativeUri
     * @param strJson
     * @param request
     * @return string Json
     */
    private static String doPostJson( String strRelativeUri, String strJson, HttpServletRequest request )
    {
        String strJsonResult = null;

        try
        {
            HttpAccess httpAccess = new HttpAccess( );
            strJsonResult = httpAccess.doPostJSON( AppPropertiesService.getProperty( PROPERTY_REST_URL ) + strRelativeUri, strJson, null, null, null, null );
        }
        catch( HttpAccessException e )
        {
            String strError = "NotificationApiService - Error calling '" + AppPropertiesService.getProperty( PROPERTY_REST_URL ) + strRelativeUri;
            AppLogService.error( strError, e );
        }

        return strJsonResult;
    }

}
