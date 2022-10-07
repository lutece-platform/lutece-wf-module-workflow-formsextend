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
package fr.paris.lutece.plugins.workflow.modules.formsextend.util;

/**
 * 
 * FormsExtendConstants
 *
 */
public final class FormsExtendConstants
{

    /**
     * Private constructor
     */
    private FormsExtendConstants( )
    {

    }

    public static final String JSON_OBJECT = "object";
    public static final String JSON_SENDER = "sender";
    public static final String JSON_MESSAGE = "message";
    public static final String JSON_ID_USER = "idUser";
    
    //PROPERTIES
    public static final String PROPERTY_LABEL_URL_RESPONSE_FO = "module.workflow.formsextend.task_mass_notification_email.config.marker.url_response_fo";
    public static final String PROPERTY_LABEL_URL_RESPONSE_BO = "module.workflow.formsextend.task_mass_notification_email.config.marker.url_response_bo";
    public static final String PROPERTY_URL_RESPONSE_FO = "workflow-formsextend.formResponse.url.fo";
    public static final String PROPERTY_URL_RESPONSE_BO = "workflow-formsextend.formResponse.url.bo";
    public static final String PROPERTY_LUTECE_PROD_URL = "lutece.prod.url";
    public static final String PROPERTY_LUTECE_ADMIN_PROD_URL = "lutece.admin.prod.url"; 
    
    //MARKS
    public static final String MARK_URL_RESPONSE_FO = "url_response_fo";
    public static final String MARK_URL_RESPONSE_BO = "url_response_bo";

}
