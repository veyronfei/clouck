<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@include file="header.jspf"%>
<title>Clouck - <decorator:title /></title>
<decorator:head />
</head>

<body <decorator:getProperty property="body.id" writeEntireProperty="true"/>>

<decorator:body />

</body>
</html>