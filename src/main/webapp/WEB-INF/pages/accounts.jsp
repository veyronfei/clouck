<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta name="decorator" content="simple-theme" />
<title>Account Setting</title>
</head>
<body>
<div class="row-fluid">
    <div class="span3">
    </div>
    <div class="span9">
        <h4>Account Setting</h4>
		<table class="table table-striped table-hover">
			<thead>
				<tr>
					<th width="25%">Name</th>
					<th width="30%">Access Key Id</th>
					<th width="30%">Secret Access Key</th>
					<th width="15%"></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${accounts}" var="account">
				<tr>
					<td>${account.name}</td>
					<td>${account.accessKeyId}</td>
					<td>${account.secretAccessKey}</td>
					<td>
					   <button class="btn btn-danger" type="button" onclick="javascript:deleteAccount('${account.id}');"><i class="icon-trash"></i> Delete</button>
<%-- 			            <div class="btn-group">
							<a class="btn btn-primary" href="#"><i class="icon-user icon-white"></i></a>
							<a class="btn btn-primary dropdown-toggle" data-toggle="dropdown" href="#">
							    <span class="caret"></span>
							</a>
							<ul class="dropdown-menu">
								<li><a href="#" onclick="javascript:deleteAccount('${account.id}');"><i class="icon-trash"></i> Delete</a></li>
							</ul>
						</div> --%>
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<a href="javascript:addNewAccount(true)" class="btn btn-success">Add</a>
    </div>
</div>
</body>
</html>
