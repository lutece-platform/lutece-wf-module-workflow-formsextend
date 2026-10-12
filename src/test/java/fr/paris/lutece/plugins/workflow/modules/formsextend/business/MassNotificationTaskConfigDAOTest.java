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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import fr.paris.lutece.test.LuteceTestCase;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * Tests the mass notification task configuration DAO.
 */
public class MassNotificationTaskConfigDAOTest extends LuteceTestCase
{
    private static final int ID_TASK = 987654;

    @Inject
    @Named( MassNotificationTaskConfigDAO.BEAN_NAME )
    private MassNotificationTaskConfigDAO _dao;

    /**
     * Creates, reads, updates and deletes a configuration.
     */
    @Test
    public void testCRUD( )
    {
        MassNotificationTaskConfig config = new MassNotificationTaskConfig( );
        config.setIdTask( ID_TASK );
        config.setEmailConfig( true );
        config.setSubjectForEmail( "Subject" );
        config.setSenderNameForEmail( "Sender" );
        config.setSenderEmailForEmail( "sender@e2e.local" );
        config.setMessageForEmail( "<p>Message</p>" );
        config.setListExtenderTypesForEmail( new String [ ] { "comment", "rating" } );
        config.setDashboardConfig( false );
        _dao.insert( config );

        MassNotificationTaskConfig loaded = _dao.load( ID_TASK );
        assertTrue( loaded.isEmailConfig( ) );
        assertFalse( loaded.isDashboardConfig( ) );
        assertEquals( "Subject", loaded.getSubjectForEmail( ) );
        assertEquals( "sender@e2e.local", loaded.getSenderEmailForEmail( ) );
        assertArrayEquals( new String [ ] { "comment", "rating" }, loaded.getListExtenderTypesForEmail( ) );
        assertNull( loaded.getListExtenderTypesForDashboard( ) );

        loaded.setDashboardConfig( true );
        loaded.setSubjectForDashboard( "Dashboard" );
        loaded.setListExtenderTypesForDashboard( new String [ ] { "feedback" } );
        _dao.store( loaded );

        MassNotificationTaskConfig stored = _dao.load( ID_TASK );
        assertTrue( stored.isDashboardConfig( ) );
        assertEquals( "Dashboard", stored.getSubjectForDashboard( ) );
        assertArrayEquals( new String [ ] { "feedback" }, stored.getListExtenderTypesForDashboard( ) );

        _dao.delete( ID_TASK );
        assertNull( _dao.load( ID_TASK ) );
    }
}
