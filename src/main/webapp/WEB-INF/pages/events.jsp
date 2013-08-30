<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="main-theme" />
<meta name="menu" content="events"/>
<title>Events</title>
</head>
<body>
<div class="row">
	<div class="span12">
		<div class="view-header">
			<h1>
				Events: <span class="light">Stream</span>
				<a class="btn btn-info" href="#" style="float: right; font-weight: normal"> Manage Events </a>
			</h1>
		</div>
	</div>
</div>
<div class="row">
	<div class="span12">
		<div class="temp-1col">
			<div class="tab-content">
				<div class="tab-pane active" id="stream_list">
					<table class="table table-striped">
						<caption>this is th event table</caption>
						<thead>
							<tr>
								<th></th>
								<th>Id</th>
								<th>Event</th>
								<th>Date</th>
							</tr>
						</thead>
						<tbody>
	                        <c:forEach var="event" items="${events}">
							<tr>
								<td><img src="/images/${event.resourceType}.gif" style="margin-left: 20px; margin-top: 0px;"></td>
								<td><a href="/resources/volumes/${event.resourceId}/accounts/${currentAccount.id}">${event.uniqueId}</a></td>
								<td>${event.message}</td>
								<td>${event.timeDetected}</td>
							</tr>
	                        </c:forEach>
						</tbody>
					</table>

						<div class="pagination">
							<ul>
								<li class="disabled"><a href="#">Prev</a></li>
								<li class="active"><a href="#">1</a></li>
								<li><a href="#">2</a></li>
								<li><a href="#">3</a></li>
								<li><a href="#">4</a></li>
								<li><a href="#">5</a></li>
								<li><a href="#">Next</a></li>
							</ul>
						</div>



						<div class="events-list-container">
                    <ul class="events-list">
						<li class="event-events-page">
							<div>${event.uniqueId} </div>
                            <div class="event-body-events-page">
                                <div class="event-message">${event.message}</div>
                                <div class="event-timestamp"></div>
                            </div>
                        </li>
                    </ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="row">
    <div class="span12">
        <div style="height: 25px;"></div>
    </div>
</div>
</body>
</html>