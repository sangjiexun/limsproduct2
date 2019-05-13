/*
 * 文件名：JsGridReportBase.java
 * 版权：
 * 描述：
 * 修改人：
 * 修改时间：
 * 修改内容：
 */
package excelTools;

import net.zjcclims.domain.SchoolClasses;
import net.zjcclims.domain.TimetableAppointmentSameNumber;
import net.zjcclims.domain.TimetableLabRelated;
import net.zjcclims.domain.TimetableTeacherRelated;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
public class JsGridReportBase {
	public SimpleDateFormat timeFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	private HttpServletResponse response;

	private HttpSession session;

	private ServletOutputStream out;

	private InputStream downloadFile;

	/**
	 * 大数据量导出静态变量
	 */
	public static int EXCEL_MAX_CNT = 100000; //每个excel文件多少条数据
	public static int SHEET_MAX_CNT = 20000; //每个sheet多少条数据

	public JsGridReportBase() {
	}

	public JsGridReportBase(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.response = response;
		session = request.getSession();
		init(this.session);
	}

	private void init(HttpSession session) throws Exception {
		out = response.getOutputStream();
	}

	/**
	 * 向浏览器输出JSON数据
	 *
	 * @param
	 * @return void
	 */
	public void outDataToBrowser(TableData tableData) {
		StringBuffer outData = new StringBuffer();

		// 向前台输出数据
		outData.append("{pageInfo: {totalRowNum: " + tableData.getTotalRows()
				+ "},");
		outData.append("data: [");
		boolean isFirst = true;

		TableHeaderMetaData headerMetaData = tableData.getTableHeader();
		List<TableDataRow> dataRows = tableData.getRows();
		try {
			for (TableDataRow dataRow : dataRows) {
				List<TableDataCell> dataCells = dataRow.getCells();
				int size = dataCells.size();
				if (!isFirst) {
					outData.append(",{");
					for (int i = 0; i < size; i++) {
						outData.append(headerMetaData.getColumnAt(i).getId()
								+ ": '" + dataCells.get(i).getValue() + "',");
					}
					int index = outData.lastIndexOf(",");
					outData.deleteCharAt(index);
					outData.append("}");
				} else {
					outData.append("{");
					for (int i = 0; i < size; i++) {
						outData.append(headerMetaData.getColumnAt(i).getId()
								+ ": '" + dataCells.get(i).getValue() + "',");
					}
					int index = outData.lastIndexOf(",");
					outData.deleteCharAt(index);
					outData.append("}");
					isFirst = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		outData.append("]");
		outData.append("}");

		try {
			out.print(outData.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @param
	 * @return void
	 */
	public void stopGrouping(HSSFSheet sheet, HashMap<Integer, String> word,
			HashMap<Integer, Integer> counter, int i, int size, int rownum,
			HSSFCellStyle style) {
		String w = word.get(i);
		if (w != null) {
			int len = counter.get(i);
			CellRangeAddress address = new CellRangeAddress(rownum - len,
					rownum - 1, i, i);
			sheet.addMergedRegion(address);
			fillMergedRegion(sheet, address, style);
			word.remove(i);
			counter.remove(i);
		}
		if (i + 1 < size) {
			stopGrouping(sheet, word, counter, i + 1, size, rownum, style);
		}
	}

	/**
	 *
	 * @param
	 * @return void
	 */
	public void generateColumn(HSSFSheet sheet, TableColumn tc, int maxlevel,
			int rownum, int colnum, HSSFCellStyle headerstyle) {
		HSSFRow row = sheet.getRow(rownum);
		if (row == null)
			row = sheet.createRow(rownum);

		HSSFCell cell = row.createCell(colnum);
		cell.setCellValue(tc.getDisplay());

		if (headerstyle != null)
			cell.setCellStyle(headerstyle);
		if (tc.isComplex()) {
			CellRangeAddress address = new CellRangeAddress(rownum, rownum,
					colnum, colnum + tc.getLength() - 1);
			sheet.addMergedRegion(address);
			fillMergedRegion(sheet, address, headerstyle);

			int cn = colnum;
			for (int i = 0; i < tc.getChildren().size(); i++) {
				if (i != 0) {
					cn = cn + tc.getChildren().get(i - 1).getLength();
				}
				generateColumn(sheet, tc.getChildren().get(i), maxlevel,
						rownum + 1, cn, headerstyle);
			}
		} else {
			CellRangeAddress address = new CellRangeAddress(rownum, rownum
					+ maxlevel - tc.level, colnum, colnum);
			sheet.addMergedRegion(address);
			fillMergedRegion(sheet, address, headerstyle);
		}
		sheet.autoSizeColumn(colnum, true);
	}

	/**
	 *
	 * @param
	 * @return void
	 */
	private void fillMergedRegion(HSSFSheet sheet, CellRangeAddress address,
			HSSFCellStyle style) {
		for (int i = address.getFirstRow(); i <= address.getLastRow(); i++) {
			HSSFRow row = sheet.getRow(i);
			if (row == null)
				row = sheet.createRow(i);
			for (int j = address.getFirstColumn(); j <= address.getLastColumn(); j++) {
				HSSFCell cell = row.getCell(j);
				if (cell == null) {
					cell = row.createCell(j);
					if (style != null)
						cell.setCellStyle(style);
				}
			}
		}
	}

	/**
	 * 写入工作表
	 * @param wb Excel工作簿
	 * @param title Sheet工作表名称
	 * @param styles 表头样式
	 * @param creator 创建人
	 * @param tableData 表格数据
	 * @throws Exception
	 */
	public HSSFWorkbook writeSheet(HSSFWorkbook wb, String title, HashMap<String, HSSFCellStyle> styles, String creator, TableData tableData) throws Exception {

		TableHeaderMetaData headerMetaData = tableData.getTableHeader();// 获得HTML的表头元素

		SimpleDateFormat formater = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
		String create_time = formater.format(new Date());

		HSSFSheet sheet = wb.createSheet(title);// 在Excel工作簿中建一工作表
		sheet.setDisplayGridlines(false);// 设置表标题是否有表格边框

		//创建标题
		HSSFRow row = sheet.createRow(0);// 创建新行
		HSSFCell cell = row.createCell(0);// 创建新列
		int rownum = 0;
		cell.setCellValue(new HSSFRichTextString(title));
		HSSFCellStyle style = styles.get("TITLE");//设置标题样式
		if (style != null)
			cell.setCellStyle(style);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headerMetaData
				.getColumnCount() - 1));//合并标题行：起始行号，终止行号， 起始列号，终止列号

		//创建副标题
		row = sheet.createRow(1);
		cell = row.createCell(0);
		cell.setCellValue(new HSSFRichTextString("创建人:"));
		style = styles.get("SUB_TITLE");
		if (style != null)
			cell.setCellStyle(style);

		cell = row.createCell(1);
		cell.setCellValue(new HSSFRichTextString(creator));
		style = styles.get("SUB_TITLE2");
		if (style != null)
			cell.setCellStyle(style);

		cell = row.createCell(2);
		cell.setCellValue(new HSSFRichTextString("创建时间:"));
		style = styles.get("SUB_TITLE");
		if (style != null)
			cell.setCellStyle(style);

		cell = row.createCell(3);
		style = styles.get("SUB_TITLE2");
		cell.setCellValue(new HSSFRichTextString(create_time));
		if (style != null)
			cell.setCellStyle(style);

		rownum = 3;// 如果rownum = 1，则去掉创建人、创建时间等副标题；如果rownum = 0， 则把标题也去掉

		HSSFCellStyle headerstyle = styles.get("TABLE_HEADER");

		int colnum = 0;
		for (int i = 0; i < headerMetaData.getOriginColumns().size(); i++) {
			TableColumn tc = headerMetaData.getOriginColumns().get(i);
			if (i != 0) {
				colnum += headerMetaData.getOriginColumns().get(i - 1)
						.getLength();
			}
			generateColumn(sheet, tc, headerMetaData.maxlevel, rownum, colnum,
					headerstyle);
		}
		rownum += headerMetaData.maxlevel;

		List<TableDataRow> dataRows = tableData.getRows();

		HashMap<Integer, Integer> counter = new HashMap<Integer, Integer>();
		HashMap<Integer, String> word = new HashMap<Integer, String>();
		int index = 0;
		for (TableDataRow dataRow : dataRows) {
			row = sheet.createRow(rownum);

			List<TableDataCell> dataCells = dataRow.getCells();
			int size = headerMetaData.getColumns().size();
			index = -1;
			for (int i = 0; i < size; i++) {
				TableColumn tc = headerMetaData.getColumns().get(i);
				if (!tc.isVisible())
					continue;
				index++;

				String value = dataCells.get(i).getValue();
				if (tc.isGrouped()) {
					String w = word.get(index);
					if (w == null) {
						word.put(index, value);
						counter.put(index, 1);
						createCell(row, tc, dataCells, i, index, styles);
					} else {
						if (w.equals(value)) {
							counter.put(index, counter.get(index) + 1);
						} else {
							stopGrouping(sheet, word, counter, index, size,
									rownum, styles.get("STRING"));

							word.put(index, value);
							counter.put(index, 1);
							createCell(row, tc, dataCells, i, index, styles);
						}
					}
				} else {
					createCell(row, tc, dataCells, i, index, styles);
				}
			}
			rownum++;
		}

		stopGrouping(sheet, word, counter, 0, index, rownum, styles
				.get("STRING"));
		// 设置前两列根据数据自动列宽
		for (int c = 0; c < headerMetaData.getColumns().size(); c++) {
			sheet.autoSizeColumn((short) c);
			String t = headerMetaData.getColumns().get(c).getDisplay();
			if(sheet.getColumnWidth(c)<t.length()*256*3)
				sheet.setColumnWidth(c, t.length()*256*3);
		}
		sheet.setGridsPrinted(true);

		return wb;
	}

	/**
	 * 写入工作表
	 * @param wb Excel工作簿
	 * @param title Sheet工作表名称
	 * @param styles 表头样式
	 * @param creator 创建人
	 * @param tableData 表格数据
	 * @throws Exception
	 */
	public HSSFWorkbook writeSheet(HSSFWorkbook wb, HashMap<String, HSSFCellStyle> styles, String creator, List<TableData> tableDataLst) throws Exception {

		SimpleDateFormat formater = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
		String create_time = formater.format(new Date());

		int cnt = 1;
		for(TableData tableData : tableDataLst){
			String sheetTitle = tableData.getSheetTitle();
			sheetTitle = sheetTitle==null||sheetTitle.equals("")?"sheet"+cnt:sheetTitle;
			cnt++;

			TableHeaderMetaData headerMetaData = tableData.getTableHeader();// 获得HTML的表头元素
			HSSFSheet sheet = wb.createSheet(sheetTitle);// 在Excel工作簿中建一工作表
			sheet.setDisplayGridlines(false);// 设置表标题是否有表格边框
			wb.cloneSheet(0);

			//创建标题
			HSSFRow row = sheet.createRow(0);// 创建新行
			HSSFCell cell = row.createCell(0);// 创建新列
			int rownum = 0;
			cell.setCellValue(new HSSFRichTextString(sheetTitle));
			HSSFCellStyle style = styles.get("TITLE");//设置标题样式
			if (style != null)
				cell.setCellStyle(style);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headerMetaData
					.getColumnCount() - 1));//合并标题行：起始行号，终止行号， 起始列号，终止列号

			//创建副标题
			row = sheet.createRow(1);
			cell = row.createCell(0);
			cell.setCellValue(new HSSFRichTextString("创建人:"));
			style = styles.get("SUB_TITLE");
			if (style != null)
				cell.setCellStyle(style);

			cell = row.createCell(1);
			cell.setCellValue(new HSSFRichTextString(creator));
			style = styles.get("SUB_TITLE2");
			if (style != null)
				cell.setCellStyle(style);

			cell = row.createCell(2);
			cell.setCellValue(new HSSFRichTextString("创建时间:"));
			style = styles.get("SUB_TITLE");
			if (style != null)
				cell.setCellStyle(style);

			cell = row.createCell(3);
			style = styles.get("SUB_TITLE2");
			cell.setCellValue(new HSSFRichTextString(create_time));
			if (style != null)
				cell.setCellStyle(style);

			rownum = 3;// 如果rownum = 1，则去掉创建人、创建时间等副标题；如果rownum = 0， 则把标题也去掉

			HSSFCellStyle headerstyle = styles.get("TABLE_HEADER");

			int colnum = 0;
			for (int i = 0; i < headerMetaData.getOriginColumns().size(); i++) {
				TableColumn tc = headerMetaData.getOriginColumns().get(i);
				if (i != 0) {
					colnum += headerMetaData.getOriginColumns().get(i - 1).getLength();
				}
				generateColumn(sheet, tc, headerMetaData.maxlevel, rownum, colnum, headerstyle);
			}
			rownum += headerMetaData.maxlevel;

			List<TableDataRow> dataRows = tableData.getRows();

			int index = 0;
			for (TableDataRow dataRow : dataRows) {
				row = sheet.createRow(rownum);

				List<TableDataCell> dataCells = dataRow.getCells();
				int size = headerMetaData.getColumns().size();
				index = -1;
				for (int i = 0; i < size; i++) {
					TableColumn tc = headerMetaData.getColumns().get(i);
					if (!tc.isVisible())
						continue;
					index++;

					createCell(row, tc, dataCells, i, index, styles);
				}
				rownum++;
			}
			// 设置前两列根据数据自动列宽
			for (int c = 0; c < headerMetaData.getColumns().size(); c++) {
				sheet.autoSizeColumn((short) c);
				String t = headerMetaData.getColumns().get(c).getDisplay();
				if(sheet.getColumnWidth(c)<t.length()*256*3)
					sheet.setColumnWidth(c, t.length()*256*3);
			}
			sheet.setGridsPrinted(true);
		}

		return wb;
	}

	/**
	 * 导出Excel(单工作表)
	 *
	 * @param title
	 *            文件名
	 * @param creator
	 *            创建人
	 * @param tableData
	 *            表格数据
	 * @return void <style name="dataset"> case SYSROWNUM%2==0?#row0:#row1;
	 *         fontsize:9px; </style> <style name="row0"> import(parent);
	 *         bgcolor:#FFFFFF; </style> <style name="row1"> import(parent);
	 *         bgcolor:#CAEAFE; </style>
	 */
	public void exportToExcel(String title, String creator, TableData tableData)
			throws Exception {

		HSSFWorkbook wb = new HSSFWorkbook();// 创建新的Excel 工作簿

		HashMap<String, HSSFCellStyle> styles = initStyles(wb);// 根据模板文件，初始化表头样式

		wb = writeSheet(wb,title,styles,creator,tableData);//写入工作表

		String sFileName = title + ".xls";
		response.setHeader("Content-Disposition", "attachment;filename="
				.concat(String.valueOf(URLEncoder.encode(sFileName, "UTF-8"))));
		response.setHeader("Connection", "close");
		response.setHeader("Content-Type", "application/vnd.ms-excel");

		wb.write(response.getOutputStream());
	}

	/**
	 * 导出Excel(多工作表)
	 *
	 * @param title
	 *            文件名
	 * @param creator
	 *            创建人
	 * @param tableDataLst
	 *            各工作格数据(注意：每个tableData要设置sheet名称，否则按默认呈现)
	 * @return void <style name="dataset"> case SYSROWNUM%2==0?#row0:#row1;
	 *         fontsize:9px; </style> <style name="row0"> import(parent);
	 *         bgcolor:#FFFFFF; </style> <style name="row1"> import(parent);
	 *         bgcolor:#CAEAFE; </style>
	 */
	public void exportToExcel(String title, String creator, List<TableData> tableDataLst)
		throws Exception {

		HSSFWorkbook wb = new HSSFWorkbook();// 创建新的Excel 工作簿
		HashMap<String, HSSFCellStyle> styles = initStyles(wb);// 初始化表头样式

		int i = 1;
		for(TableData tableData : tableDataLst){
			String sheetTitle = tableData.getSheetTitle();
			sheetTitle = sheetTitle==null||sheetTitle.equals("")?"sheet"+i:sheetTitle;
			wb = writeSheet(wb,tableData.getSheetTitle(),styles,creator,tableData);//写入工作表
			i++;
		}

		String sFileName = title + ".xls";
		response.setHeader("Content-Disposition", "attachment;filename="
				.concat(String.valueOf(URLEncoder.encode(sFileName, "UTF-8"))));
		response.setHeader("Connection", "close");
		response.setHeader("Content-Type", "application/vnd.ms-excel");

		wb.write(response.getOutputStream());
	}

	/**
	 * 创建单元格（带隔行背景色）
	 *
	 * @param
	 * @return void
	 */
	public void createCell(HSSFRow row, TableColumn tc,
			List<TableDataCell> data, int i, int index,
			HashMap<String, HSSFCellStyle> styles) {
		TableDataCell dc = data.get(i);
		HSSFCell cell = row.createCell(index);
		switch (tc.getColumnType()) {
		case TableColumn.COLUMN_TYPE_INTEGER:
			cell.setCellValue(dc.getIntValue());
			HSSFCellStyle style = styles.get("INT");
			if (row.getRowNum() % 2 != 0)
				style = styles.get("INT_C");
			if (style != null)
				cell.setCellStyle(style);
			break;
		case TableColumn.COLUMN_TYPE_FLOAT_2:
			cell.setCellValue(dc.getDoubleValue());
			style = styles.get("D2");
			if (row.getRowNum() % 2 != 0)
				style = styles.get("D2_C");
			if (style != null)
				cell.setCellStyle(style);
			break;
		case TableColumn.COLUMN_TYPE_FLOAT_3:
			cell.setCellValue(dc.getDoubleValue());
			style = styles.get("D3");
			if (row.getRowNum() % 2 != 0)
				style = styles.get("D3_C");
			if (style != null)
				cell.setCellStyle(style);
			break;
		case TableColumn.COLUMN_TYPE_RED_BG:
			cell.setCellValue(dc.getValue());
			style = styles.get("RED_BG");
			if (style != null)
				cell.setCellStyle(style);
			break;
		case TableColumn.COLUMN_TYPE_YELLOW_BG:
			cell.setCellValue(dc.getValue());
			style = styles.get("YELLOW_BG");
			if (style != null)
				cell.setCellStyle(style);
			break;
		case TableColumn.COLUMN_TYPE_GREEN_BG:
			cell.setCellValue(dc.getValue());
			style = styles.get("GREEN_BG");
			if (style != null)
				cell.setCellStyle(style);
			break;
		default:
			if (dc.getValue().equalsIgnoreCase("&nbsp;"))
				cell.setCellValue("");
			else
				cell.setCellValue(dc.getValue());
			style = styles.get("STRING");
			if (row.getRowNum() % 2 != 0)
				style = styles.get("STRING_C");
			if (style != null)
				cell.setCellStyle(style);
		}
	}

	/**
	 * 根据模板初始化样式
	 * 		注意：module.xls模板文件跟该类同一路径
	 * @param wb
	 * @return
	 */
	public HashMap<String, HSSFCellStyle> initStyles(HSSFWorkbook wb) {
		HashMap<String, HSSFCellStyle> ret = new HashMap<String, HSSFCellStyle>();
		try {
			ClassPathResource res = new ClassPathResource("module.xls", this.getClass());//注意：module.xls模板文件跟该类同一路径
			InputStream is = res.getInputStream();
			POIFSFileSystem fs = new POIFSFileSystem(is);

			HSSFWorkbook src = new HSSFWorkbook(fs);
			HSSFSheet sheet = src.getSheetAt(0);

			buildStyle(wb, src, sheet, 0, ret, "TITLE");//标题样式
			buildStyle(wb, src, sheet, 1, ret, "SUB_TITLE");//副标题样式
			buildStyle(wb, src, sheet, 2, ret, "SUB_TITLE2");//副标题2样式

			buildStyle(wb, src, sheet, 4, ret, "TABLE_HEADER");//表头样式
			buildStyle(wb, src, sheet, 5, ret, "STRING");//字符串单元格样式
			buildStyle(wb, src, sheet, 6, ret, "INT");//整数单元格样式
			buildStyle(wb, src, sheet, 7, ret, "D2");//2位小数单元格样式
			buildStyle(wb, src, sheet, 8, ret, "D3");//3位小数单元格样式

			buildStyle(wb, src, sheet, 10, ret, "STRING_C");//字符串单元格样式（带背景色）
			buildStyle(wb, src, sheet, 11, ret, "INT_C");//整数单元格样式（带背景色）
			buildStyle(wb, src, sheet, 12, ret, "D2_C");//2位小数单元格样式（带背景色）
			buildStyle(wb, src, sheet, 13, ret, "D3_C");//3位小数单元格样式（带背景色）

			buildStyle(wb, src, sheet, 15, ret, "RED_BG");//红色单元格背景
			buildStyle(wb, src, sheet, 16, ret, "YELLOW_BG");//黄色单元格背景
			buildStyle(wb, src, sheet, 17, ret, "GREEN_BG");//绿色单元格背景
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 *
	 * @param
	 * @return void
	 */
	private void buildStyle(HSSFWorkbook wb, HSSFWorkbook src, HSSFSheet sheet,
			int index, HashMap<String, HSSFCellStyle> ret, String key) {
		HSSFRow row = sheet.getRow(index);
		HSSFCell cell = row.getCell(1);
		HSSFCellStyle nstyle = wb.createCellStyle();
		ExcelUtils.copyCellStyle(wb, nstyle, src, cell.getCellStyle());
		ret.put(key, nstyle);
	}

	/**
	 * 工具方法，将一个字符串转换为UTF-8编码
	 *
	 * @param string
	 *            需要转换的字符串
	 * @return String 转换后的UTF-8字符串
	 */
	protected String getUTF8String(String string) {
		if (string == null) {
			return null;
		} else {
			try {
				String str = new String(string.getBytes("ISO8859-1"), "UTF-8");
				return str;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return string;
			}
		}
	}

	/**
	 * 工具方法，将一个字符串转换为GBK编码
	 *
	 * @param string
	 *            需要转换的字符串
	 * @return String 转换后的GBK字符串
	 */
	protected String getGBKString(String string) {
		if (string == null) {
			return null;
		} else {
			try {
				String str = new String(string.getBytes("ISO8859-1"), "GBK");
				return str;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return string;
			}
		}
	}

	/**
	 * 单元格值为空处理
	 *
	 * @param value
	 *            单元格值
	 * @return String 如果单元格值为空，则返回"&nbsp;"，否则返回原值
	 */
	public String fieldRender(String value) {
		if (value == null) {
			return "&nbsp;";
		} else {
			return value;
		}
	}
	/**
	 * 改写，新加参数导出信息info
	 */
	public void exportExcel(String title, String creator, String info, TableData tableData)
			throws Exception {

		HSSFWorkbook wb = new HSSFWorkbook();// 创建新的Excel 工作簿

		HashMap<String, HSSFCellStyle> styles = initStyles(wb);// 根据模板文件，初始化表头样式

		wb = writeSheets(wb,title,info,styles,creator,tableData);//写入工作表

		String sFileName = title + ".xls";
		response.setHeader("Content-Disposition", "attachment;filename="
				.concat(String.valueOf(URLEncoder.encode(sFileName, "UTF-8"))));
		response.setHeader("Connection", "close");
		response.setHeader("Content-Type", "application/vnd.ms-excel");

		wb.write(response.getOutputStream());
	}
	/**
	 * @Description 改写，实验教学计划表导出excel
	 * @data 2019-05-13
	 * @author 刘博越
	 */
	public void exportExcelForTeachPlan(String title,String schoolTermName, String creator, String info1,String info2,String info3,String info4,String str1,
							String str2,String str3,String str4,String str5,String str6,String str7,String str8,String str9,String str10,String str11,TableData tableData1,TableData tableData2,
							String courseName,String className, String judgeDate)
			throws Exception {

		HSSFWorkbook wb = new HSSFWorkbook();// 创建新的Excel 工作簿

		HashMap<String, HSSFCellStyle> styles = initStyles(wb);// 根据模板文件，初始化表头样式

		wb = writeSheetsForTeachPlan(wb,title,info1,info2,info3,info4,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10,str11,styles,schoolTermName,creator,tableData1,tableData2,courseName,className,judgeDate);//写入工作表

		creator = creator.replaceAll(" ","");
		String sFileName = courseName+"_"+creator+"_"+className+".xls";
		response.setHeader("Content-Disposition", "attachment;filename="
				.concat(String.valueOf(URLEncoder.encode(sFileName, "UTF-8"))));
		response.setHeader("Connection", "close");
		response.setHeader("Content-Type", "application/vnd.ms-excel");

		wb.write(response.getOutputStream());
	}
	/**
	 * 写入工作表
	 * @param wb Excel工作簿
	 * @param title Sheet工作表名称
	 * @param styles 表头样式
	 * @param creator 创建人
	 * @param tableData 表格数据
	 * @throws Exception
	 */
	public HSSFWorkbook writeSheets(HSSFWorkbook wb, String title,String info, HashMap<String, HSSFCellStyle> styles, String creator, TableData tableData) throws Exception {

		TableHeaderMetaData headerMetaData = tableData.getTableHeader();// 获得HTML的表头元素

		SimpleDateFormat formater = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
		String create_time = formater.format(new Date());

		HSSFSheet sheet = wb.createSheet(title);// 在Excel工作簿中建一工作表
		sheet.setDisplayGridlines(false);// 设置表标题是否有表格边框

		//创建标题
		HSSFRow row = sheet.createRow(0);// 创建新行
		HSSFCell cell = row.createCell(0);// 创建新列
		int rownum = 0;
		cell.setCellValue(new HSSFRichTextString(title));
		HSSFCellStyle style = styles.get("TITLE");//设置标题样式
		if (style != null)
			cell.setCellStyle(style);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headerMetaData
				.getColumnCount() - 1));//合并标题行：起始行号，终止行号， 起始列号，终止列号

		//创建副标题
		row = sheet.createRow(1);
		cell = row.createCell(0);
		cell.setCellValue(new HSSFRichTextString("创建人:"));
		style = styles.get("SUB_TITLE");
		if (style != null)
			cell.setCellStyle(style);

		cell = row.createCell(1);
		cell.setCellValue(new HSSFRichTextString(creator));
		style = styles.get("SUB_TITLE2");
		if (style != null)
			cell.setCellStyle(style);

		cell = row.createCell(2);
		cell.setCellValue(new HSSFRichTextString("创建时间:"));
		style = styles.get("SUB_TITLE");
		if (style != null)
			cell.setCellStyle(style);

		cell = row.createCell(3);
		style = styles.get("SUB_TITLE2");
		cell.setCellValue(new HSSFRichTextString(create_time));
		if (style != null)
			cell.setCellStyle(style);

		cell = row.createCell(4);
		cell.setCellValue(new HSSFRichTextString("信息:"));
		style = styles.get("SUB_TITLE");
		if (style != null)
			cell.setCellStyle(style);

		cell = row.createCell(5);
		style = styles.get("SUB_TITLE2");
		cell.setCellValue(new HSSFRichTextString(info));
		if (style != null)
			cell.setCellStyle(style);

		rownum = 3;// 如果rownum = 1，则去掉创建人、创建时间等副标题；如果rownum = 0， 则把标题也去掉

		HSSFCellStyle headerstyle = styles.get("TABLE_HEADER");

		int colnum = 0;
		for (int i = 0; i < headerMetaData.getOriginColumns().size(); i++) {
			TableColumn tc = headerMetaData.getOriginColumns().get(i);
			if (i != 0) {
				colnum += headerMetaData.getOriginColumns().get(i - 1)
						.getLength();
			}
			generateColumn(sheet, tc, headerMetaData.maxlevel, rownum, colnum,
					headerstyle);
		}
		rownum += headerMetaData.maxlevel;

		List<TableDataRow> dataRows = tableData.getRows();

		HashMap<Integer, Integer> counter = new HashMap<Integer, Integer>();
		HashMap<Integer, String> word = new HashMap<Integer, String>();
		int index = 0;
		for (TableDataRow dataRow : dataRows) {
			row = sheet.createRow(rownum);

			List<TableDataCell> dataCells = dataRow.getCells();
			int size = headerMetaData.getColumns().size();
			index = -1;
			for (int i = 0; i < size; i++) {
				TableColumn tc = headerMetaData.getColumns().get(i);
				if (!tc.isVisible())
					continue;
				index++;

				String value = dataCells.get(i).getValue();
				if (tc.isGrouped()) {
					String w = word.get(index);
					if (w == null) {
						word.put(index, value);
						counter.put(index, 1);
						createCell(row, tc, dataCells, i, index, styles);
					} else {
						if (w.equals(value)) {
							counter.put(index, counter.get(index) + 1);
						} else {
							stopGrouping(sheet, word, counter, index, size,
									rownum, styles.get("STRING"));

							word.put(index, value);
							counter.put(index, 1);
							createCell(row, tc, dataCells, i, index, styles);
						}
					}
				} else {
					createCell(row, tc, dataCells, i, index, styles);
				}
			}
			rownum++;
		}

		stopGrouping(sheet, word, counter, 0, index, rownum, styles
				.get("STRING"));
		// 设置前两列根据数据自动列宽
		for (int c = 0; c < headerMetaData.getColumns().size(); c++) {
			sheet.autoSizeColumn((short) c);
			String t = headerMetaData.getColumns().get(c).getDisplay();
			if(sheet.getColumnWidth(c)<t.length()*256*3){
				sheet.setColumnWidth(c, t.length()*256*3);
			}
			if(c==1){
				sheet.setColumnWidth(c, 7700);
			}
		}
		sheet.setGridsPrinted(true);

		return wb;
	}
	/**
	 * 写入工作表
	 * @param wb Excel工作簿
	 * @param title Sheet工作表名称
	 * @param styles 表头样式
	 * @param creator 创建人
	 * @param tableData1/tableData2 表格数据
	 * @throws Exception
	 */
	public HSSFWorkbook writeSheetsForTeachPlan(HSSFWorkbook wb, String title,String info1,String info2,String info3,String info4,String str1,
									String str2,String str3,String str4,String str5,String str6,String str7,String str8,String str9,String str10,String str11,
									HashMap<String, HSSFCellStyle> styles,String schoolTermName, String creator, TableData tableData1,TableData tableData2,String courseName,String className, String judgeDate) throws Exception {

		TableHeaderMetaData headerMetaData1 = tableData1.getTableHeader();// 获得HTML的第一个表头元素
		TableHeaderMetaData headerMetaData2 = tableData2.getTableHeader();// 获得HTML的第二个表头元素

		String create_time = "";
		if(judgeDate != null) {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date date=sdf.parse(judgeDate);

			SimpleDateFormat formater = new SimpleDateFormat("yyyy年MM月dd日");
			create_time = formater.format(date);
		}

		HSSFSheet sheet = wb.createSheet(title);// 在Excel工作簿中建一工作表

		sheet.setDisplayGridlines(false);// 设置表标题是否有表格边框

		//创建标题
		HSSFRow row = sheet.createRow(0);// 创建新行
		HSSFCell cell = row.createCell(0);// 创建新列
		int rownum = 0;
		cell.setCellValue(new HSSFRichTextString(title));
		HSSFCellStyle style = styles.get("TITLE");//设置标题样式
		if (style != null)
			cell.setCellStyle(style);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headerMetaData1
				.getColumnCount() - 1));//合并标题行：起始行号，终止行号， 起始列号，终止列号

		//创建副标题
		row = sheet.createRow(1);
		cell = row.createCell(5);
		cell.setCellValue(new HSSFRichTextString("("+schoolTermName)+")");

		row = sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellValue(new HSSFRichTextString(info1));

		cell = row.createCell(11);
		cell.setCellValue(new HSSFRichTextString(info2));

		row = sheet.createRow(3);
		cell = row.createCell(5);
		style = styles.get("TITLE");
		cell.setCellValue(new HSSFRichTextString(info3));
		if (style != null)
			cell.setCellStyle(style);

		row = sheet.createRow(6);
		cell = row.createCell(5);
		style = styles.get("TITLE");
		cell.setCellValue(new HSSFRichTextString(info4));
		if (style != null)
			cell.setCellStyle(style);

		rownum = 4;// 如果rownum = 1，则去掉创建人、创建时间等副标题；如果rownum = 0， 则把标题也去掉

		HSSFCellStyle headerstyle = styles.get("STRING");

		int colnum = 0;
		for (int i = 0; i < headerMetaData1.getOriginColumns().size(); i++) {
			TableColumn tc = headerMetaData1.getOriginColumns().get(i);
			if (i != 0) {
				colnum += headerMetaData1.getOriginColumns().get(i - 1)
						.getLength();
			}
			generateColumn1(sheet, tc, headerMetaData1.maxlevel, rownum, colnum,
					headerstyle);
		}
		rownum += headerMetaData1.maxlevel;

		List<TableDataRow> dataRows1 = tableData1.getRows();

		HashMap<Integer, Integer> counter1 = new HashMap<Integer, Integer>();
		HashMap<Integer, String> word1 = new HashMap<Integer, String>();
		int index = 0;
		for (TableDataRow dataRow : dataRows1) {
			row = sheet.createRow(rownum);
			List<TableDataCell> dataCells = dataRow.getCells();
			int size = headerMetaData1.getColumns().size();
			index = -1;
			int lengthMax = 0;
			for (int i = 0; i < size; i++) {
				String value = dataCells.get(i).getValue();
				if(value.length()>lengthMax){
					lengthMax = value.length();
				}
			}

			for (int i = 0; i < size; i++) {
				TableColumn tc = headerMetaData1.getColumns().get(i);
				if (!tc.isVisible())
					continue;
				index++;

				String value = dataCells.get(i).getValue();
				if (tc.isGrouped()) {
					String w = word1.get(index);
					if (w == null) {
						word1.put(index, value);
						counter1.put(index, 1);
						createCell(row, tc, dataCells, i, index, styles);
					} else {
						if (w.equals(value)) {
							counter1.put(index, counter1.get(index) + 1);
						} else {
							stopGrouping(sheet, word1, counter1, index, size,
									rownum, styles.get("STRING"));

							word1.put(index, value);
							counter1.put(index, 1);
							createCell(row, tc, dataCells, i, index, styles);
						}
					}
				} else {
					createCell(row, tc, dataCells, i, index, styles);
				}
			}
			row.setHeight((short)((lengthMax/12+1)*280));
			rownum++;
		}

		// 设置前两列根据数据自动列宽
//		for (int c = 0; c < headerMetaData1.getColumns().size(); c++) {
//			String t = headerMetaData1.getColumns().get(c).getDisplay();
//			if(sheet.getColumnWidth(c)<t.length()*256*3)
//				sheet.setColumnWidth(c, 3766);
//		}
		sheet.setGridsPrinted(true);

		HSSFCellStyle headerstyle1 = styles.get("STRING");
		rownum = 7;
		int colnum1 = 0;
		for (int i = 0; i < headerMetaData2.getOriginColumns().size(); i++) {
			TableColumn tc = headerMetaData2.getOriginColumns().get(i);
			if (i != 0) {
				colnum1 += headerMetaData2.getOriginColumns().get(i - 1)
						.getLength();
			}
			generateColumn1(sheet, tc, headerMetaData2.maxlevel, rownum, colnum1,
					headerstyle1);
		}
		rownum += headerMetaData2.maxlevel;

		List<TableDataRow> dataRows2 = tableData2.getRows();
		//合并单元格参数
		HashMap<Integer, Integer> counter = new HashMap<Integer, Integer>();
		HashMap<Integer, String> word = new HashMap<Integer, String>();
		int index1 = 0;
		for (TableDataRow dataRow : dataRows2) {
			row = sheet.createRow(rownum);
			List<TableDataCell> dataCells = dataRow.getCells();
			int size = headerMetaData2.getColumns().size();
			index1 = -1;
			// 行高设置，取字符最长计算
			int lengthMax = 0;
			for (int i = 0; i < size; i++) {
				String value = dataCells.get(i).getValue();
				if(value.length()>lengthMax){
					lengthMax = value.length();
				}
			}
			for (int i = 0; i < size; i++) {
				TableColumn tc = headerMetaData2.getColumns().get(i);
				if (!tc.isVisible())
					continue;
				index1++;//列数

				String value = dataCells.get(i).getValue();
				if (tc.isGrouped()) {
					String w = word.get(index1);
					if (w == null) {
						word.put(index1, value);
						counter.put(index1, 1);
						createCell(row, tc, dataCells, i, index1, styles);
					} else {
						stopGrouping(sheet, word, counter, index1, size,
								rownum, styles.get("STRING"));

						word.put(index1, value);
						counter.put(index1, 1);
						createCell(row, tc, dataCells, i, index1, styles);
					}
				} else {
					createCell(row, tc, dataCells, i, index1, styles);
				}
			}
			row.setHeight((short)((lengthMax/12+1)*280));
			rownum++;
		}

		stopGrouping(sheet, word, counter, 0, index1, rownum, styles.get("STRING"));

		row = sheet.createRow(rownum+2);
		cell = row.createCell(0);
		cell.setCellValue(new HSSFRichTextString("填报人:"));

		cell = row.createCell(1);
		cell.setCellValue(new HSSFRichTextString(creator));

		cell = row.createCell(10);
		cell.setCellValue(new HSSFRichTextString("填报时间:"));

		cell = row.createCell(11);
		cell.setCellValue(new HSSFRichTextString(create_time));

		row = sheet.createRow(rownum+3);
		cell = row.createCell(0);
		cell.setCellValue(new HSSFRichTextString(str1));

		row = sheet.createRow(rownum+4);
		cell = row.createCell(0);
		cell.setCellValue(new HSSFRichTextString(str2));

		row = sheet.createRow(rownum+5);
		cell = row.createCell(0);
		cell.setCellValue(new HSSFRichTextString(str3));

		row = sheet.createRow(rownum+6);
		cell = row.createCell(0);
		cell.setCellValue(new HSSFRichTextString(str4));

		row = sheet.createRow(rownum+7);
		cell = row.createCell(0);
		cell.setCellValue(new HSSFRichTextString(str5));

		row = sheet.createRow(rownum+8);
		cell = row.createCell(0);
		cell.setCellValue(new HSSFRichTextString(str6));

		row = sheet.createRow(rownum+9);
		cell = row.createCell(0);
		cell.setCellValue(new HSSFRichTextString(str7));

		row = sheet.createRow(rownum+10);
		cell = row.createCell(0);
		cell.setCellValue(new HSSFRichTextString(str8));

		row = sheet.createRow(rownum+11);
		cell = row.createCell(0);
		cell.setCellValue(new HSSFRichTextString(str9));

		row = sheet.createRow(rownum+12);
		cell = row.createCell(0);
		cell.setCellValue(new HSSFRichTextString(str10));

		row = sheet.createRow(rownum+13);
		cell = row.createCell(0);
		cell.setCellValue(new HSSFRichTextString(str11));
		//设置横向打印
		sheet.getPrintSetup().setLandscape(true);
		//设置打印缩放
		//sheet.getPrintSetup().setScale((short) 65);
		//将工作表调整为一页
		sheet.setAutobreaks(true);
		HSSFPrintSetup printSetup = sheet.getPrintSetup();
		//A4纸
		printSetup.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
		wb.setPrintArea(0, 0, 12, 0, rownum+14);
		return wb;
	}
	/**
	 * 导出Excel(多工作表)-多sheet
	 *
	 * @param title
	 *            文件名
	 * @param creator
	 *            创建人 罗璇
	 * @param tableDataList
	 *            各工作格数据(注意：每个tableData要设置sheet名称，否则按默认呈现)
	 * @return void <style name="dataset"> case SYSROWNUM%2==0?#row0:#row1;
	 *         fontsize:9px; </style> <style name="row0"> import(parent);
	 *         bgcolor:#FFFFFF; </style> <style name="row1"> import(parent);
	 *         bgcolor:#CAEAFE; </style>
	 */
	public void exportToExcelForSheets(String title, String creator, List<TableData> tableDataList)
		throws Exception {

		HSSFWorkbook wb = new HSSFWorkbook();// 创建新的Excel 工作簿
		HashMap<String, HSSFCellStyle> styles = initStyles(wb);// 初始化表头样式

		int i = 1;
		for(TableData tableData : tableDataList){
			String sheetTitle = title + i;
			wb = writeSheet(wb,sheetTitle,styles,creator,tableData);//写入工作表
			i++;
		}

		String sFileName = title + ".xls";
		response.setHeader("Content-Disposition", "attachment;filename="
				.concat(String.valueOf(URLEncoder.encode(sFileName, "UTF-8"))));
		response.setHeader("Connection", "close");
		response.setHeader("Content-Type", "application/vnd.ms-excel");

		wb.write(response.getOutputStream());
	}

