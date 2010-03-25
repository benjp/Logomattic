/*
 * Copyright (C) 2010 eXo Platform SAS.
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

package org.logomattic.portlet;

import org.apache.commons.fileupload.FileItem;
import org.chromattic.api.Chromattic;
import org.logomattic.model.Document;
import org.logomattic.model.Model;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;
import java.io.IOException;

/**
 * The logomattic context extends the {@link Model} class to provide a runtime content for the portlet environment.
 * A context is associated with a portlet request with full access to the {@link PortletRequest} and {@link PortletResponse}
 * objects.
 *
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class LogomatticContext extends Model
{

   /** . */
   private static final String WORKSPACE_NAME = "portal-system";

   /** . */
   private PortletRequest request;

   /** . */
   private PortletResponse response;

   public LogomatticContext(Chromattic chromattic, PortletRequest request, PortletResponse response)
   {
      super(chromattic.openSession(WORKSPACE_NAME));

      //
      this.request = request;
      this.response = response;
   }

   /**
    * Returns the current title. The value is retrieved from the portlet preferences.
    *
    * @return the title
    */
   public String getTitle()
   {
      PortletPreferences prefs = request.getPreferences();
      return prefs.getValue("title", "Logomattic");
   }

   /**
    * Returns the URL that will invoke the update of the current image with the specified document.
    *
    * @param doc the document to use
    * @return an URL
    */
   public String getUseImageURL(Document doc)
   {
      PortletURL url = ((RenderResponse)response).createActionURL();
      url.setParameter("docid", doc.getId());
      url.setParameter("action", "use");
      return url.toString();
   }

   /**
    * Returns the URL that will invoke the removals of the specified document.
    *
    * @param doc the document to remove
    * @return an URL
    */
   public String getRemoveImageURL(Document doc)
   {
      PortletURL url = ((RenderResponse)response).createActionURL();
      url.setParameter("docid", doc.getId());
      url.setParameter("action", "remove");
      return url.toString();
   }

   /**
    * Returns the URL that will serve the specified document.
    *
    * @param doc the document to serve
    * @return an URL
    */
   public String getImageURL(Document doc)
   {
      String path = doc.getPath();
      return "/rest/jcr/repository/" + WORKSPACE_NAME + path;
   }

   /**
    * Returns the URL of the image to display. The URL is computed using the portlet preferences. When no
    * such preferences exist, then a default URL is returned.
    *
    * @return an URL
    */
   public String getImageURL()
   {
      PortletPreferences prefs = request.getPreferences();
      String url = prefs.getValue("url", null);
      if (url == null)
      {
         url = request.getContextPath() + "/bubbles.png";
      }
      else if (!url.startsWith("http://"))
      {
          Document doc = findDocumentById(url);
          if (doc != null)
          {
              url = getImageURL(doc);
          }
      }
      return url;
   }

   /**
    * Returns true if the specified document is used as saved logo.
    *
    * @param doc the document to test
    * @return the boolean indicating if the specified document is in use
    */
   public boolean isInUse(Document doc)
   {
      PortletPreferences prefs = request.getPreferences();
      String url = prefs.getValue("url", null);
      return doc.getId().equals(url);
   }

   /**
    * Saves the specified image in the repository.
    *
    * @param image the image to save
    * @throws IOException any IOException
    */
   public void save(FileItem image) throws IOException
   {
      getRoot().saveDocument(image.getName(), image.getContentType(), image.getInputStream());
      save();
   }
}
