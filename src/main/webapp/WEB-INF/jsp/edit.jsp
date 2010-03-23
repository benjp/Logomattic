<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ page import="org.logomattic.model.*" %>
<%@ page import="org.logomattic.portlet.LogomatticContext" %>

<portlet:defineObjects/>

<%
   LogomatticContext model = (LogomatticContext)request.getAttribute("model");
   String url = model.getImageURL();
   String title = model.getTitle();
%>

<style type="text/css">

   .logomattic-container {
      font-family: "Lucida Grande", "Lucida Sans Unicode", "Lucida Sans", Verdana, Arial, sans-serif;
      color: #000;
      margin: 0;
      padding: 10px;
      min-height: 100%;
      background-color: #fff;
   }

   h3 {
      font-size: 1.4em;
      color: #30742b;
   }

   h3.gray {
      font-size: 1.18em;
      color: #555;
      padding: 5px;
      margin: 0;
      background-color: #e5e5e5;
      text-align: left;
   }

   .title-gray {
      font-size: 1.18em;
      color: #555;
      padding: 5px;
      margin: 0;
      background-color: #e5e5e5;
      font-weight: bold;
   }

   h4 {
      font-size: 1.0em;
      color: #3d3d3d;
   }

   h5 {
      font-size: 1.0em;
      color: #3d3d3d;
   }

   hr {
      color: #777;
   }

   .upload-logo {
      /*float: right;*/
      padding: 5px;
      background-color: #ecffe5;
   }

   .selected-logo {
      width: 100%;
      text-align: center;
      border: 1px solid #e5e5e5;
      padding: 1px;
   }

   .selected-logo img {
      margin: 0 auto 0 auto;
      padding: 5px;
   }

   label {
      margin: 0;
      padding: 0 3px 0 0;
      font-weight: bold;
      color: #000;
   }

   .current-title {
      padding: 5px 0 5px 0;
   }

   .image-table-container {
      width: 100%;
      border: 1px solid #e5e5e5;
      padding: 1px;
   }

   .image-table-container table {
      width: 98%;
      margin: 0 auto;
      table-layout: fixed;
      border-top:1px solid #E5E5E5;
   }

   .image-table-container table tr {
      border-bottom: 1px solid #e5e5e5;
   }


   .image-table-container table tr td.filename {
      font-weight: bold;
      text-align: left;
      text-decoration: underline;
   }

   .image-table-container table tr td.filename-selected {
      text-align: left;
      font-style: italic;
   }

   .image-table-container table tr td {
      padding: 4px 0 4px 0;
      text-align: center;
   }




</style>


<div class="logomattic-container">



   <div class="selected-logo">
      <h3 class="gray">Selected Logo</h3>
      <% if (url != null)
      { %>
      <img src="<%= url %>" alt=""/>
      <% } %>


   <div class="current-title">
       <form action="<%= renderResponse.createActionURL() %>" method="POST">
       <input type="hidden" name="action" value="title"/>
         <label for="title">Current Title</label> <input id="title" name="title" type="text" value="<%= title %>"/>
         <input type="submit" value="Change"/>
      </form>
   </div>

   </div>

   <br/>

   <div class="image-table-container">
   <h3 class="gray">Available Logos</h3>
   <div class="upload-logo">
      <form enctype="multipart/form-data" action="<%= renderResponse.createActionURL() %>" method="POST">
      <input type="hidden" name="MAX_FILE_SIZE" value="100000"/>
       <label for="uploadimage">Add New Logo</label>  <input id="uploadimage" name="uploadedimage" type="file"/>
      <input type="submit" value="Upload File"/>
      </form>
   </div>
   <table>
      <%
         Directory root = model.getRoot();
         for (Document doc : root.getDocuments())
         {
            String imageURL = model.getImageURL(doc);
            String useURL = model.getUseImageURL(doc);
            String removeURL = model.getRemoveImageURL(doc);
      %>
      <tr>
         <% if (model.isInUse(doc))
         { %>
         <td class="filename-selected"><%= doc.getName() %>
         </td>
         <% }
         else
         { %>
         <td class="filename"><a href="<%= useURL %>"><%= doc.getName() %>
         </a></td>
         <% } %>
         <td><a href="<%= imageURL %>" target="_blank">View</a></td>
         <td><a href="<%= removeURL %>">Remove</a></td>
      </tr>
      <%
         }

      %>
   </table>
    </div>




</div>