	/*****************************************************************
	 * 功能：导出-课表查询
	 * 作者：廖文辉
	 * 时间：2018.11.27
	 *****************************************************************/
	public void exportTeachingTimetableToExcel(List<TimetableLabRelated> timetableLabRelated, String termName) throws Exception {
		String title = "课表查询-";
		if(termName != null) {
			title += termName;
		}
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(title);

		int countMon = 0;
		int countTues = 0;
		int countWed = 0;
		int countThur = 0;
		int countFri = 0;
		int countSat=0;
		int countSun=0;
		int countAll = 0;
		List<Integer> monBeginList = new ArrayList<Integer>();
		List<Integer> tuesBeginList = new ArrayList<Integer>();
		List<Integer> wedBeginList = new ArrayList<Integer>();
		List<Integer> thurBeginList = new ArrayList<Integer>();
		List<Integer> friBeginList = new ArrayList<Integer>();
		List<Integer> satBeginList = new ArrayList<Integer>();
		List<Integer> sunBeginList = new ArrayList<Integer>();
		List<Integer> monEndList = new ArrayList<Integer>();
		List<Integer> tuesEndList = new ArrayList<Integer>();
		List<Integer> wedEndList = new ArrayList<Integer>();
		List<Integer> thurEndList = new ArrayList<Integer>();
		List<Integer> friEndList = new ArrayList<Integer>();
		List<Integer> satEndList = new ArrayList<Integer>();
		List<Integer> sunEndList = new ArrayList<Integer>();
		List<String> monContentList = new ArrayList<String>();
		List<String> tuesContentList = new ArrayList<String>();
		List<String> wedContentList = new ArrayList<String>();
		List<String> thurContentList = new ArrayList<String>();
		List<String> friContentList = new ArrayList<String>();
		List<String> satContentList = new ArrayList<String>();
		List<String> sunContentList = new ArrayList<String>();
		int ss = 0;// appointment  id
		int sc = 0;// start class
		int ec = 0;// end class
		for(TimetableLabRelated current : timetableLabRelated) {
			// 主带老师
			String teacher = null;
			Set<TimetableTeacherRelated> curRelateds = current.getTimetableAppointment().getTimetableTeacherRelateds();
			for(TimetableTeacherRelated currRelated : curRelateds) {
				teacher = currRelated.getUser().getCname();
			}
/*			// 辅带老师
			String tutor = null;
			Set<TimetableTutorRelated> currRelateds = current.getTimetableAppointment().getTimetableTutorRelateds();
			for(TimetableTutorRelated cuRelatedsRelated : currRelateds) {
				tutor = cuRelatedsRelated.getUser().getCname();
			}*/
			// 班级
			String classNameString = "";
			if(current.getTimetableAppointment()!=null&&current.getTimetableAppointment().getSchoolCourse()!=null
					&&current.getTimetableAppointment().getSchoolCourse().getSchoolClasseses()!=null) {
				Set<SchoolClasses> currClasses = current.getTimetableAppointment().getSchoolCourse().getSchoolClasseses();
				for (SchoolClasses schoolClasses : currClasses) {
					classNameString += schoolClasses.getClassName() + " ";
				}
			}

            for(TimetableAppointmentSameNumber timetableAppointmentSameNumber:current.getTimetableAppointment().getTimetableAppointmentSameNumbers()) {
				/*ss = timetableAppointmentSameNumber.getTimetableAppointment().getId();*/
			if (timetableAppointmentSameNumber.getStartClass() != sc
						&& timetableAppointmentSameNumber.getEndClass() != ec
					        || timetableAppointmentSameNumber.getTimetableAppointment().getId()!=ss) {
					String weekString = "";
					if(current.getTimetableAppointment().getTimetableAppointmentSameNumbers().size()>0) {
						Set<TimetableAppointmentSameNumber> currAppointmentSameNumbers = current.getTimetableAppointment().getTimetableAppointmentSameNumbers();
						for(TimetableAppointmentSameNumber currSameNumber : currAppointmentSameNumbers) {
							if(currSameNumber.getStartWeek().equals(currSameNumber.getEndWeek())){
								weekString +=  currSameNumber.getStartWeek()+"\n";
							}else {
								weekString += currSameNumber.getStartWeek() + "-" + currSameNumber.getEndWeek() + "\n";
							}
						}
					}
					if (new Integer(current.getTimetableAppointment().getWeekday()) == 1) {
						countMon++;
						monBeginList.add(new Integer(timetableAppointmentSameNumber.getStartClass()));
						monEndList.add(new Integer(timetableAppointmentSameNumber.getEndClass()));
						if(current.getTimetableAppointment().getTimetableStyle() == 7) {
                            monContentList.add("实验室:" + current.getLabRoom().getLabRoomName() + "\n" + "课程名称:实验室预约" + "\n" +
                                    "预约人:" + current.getTimetableAppointment().getTimetableTeacherRelateds().iterator().next().getUser().getCname() + "\n" +
                                    "人数:" + current.getTimetableAppointment().getGroupCount().toString() + "\n" + "\n" + "周次:" + weekString + "班级:" + classNameString);
                        }else{
                            monContentList.add("实验室:" + current.getLabRoom().getLabRoomName() + "\n" + "课程名称:" + current.getTimetableAppointment().getSchoolCourseInfo().getCourseName() + "\n" +
                                    "教师:" + teacher + "\n" + "\n" + "周次:" + weekString + "班级:" + classNameString);
                        }
					}
					if (new Integer(current.getTimetableAppointment().getWeekday()) == 2) {
						countTues++;
						tuesBeginList.add(new Integer(timetableAppointmentSameNumber.getStartClass()));
						tuesEndList.add(new Integer(timetableAppointmentSameNumber.getEndClass()));
                        if(current.getTimetableAppointment().getTimetableStyle() == 7) {
                            tuesContentList.add("实验室:" + current.getLabRoom().getLabRoomName() + "\n" + "课程名称:实验室预约" + "\n" +
                                            "预约人:" + current.getTimetableAppointment().getTimetableTeacherRelateds().iterator().next().getUser().getCname() + "\n" +
                                    "人数:" + current.getTimetableAppointment().getGroupCount().toString() + "\n" + "\n" + "周次:" + weekString);
                        }else{
                            tuesContentList.add("实验室:" + current.getLabRoom().getLabRoomName() + "\n" + "课程名称:" + current.getTimetableAppointment().getSchoolCourseInfo().getCourseName() + "\n" +
                                    "教师:" + teacher + "\n" + "\n" + "周次:" + weekString + "班级:" + classNameString);
                        }
					}
					if (new Integer(current.getTimetableAppointment().getWeekday()) == 3) {
						countWed++;
						wedBeginList.add(new Integer(timetableAppointmentSameNumber.getStartClass()));
						wedEndList.add(new Integer(timetableAppointmentSameNumber.getEndClass()));
                        if(current.getTimetableAppointment().getTimetableStyle() == 7) {
                            wedContentList.add("实验室:" + current.getLabRoom().getLabRoomName() + "\n" + "课程名称:实验室预约" + "\n" +
                                            "预约人:" + current.getTimetableAppointment().getTimetableTeacherRelateds().iterator().next().getUser().getCname() + "\n" +
                                    "人数:" + current.getTimetableAppointment().getGroupCount().toString() + "\n" + "\n" + "周次:" + weekString);
                        }else{
                            wedContentList.add("实验室:" + current.getLabRoom().getLabRoomName() + "\n" + "课程名称:" + current.getTimetableAppointment().getSchoolCourseInfo().getCourseName() + "\n" +
                                    "教师:" + teacher + "\n" + "\n" + "周次:" + weekString + "班级:" + classNameString);
                        }
					}
					if (new Integer(current.getTimetableAppointment().getWeekday()) == 4) {
						countThur++;
						thurBeginList.add(new Integer(timetableAppointmentSameNumber.getStartClass()));
						thurEndList.add(new Integer(timetableAppointmentSameNumber.getEndClass()));
                        if(current.getTimetableAppointment().getTimetableStyle() == 7) {
                            thurContentList.add("实验室:" + current.getLabRoom().getLabRoomName() + "\n" + "课程名称:实验室预约" + "\n" +
                                            "预约人:" + current.getTimetableAppointment().getTimetableTeacherRelateds().iterator().next().getUser().getCname() + "\n" +
                                    "人数:" + current.getTimetableAppointment().getGroupCount().toString() + "\n" + "\n" + "周次:" + weekString);
                        }else{
                            thurContentList.add("实验室:" + current.getLabRoom().getLabRoomName() + "\n" + "课程名称:" + current.getTimetableAppointment().getSchoolCourseInfo().getCourseName() + "\n" +
                                    "教师:" + teacher + "\n" + "\n" + "周次:" + weekString + "班级:" + classNameString);
                        }
					}
					if (new Integer(current.getTimetableAppointment().getWeekday()) == 5) {
						countFri++;
						friBeginList.add(new Integer(timetableAppointmentSameNumber.getStartClass()));
						friEndList.add(new Integer(timetableAppointmentSameNumber.getEndClass()));
                        if(current.getTimetableAppointment().getTimetableStyle() == 7) {
                            friContentList.add("实验室:" + current.getLabRoom().getLabRoomName() + "\n" + "课程名称:实验室预约" + "\n" +
                                            "预约人:" + current.getTimetableAppointment().getTimetableTeacherRelateds().iterator().next().getUser().getCname() + "\n" +
                                    "人数:" + current.getTimetableAppointment().getGroupCount().toString() + "\n" + "\n" + "周次:" + weekString);
                        }else{
                            friContentList.add("实验室:" + current.getLabRoom().getLabRoomName() + "\n" + "课程名称:" + current.getTimetableAppointment().getSchoolCourseInfo().getCourseName() + "\n" +
                                    "教师:" + teacher + "\n" + "\n" + "周次:" + weekString + "班级:" + classNameString);
                        }
					}
					if (new Integer(current.getTimetableAppointment().getWeekday()) == 6) {
						countSat++;
						satBeginList.add(new Integer(timetableAppointmentSameNumber.getStartClass()));
						satEndList.add(new Integer(timetableAppointmentSameNumber.getEndClass()));
                        if(current.getTimetableAppointment().getTimetableStyle() == 7) {
                            satContentList.add("实验室:" + current.getLabRoom().getLabRoomName() + "\n" + "课程名称:实验室预约" + "\n" +
                                            "预约人:" + current.getTimetableAppointment().getTimetableTeacherRelateds().iterator().next().getUser().getCname() + "\n" +
                                    "人数:" + current.getTimetableAppointment().getGroupCount().toString() + "\n" + "\n" + "周次:" + weekString);
                        }else{
                            satContentList.add("实验室:" + current.getLabRoom().getLabRoomName() + "\n" + "课程名称:" + current.getTimetableAppointment().getSchoolCourseInfo().getCourseName() + "\n" +
                                    "教师:" + teacher + "\n" + "\n" + "周次:" + weekString + "班级:" + classNameString);
                        }
					}
					if (new Integer(current.getTimetableAppointment().getWeekday()) == 7) {
						countSun++;
						sunBeginList.add(new Integer(timetableAppointmentSameNumber.getStartClass()));
						sunEndList.add(new Integer(timetableAppointmentSameNumber.getEndClass()));
                        if(current.getTimetableAppointment().getTimetableStyle() == 7) {
                            sunContentList.add("实验室:" + current.getLabRoom().getLabRoomName() + "\n" + "课程名称:实验室预约" + "\n" +
                                            "预约人:" + current.getTimetableAppointment().getTimetableTeacherRelateds().iterator().next().getUser().getCname() + "\n" +
                                    "人数:" + current.getTimetableAppointment().getGroupCount().toString() + "\n" + "\n" + "周次:" + weekString);
                        }else{
                            sunContentList.add("实验室:" + current.getLabRoom().getLabRoomName() + "\n" + "课程名称:" + current.getTimetableAppointment().getSchoolCourseInfo().getCourseName() + "\n" +
                                    "教师:" + teacher + "\n" + "\n" + "周次:" + weekString + "班级:" + classNameString);
                        }
					}
				}
				ss = timetableAppointmentSameNumber.getTimetableAppointment().getId();
				sc = timetableAppointmentSameNumber.getStartClass();
				ec = timetableAppointmentSameNumber.getEndClass();
			}
		}
		if(countMon == 0) {
			countMon = 1;
		}
		if(countTues == 0) {
			countTues = 1;
		}
		if(countWed == 0) {
			countWed = 1;
		}
		if(countThur == 0) {
			countThur = 1;
		}
		if(countFri == 0) {
			countFri = 1;
		}
		if(countSat == 0) {
			countSat = 1;
		}
		if(countSun == 0) {
			countSun = 1;
		}
		countAll = countMon + countTues + countWed + countThur + countFri + countSat + countSun;

		HSSFRow row1 = sheet.createRow(0);
		row1.setHeight((short)780);
		HSSFCell cell1 = row1.createCell(0);
		cell1.setCellValue(new HSSFRichTextString(title));
		HSSFCellStyle style1 = wb.createCellStyle();
		Font font1 = wb.createFont();
		font1.setFontHeightInPoints((short)16);
		font1.setFontName("黑体");
		style1.setFont(font1);
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1+countAll));
		cell1.setCellStyle(style1);

		HSSFRow row2 = sheet.createRow(1);
		sheet.setColumnWidth(0,1350);
		HSSFCell cell212 = row2.createCell(1);
		cell212.setCellValue("星期");
		HSSFCellStyle style2 = wb.createCellStyle();
		Font font2 = wb.createFont();
		font2.setFontHeightInPoints((short)12);
		font2.setFontName("黑体");
		style2.setFont(font2);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cell212.setCellStyle(style2);
		style2.setWrapText(true);
