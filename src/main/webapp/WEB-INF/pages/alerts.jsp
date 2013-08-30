<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
    <title>Alerts</title>
</head>
<body>

<h1 class="fl">Alerts</h1>
<div class="cb mt1">&nbsp;</div>

<a id="single_1" href="#abc" class="btn btn-small btn-success fr">
    <i class="icon-plus-sign icon-white"></i>Add Alert</a>

<div class="cb">&nbsp;</div>
<div id="node_form">
	<form:form method="post" action="/alerts" modelAttribute="user" class="main" id="new_node" >
	    <form:hidden path="email" />
        <form:hidden path="password" />
        <form:hidden path="enabled" />
        <form:hidden path="fullName" />
        <form:hidden path="alertConfs" />
        <form:hidden path="accounts" />
		<table id="nodes" class="data_table" cellspacing="0" cellpadding="0">
		    <thead>
		        <tr>
			        <th class="tal">Event Type</th>
			        <th class="tal">Is Send Email Immediately</th>
		        </tr>
		    </thead>
		    <tbody>
		        <c:forEach items="${user.alertConfs}" var="alertConf" varStatus="status">
			        <tr id="row_212" class="index_table_row">
			            <td id="name">
			             ${alertConf.eventType}
			             </td>
			             <td id="name">
			                 <input type="checkbox" name="alertConfs[${status.index}].isSendEmail" value="${alertConf.sendEmail}">
			             </td>
			        </tr>
		        </c:forEach>
		    </tbody>
		</table>
	    <br/>
	    <input type="submit" value="Save" />
	</form:form>
</div>

<div id="abc" style="display: none">
    <table width="100%" id="test_templates" class="data_table filterable" cellpadding="0" cellspacing="0">
        <thead>
            <tr>
                <th class="rt tal">Alert Type</th>
                <th class="rtr">&nbsp;</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="eventType" items="${eventTypes}">
                <tr id="template_854bd5d9-45a7-4fbf-bffe-c3c8e58ab06f">
                    <td id="description" class="vat">${eventType.description}</td>
                    <td class="action p0 w6">
                        <a href="/alerts/${eventType.name}" class="btn btn-mini btn-primary">Select</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
<script type="text/javascript">
$(document).ready(function() {
    $("#single_1").fancybox({
        helpers: {
            title : {
                type : 'float'
            }
        }
    });
    
    
    
});
</script>
</body>
</html>
