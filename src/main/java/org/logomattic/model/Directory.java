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

import org.apache.commons.fileupload.FileItem;
import org.chromattic.api.annotations.Create;
import org.chromattic.api.annotations.OneToMany;
import org.chromattic.api.annotations.PrimaryType;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
@PrimaryType(name = "nt:folder")
public abstract class Directory extends File
{

   @OneToMany
   protected abstract Map<String, File> getFileMap();

   @OneToMany
   protected abstract Map<String, Document> getDocumentMap();

   @Create
   protected abstract Document createDocument();

   @Create
   protected abstract Directory createDirectory();

   public Collection<File> getFiles()
   {
      return getFileMap().values();
   }

   public void removeFile(String name)
   {
      getFileMap().remove(name);
   }

   public Document getDocument(String name)
   {
      return getDocumentMap().get(name);
   }

   public Collection<Document> getDocuments()
   {
      return getDocumentMap().values();
   }

   public void save(String name, String mimeType, byte[] data)
   {
      save(name, mimeType, new ByteArrayInputStream(data));
   }

   public void save(String name, String mimeType, InputStream data)
   {
      Document doc = getDocument(name);

      //
      if (doc != null)
      {
         doc.update(mimeType, data);
      }
      else
      {
         addDocument(name, mimeType, data);
      }
   }

   public Directory addDirectory(String name)
   {
      Directory dir = createDirectory();
      getFileMap().put(name, dir);
      return dir;
   }

   public Document addDocument(String name, String mimeType, InputStream data)
   {
      Document doc = createDocument();
      getFileMap().put(name, doc);
      doc.update(mimeType, data);
      return doc;
   }
}