//		sheet.setColumnWidth(1,1350);

/*		HSSFRow row3 = sheet.createRow(2);
		HSSFCell cell221 = row3.createCell(0);
		sheet.setColumnWidth(0,1350);
		HSSFCell cell222 = row3.createCell(1);
		sheet.setColumnWidth(1,1350);*/

		HSSFRow row4 = sheet.createRow(3);
		HSSFCell cell231 = row4.createCell(0);
/*		HSSFCell cell232 = row4.createCell(1);*/
		cell231.setCellValue("节次");
		cell231.setCellStyle(style2);
		sheet.setColumnWidth(0,1350);
/*		cell232.setCellValue("学科");
		cell232.setCellStyle(style2);
		sheet.setColumnWidth(1,1350);*/

		HSSFCell cell213 = row2.createCell(2);
		cell213.setCellValue("星期一");
		Font font3 = wb.createFont();
		font3.setFontHeightInPoints((short)12);
		HSSFCellStyle style3 = wb.createCellStyle();
		style3.setFont(font3);
		style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cell213.setCellStyle(style3);
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 2, 1+countMon));

		HSSFCell cell214 = row2.createCell(2+countMon);
		cell214.setCellValue("星期二");
		cell214.setCellStyle(style3);
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 2+countMon, 1+countMon+countTues));

		HSSFCell cell215 = row2.createCell(2+countMon+countTues);
		cell215.setCellValue("星期三");
		cell215.setCellStyle(style3);
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 2+countMon+countTues, 1+countMon+countTues+countWed));

		HSSFCell cell216 = row2.createCell(2+countMon+countTues+countWed);
		cell216.setCellValue("星期四");
		cell216.setCellStyle(style3);
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 2+countMon+countTues+countWed, 1+countMon+countTues+countWed+countThur));

		HSSFCell cell217 = row2.createCell(2+countMon+countTues+countWed+countThur);
		cell217.setCellValue("星期五");
		cell217.setCellStyle(style3);
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 2+countMon+countTues+countWed+countThur, 1+countMon+countTues+countWed+countThur+countFri));

		HSSFCell cell218 = row2.createCell(2+countMon+countTues+countWed+countThur+countFri);
		cell218.setCellValue("星期六");
		cell218.setCellStyle(style3);
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 2+countMon+countTues+countWed+countThur+countFri, 1+countMon+countTues+countWed+countThur+countFri+countSat));

		HSSFCell cell219 = row2.createCell(2+countMon+countTues+countWed+countThur+countFri+countSat);
		cell219.setCellValue("星期日");
		cell219.setCellStyle(style3);
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 2+countMon+countTues+countWed+countThur+countFri+countSat, 1+countMon+countTues+countWed+countThur+countFri+countSat+countSun));

		HSSFCellStyle style4 = wb.createCellStyle();
		Font font4 = wb.createFont();
		font4.setFontHeightInPoints((short)10);
		style4.setFont(font4);
		style4.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style4.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style4.setWrapText(true);

