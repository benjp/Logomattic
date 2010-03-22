<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ page import="org.logomattic.model.*" %>
<%@ page import="javax.portlet.PortletURL" %>

<portlet:defineObjects/>

<table >
<%

    Model model = (Model)request.getAttribute("model");
    Directory root = model.getRoot();
    for (Document doc : root.getDocuments())
    {
        PortletURL useURL = renderResponse.createActionURL();
        useURL.setParameter("docid", doc.getId());
        useURL.setParameter("action", "use");
        PortletURL removeURL = renderResponse.createActionURL();
        removeURL.setParameter("action", "remove");
        removeURL.setParameter("docid", doc.getId());
    %>
    <tr>
        <td><a href="<%= useURL %>"><%= doc.getName() %></a></td>
        <td><a href="/rest/jcr/repository/portal-system/logomattic/<%= doc.getName()%>">View</a></td>
        <td><a href="<%= removeURL %>">Remove</a></td>
    </tr>
    <%
    }

%>
</table>

<form action="<%= renderResponse.createActionURL() %>" method="POST">
<input type="hidden" name="action" value="title"/>
Title: <input name="title" type="text" value="<%= renderRequest.getPreferences().getValue("title", "&nbsp;") %>"/><br/>
<input type="submit" value="Change the title"/>
</form>

<form enctype="multipart/form-data" action="<%= renderResponse.createActionURL() %>" method="POST">
<input type="hidden" name="MAX_FILE_SIZE" value="100000"/>
Choose a file to upload: <input name="uploadedimage" type="file"/><br/>
<input type="submit" value="Upload File"/>
</form>