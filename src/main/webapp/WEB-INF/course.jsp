<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Belt Exam</title>

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bulma/0.7.1/css/bulma.min.css">

</head>
<body>

	<div class="container">
		<div >
			<h1 class="title">
				
				Create a new course
			</h1>
		</div>
		<div>
			<a href="/courses">Dashboard</a> | <a href="/logout">Logout</a>
		</div>
		
		

			<div class="columns">
				<div class="column">

					<form:form method="POST" action="/courses/new" modelAttribute="course">
						<table>
							<tr>
								<td><form:label path="name">Name:</form:label></td>
								<td><form:input path="name" class="input" /></td>
								<td><form:errors path="name" /></td>
							</tr>
								
							<tr>
								<td><form:label path="instructor">Instructor:</form:label></td>
								<td><form:input path="instructor" class="input" /></td>
								<td><form:errors path="instructor" /></td>
							</tr>
							<tr>
								<td><form:label path="capacity">Capacity:</form:label></td>
								<td><form:input path="capacity" class="input" type="number"/></td>
								<td><form:errors path="capacity" /></td>
							</tr>
						</table>
						
							<input type="submit" value="Create" class="button is-dark" />
						
					</form:form>

				</div>
				<div class="column"></div>
			</div>

		
		
		
	</div>
</body>
</html>