//
		HSSFRow row5 = sheet.createRow(4);
		row5.setHeight((short)1500);
		HSSFCell cell42 = row5.createCell(0);
		cell42.setCellValue("第一节");
		cell42.setCellStyle(style2);

		for(int i=0; i<countMon; i++) {
			HSSFCell cell43 = row5.createCell(2+i);
			cell43.setCellStyle(style4);
			if(monBeginList.size() > 0) {
				if(monBeginList.get(i) == 1) {
					cell43.setCellValue(monContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+monBeginList.get(i), 3+monEndList.get(i), 2+i, 2+i));
					sheet.setColumnWidth(2+i, 10*256);
				}
			}
		}
		for(int i=0; i<countTues; i++) {
			HSSFCell cell44 = row5.createCell(2+countMon+i);
			cell44.setCellStyle(style4);
			if(tuesBeginList.size() > 0) {
				if(tuesBeginList.get(i) == 1) {
					cell44.setCellValue(tuesContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+tuesBeginList.get(i), 3+tuesEndList.get(i), 2+countMon+i, 2+countMon+i));
					sheet.setColumnWidth(2+countMon+i, 10*256);
				}
			}
		}
		for(int i=0; i<countWed; i++) {
			HSSFCell cell45 = row5.createCell(2+countMon+countTues+i);
			cell45.setCellStyle(style4);
			if(wedBeginList.size() > 0) {
				if(wedBeginList.get(i) == 1) {
					cell45.setCellValue(wedContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+wedBeginList.get(i), 3+wedEndList.get(i),2+countMon+countTues+i, 2+countMon+countTues+i));
					sheet.setColumnWidth(2+countMon+countTues+i, 10*256);
				}
			}
		}
		for(int i=0; i<countThur; i++) {
			HSSFCell cell46 = row5.createCell(2+countMon+countTues+countWed+i);
			cell46.setCellStyle(style4);
			if(thurBeginList.size() > 0) {
				if(thurBeginList.get(i) == 1) {
					cell46.setCellValue(thurContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+thurBeginList.get(i), 3+thurEndList.get(i), 2+countMon+countTues+countWed+i, 2+countMon+countTues+countWed+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+i, 10*256);
				}
			}
		}
		for(int i=0; i<countFri; i++) {
			HSSFCell cell47 = row5.createCell(2+countMon+countTues+countWed+countThur+i);
			cell47.setCellStyle(style4);
			if(friBeginList.size() > 0) {
				if(friBeginList.get(i) == 1) {
					cell47.setCellValue(friContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+friBeginList.get(i), 3+friEndList.get(i), 2+countMon+countTues+countWed+countThur+i, 2+countMon+countTues+countWed+countThur+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+countThur+i, 10*256);
				}
			}
		}
		for(int i=0; i<countSat; i++) {
			HSSFCell cell48 = row5.createCell(2+countMon+countTues+countWed+countThur+countFri+i);
			cell48.setCellStyle(style4);
			if(satBeginList.size() > 0) {
				if(satBeginList.get(i) == 1) {
					cell48.setCellValue(satContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+satBeginList.get(i), 3+satEndList.get(i), 2+countMon+countTues+countWed+countThur+countFri+i, 2+countMon+countTues+countWed+countThur+countFri+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+countThur+countFri+i, 10*256);
				}
			}
		}
		for(int i=0; i<countSun; i++) {
			HSSFCell cell49 = row5.createCell(2+countMon+countTues+countWed+countThur+countFri+countSat+i);
			cell49.setCellStyle(style4);
			if(sunBeginList.size() > 0) {
				if(sunBeginList.get(i) == 1) {
					cell49.setCellValue(sunContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+sunBeginList.get(i), 3+sunEndList.get(i), 2+countMon+countTues+countWed+countThur+countFri+countSat+i, 2+countMon+countTues+countWed+countThur+countFri+countSat+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+countThur+countFri+countSat+i, 10*256);
				}
			}
		}

		HSSFRow row6 = sheet.createRow(5);
		row6.setHeight((short)1500);
