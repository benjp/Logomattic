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

import org.chromattic.api.Chromattic;
import org.chromattic.api.ChromatticSession;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class Model
{

   /** . */
   private ChromatticSession session;

   public Model(ChromatticSession session)
   {
      this.session = session;
   }

   public Directory getRoot()
   {
      Directory root = session.findByPath(Directory.class, "logomattic");
      if (root == null)
      {
          root = session.insert(Directory.class, "logomattic");
          session.save();
      }
      return root;
   }

   public boolean remove(String docId)
   {
      Document doc = session.findById(Document.class, docId);
      if (doc != null)
      {
         session.remove(doc);
         session.save();
         return true;
      }
      return false;
   }

   public Document findDocumentById(String docId)
   {
      return session.findById(Document.class, docId);
   }

   public void save()
   {
      session.save();
   }

   public void close()
   {
      session.close();
   }
}
