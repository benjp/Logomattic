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

package org.logomattic;

import org.chromattic.api.ChromatticSession;
import org.logomattic.model.Directory;
import org.logomattic.model.Document;

import java.io.ByteArrayInputStream;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class ChromatticTestCase extends AbstractTestCase
{

   public void testSimple() throws Exception
   {
      ChromatticSession session = chromattic.openSession();

      //
      Directory dir = session.insert(Directory.class, "logos");
      assertEquals("logos", dir.getName());
      assertEquals(0, dir.getFiles().size());

      //
      Document logo = dir.addDocument("mylogo", "image/png", new ByteArrayInputStream(new byte[]{0, 1, 2}));
      assertEquals(dir, logo.getParent());

      //
      assertEquals(1, dir.getFiles().size());
      assertTrue(dir.getFiles().contains(logo));

      //
      session.close();
   }
}