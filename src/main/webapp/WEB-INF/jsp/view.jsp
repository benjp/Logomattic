<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ page import="org.logomattic.portlet.LogomatticContext" %>

<portlet:defineObjects/>

<%
    LogomatticContext model = (LogomatticContext)request.getAttribute("model");
    String url = model.getImageURL();
    String title = model.getTitle();
%>

<div class="UILogomatticPortlet ClearFix">
   <a href="#" class="Img"><img src="<%= url %>" alt=""/></a>
   <div class="BannerTitle"><%= title %></div>
</div>