/*		HSSFCell cell51 = row6.createCell(0);
*//*		cell51.setCellValue("上午");
		cell51.setCellStyle(style2);*/
		HSSFCell cell52 = row6.createCell(0);
		cell52.setCellValue("第二节");
		cell52.setCellStyle(style2);

		for(int i=0; i<countMon; i++) {
			HSSFCell cell53 = row6.createCell(2+i);
			cell53.setCellStyle(style4);
			if(monBeginList.size() > 0) {
				if(monBeginList.get(i) == 2) {
					cell53.setCellValue(monContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+monBeginList.get(i),3+monEndList.get(i),2+i,2+i));
					sheet.setColumnWidth(2+i, 10*256);
				}
			}
		}
		for(int i=0; i<countTues; i++) {
			HSSFCell cell54 = row6.createCell(2+countMon+i);
			cell54.setCellStyle(style4);
			if(tuesBeginList.size() > 0) {
				if(tuesBeginList.get(i) == 2) {
					cell54.setCellValue(tuesContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+tuesBeginList.get(i),3+tuesEndList.get(i),2+countMon+i,2+countMon+i));
					sheet.setColumnWidth(2+countMon+i, 10*256);
				}
			}
		}
		for(int i=0; i<countWed; i++) {
			HSSFCell cell55 = row6.createCell(2+countMon+countTues+i);
			cell55.setCellStyle(style4);
			if(wedBeginList.size() > 0) {
				if(wedBeginList.get(i) == 2) {
					cell55.setCellValue(wedContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+wedBeginList.get(i),3+wedEndList.get(i),2+countMon+countTues+i,2+countMon+countTues+i));
					sheet.setColumnWidth(2+countMon+countTues+i, 10*256);
				}
			}
		}

		for(int i=0; i<countThur; i++) {
			HSSFCell cell56 = row6.createCell(2+countMon+countTues+countWed+i);
			cell56.setCellStyle(style4);
			if(thurBeginList.size() > 0) {
				if(thurBeginList.get(i) == 2) {
					cell56.setCellValue(thurContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+thurBeginList.get(i),3+thurEndList.get(i),2+countMon+countTues+countWed+i,2+countMon+countTues+countWed+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+i, 10*256);
				}
			}
		}
		for(int i=0; i<countFri; i++) {
			HSSFCell cell57 = row6.createCell(2+countMon+countTues+countWed+countThur+i);
			cell57.setCellStyle(style4);
			if(friBeginList.size() > 0) {
				if(friBeginList.get(i) == 2) {
					cell57.setCellValue(friContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+friBeginList.get(i),3+friEndList.get(i),2+countMon+countTues+countWed+countThur+i,2+countMon+countTues+countWed+countThur+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+countThur+i, 10*256);
				}
			}
		}
		for(int i=0; i<countSat; i++) {
			HSSFCell cell58 = row6.createCell(2+countMon+countTues+countWed+countThur+countFri+i);
			cell58.setCellStyle(style4);
			if(satBeginList.size() > 0) {
				if(satBeginList.get(i) == 2) {
					cell58.setCellValue(satContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+satBeginList.get(i), 3+satEndList.get(i), 2+countMon+countTues+countWed+countThur+countFri+i, 2+countMon+countTues+countWed+countThur+countFri+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+countThur+countFri+i, 10*256);
				}
			}
		}
		for(int i=0; i<countSun; i++) {
			HSSFCell cell59 = row6.createCell(2+countMon+countTues+countWed+countThur+countFri+countSat+i);
			cell59.setCellStyle(style4);
			if(sunBeginList.size() > 0) {
				if(sunBeginList.get(i) == 2) {
					cell59.setCellValue(sunContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+sunBeginList.get(i), 3+sunEndList.get(i), 2+countMon+countTues+countWed+countThur+countFri+countSat+i, 2+countMon+countTues+countWed+countThur+countFri+countSat+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+countThur+countFri+countSat+i, 10*256);
				}
			}
		}

		HSSFRow row7 = sheet.createRow(6);
		row7.setHeight((short)1500);
		HSSFCell cell62 = row7.createCell(0);
		cell62.setCellValue("第三节");
		cell62.setCellStyle(style2);

		for(int i=0; i<countMon; i++) {
			HSSFCell cell63 = row7.createCell(2+i);
			cell63.setCellStyle(style4);
			if(monBeginList.size() > 0) {
				if(monBeginList.get(i) == 3) {
					cell63.setCellValue(monContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+monBeginList.get(i),3+monEndList.get(i),2+i,2+i));
					sheet.setColumnWidth(2+i, 10*256);
				}
			}
		}
		for(int i=0; i<countTues; i++) {
			HSSFCell cell64 = row7.createCell(2+countMon+i);
			cell64.setCellStyle(style4);
			if(tuesBeginList.size() > 0) {
				if(tuesBeginList.get(i) == 3) {
					cell64.setCellValue(tuesContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+tuesBeginList.get(i),3+tuesEndList.get(i),2+countMon+i,2+countMon+i));
					sheet.setColumnWidth(2+countMon+i, 10*256);
				}
			}
		}
		for(int i=0; i<countWed; i++) {
			HSSFCell cell65 = row7.createCell(2+countMon+countTues+i);
			cell65.setCellStyle(style4);
			if(wedBeginList.size() > 0) {
				if(wedBeginList.get(i) == 3) {
					cell65.setCellValue(wedContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+wedBeginList.get(i),3+wedEndList.get(i),2+countMon+countTues+i,2+countMon+countTues+i));
					sheet.setColumnWidth(2+countMon+countTues+i, 6*256);
				}
			}
		}
		for(int i=0; i<countThur; i++) {
			HSSFCell cell66 = row7.createCell(2+countMon+countTues+countWed+i);
			cell66.setCellStyle(style4);
			if(thurBeginList.size() > 0) {
				if(thurBeginList.get(i) == 3) {
					cell66.setCellValue(thurContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+thurBeginList.get(i),3+thurEndList.get(i),2+countMon+countTues+countWed+i,2+countMon+countTues+countWed+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+i, 6*256);
				}
			}
		}
		for(int i=0; i<countFri; i++) {
			HSSFCell cell67 = row7.createCell(2+countMon+countTues+countWed+countThur+i);
			cell67.setCellStyle(style4);
			if(friBeginList.size() > 0) {
				if(friBeginList.get(i) == 3) {
					cell67.setCellValue(friContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+friBeginList.get(i),3+friEndList.get(i),2+countMon+countTues+countWed+countThur+i,2+countMon+countTues+countWed+countThur+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+countThur+i, 10*256);
				}
			}
		}
		for(int i=0; i<countSat; i++) {
			HSSFCell cell68 = row7.createCell(2+countMon+countTues+countWed+countThur+countFri+i);
			cell68.setCellStyle(style4);
			if(satBeginList.size() > 0) {
				if(satBeginList.get(i) == 3) {
					cell68.setCellValue(satContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+satBeginList.get(i), 3+satEndList.get(i), 2+countMon+countTues+countWed+countThur+countFri+i, 2+countMon+countTues+countWed+countThur+countFri+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+countThur+countFri+i, 10*256);
				}
			}
		}
		for(int i=0; i<countSun; i++) {
			HSSFCell cell69 = row7.createCell(2+countMon+countTues+countWed+countThur+countFri+countSat+i);
			cell69.setCellStyle(style4);
			if(sunBeginList.size() > 0) {
				if(sunBeginList.get(i) == 3) {
					cell69.setCellValue(sunContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+sunBeginList.get(i), 3+sunEndList.get(i), 2+countMon+countTues+countWed+countThur+countFri+countSat+i, 2+countMon+countTues+countWed+countThur+countFri+countSat+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+countThur+countFri+countSat+i, 10*256);
				}
			}
		}

		HSSFRow row8 = sheet.createRow(7);
		row8.setHeight((short)1500);
		HSSFCell cell72 = row8.createCell(0);
		cell72.setCellValue("第四节");
		cell72.setCellStyle(style2);

		for(int i=0; i<countMon; i++) {
			HSSFCell cell73 = row8.createCell(2+i);
			cell73.setCellStyle(style4);
			if(monBeginList.size() > 0) {
				if(monBeginList.get(i) == 4) {
					cell73.setCellValue(monContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+monBeginList.get(i),3+monEndList.get(i),2+i,2+i));
					sheet.setColumnWidth(2+i, 10*256);
				}
			}
		}
		for(int i=0; i<countTues; i++) {
			HSSFCell cell74 = row8.createCell(2+countMon+i);
			cell74.setCellStyle(style4);
			if(tuesBeginList.size() > 0) {
				if(tuesBeginList.get(i) == 4) {
					cell74.setCellValue(tuesContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+tuesBeginList.get(i),3+tuesEndList.get(i),2+countMon+i,2+countMon+i));
					sheet.setColumnWidth(2+countMon+i, 10*256);
				}
			}
		}
		for(int i=0; i<countWed; i++) {
			HSSFCell cell75 = row8.createCell(2+countMon+countTues+i);
			cell75.setCellStyle(style4);
			if(wedBeginList.size() > 0) {
				if(wedBeginList.get(i) == 4) {
					cell75.setCellValue(wedContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+wedBeginList.get(i),3+wedEndList.get(i),2+countMon+countTues+i,2+countMon+countTues+i));
					sheet.setColumnWidth(2+countMon+countTues+i, 10*256);
				}
			}
		}
		for(int i=0; i<countThur; i++) {
			HSSFCell cell76 = row8.createCell(2+countMon+countTues+countWed+i);
			cell76.setCellStyle(style4);
			if(thurBeginList.size() > 0) {
				if(thurBeginList.get(i) == 4) {
					cell76.setCellValue(thurContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+thurBeginList.get(i),3+thurEndList.get(i),2+countMon+countTues+countWed+i,2+countMon+countTues+countWed+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+i, 10*256);
				}
			}
		}
		for(int i=0; i<countFri; i++) {
			HSSFCell cell77 = row8.createCell(2+countMon+countTues+countWed+countThur+i);
			cell77.setCellStyle(style4);
			if(friBeginList.size() > 0) {
				if(friBeginList.get(i) == 4) {
					cell77.setCellValue(friContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+friBeginList.get(i),3+friEndList.get(i),2+countMon+countTues+countWed+countThur+i,2+countMon+countTues+countWed+countThur+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+countThur+i, 10*256);
				}
			}
		}
		for(int i=0; i<countSat; i++) {
			HSSFCell cell78 = row8.createCell(2+countMon+countTues+countWed+countThur+countFri+i);
			cell78.setCellStyle(style4);
			if(satBeginList.size() > 0) {
				if(satBeginList.get(i) == 4) {
					cell78.setCellValue(satContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+satBeginList.get(i), 3+satEndList.get(i), 2+countMon+countTues+countWed+countThur+countFri+i, 2+countMon+countTues+countWed+countThur+countFri+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+countThur+countFri+i, 10*256);
				}
			}
		}
		for(int i=0; i<countSun; i++) {
			HSSFCell cell79 = row8.createCell(2+countMon+countTues+countWed+countThur+countFri+countSat+i);
			cell79.setCellStyle(style4);
			if(sunBeginList.size() > 0) {
				if(sunBeginList.get(i) == 4) {
					cell79.setCellValue(sunContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+sunBeginList.get(i), 3+sunEndList.get(i), 2+countMon+countTues+countWed+countThur+countFri+countSat+i, 2+countMon+countTues+countWed+countThur+countFri+countSat+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+countThur+countFri+countSat+i, 10*256);
				}
			}
		}

		HSSFRow row9 = sheet.createRow(8);
		row9.setHeight((short)1500);
		HSSFCell cell82 = row9.createCell(0);
		cell82.setCellValue("第五节");
		cell82.setCellStyle(style2);

		for(int i=0; i<countMon; i++) {
			HSSFCell cell83 = row9.createCell(2+i);
			cell83.setCellStyle(style4);
			if(monBeginList.size() > 0) {
				if(monBeginList.get(i) == 5) {
					cell83.setCellValue(monContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+monBeginList.get(i),3+monEndList.get(i),2+i,2+i));
					sheet.setColumnWidth(2+i, 10*256);
				}
			}
		}
		for(int i=0; i<countTues; i++) {
			HSSFCell cell84 = row9.createCell(2+countMon+i);
			cell84.setCellStyle(style4);
			if(tuesBeginList.size() > 0) {
				if(tuesBeginList.get(i) == 5) {
					cell84.setCellValue(tuesContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+tuesBeginList.get(i),3+tuesEndList.get(i),2+countMon+i,2+countMon+i));
					sheet.setColumnWidth(2+countMon+i, 10*256);
				}
			}
		}
		for(int i=0; i<countWed; i++) {
			HSSFCell cell85 = row9.createCell(2+countMon+countTues+i);
			cell85.setCellStyle(style4);
			if(wedBeginList.size() > 0) {
				if(wedBeginList.get(i) == 5) {
					cell85.setCellValue(wedContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+wedBeginList.get(i),3+wedEndList.get(i),2+countMon+countTues+i,2+countMon+countTues+i));
					sheet.setColumnWidth(2+countMon+countTues+i, 10*256);
				}
			}
		}
		for(int i=0; i<countThur; i++) {
			HSSFCell cell86 = row9.createCell(2+countMon+countTues+countWed+i);
			cell86.setCellStyle(style4);
			if(thurBeginList.size() > 0) {
				if(thurBeginList.get(i) == 5) {
					cell86.setCellValue(thurContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+thurBeginList.get(i),3+thurEndList.get(i),2+countMon+countTues+countWed+i,2+countMon+countTues+countWed+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+i, 10*256);
				}
			}
		}
		for(int i=0; i<countFri; i++) {
			HSSFCell cell87 = row9.createCell(2+countMon+countTues+countWed+countThur+i);
			cell87.setCellStyle(style4);
			if(friBeginList.size() > 0) {
				if(friBeginList.get(i) == 5) {
					cell87.setCellValue(friContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+friBeginList.get(i),3+friEndList.get(i),2+countMon+countTues+countWed+countThur+i,2+countMon+countTues+countWed+countThur+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+countThur+i, 10*256);
				}
			}
		}
		for(int i=0; i<countSat; i++) {
			HSSFCell cell88 = row9.createCell(2+countMon+countTues+countWed+countThur+countFri+i);
			cell88.setCellStyle(style4);
			if(satBeginList.size() > 0) {
				if(satBeginList.get(i) == 5) {
					cell88.setCellValue(satContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+satBeginList.get(i), 3+satEndList.get(i), 2+countMon+countTues+countWed+countThur+countFri+i, 2+countMon+countTues+countWed+countThur+countFri+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+countThur+countFri+i, 10*256);
				}
			}
		}
		for(int i=0; i<countSun; i++) {
			HSSFCell cell89 = row9.createCell(2+countMon+countTues+countWed+countThur+countFri+countSat+i);
			cell89.setCellStyle(style4);
			if(sunBeginList.size() > 0) {
				if(sunBeginList.get(i) == 5) {
					cell89.setCellValue(sunContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+sunBeginList.get(i), 3+sunEndList.get(i), 2+countMon+countTues+countWed+countThur+countFri+countSat+i, 2+countMon+countTues+countWed+countThur+countFri+countSat+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+countThur+countFri+countSat+i, 10*256);
				}
			}
		}
