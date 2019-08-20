package com.bicsoft.sy.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bicsoft.sy.entity.GraiFore;
import com.bicsoft.sy.model.GraiExcelModel;

public class ExcelUtil {
     public static String readValue(Cell cell){
    	 String value = "";
		 if(cell==null){
			 return "";
		 }

		 int rowType=cell.getCellType();
		 if( rowType== Cell.CELL_TYPE_STRING){
			 value=cell.getStringCellValue();
		 }else if(rowType == Cell.CELL_TYPE_NUMERIC){
			 if(HSSFDateUtil.isCellDateFormatted(cell)){

			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			 String date=sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
			 value=date.toString();
		 }else{
			 Number v=cell.getNumericCellValue();
			 //如果是身份证(第三列)（因为身份证全是数字）则把小数部分去掉
			 if(cell.getColumnIndex()==3){
				 double va=Double.valueOf(v.toString());
				 int val=(int)va;
				 value=String.valueOf(val);
			 }else{
				 value=v.toString();
			 }

		 }
	 }

	 return value;
 }
    //读取单元格的数据,把一行行的数据保存到map中，用map来过滤重复身份证的数据
 public static List<GraiExcelModel> readExcelFile(String fileName){
     //创建对Excel工作薄文件的引用
	 HSSFWorkbook workbook = null;
	 FileInputStream in = null;
	 List<GraiExcelModel> list = new ArrayList<GraiExcelModel>();
	 try {
		 in = new FileInputStream(fileName);
		 Workbook wb;
		 if(fileName.endsWith(".xls")) {
			 //Excel2003
             wb = new HSSFWorkbook(in);
         } else {
        	 //Excel 2007
             wb = new XSSFWorkbook(in);
         }
		 //创建对工作表的引用
		 Sheet sheet= wb.getSheetAt(0);
		 //遍历所有单元格，读取单元格
		 int row_num=sheet.getLastRowNum();
		 //姓名和idNumber
		 String name = "";
		 String idNumber = "";
		 String head00 = readValue(sheet.getRow(0).getCell(0) );
		 String head01 = readValue(sheet.getRow(0).getCell(1) );
		 if( !"农户姓名".equals( head00 )  && !"身份证号".equals( head01 ) ){
			 return list;
		 }
		 for(int i = 1; i <= row_num; i++){
			 Row row = sheet.getRow(i);
			 //获取姓名
			 name = readValue(row.getCell(0));
			 idNumber = readValue( row.getCell(1) );

			 if( StringUtils.isNotEmpty( name ) && StringUtils.isNotEmpty( idNumber ) ){  //都不为空才获取
				 GraiExcelModel model = new GraiExcelModel();
				 model.setName( name );
				 model.setIdNumber( idNumber );

				 list.add( model );
			 }
		 }
	}catch (Exception e) {
		 e.printStackTrace();
	 }finally{
		 try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }
	 return list;
  }

