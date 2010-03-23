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

package org.logomattic.model;

import org.chromattic.api.annotations.Create;
import org.chromattic.api.annotations.MappedBy;
import org.chromattic.api.annotations.OneToOne;
import org.chromattic.api.annotations.Owner;
import org.chromattic.api.annotations.PrimaryType;

import java.io.InputStream;
import java.util.Date;

/**
 * Models an <code>nt:file</code> node.
 *
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
@PrimaryType(name = "nt:file")
public abstract class Document extends File
{

   /**
    * Returns the document's content or null if no content is associated with this document.
    *
    * @return the document content
    */
   @OneToOne
   @Owner
   @MappedBy("jcr:content")
   protected abstract Content getContent();

   /**
    * Set the document content.
    *
    * @param content the document content
    */
   protected abstract void setContent(Content content);

   /**
    * Factory method for content object.
    *
    * @return a blank content
    */
   @Create
   protected abstract Content createContent();

   /**
    * Updates the current document with the specified mime type and data.
    *
    * @param mimeType the mime type
    * @param data the data
    */
   public void update(String mimeType, InputStream data)
   {
      Content content = getContent();

      //
      if (content == null)
      {
         content = createContent();
         setContent(content);
      }

      //
      content.setData(data);
      content.setMimeType(mimeType);
      content.setLastModified(new Date());
   }
}
