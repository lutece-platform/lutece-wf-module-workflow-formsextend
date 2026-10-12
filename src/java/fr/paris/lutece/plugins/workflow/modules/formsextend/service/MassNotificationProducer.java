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

import org.eclipse.microprofile.config.inject.ConfigProperty;

import fr.paris.lutece.plugins.workflow.modules.formsextend.business.MassNotificationTaskConfig;
import fr.paris.lutece.plugins.workflow.modules.formsextend.business.MassNotificationTaskConfigDAO;
import fr.paris.lutece.plugins.workflowcore.business.config.ITaskConfigDAO;
import fr.paris.lutece.plugins.workflowcore.business.task.ITaskType;
import fr.paris.lutece.plugins.workflowcore.business.task.TaskType;
import fr.paris.lutece.plugins.workflowcore.service.config.ITaskConfigService;
import fr.paris.lutece.plugins.workflowcore.service.config.TaskConfigService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;

/**
 * Produces the task type, the configuration and the configuration service of the mass notification task.
 */
@ApplicationScoped
public class MassNotificationProducer
{
    private static final String PREFIX = "workflow-formsextend.taskMassNotification.";

    /**
     * Produces the mass notification task type.
     *
     * @param strKey
     *            the task type key
     * @param strTitleI18nKey
     *            the i18n key of the title
     * @param strBeanName
     *            the CDI name of the task
     * @param strConfigBeanName
     *            the CDI name of the task configuration
     * @param bConfigRequired
     *            true if the task needs a configuration
     * @param bFormTaskRequired
     *            true if the task needs a form
     * @param bTaskForAutomaticAction
     *            true if the task may run in an automatic action
     * @return the task type
     */
    @Produces
    @ApplicationScoped
    @Named( WorkflowFormsextendPlugin.BEAN_TASK_TYPE )
    public ITaskType produceTaskType( @ConfigProperty( name = PREFIX + "key" ) String strKey,
            @ConfigProperty( name = PREFIX + "titleI18nKey" ) String strTitleI18nKey,
            @ConfigProperty( name = PREFIX + "beanName" ) String strBeanName,
            @ConfigProperty( name = PREFIX + "configBeanName" ) String strConfigBeanName,
            @ConfigProperty( name = PREFIX + "configRequired", defaultValue = "false" ) boolean bConfigRequired,
            @ConfigProperty( name = PREFIX + "formTaskRequired", defaultValue = "false" ) boolean bFormTaskRequired,
            @ConfigProperty( name = PREFIX + "taskForAutomaticAction", defaultValue = "false" ) boolean bTaskForAutomaticAction )
    {
        TaskType taskType = new TaskType( );
        taskType.setKey( strKey );
        taskType.setTitleI18nKey( strTitleI18nKey );
        taskType.setBeanName( strBeanName );
        taskType.setConfigBeanName( strConfigBeanName );
        taskType.setConfigRequired( bConfigRequired );
        taskType.setFormTaskRequired( bFormTaskRequired );
        taskType.setTaskForAutomaticAction( bTaskForAutomaticAction );

        return taskType;
    }

    /**
     * Produces an empty mass notification task configuration.
     *
     * @return the configuration
     */
    @Produces
    @Dependent
    @Named( WorkflowFormsextendPlugin.BEAN_TASK_CONFIG )
    public MassNotificationTaskConfig produceTaskConfig( )
    {
        return new MassNotificationTaskConfig( );
    }

    /**
     * Produces the configuration service of the mass notification task.
     *
     * @param taskConfigDAO
     *            the configuration DAO
     * @return the configuration service
     */
    @Produces
    @ApplicationScoped
    @Named( WorkflowFormsextendPlugin.BEAN_CONFIG )
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public ITaskConfigService produceTaskConfigService( @Named( MassNotificationTaskConfigDAO.BEAN_NAME ) MassNotificationTaskConfigDAO taskConfigDAO )
    {
        TaskConfigService taskConfigService = new TaskConfigService( );
        taskConfigService.setTaskConfigDAO( (ITaskConfigDAO) taskConfigDAO );

        return taskConfigService;
    }
}
