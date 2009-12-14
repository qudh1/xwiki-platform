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
package com.xpn.xwiki.wysiwyg.client;

import java.util.List;

import org.xwiki.component.annotation.ComponentRole;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.xpn.xwiki.wysiwyg.client.plugin.link.LinkConfig;
import com.xpn.xwiki.wysiwyg.client.util.Attachment;
import com.xpn.xwiki.wysiwyg.client.util.WikiPage;

/**
 * The service interface used on the server.
 * <p>
 * NOTE: This component interface should be split in multiple domain specific interfaces. Don't add any more methods!
 * 
 * @version $Id$
 */
@ComponentRole
@RemoteServiceRelativePath("WikiService.gwtrpc")
public interface WikiService extends RemoteService
{
    /**
     * Check if the current wiki is part of a multiwiki (i.e. this is a virtual wiki).
     * 
     * @return true if the current wiki is a multiwiki, and false in the other case
     */
    Boolean isMultiWiki();

    /**
     * @return a list containing the names of all wikis.
     */
    List<String> getVirtualWikiNames();

    /**
     * @param wikiName the name of the wiki to search for spaces. If this is <code>null</code>, the current wiki will be
     *            used.
     * @return a list of all spaces names in the specified wiki.
     */
    List<String> getSpaceNames(String wikiName);

    /**
     * @param wikiName the name of the wiki. Pass <code>null</code> if this should use the current wiki.
     * @param spaceName the name of the space
     * @return the list of the page names from a given space and a given wiki.
     */
    List<String> getPageNames(String wikiName, String spaceName);

    /**
     * @param start the start index of the list of pages to return
     * @param count the number of pages to return
     * @return the recently {@code count} modified pages of the current user, starting from position {@code start}
     */
    List<WikiPage> getRecentlyModifiedPages(int start, int count);

    /**
     * @param start the start index of the list of pages to return
     * @param count the number of pages to return
     * @param keyword the keyword to search the pages for
     * @return the {@code count} pages whose full name or title match the keyword, starting from position {@code start}
     */
    List<WikiPage> getMatchingPages(String keyword, int start, int count);

    /**
     * Creates a page link (URL, reference) from the given parameters. None of them are mandatory, if one misses, it is
     * replaced with a default value.
     * 
     * @param wikiName the name of the wiki to which to link
     * @param spaceName the name of the space of the page. If this parameter is missing, it is replaced with the space
     *            of the current document in the context.
     * @param pageName the name of the page to which to link to. If it's missing, it is replaced with "WebHome".
     * @param revision the value for the page revision to which to link to. If this is missing, the link is made to the
     *            latest revision, the default view action for the document.
     * @param anchor the name of the anchor type.
     * @return the data of the link to the document, containing link url and link reference information.
     */
    LinkConfig getPageLink(String wikiName, String spaceName, String pageName, String revision, String anchor);

    /**
     * Returns attachment information from the passed parameters, testing if the passed attachment exists. Note that the
     * {@code attachmentName} name will be cleaned to match the attachment names cleaning rules, and the attachment
     * reference and URL will be generated with the cleaned name. This function will be used as a method to test the
     * correct upload of a file to a page.
     * 
     * @param wikiName the name of the wiki of the page the file is attached to
     * @param spaceName the name of the space of the page the file is attached to
     * @param pageName the name of the page the file is attached to
     * @param attachmentName the uncleaned name of the attachment, which is to be cleaned on the server
     * @return an {@link Attachment} containing the reference and the URL of the attachment, or {@code null} in case the
     *         attachment was not found
     */
    Attachment getAttachment(String wikiName, String spaceName, String pageName, String attachmentName);

    /**
     * Returns all the image attachments from the referred page.
     * 
     * @param wikiName the name of the wiki to get images from
     * @param spaceName the name of the space to get image attachments from
     * @param pageName the name of the page to get image attachments from
     * @return list of the image attachments
     */
    List<Attachment> getImageAttachments(String wikiName, String spaceName, String pageName);

    /**
     * Returns all the attachments from the referred page.
     * 
     * @param wikiName the name of the wiki to get attachments from
     * @param spaceName the name of the space to get attachments from
     * @param pageName the name of the page to get attachments from
     * @return list of the attachments
     */
    List<Attachment> getAttachments(String wikiName, String spaceName, String pageName);
}
