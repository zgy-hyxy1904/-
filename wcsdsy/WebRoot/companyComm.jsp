<%--
    company dict
 --%>
<c:if test="${sessionScope.isCompanyUser}">
	<simple:select id="companyCode" name="companyCode" entityName="Company" width="187" value="${sessionScope.user.companyCode}" 
	hasPleaseSelectOption="${!sessionScope.isCompanyUser}"
	readOnly="${sessionScope.isCompanyUser}" 
	canEdit="${!sessionScope.isCompanyUser}"/>
</c:if>
<c:if test="${!sessionScope.isCompanyUser}">
	<simple:select id="companyCode" name="companyCode" hasPleaseSelectOption="true" value="${companyCode}" entityName="Company" width="187" canEdit="true"/>
</c:if>
