<!-- vtf-40 -->

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${not empty deliveryMethods }">
	<div class="form-group">
		<c:forEach items="${deliveryMethods}" var="deliveryMethod">

			<table>
				<tr>
					<th>${deliveryMethod.name}</th>
				</tr>
				
				<tr>
					<td>${deliveryMethod.description}</td>
					<td>-----  ${deliveryMethod.deliveryCost.formattedValue}</td>
				</tr>
			</table>
		</c:forEach>
	</div>
</c:if>
