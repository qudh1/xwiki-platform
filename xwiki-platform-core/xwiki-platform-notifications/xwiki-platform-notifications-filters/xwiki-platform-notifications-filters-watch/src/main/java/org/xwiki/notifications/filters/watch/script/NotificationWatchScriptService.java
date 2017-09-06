/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.notifications.filters.watch.script;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.xwiki.bridge.DocumentAccessBridge;
import org.xwiki.component.annotation.Component;
import org.xwiki.model.reference.EntityReference;
import org.xwiki.notifications.NotificationException;
import org.xwiki.notifications.filters.watch.WatchedEntitiesManager;
import org.xwiki.notifications.filters.watch.WatchedEntityFactory;
import org.xwiki.script.service.ScriptService;

/**
 * Script Service to handle the watched entities. We call `Watched Entities` the locations or the users for that we
 * receive all events.
 *
 * @version $Id$
 * @since 9.8RC1
 */
@Component
@Singleton
@Named(NotificationWatchScriptService.ROLE_HINT)
public class NotificationWatchScriptService implements ScriptService
{
    /**
     * Hint of the component.
     */
    public static final String ROLE_HINT = "notification.watch";

    @Inject
    private WatchedEntitiesManager watchedEntitiesManager;

    @Inject
    private DocumentAccessBridge documentAccessBridge;

    @Inject
    private WatchedEntityFactory watchedEntityFactory;

    /**
     * Add a filter to watch the specified location.
     *
     * @param location the location to watch
     * @throws NotificationException if an error happens
     */
    public void watchLocation(EntityReference location) throws NotificationException
    {
        watchedEntitiesManager.watchEntity(documentAccessBridge.getCurrentUserReference(),
                watchedEntityFactory.createWatchedLocationReference(location));
    }

    /**
     * Remove a filter to stop watching the specified entity.
     *
     * @param location the location
     * @throws NotificationException if an error happens
     */
    public void unwatchLocation(EntityReference location) throws NotificationException
    {
        watchedEntitiesManager.unwatchEntity(documentAccessBridge.getCurrentUserReference(),
                watchedEntityFactory.createWatchedLocationReference(location));
    }

    /**
     * @param location the location
     * @return either or not the location is already watched by the given user
     * @throws NotificationException if an error happens
     */
    public boolean isLocationWatched(EntityReference location) throws NotificationException
    {
        return watchedEntitiesManager.isEntityWatched(documentAccessBridge.getCurrentUserReference(),
                watchedEntityFactory.createWatchedLocationReference(location));
    }

    /**
     * @param location the location
     * @return either or not the location is directly watched by the user
     * @throws NotificationException if an error happens
     */
    public boolean isLocationDirectlyWatched(EntityReference location) throws NotificationException
    {
        return watchedEntitiesManager.isEntityDirectlyWatched(documentAccessBridge.getCurrentUserReference(),
                watchedEntityFactory.createWatchedLocationReference(location));
    }
}