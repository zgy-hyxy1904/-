<select name="monitorPointCode" class="easyui-combobox" style="width:170px;" data-options="editable:false">
<%
	java.util.List<MoniPoint> list = (List<MoniPoint>)request.getAttribute("pointList");
	
	String _monitorPointCode = (String)request.getAttribute("monitorPointCode");
	System.out.println("code:" + _monitorPointCode); 
	for( MoniPoint point : list ){
%>
	<option value="<%=point.getMonitorPointCode()%>" <%if( _monitorPointCode != null && _monitorPointCode.equals(point.getMonitorPointCode())) {out.println("selected=selected");}%>><%=point.getMonitorPointName()%></option>
<%		
	}
%>
</select>