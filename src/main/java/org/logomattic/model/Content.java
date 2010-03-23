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

import org.chromattic.api.annotations.MappedBy;
import org.chromattic.api.annotations.OneToOne;
import org.chromattic.api.annotations.PrimaryType;
import org.chromattic.api.annotations.Property;

import java.io.InputStream;
import java.util.Date;

/**
 * Models an <code>nt:resource</code> node.
 *
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
@PrimaryType(name = "nt:resource")
public abstract class Content
{

   /**
    * Returns the content encoding.
    *
    * @return the content encoding
    */
   @Property(name = "jcr:encoding")
   public abstract String getEncoding();

   /**
    * Set the content encoding
    *
    * @param encoding the new encoding
    */
   public abstract void setEncoding(String encoding);

   /**
    * Returns the content mime type.
    *
    * @return the content mime tpye
    */
   @Property(name = "jcr:mimeType")
   public abstract String getMimeType();

   /**
    * Set the content mime type
    *
    * @param mimeType the new mime type
    */
   public abstract void setMimeType(String mimeType);

   /**
    * Returns the content data.
    *
    * @return the content data
    */
   @Property(name = "jcr:data")
   public abstract InputStream getData();

   /**
    * Set the content data
    *
    * @param data the new data
    */
   public abstract void setData(InputStream data);

   /**
    * Returns the content last modified date.
    *
    * @return the content last modified date
    */
   @Property(name = "jcr:lastModified")
   public abstract Date getLastModified();

   /**
    * Set the content last modified date
    *
    * @param date the new last modified date
    */
   public abstract void setLastModified(Date date);

   /**
    * Returns the file related to this content
    *
    * @return the containing file
    */
   @OneToOne
   @MappedBy("jcr:content")
   public abstract File getFile();

}
