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
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.portlet.PortletFileUpload;
import org.chromattic.api.Chromattic;
import org.chromattic.api.ChromatticBuilder;
import org.logomattic.portlet.LogomatticContext;
import org.logomattic.integration.CurrentRepositoryLifeCycle;
import org.logomattic.model.Content;
import org.logomattic.model.Directory;
import org.logomattic.model.Document;
import org.logomattic.model.File;
import org.logomattic.model.Model;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.util.List;

/**
 * The logomattic portlet.
 *
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class LogomatticPortlet extends GenericPortlet
{

   /** . */
   private Chromattic chromattic;

   @Override
   public void init() throws PortletException
   {
      ChromatticBuilder builder = ChromatticBuilder.create();
      builder.add(Directory.class);
      builder.add(Content.class);
      builder.add(Document.class);
      builder.add(File.class);

      //
      builder.setOptionValue(ChromatticBuilder.SESSION_LIFECYCLE_CLASSNAME, CurrentRepositoryLifeCycle.class.getName());
      builder.setOptionValue(ChromatticBuilder.CREATE_ROOT_NODE, true);
      builder.setOptionValue(ChromatticBuilder.ROOT_NODE_PATH, "/");

      //
      chromattic = builder.build();
   }

   @Override
   public void processAction(ActionRequest request, ActionResponse response) throws PortletException, IOException
   {
      LogomatticContext model = new LogomatticContext(chromattic, request, response);
      try
      {
         if (PortletFileUpload.isMultipartContent(request))
         {
            FileItem image = null;
            try
            {
               FileItemFactory factory = new DiskFileItemFactory();
               PortletFileUpload fu = new PortletFileUpload(factory);
               List<FileItem> list = fu.parseRequest(request);
               for (FileItem item : list)
               {
                  if (item.getFieldName().equals("uploadedimage"))
                  {
                     if (item.getContentType().startsWith("image/"))
                     {
                        image = item;
                        break;
                     }
                  }
               }
            }
            catch (FileUploadException e)
            {
               throw new PortletException("Could not process file upload", e);
            }

            //
            if (image != null)
            {
               model.save(image);
            }
         }
         else
         {
            String action = request.getParameter("action");
            if ("use".equals(action))
            {
               String docId = request.getParameter("docid");
               if (docId != null)
               {
                  PortletPreferences prefs = request.getPreferences();
                  prefs.setValue("url", docId);
                  prefs.store();
               }
            }
            else if ("remove".equals(action))
            {
               String docId = request.getParameter("docid");
               if (docId != null)
               {
                  PortletPreferences prefs = request.getPreferences();
                  if (docId.equals(prefs.getValue("url", null)))
                  {
                     prefs.reset("url");
                     prefs.store();
                  }

                  //
                  model.remove(docId);
               }
            }
            else if ("title".equals(action))
            {
               String title = request.getParameter("title");
               if (title != null)
               {
                  PortletPreferences prefs = request.getPreferences();
                  prefs.setValue("title", title);
                  prefs.store();
               }
            }
         }
      }
      finally
      {
         model.close();
      }
   }

   @Override
   public void render(RenderRequest request, RenderResponse response) throws PortletException, IOException
   {
      LogomatticContext model = new LogomatticContext(chromattic, request, response);

      //
      try
      {
         request.setAttribute("model", model);
         super.render(request, response);
      }
      finally
      {
         request.removeAttribute("model");
         model.close();
      }
   }

   @Override
   protected void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException
   {
      PortletRequestDispatcher dispatcher = getPortletContext().getRequestDispatcher("/WEB-INF/jsp/view.jsp");
      dispatcher.include(request, response);
   }

   @Override
   protected void doEdit(RenderRequest request, RenderResponse response) throws PortletException, IOException
   {
      PortletRequestDispatcher dispatcher = getPortletContext().getRequestDispatcher("/WEB-INF/jsp/edit.jsp");
      dispatcher.include(request, response);
   }
}
