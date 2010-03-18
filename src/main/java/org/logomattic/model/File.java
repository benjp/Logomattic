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

import org.chromattic.api.annotations.Id;
import org.chromattic.api.annotations.ManyToOne;
import org.chromattic.api.annotations.Name;
import org.chromattic.api.annotations.PrimaryType;
import org.chromattic.api.annotations.Property;

import java.util.Date;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
@PrimaryType(name = "nt:hierarchyNode")
public abstract class File 
{

   @Id
   public abstract String getId();

   @Name
   public abstract String getName();

   @ManyToOne
   public abstract Directory getParent();

   @Property(name = "jcr:created")
   public abstract Date getCreated();

}
