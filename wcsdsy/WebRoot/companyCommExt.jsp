<c:if test="${sessionScope.isCompanyUser}">
	<simple:select id="companyCode" name="companyCode" entityName="Company" width="187" value="${sessionScope.user.companyCode}" 
	hasPleaseSelectOption="${!sessionScope.isCompanyUser}"
	readOnly="${sessionScope.isCompanyUser}" 
	canEdit="${!sessionScope.isCompanyUser}"/>
</c:if>
<c:if test="${!sessionScope.isCompanyUser}">
	<simple:select
			id="companyCode" 
			name="companyCode" 
			codeKey="02" 
			entityName="Company"
			cssClass="easyui-combobox"
			readOnly="false"
			hasPleaseSelectOption="TRUE"
			value="${companyCode}" 
			width="187"
			height="20"
			canEdit="true"/>
</c:if>