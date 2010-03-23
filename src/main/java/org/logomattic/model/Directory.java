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
import org.chromattic.api.annotations.OneToMany;
import org.chromattic.api.annotations.PrimaryType;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

/**
 * Models an <code>nt:folder</code> node.
 *
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
@PrimaryType(name = "nt:folder")
public abstract class Directory extends File
{

   /**
    * Returns the file children of this directory
    *
    * @return the file children
    */
   @OneToMany
   protected abstract Map<String, File> getFileMap();

   /**
    * Returns the document children of this directory
    *
    * @return the document children
    */
   @OneToMany
   protected abstract Map<String, Document> getDocumentMap();

   /**
    * Factory method for document object.
    *
    * @return a blank content
    */
   @Create
   protected abstract Document createDocument();

   /**
    * Factory method for directory object.
    *
    * @return a blank content
    */
   @Create
   protected abstract Directory createDirectory();

   /**
    * Returns the collection of file children.
    *
    * @return the file children collection
    */
   public Collection<File> getFiles()
   {
      return getFileMap().values();
   }

   /**
    * Removes a specified file.
    *
    * @param name the file name
    */
   public void removeFile(String name)
   {
      getFileMap().remove(name);
   }

   /**
    * Returns a specified document
    *
    * @param name the document name
    * @return the document
    */
   public Document getDocument(String name)
   {
      return getDocumentMap().get(name);
   }

   /**
    * Returns the collection of document children.
    *
    * @return the document children collection
    */
   public Collection<Document> getDocuments()
   {
      return getDocumentMap().values();
   }

   public void saveDocument(String name, String mimeType, byte[] data)
   {
      saveDocument(name, mimeType, new ByteArrayInputStream(data));
   }

   /**
    * Saves a document in the current directory. A document is created if none exist otherwise the existing document
    * is updated.
    *
    * @param name the document name
    * @param mimeType the document mime type
    * @param data the document data
    */
   public void saveDocument(String name, String mimeType, InputStream data)
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

   /**
    * Creates a sub directory.
    *
    * @param name the directory name
    * @return the created directory
    */
   public Directory addDirectory(String name)
   {
      Directory dir = createDirectory();
      getFileMap().put(name, dir);
      return dir;
   }

   /**
    * Creates a document.
    *
    * @param name the document name
    * @param mimeType the document mime type
    * @param data the document data
    * @return the created document
    */
   public Document addDocument(String name, String mimeType, InputStream data)
   {
      Document doc = createDocument();
      getFileMap().put(name, doc);
      doc.update(mimeType, data);
      return doc;
   }
}
