function addRow(tableId){
	var testTbl = $("#" + tableId);
	//添加一行
	var newTr = testTbl.insertRow();
	//添加两列
	var newTd0 = newTr.insertCell();
	var newTd1 = newTr.insertCell();
	//设置列内容和属性
	newTd0.innerHTML = '<input type=checkbox id="box4">';
	newTd2.innerText= '新加行';
}

function addSignRow(){
	var txtTRLastIndex = findObj("txtTRLastIndex",document);
	var rowID = parseInt(txtTRLastIndex.value);

	var signFrame = findObj("SignFrame",document);
	//添加行
	var newTR = signFrame.insertRow(signFrame.rows.length);
	newTR.id = "SignItem" + rowID;

	//添加列:序号
	var newNameTD=newTR.insertCell(0);
	//添加列内容
	newNameTD.innerHTML = newTR.rowIndex.toString();
}

function findObj(theObj, theDoc){ 
	var p, i, foundObj; 
	if(!theDoc) theDoc = document; 
	if( (p = theObj.indexOf("?")) > 0 && parent.frames.length){    
		theDoc = parent.frames[theObj.substring(p+1)].document;    
		theObj = theObj.substring(0,p); 
	} 
	if(!(foundObj = theDoc[theObj]) && theDoc.all) 
		foundObj = theDoc.all[theObj]; 
	for (i=0; !foundObj && i < theDoc.forms.length; i++)     
		foundObj = theDoc.forms[i][theObj]; 
	for(i=0; !foundObj && theDoc.layers && i < theDoc.layers.length; i++)     
		foundObj = findObj(theObj,theDoc.layers[i].document); 
	if(!foundObj && document.getElementById) 
		foundObj = document.getElementById(theObj);
	
	return foundObj;
}