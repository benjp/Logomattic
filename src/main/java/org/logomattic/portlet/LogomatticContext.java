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

import org.chromattic.api.Chromattic;
import org.logomattic.model.Document;
import org.logomattic.model.Model;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class LogomatticContext extends Model
{

   /** . */
   private PortletRequest request;

   /** . */
   private PortletResponse response;

   public LogomatticContext(Chromattic chromattic, PortletRequest request, PortletResponse response)
   {
      super(chromattic);

      //
      this.request = request;
      this.response = response;
   }

   public String getTitle()
   {
      PortletPreferences prefs = request.getPreferences();
      return prefs.getValue("title", "&nbsp;");
   }

   public String getUseImageURL(Document doc)
   {
      PortletURL url = ((RenderResponse)response).createActionURL();
      url.setParameter("docid", doc.getId());
      url.setParameter("action", "use");
      return url.toString();
   }

   public String getRemoveImageURL(Document doc)
   {
      PortletURL url = ((RenderResponse)response).createActionURL();
      url.setParameter("docid", doc.getId());
      url.setParameter("action", "remove");
      return url.toString();
   }

   public String getImageURL(Document doc)
   {
      String path = doc.getPath();
      return "/rest/jcr/repository/portal-system" + path;
   }

   public String getImageURL()
   {
      PortletPreferences prefs = request.getPreferences();
      String url = prefs.getValue("url", null);
      if (url != null)
      {
          if (!url.startsWith("http://"))
          {
              Document doc = findDocumentById(url);
              if (doc != null)
              {
                  url = getImageURL(doc);
              }
          }
      }
      else
      {
          url = "http://www.nmpp.fr/reseau/animquot/images/logo_bilto.jpg";
      }
      return url;
   }
}
