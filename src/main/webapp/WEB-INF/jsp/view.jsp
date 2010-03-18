<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ page import="javax.portlet.PortletPreferences" %>
<%@ page import="org.chromattic.api.*" %>
<%@ page import="org.logomattic.model.Document" %>

<portlet:defineObjects/>

<%
    PortletPreferences prefs = renderRequest.getPreferences();
    String url = prefs.getValue("url", null);
    if (url != null)
    {
        if (!url.startsWith("http://"))
        {
            ChromatticSession csession = (ChromatticSession)request.getAttribute("session");
            Document doc = csession.findById(Document.class, url);
            if (doc != null)
            {
                String path = csession.getPath(doc);
                url = "/rest/jcr/repository/portal-system" + path;
            }
        }
    }
    else
    {
        url = "http://www.nmpp.fr/reseau/animquot/images/logo_bilto.jpg";
    }

    String title = prefs.getValue("title", "&nbsp;");
%>

<div class="UILogoPortlet ClearFix">
   <a href="#" class="Img"><img src="<%= url %>" alt=""/></a>
   <div class="BannerTitle"><%= title %></div>
</div>