//		sheet.addMergedRegion(new CellRangeAddress(5, 8, 0, 0));
		HSSFRow row10 = sheet.createRow(9);
		row10.setHeight((short)1500);/*
		HSSFCell cell91 = row10.createCell(0);
		cell91.setCellValue("中午");
		cell91.setCellStyle(style2);*/
		HSSFCell cell92 = row10.createCell(0);
		cell92.setCellValue("第六节");
		cell92.setCellStyle(style2);

		for(int i=0; i<countMon; i++) {
			HSSFCell cell93 = row10.createCell(2+i);
			cell93.setCellStyle(style4);
			if(monBeginList.size() > 0) {
				if(monBeginList.get(i) == 6) {
					cell93.setCellValue(monContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+monBeginList.get(i),3+monEndList.get(i),2+i,2+i));
					sheet.setColumnWidth(2+i, 10*256);
				}
			}
		}
		for(int i=0; i<countTues; i++) {
			HSSFCell cell94 = row10.createCell(2+countMon+i);
			cell94.setCellStyle(style4);
			if(tuesBeginList.size() > 0) {
				if(tuesBeginList.get(i) == 6) {
					cell94.setCellValue(tuesContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+tuesBeginList.get(i),3+tuesEndList.get(i),2+countMon+i,2+countMon+i));
					sheet.setColumnWidth(2+countMon+i, 10*256);
				}
			}
		}
		for(int i=0; i<countWed; i++) {
			HSSFCell cell95 = row10.createCell(2+countMon+countTues+i);
			cell95.setCellStyle(style4);
			if(wedBeginList.size() > 0) {
				if(wedBeginList.get(i) == 6) {
					cell95.setCellValue(wedContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+wedBeginList.get(i), 3+wedEndList.get(i), 2+countMon+countTues+i, 2+countMon+countTues+i));
					sheet.setColumnWidth(2+countMon+countTues+i, 10*256);
				}
			}
		}
		for(int i=0; i<countThur; i++) {
			HSSFCell cell96 = row10.createCell(2+countMon+countTues+countWed+i);
			cell96.setCellStyle(style4);
			if(thurBeginList.size() > 0) {
				if(thurBeginList.get(i) == 6) {
					cell96.setCellValue(thurContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+thurBeginList.get(i),3+thurEndList.get(i),2+countMon+countTues+countWed+i,2+countMon+countTues+countWed+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+i, 10*256);
				}
			}
		}
		for(int i=0; i<countFri; i++) {
			HSSFCell cell97 = row10.createCell(2+countMon+countTues+countWed+countThur+i);
			cell97.setCellStyle(style4);
			if(friBeginList.size() > 0) {
				if(friBeginList.get(i) == 6) {
					cell97.setCellValue(friContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+friBeginList.get(i),3+friEndList.get(i),2+countMon+countTues+countWed+countThur+i,2+countMon+countTues+countWed+countThur+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+countThur+i, 10*256);
				}
			}
		}
		for(int i=0; i<countSat; i++) {
			HSSFCell cell98 = row10.createCell(2+countMon+countTues+countWed+countThur+countFri+i);
			cell98.setCellStyle(style4);
			if(satBeginList.size() > 0) {
				if(satBeginList.get(i) == 6) {
					cell98.setCellValue(satContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+satBeginList.get(i), 3+satEndList.get(i), 2+countMon+countTues+countWed+countThur+countFri+i, 2+countMon+countTues+countWed+countThur+countFri+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+countThur+countFri+i, 10*256);
				}
			}
		}
		for(int i=0; i<countSun; i++) {
			HSSFCell cell99 = row10.createCell(2+countMon+countTues+countWed+countThur+countFri+countSat+i);
			cell99.setCellStyle(style4);
			if(sunBeginList.size() > 0) {
				if(sunBeginList.get(i) == 6) {
					cell99.setCellValue(sunContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+sunBeginList.get(i), 3+sunEndList.get(i), 2+countMon+countTues+countWed+countThur+countFri+countSat+i, 2+countMon+countTues+countWed+countThur+countFri+countSat+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+countThur+countFri+countSat+i, 10*256);
				}
			}
		}
		HSSFRow row11 = sheet.createRow(10);
		row11.setHeight((short)1500);
		/*HSSFCell cellA1 = row11.createCell(0);
		cellA1.setCellValue("下午");
		cellA1.setCellStyle(style2);*/
		HSSFCell cellA2 = row11.createCell(0);
		cellA2.setCellValue("第七节");
		cellA2.setCellStyle(style2);

		for(int i=0; i<countMon; i++) {
			HSSFCell cellA3 = row11.createCell(2+i);
			cellA3.setCellStyle(style4);
			if(monBeginList.size() > 0) {
				if(monBeginList.get(i) == 7) {
					cellA3.setCellValue(monContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+monBeginList.get(i),3+monEndList.get(i),2+i,2+i));
					sheet.setColumnWidth(2+i, 10*256);
				}
			}
		}
		for(int i=0; i<countTues; i++) {
			HSSFCell cellA4 = row11.createCell(2+countMon+i);
			cellA4.setCellStyle(style4);
			if(tuesBeginList.size() > 0) {
				if(tuesBeginList.get(i) == 7) {
					cellA4.setCellValue(tuesContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+tuesBeginList.get(i),3+tuesEndList.get(i),2+countMon+i,2+countMon+i));
					sheet.setColumnWidth(2+countMon+i, 10*256);
				}
			}
		}
		for(int i=0; i<countWed; i++) {
			HSSFCell cellA5 = row11.createCell(2+countMon+countTues+i);
			cellA5.setCellStyle(style4);
			if(wedBeginList.size() > 0) {
				if(wedBeginList.get(i) == 7) {
					cellA5.setCellValue(wedContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+wedBeginList.get(i),3+wedEndList.get(i),2+countMon+countTues+i,2+countMon+countTues+i));
					sheet.setColumnWidth(2+countMon+countTues+i, 10*256);
				}
			}
		}
		for(int i=0; i<countThur; i++) {
			HSSFCell cellA6 = row11.createCell(2+countMon+countTues+countWed+i);
			cellA6.setCellStyle(style4);
			if(thurBeginList.size() > 0) {
				if(thurBeginList.get(i) == 7) {
					cellA6.setCellValue(thurContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+thurBeginList.get(i),3+thurEndList.get(i),2+countMon+countTues+countWed+i,2+countMon+countTues+countWed+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+i, 10*256);
				}
			}
		}
		for(int i=0; i<countFri; i++) {
			HSSFCell cellA7 = row11.createCell(2+countMon+countTues+countWed+countThur+i);
			cellA7.setCellStyle(style4);
			if(friBeginList.size() > 0) {
				if(friBeginList.get(i) == 7) {
					cellA7.setCellValue(friContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+friBeginList.get(i),3+friEndList.get(i),2+countMon+countTues+countWed+countThur+i,2+countMon+countTues+countWed+countThur+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+countThur+i, 10*256);
				}
			}
		}
		for(int i=0; i<countSat; i++) {
			HSSFCell cellA8 = row11.createCell(2+countMon+countTues+countWed+countThur+countFri+i);
			cellA8.setCellStyle(style4);
			if(satBeginList.size() > 0) {
				if(satBeginList.get(i) == 7) {
					cellA8.setCellValue(satContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+satBeginList.get(i), 3+satEndList.get(i), 2+countMon+countTues+countWed+countThur+countFri+i, 2+countMon+countTues+countWed+countThur+countFri+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+countThur+countFri+i, 10*256);
				}
			}
		}
		for(int i=0; i<countSun; i++) {
			HSSFCell cellA9 = row11.createCell(2+countMon+countTues+countWed+countThur+countFri+countSat+i);
			cellA9.setCellStyle(style4);
			if(sunBeginList.size() > 0) {
				if(sunBeginList.get(i) == 7) {
					cellA9.setCellValue(sunContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+sunBeginList.get(i), 3+sunEndList.get(i), 2+countMon+countTues+countWed+countThur+countFri+countSat+i, 2+countMon+countTues+countWed+countThur+countFri+countSat+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+countThur+countFri+countSat+i, 10*256);
				}
			}
		}
		HSSFRow row12 = sheet.createRow(11);
		row12.setHeight((short)1500);
		HSSFCell cellB2 = row12.createCell(0);
		cellB2.setCellValue("第八节");
		cellB2.setCellStyle(style2);

		for(int i=0; i<countMon; i++) {
			HSSFCell cellB3 = row12.createCell(2+i);
			cellB3.setCellStyle(style4);
			if(monBeginList.size() > 0) {
				if(monBeginList.get(i) == 8) {
					cellB3.setCellValue(monContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+monBeginList.get(i),3+monEndList.get(i),2+i,2+i));
					sheet.setColumnWidth(2+i, 10*256);
				}
			}
		}
		for(int i=0; i<countTues; i++) {
			HSSFCell cellB4 = row12.createCell(2+countMon+i);
			cellB4.setCellStyle(style4);
			if(tuesBeginList.size() > 0) {
				if(tuesBeginList.get(i) == 8) {
					cellB4.setCellValue(tuesContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+tuesBeginList.get(i),3+tuesEndList.get(i),2+countMon+i,2+countMon+i));
					sheet.setColumnWidth(2+countMon+i, 10*256);
				}
			}
		}
		for(int i=0; i<countWed; i++) {
			HSSFCell cellB5 = row12.createCell(2+countMon+countTues+i);
			cellB5.setCellStyle(style4);
			if(wedBeginList.size() > 0) {
				if(wedBeginList.get(i) == 8) {
					cellB5.setCellValue(wedContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+wedBeginList.get(i),3+wedEndList.get(i),2+countMon+countTues+i,2+countMon+countTues+i));
					sheet.setColumnWidth(2+countMon+countTues+i, 10*256);
				}
			}
		}
		for(int i=0; i<countThur; i++) {
			HSSFCell cellB6 = row12.createCell(2+countMon+countTues+countWed+i);
			cellB6.setCellStyle(style4);
			if(thurBeginList.size() > 0) {
				if(thurBeginList.get(i) == 8) {
					cellB6.setCellValue(thurContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+thurBeginList.get(i),3+thurEndList.get(i),2+countMon+countTues+countWed+i,2+countMon+countTues+countWed+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+i, 10*256);
				}
			}
		}
		for(int i=0; i<countFri; i++) {
			HSSFCell cellB7 = row12.createCell(2+countMon+countTues+countWed+countThur+i);
			cellB7.setCellStyle(style4);
			if(friBeginList.size() > 0) {
				if(friBeginList.get(i) == 8) {
					cellB7.setCellValue(friContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+friBeginList.get(i),3+friEndList.get(i),2+countMon+countTues+countWed+countThur+i,2+countMon+countTues+countWed+countThur+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+countThur+i, 10*256);
				}
			}
		}
		for(int i=0; i<countSat; i++) {
			HSSFCell cellB8 = row12.createCell(2+countMon+countTues+countWed+countThur+countFri+i);
			cellB8.setCellStyle(style4);
			if(satBeginList.size() > 0) {
				if(satBeginList.get(i) == 8) {
					cellB8.setCellValue(satContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+satBeginList.get(i), 3+satEndList.get(i), 2+countMon+countTues+countWed+countThur+countFri+i, 2+countMon+countTues+countWed+countThur+countFri+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+countThur+countFri+i, 10*256);
				}
			}
		}
		for(int i=0; i<countSun; i++) {
			HSSFCell cellB9 = row12.createCell(2+countMon+countTues+countWed+countThur+countFri+countSat+i);
			cellB9.setCellStyle(style4);
			if(sunBeginList.size() > 0) {
				if(sunBeginList.get(i) == 8) {
					cellB9.setCellValue(sunContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+sunBeginList.get(i), 3+sunEndList.get(i), 2+countMon+countTues+countWed+countThur+countFri+countSat+i, 2+countMon+countTues+countWed+countThur+countFri+countSat+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+countThur+countFri+countSat+i, 10*256);
				}
			}
		}
		HSSFRow row13 = sheet.createRow(12);
		row13.setHeight((short)1500);
		HSSFCell cellC2 = row13.createCell(0);
		cellC2.setCellValue("第九节");
		cellC2.setCellStyle(style2);

		for(int i=0; i<countMon; i++) {
			HSSFCell cellC3 = row13.createCell(2+i);
			cellC3.setCellStyle(style4);
			if(monBeginList.size() > 0) {
				if(monBeginList.get(i) == 9) {
					cellC3.setCellValue(monContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+monBeginList.get(i),3+monEndList.get(i),2+i,2+i));
					sheet.setColumnWidth(2+i, 10*256);
				}
			}
		}
		for(int i=0; i<countTues; i++) {
			HSSFCell cellC4 = row13.createCell(2+countMon+i);
			cellC4.setCellStyle(style4);
			if(tuesBeginList.size() > 0) {
				if(tuesBeginList.get(i) == 9) {
					cellC4.setCellValue(tuesContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+tuesBeginList.get(i),3+tuesEndList.get(i),2+countMon+i,2+countMon+i));
					sheet.setColumnWidth(2+countMon+i, 10*256);
				}
			}
		}
		for(int i=0; i<countWed; i++) {
			HSSFCell cellC5 = row13.createCell(2+countMon+countTues+i);
			cellC5.setCellStyle(style4);
			if(wedBeginList.size() > 0) {
				if(wedBeginList.get(i) == 9) {
					cellC5.setCellValue(wedContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+wedBeginList.get(i),3+wedEndList.get(i),2+countMon+countTues+i,2+countMon+countTues+i));
					sheet.setColumnWidth(2+countMon+countTues+i, 10*256);
				}
			}
		}
		for(int i=0; i<countThur; i++) {
			HSSFCell cellC6 = row13.createCell(2+countMon+countTues+countWed+i);
			cellC6.setCellStyle(style4);
			if(thurBeginList.size() > 0) {
				if(thurBeginList.get(i) == 9) {
					cellC6.setCellValue(thurContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+thurBeginList.get(i),3+thurEndList.get(i),2+countMon+countTues+countWed+i,2+countMon+countTues+countWed+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+i, 10*256);
				}
			}
		}
		for(int i=0; i<countFri; i++) {
			HSSFCell cellC7 = row13.createCell(2+countMon+countTues+countWed+countThur+i);
			cellC7.setCellStyle(style4);
			if(friBeginList.size() > 0) {
				if(friBeginList.get(i) == 9) {
					cellC7.setCellValue(friContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+friBeginList.get(i),3+friEndList.get(i),2+countMon+countTues+countWed+countThur+i,2+countMon+countTues+countWed+countThur+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+countThur+i, 10*256);
				}
			}
		}
		for(int i=0; i<countSat; i++) {
			HSSFCell cellC8 = row13.createCell(2+countMon+countTues+countWed+countThur+countFri+i);
			cellC8.setCellStyle(style4);
			if(satBeginList.size() > 0) {
				if(satBeginList.get(i) == 9) {
					cellC8.setCellValue(satContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+satBeginList.get(i), 3+satEndList.get(i), 2+countMon+countTues+countWed+countThur+countFri+i, 2+countMon+countTues+countWed+countThur+countFri+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+countThur+countFri+i, 10*256);
				}
			}
		}
		for(int i=0; i<countSun; i++) {
			HSSFCell cellC9 = row13.createCell(2+countMon+countTues+countWed+countThur+countFri+countSat+i);
			cellC9.setCellStyle(style4);
			if(sunBeginList.size() > 0) {
				if(sunBeginList.get(i) == 9) {
					cellC9.setCellValue(sunContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+sunBeginList.get(i), 3+sunEndList.get(i), 2+countMon+countTues+countWed+countThur+countFri+countSat+i, 2+countMon+countTues+countWed+countThur+countFri+countSat+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+countThur+countFri+countSat+i, 10*256);
				}
			}
		}
		HSSFRow row14 = sheet.createRow(13);
		row14.setHeight((short)1500);
		HSSFCell cellD2 = row14.createCell(0);
		cellD2.setCellValue("第十节");
		cellD2.setCellStyle(style2);

		for(int i=0; i<countMon; i++) {
			HSSFCell cellD3 = row14.createCell(2+i);
			cellD3.setCellStyle(style4);
			if(monBeginList.size() > 0) {
				if(monBeginList.get(i) == 10) {
					cellD3.setCellValue(monContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+monBeginList.get(i),3+monEndList.get(i),2+i,2+i));
					sheet.setColumnWidth(2+i, 10*256);
				}
			}
		}
		for(int i=0; i<countTues; i++) {
			HSSFCell cellD4 = row14.createCell(2+countMon+i);
			cellD4.setCellStyle(style4);
			if(tuesBeginList.size() > 0) {
				if(tuesBeginList.get(i) == 10) {
					cellD4.setCellValue(tuesContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+tuesBeginList.get(i),3+tuesEndList.get(i),2+countMon+i,2+countMon+i));
					sheet.setColumnWidth(2+countMon+i, 10*256);
				}
			}
		}
		for(int i=0; i<countWed; i++) {
			HSSFCell cellD5 = row14.createCell(2+countMon+countTues+i);
			cellD5.setCellStyle(style4);
			if(wedBeginList.size() > 0) {
				if(wedBeginList.get(i) == 10) {
					cellD5.setCellValue(wedContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+wedBeginList.get(i),3+wedEndList.get(i),2+countMon+countTues+i,2+countMon+countTues+i));
					sheet.setColumnWidth(2+countMon+countTues+i, 10*256);
				}
			}
		}
		for(int i=0; i<countThur; i++) {
			HSSFCell cellD6 = row14.createCell(2+countMon+countTues+countWed+i);
			cellD6.setCellStyle(style4);
			if(thurBeginList.size() > 0) {
				if(thurBeginList.get(i) == 10) {
					cellD6.setCellValue(thurContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+thurBeginList.get(i),3+thurEndList.get(i),2+countMon+countTues+countWed+i,2+countMon+countTues+countWed+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+i, 10*256);
				}
			}
		}
		for(int i=0; i<countFri; i++) {
			HSSFCell cellD7 = row14.createCell(2+countMon+countTues+countWed+countThur+i);
			cellD7.setCellStyle(style4);
			if(friBeginList.size() > 0) {
				if(friBeginList.get(i) == 10) {
					cellD7.setCellValue(friContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+friBeginList.get(i),3+friEndList.get(i),2+countMon+countTues+countWed+countThur+i,2+countMon+countTues+countWed+countThur+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+countThur+i, 10*256);
				}
			}
		}
		for(int i=0; i<countSat; i++) {
			HSSFCell cellD8 = row14.createCell(2+countMon+countTues+countWed+countThur+countFri+i);
			cellD8.setCellStyle(style4);
			if(satBeginList.size() > 0) {
				if(satBeginList.get(i) == 10) {
					cellD8.setCellValue(satContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+satBeginList.get(i), 3+satEndList.get(i), 2+countMon+countTues+countWed+countThur+countFri+i, 2+countMon+countTues+countWed+countThur+countFri+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+countThur+countFri+i, 10*256);
				}
			}
		}
		for(int i=0; i<countSun; i++) {
			HSSFCell cellD9 = row14.createCell(2+countMon+countTues+countWed+countThur+countFri+countSat+i);
			cellD9.setCellStyle(style4);
			if(sunBeginList.size() > 0) {
				if(sunBeginList.get(i) == 10) {
					cellD9.setCellValue(sunContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+sunBeginList.get(i), 3+sunEndList.get(i), 2+countMon+countTues+countWed+countThur+countFri+countSat+i, 2+countMon+countTues+countWed+countThur+countFri+countSat+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+countThur+countFri+countSat+i, 10*256);
				}
			}
		}
		HSSFRow row15 = sheet.createRow(14);
		row15.setHeight((short)1500);
		HSSFCell cellE2 = row15.createCell(0);
		cellE2.setCellValue("第十一节");
		cellE2.setCellStyle(style2);

		for(int i=0; i<countMon; i++) {
			HSSFCell cellE3 = row15.createCell(2+i);
			cellE3.setCellStyle(style4);
			if(monBeginList.size() > 0) {
				if(monBeginList.get(i) == 11) {
					cellE3.setCellValue(monContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+monBeginList.get(i),3+monEndList.get(i),2+i,2+i));
					sheet.setColumnWidth(2+i, 10*256);
				}
			}
		}
		for(int i=0; i<countTues; i++) {
			HSSFCell cellE4 = row15.createCell(2+countMon+i);
			cellE4.setCellStyle(style4);
			if(tuesBeginList.size() > 0) {
				if(tuesBeginList.get(i) == 11) {
					cellE4.setCellValue(tuesContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+tuesBeginList.get(i),3+tuesEndList.get(i),2+countMon+i,2+countMon+i));
					sheet.setColumnWidth(2+countMon+i, 10*256);
				}
			}
		}
		for(int i=0; i<countWed; i++) {
			HSSFCell cellE5 = row15.createCell(2+countMon+countTues+i);
			cellE5.setCellStyle(style4);
			if(wedBeginList.size() > 0) {
				if(wedBeginList.get(i) == 11) {
					cellE5.setCellValue(wedContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+wedBeginList.get(i),3+wedEndList.get(i),2+countMon+countTues+i,2+countMon+countTues+i));
					sheet.setColumnWidth(2+countMon+countTues+i, 10*256);
				}
			}
		}
		for(int i=0; i<countThur; i++) {
			HSSFCell cellE6 = row15.createCell(2+countMon+countTues+countWed+i);
			cellE6.setCellStyle(style4);
			if(thurBeginList.size() > 0) {
				if(thurBeginList.get(i) == 11) {
					cellE6.setCellValue(thurContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+thurBeginList.get(i),3+thurEndList.get(i),2+countMon+countTues+countWed+i,2+countMon+countTues+countWed+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+i, 10*256);
				}
			}
		}
		for(int i=0; i<countFri; i++) {
			HSSFCell cellE7 = row15.createCell(2+countMon+countTues+countWed+countThur+i);
			cellE7.setCellStyle(style4);
			if(friBeginList.size() > 0) {
				if(friBeginList.get(i) == 11) {
					cellE7.setCellValue(friContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+friBeginList.get(i),3+friEndList.get(i),2+countMon+countTues+countWed+countThur+i,2+countMon+countTues+countWed+countThur+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+countThur+i, 10*256);
				}
			}
		}
		for(int i=0; i<countSat; i++) {
			HSSFCell cellE8 = row15.createCell(2+countMon+countTues+countWed+countThur+countFri+i);
			cellE8.setCellStyle(style4);
			if(satBeginList.size() > 0) {
				if(satBeginList.get(i) == 11) {
					cellE8.setCellValue(satContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+satBeginList.get(i), 3+satEndList.get(i), 2+countMon+countTues+countWed+countThur+countFri+i, 2+countMon+countTues+countWed+countThur+countFri+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+countThur+countFri+i, 10*256);
				}
			}
		}
		for(int i=0; i<countSun; i++) {
			HSSFCell cellE9 = row15.createCell(2+countMon+countTues+countWed+countThur+countFri+countSat+i);
			cellE9.setCellStyle(style4);
			if(sunBeginList.size() > 0) {
				if(sunBeginList.get(i) == 11) {
					cellE9.setCellValue(sunContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+sunBeginList.get(i), 3+sunEndList.get(i), 2+countMon+countTues+countWed+countThur+countFri+countSat+i, 2+countMon+countTues+countWed+countThur+countFri+countSat+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+countThur+countFri+countSat+i, 10*256);
				}
			}
		}
		HSSFRow row16 = sheet.createRow(15);
		row16.setHeight((short)1500);
		HSSFCell cellF2 = row16.createCell(0);
		cellF2.setCellValue("第十二节");
		cellF2.setCellStyle(style2);

		for(int i=0; i<countMon; i++) {
			HSSFCell cellF3 = row16.createCell(2+i);
			cellF3.setCellStyle(style4);
			if(monBeginList.size() > 0) {
				if(monBeginList.get(i) == 12) {
					cellF3.setCellValue(monContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+monBeginList.get(i),3+monEndList.get(i),2+i,2+i));
					sheet.setColumnWidth(2+i, 10*256);
				}
			}
		}
		for(int i=0; i<countTues; i++) {
			HSSFCell cellF4 = row16.createCell(2+countMon+i);
			cellF4.setCellStyle(style4);
			if(tuesBeginList.size() > 0) {
				if(tuesBeginList.get(i) == 12) {
					cellF4.setCellValue(tuesContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+tuesBeginList.get(i),3+tuesEndList.get(i),2+countMon+i,2+countMon+i));
					sheet.setColumnWidth(2+countMon+i, 10*256);
				}
			}
		}
		for(int i=0; i<countWed; i++) {
			HSSFCell cellF5 = row16.createCell(2+countMon+countTues+i);
			cellF5.setCellStyle(style4);
			if(wedBeginList.size() > 0) {
				if(wedBeginList.get(i) == 12) {
					cellF5.setCellValue(wedContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+wedBeginList.get(i),3+wedEndList.get(i),2+countMon+countTues+i,2+countMon+countTues+i));
					sheet.setColumnWidth(2+countMon+countTues+i, 10*256);
				}
			}
		}
		for(int i=0; i<countThur; i++) {
			HSSFCell cellF6 = row16.createCell(2+countMon+countTues+countWed+i);
			cellF6.setCellStyle(style4);
			if(thurBeginList.size() > 0) {
				if(thurBeginList.get(i) == 12) {
					cellF6.setCellValue(thurContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+thurBeginList.get(i),3+thurEndList.get(i),2+countMon+countTues+countWed+i,2+countMon+countTues+countWed+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+i, 10*256);
				}
			}
		}
		for(int i=0; i<countFri; i++) {
			HSSFCell cellF7 = row16.createCell(2+countMon+countTues+countWed+countThur+i);
			cellF7.setCellStyle(style4);
			if(friBeginList.size() > 0) {
				if(friBeginList.get(i) == 12) {
					cellF7.setCellValue(friContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+friBeginList.get(i),3+friEndList.get(i),2+countMon+countTues+countWed+countThur+i,2+countMon+countTues+countWed+countThur+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+countThur+i, 10*256);
				}
			}
		}
		for(int i=0; i<countSat; i++) {
			HSSFCell cellF8 = row16.createCell(2+countMon+countTues+countWed+countThur+countFri+i);
			cellF8.setCellStyle(style4);
			if(satBeginList.size() > 0) {
				if(satBeginList.get(i) == 11) {
					cellF8.setCellValue(satContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+satBeginList.get(i), 3+satEndList.get(i), 2+countMon+countTues+countWed+countThur+countFri+i, 2+countMon+countTues+countWed+countThur+countFri+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+countThur+countFri+i, 10*256);
				}
			}
		}
		for(int i=0; i<countSun; i++) {
			HSSFCell cellF9 = row16.createCell(2+countMon+countTues+countWed+countThur+countFri+countSat+i);
			cellF9.setCellStyle(style4);
			if(sunBeginList.size() > 0) {
				if(sunBeginList.get(i) == 11) {
					cellF9.setCellValue(sunContentList.get(i));
					sheet.addMergedRegion(new CellRangeAddress(3+sunBeginList.get(i), 3+sunEndList.get(i), 2+countMon+countTues+countWed+countThur+countFri+countSat+i, 2+countMon+countTues+countWed+countThur+countFri+countSat+i));
					sheet.setColumnWidth(2+countMon+countTues+countWed+countThur+countFri+countSat+i, 10*256);
				}
			}
		}