 /**
  * 创建excel文档，
  * @param list 数据
  * @param keys list中map的key数组集合
  * @param columnNames excel的列名
  * */
 public static Workbook createWorkBook(List<GraiFore> list, String columnNames[]) {
     // 创建excel工作簿
     Workbook wb = new HSSFWorkbook();
     // 创建第一个sheet（页），并命名
     Sheet sheet = wb.createSheet("sheet1");
     // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
     for(int i = 0; i < columnNames.length; i++){
         sheet.setColumnWidth((short) i, (short) (35.7 * 150));
     }
     // 创建第一行
     Row row = sheet.createRow((short) 0);
     // 创建两种单元格格式
     CellStyle cs = wb.createCellStyle();
     CellStyle cs2 = wb.createCellStyle();

     // 创建两种字体
     Font f = wb.createFont();
     Font f2 = wb.createFont();

     // 创建第一种字体样式（用于列名）
     f.setFontHeightInPoints((short) 10);
     f.setColor(IndexedColors.BLACK.getIndex());
     f.setBoldweight(Font.BOLDWEIGHT_BOLD);

     // 创建第二种字体样式（用于值）
     f2.setFontHeightInPoints((short) 10);
     f2.setColor(IndexedColors.BLACK.getIndex());

     // 设置第一种单元格的样式（用于列名）
     cs.setFont(f);
     cs.setBorderLeft(CellStyle.BORDER_THIN);
     cs.setBorderRight(CellStyle.BORDER_THIN);
     cs.setBorderTop(CellStyle.BORDER_THIN);
     cs.setBorderBottom(CellStyle.BORDER_THIN);
     cs.setAlignment(CellStyle.ALIGN_CENTER);

     // 设置第二种单元格的样式（用于值）
     cs2.setFont(f2);
     cs2.setBorderLeft(CellStyle.BORDER_THIN);
     cs2.setBorderRight(CellStyle.BORDER_THIN);
     cs2.setBorderTop(CellStyle.BORDER_THIN);
     cs2.setBorderBottom(CellStyle.BORDER_THIN);
     cs2.setAlignment(CellStyle.ALIGN_CENTER);
     //设置列名
     for(int i=0;i<columnNames.length;i++){
         Cell cell = row.createCell(i);
         cell.setCellValue(columnNames[i]);
         cell.setCellStyle(cs);
     }
     DecimalFormat  fnum = new DecimalFormat("##0.00");
     //设置每行每列的值
     for (short i = 0; i < list.size(); i++) {
         // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
         // 创建一行，在页sheet上
         Row row1 = sheet.createRow((short) i+1);
         // 在row行上创建一个方格
         Cell cell = row1.createCell( 0 );
         cell.setCellValue( list.get(i).getYear() );
         cell.setCellStyle(cs2);

         cell = row1.createCell( 1 );
         cell.setCellValue( list.get(i).getCompanyName() );
         cell.setCellStyle(cs2);

         cell = row1.createCell( 2 );
         cell.setCellValue( list.get(i).getFarmerName());
         cell.setCellStyle(cs2);

         cell = row1.createCell( 3 );
         cell.setCellValue( list.get(i).getIdNumber());
         cell.setCellStyle(cs2);

         cell = row1.createCell( 4 );
         cell.setCellValue( fnum.format(list.get(i).getActualMu()) );
         cell.setCellStyle(cs2);

         cell = row1.createCell( 5 );
         cell.setCellValue( fnum.format(list.get(i).getMeasurementMu()) );
         cell.setCellStyle(cs2);

         cell = row1.createCell( 6 );
         cell.setCellValue( list.get(i).getMinEstimateTotalYield() + "~" + list.get(i).getMaxEstimateTotalYield());
         cell.setCellStyle(cs2);

         cell = row1.createCell( 7 );
         cell.setCellValue(  DateTimeUtil.getStringFromDate("yyyy-MM-dd" , list.get(i).getForecastDate()) );
         cell.setCellStyle(cs2);

         cell = row1.createCell( 8 );
         cell.setCellValue(   list.get(i).getZyLand() );
         cell.setCellStyle(cs2);
         if( "是".equals( list.get(i).getZyLand() ) ){
        	 cell = row1.createCell( 9 );
             cell.setCellValue(  list.get(i).getCompanyConnectName() );
             cell.setCellStyle(cs2);

             cell = row1.createCell( 10 );
             cell.setCellValue(  list.get(i).getCompanyConnectPhone() );
             cell.setCellStyle(cs2);
         }else{
        	 cell = row1.createCell( 9 );
        	 cell.setCellValue(  list.get(i).getCompanyConnectName() );
             cell.setCellStyle(cs2);

             cell = row1.createCell( 10 );
             cell.setCellValue(  list.get(i).getCompanyConnectPhone() );
             cell.setCellStyle(cs2);
         }
     }
     return wb;
 }
}