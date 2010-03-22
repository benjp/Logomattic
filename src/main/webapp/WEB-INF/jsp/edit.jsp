<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ page import="org.logomattic.model.*" %>
<%@ page import="org.logomattic.portlet.LogomatticContext" %>

<portlet:defineObjects/>

<%
    LogomatticContext model = (LogomatticContext)request.getAttribute("model");
    String url = model.getImageURL();
    String title = model.getTitle();
%>

<% if (url != null) { %>
    <img src="<%= url %>" alt=""/>
<% } %>

<table >
<%
    Directory root = model.getRoot();
    for (Document doc : root.getDocuments()) {
        String imageURL = model.getImageURL(doc);
        String useURL = model.getUseImageURL(doc);
        String removeURL = model.getRemoveImageURL(doc);
    %>
    <tr>
        <% if (model.isInUse(doc)) { %>
        <td><%= doc.getName() %></td>
        <% } else  { %>
        <td><a href="<%= useURL %>"><%= doc.getName() %></a></td>
        <% } %>
        <td><a href="<%= imageURL %>" target="_blank">View</a></td>
        <td><a href="<%= removeURL %>">Remove</a></td>
    </tr>
    <%
    }

%>
</table>

<form action="<%= renderResponse.createActionURL() %>" method="POST">
<input type="hidden" name="action" value="title"/>
Title: <input name="title" type="text" value="<%= title %>"/><br/>
<input type="submit" value="Change the title"/>
</form>

<form enctype="multipart/form-data" action="<%= renderResponse.createActionURL() %>" method="POST">
<input type="hidden" name="MAX_FILE_SIZE" value="100000"/>
Choose a file to upload: <input name="uploadedimage" type="file"/><br/>
<input type="submit" value="Upload File"/>
</form>