//		合并
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 1));
		sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 1));
		sheet.addMergedRegion(new CellRangeAddress(6, 6, 0, 1));
		sheet.addMergedRegion(new CellRangeAddress(7, 7, 0, 1));
		sheet.addMergedRegion(new CellRangeAddress(8, 8, 0, 1));
		sheet.addMergedRegion(new CellRangeAddress(9, 9, 0, 1));
		sheet.addMergedRegion(new CellRangeAddress(10, 10, 0, 1));
		sheet.addMergedRegion(new CellRangeAddress(11, 11, 0, 1));
		sheet.addMergedRegion(new CellRangeAddress(12, 12, 0, 1));
		sheet.addMergedRegion(new CellRangeAddress(13, 13, 0, 1));
		sheet.addMergedRegion(new CellRangeAddress(14, 14, 0, 1));
		sheet.addMergedRegion(new CellRangeAddress(15, 15, 0, 1));

		String sFileName = title + ".xls";
		response.setHeader("Content-Disposition", "attachment;filename="
				.concat(String.valueOf(URLEncoder.encode(sFileName, "UTF-8"))));
		response.setHeader("Connection", "close");
		response.setHeader("Content-Type", "application/vnd.ms-excel");

		wb.write(response.getOutputStream());
	}


	/**
	 * @Description 改写，时间段startTime-endTime
	 * @data 2019-01-10
	 * @author 张德冰
	 */
	public void exportExcelse(String title, String creator, String info, TableData tableData, String startTime, String endTime)
			throws Exception {

		HSSFWorkbook wb = new HSSFWorkbook();// 创建新的Excel 工作簿

		HashMap<String, HSSFCellStyle> styles = initStyles(wb);// 根据模板文件，初始化表头样式

		wb = writeSheetAdse(wb,title,info,styles,creator,tableData,startTime, endTime);//写入工作表

		String sFileName = title + ".xls";
		response.setHeader("Content-Disposition", "attachment;filename="
				.concat(String.valueOf(URLEncoder.encode(sFileName, "UTF-8"))));
		response.setHeader("Connection", "close");
		response.setHeader("Content-Type", "application/vnd.ms-excel");

		wb.write(response.getOutputStream());
	}
	/**
	 * 写入工作表
	 * @param wb Excel工作簿
	 * @param title Sheet工作表名称
	 * @param styles 表头样式
	 * @param creator 创建人
	 * @param tableData 表格数据
	 * @throws Exception
	 */
	public HSSFWorkbook writeSheetAdse(HSSFWorkbook wb, String title,String info, HashMap<String, HSSFCellStyle> styles, String creator, TableData tableData, String startTime, String endTime) throws Exception {

		TableHeaderMetaData headerMetaData = tableData.getTableHeader();// 获得HTML的表头元素

		SimpleDateFormat formater = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		SimpleDateFormat sdft = new SimpleDateFormat("yyyy-MM-dd");
		String create_time = formater.format(new Date());

		HSSFSheet sheet = wb.createSheet(title);// 在Excel工作簿中建一工作表
		sheet.setDisplayGridlines(false);// 设置表标题是否有表格边框

		//创建标题
		HSSFRow row = sheet.createRow(0);// 创建新行
		HSSFCell cell = row.createCell(0);// 创建新列
		int rownum = 0;
		cell.setCellValue(new HSSFRichTextString(title));
		HSSFCellStyle style = styles.get("TITLE");//设置标题样式
		if (style != null)
			cell.setCellStyle(style);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headerMetaData
				.getColumnCount() - 1));//合并标题行：起始行号，终止行号， 起始列号，终止列号

		//创建副标题
		row = sheet.createRow(1);
		if(creator != null && !creator.equals("")) {
			cell = row.createCell(0);
			cell.setCellValue(new HSSFRichTextString("创建人:"));
			style = styles.get("SUB_TITLE");
			if (style != null)
				cell.setCellStyle(style);

			cell = row.createCell(1);
			cell.setCellValue(new HSSFRichTextString(creator));
			style = styles.get("SUB_TITLE2");
			if (style != null)
				cell.setCellStyle(style);
		}
		/*if(create_time != null && !create_time.equals("")) {
			cell = row.createCell(2);
			cell.setCellValue(new HSSFRichTextString("创建时间:"));
			style = styles.get("SUB_TITLE");
			if (style != null)
				cell.setCellStyle(style);

			cell = row.createCell(3);
			style = styles.get("SUB_TITLE2");
			cell.setCellValue(new HSSFRichTextString(create_time));
			if (style != null)
				cell.setCellStyle(style);

		}*/

		if(startTime != null && endTime != null) {
			cell = row.createCell(4);
			cell.setCellValue(new HSSFRichTextString("时间段:"));
			style = styles.get("SUB_TITLE");
			if (style != null)
				cell.setCellStyle(style);
			String time = "";
			String t = "";
			if(!startTime.equals("")){
				Date st = sdft.parse(startTime);
				t = sdf.format(st);
			}
			time = t+"-";
			if(!endTime.equals("")){
				Date et = sdft.parse(endTime);
				t = sdf.format(et);
			}else {
				t = sdf.format(new Date());
			}
			time += t;
			cell = row.createCell(5);
			style = styles.get("SUB_TITLE2");
			cell.setCellValue(new HSSFRichTextString(time));
			if (style != null)
				cell.setCellStyle(style);
		}

		if(info != null && !info.equals("")) {
			cell = row.createCell(6);
			cell.setCellValue(new HSSFRichTextString("信息:"));
			style = styles.get("SUB_TITLE");
			if (style != null)
				cell.setCellStyle(style);

			cell = row.createCell(7);
			style = styles.get("SUB_TITLE2");
			cell.setCellValue(new HSSFRichTextString(info));
			if (style != null)
				cell.setCellStyle(style);
		}

		rownum = 3;// 如果rownum = 1，则去掉创建人、创建时间等副标题；如果rownum = 0， 则把标题也去掉

		HSSFCellStyle headerstyle = styles.get("TABLE_HEADER");

		int colnum = 0;
		for (int i = 0; i < headerMetaData.getOriginColumns().size(); i++) {
			TableColumn tc = headerMetaData.getOriginColumns().get(i);
			if (i != 0) {
				colnum += headerMetaData.getOriginColumns().get(i - 1)
						.getLength();
			}
			generateColumn(sheet, tc, headerMetaData.maxlevel, rownum, colnum,
					headerstyle);
		}
		rownum += headerMetaData.maxlevel;

		List<TableDataRow> dataRows = tableData.getRows();

		HashMap<Integer, Integer> counter = new HashMap<Integer, Integer>();
		HashMap<Integer, String> word = new HashMap<Integer, String>();
		int index = 0;
		for (TableDataRow dataRow : dataRows) {
			row = sheet.createRow(rownum);

			List<TableDataCell> dataCells = dataRow.getCells();
			int size = headerMetaData.getColumns().size();
			index = -1;
			for (int i = 0; i < size; i++) {
				TableColumn tc = headerMetaData.getColumns().get(i);
				if (!tc.isVisible())
					continue;
				index++;

				String value = dataCells.get(i).getValue();
				if (tc.isGrouped()) {
					String w = word.get(index);
					if (w == null) {
						word.put(index, value);
						counter.put(index, 1);
						createCell(row, tc, dataCells, i, index, styles);
					} else {
						if (w.equals(value)) {
							counter.put(index, counter.get(index) + 1);
						} else {
							stopGrouping(sheet, word, counter, index, size,
									rownum, styles.get("STRING"));

							word.put(index, value);
							counter.put(index, 1);
							createCell(row, tc, dataCells, i, index, styles);
						}
					}
				} else {
					createCell(row, tc, dataCells, i, index, styles);
				}
			}
			rownum++;
		}

		stopGrouping(sheet, word, counter, 0, index, rownum, styles
				.get("STRING"));
		// 设置前两列根据数据自动列宽
		for (int c = 0; c < headerMetaData.getColumns().size(); c++) {
			sheet.autoSizeColumn((short) c);
			String t = headerMetaData.getColumns().get(c).getDisplay();
			if(sheet.getColumnWidth(c)<t.length()*256*3){
				sheet.setColumnWidth(c, t.length()*256*3);
			}
			if(c==1){
				sheet.setColumnWidth(c, 7700);
			}
		}
		sheet.setGridsPrinted(true);

		return wb;
	}
	/**
	 *
	 * @param
	 * @return void
	 */
	public void generateColumn1(HSSFSheet sheet, TableColumn tc, int maxlevel,
								int rownum, int colnum, HSSFCellStyle headerstyle) {
		sheet.setColumnWidth(0, 5000);
		sheet.setColumnWidth(1, 7000);
		sheet.setColumnWidth(2, 3200);
		sheet.setColumnWidth(3, 3000);
		sheet.setColumnWidth(4, 4000);
		sheet.setColumnWidth(5, 8000);
		sheet.setColumnWidth(6, 2000);
		sheet.setColumnWidth(7, 2000);
		sheet.setColumnWidth(8, 2800);
		sheet.setColumnWidth(9, 3000);
		sheet.setColumnWidth(10, 2000);
		sheet.setColumnWidth(11, 3800);
		sheet.setColumnWidth(12, 3000);
		HSSFRow row = sheet.getRow(rownum);
		headerstyle.setWrapText(true);
		if (row == null){
			row = sheet.createRow(rownum);
			row.setHeight((short)(256*3));
		}

		HSSFCell cell = row.createCell(colnum);
		cell.setCellValue(tc.getDisplay());

		if (headerstyle != null)
			cell.setCellStyle(headerstyle);
		if (tc.isComplex()) {
			CellRangeAddress address = new CellRangeAddress(rownum, rownum,
					colnum, colnum + tc.getLength() - 1);
			sheet.addMergedRegion(address);
			fillMergedRegion(sheet, address, headerstyle);

			int cn = colnum;
			for (int i = 0; i < tc.getChildren().size(); i++) {
				if (i != 0) {
					cn = cn + tc.getChildren().get(i - 1).getLength();
				}
				generateColumn(sheet, tc.getChildren().get(i), maxlevel,
						rownum + 1, cn, headerstyle);
			}
		} else {
			CellRangeAddress address = new CellRangeAddress(rownum, rownum
					+ maxlevel - tc.level, colnum, colnum);
			sheet.addMergedRegion(address);
			fillMergedRegion(sheet, address, headerstyle);
		}
//		sheet.autoSizeColumn(colnum, true);
	}
}
