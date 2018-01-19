package com.dhee.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.aspectj.util.FileUtil;

import com.dhee.dao.BookDao;
import com.dhee.dao.CategoryDao;
import com.dhee.vo.BooksVo;
import com.dhee.vo.CategorysVo;
import com.opensymphony.xwork2.ActionSupport;
/**
 * ͼ������߼���
 */
public class BookAction extends ActionSupport implements RequestAware{

	private static final long serialVersionUID = -3498173228078093869L;
	private CategoryDao categoryDao;
	private Map<String,Object> request;
	private BooksVo book;
	private File img;
	private String imgContentType;
	private String imgFileName;
	private BookDao bookDao;
	private String id;
	private String caid;
	
	

	public String getCaid() {
		return caid;
	}

	public void setCaid(String caid) {
		this.caid = caid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	public File getImg() {
		return img;
	}

	public void setImg(File img) {
		this.img = img;
	}

	public String getImgContentType() {
		return imgContentType;
	}

	public void setImgContentType(String imgContentType) {
		this.imgContentType = imgContentType;
	}

	public String getImgFileName() {
		return imgFileName;
	}

	public void setImgFileName(String imgFileName) {
		this.imgFileName = imgFileName;
	}

	public BooksVo getBook() {
		return book;
	}

	public void setBook(BooksVo book) {
		this.book = book;
	}

	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}
	
	public void setRequest(Map<String, Object> request) {
		
		this.request = request;
		
	}


	/**
	 * �ṩͼ����ӵĽ���
	 * @return
	 * @throws Exception
	 */
	public String addUI() throws Exception {
		
		List<CategorysVo> list = categoryDao.findAll();
		request.put("categorys", list);
		
		return SUCCESS;
	}
	
	/**
	 * ͼ����Ϣ�����
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception{
		
		/*book.setName(new String(book.getName().getBytes("iso-8859-1"),"utf-8"));
		book.setAuthor(new String(book.getAuthor().getBytes("iso-8859-1"),"utf-8"));
		book.setDescription(new String(book.getDescription().getBytes("iso-8859-1"),"utf-8"));*/
		
		String realPath=ServletActionContext.getServletContext().getRealPath("/images");
		File file = new File(realPath);
		
		/*setImgFileName(UUID.randomUUID().toString() + ".gif");
		System.out.println(imgFileName);*/
		book.setImage(imgFileName);
		
		if(!file.exists())
            file.mkdirs();
		
		//new File(imgRealPath,this.uploadImageFileName) ��˼����ǰ�ߵ�Ŀ¼�´�����ߵ��ļ�
	    //�±���˼�Ǹ����ļ�����ǰ�ߵ��ļ����Ƶ���ߵ��ļ���
		FileUtil.copyFile(img, new File(realPath, imgFileName));
		
		//�������Լ���д��io���ļ��ϴ����������رպ󣬲��ú����Զ�ɾ��
		/*FileOutputStream out=
                new FileOutputStream(new File(file,getImgFileName()));
		
		FileInputStream in=new FileInputStream(getImg());
		
		
		byte[] buffer=new byte[1024];
        int len=0;
        while((len=in.read(buffer))>0)
            out.write(buffer,0,len);
        out.close();
        in.close();*/
		
        bookDao.add(book);
        request.put("message", "��ӳɹ�������");
		
		return "yes";
	}
	
	/**
	 * ��̨��ѯ����ͼ����Ϣ
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception{
		
		List<BooksVo> list = bookDao.list();
		request.put("books", list);
		
		return "list";
	}
	
	/**
	 * ɾ��ͼ��
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception{
		
		int bookid = Integer.parseInt(id);
		List<BooksVo> list = bookDao.findById(bookid);
		for(BooksVo b :list) {
			book = new BooksVo();
			book = b;
		}
		//��Ҫɾ����
		bookDao.delete(book);
		request.put("message", "ɾ���ɹ�!!!");
		
		return "delete";
	}
	
	/**
	 * ��ѯ��ָ��ͼ��
	 * @return
	 * @throws Exception
	 */
	public String findById() throws Exception{
		
		int bookid = Integer.parseInt(id);
		List<BooksVo> list = bookDao.findById(bookid);
		for(BooksVo b :list) {
			book = new BooksVo();
			book = b;
		}
		request.put("book", book);
		
		
		//��ѯָ������
		CategorysVo ca = categoryDao.findById(book.getCategory_id() + "");
		request.put("ca", ca);
		
		//��ѯ��������
		List<CategorysVo> categorys = categoryDao.findAll();
		request.put("categorys", categorys);
		
		return "find";
	}
	
	/**
	 * �޸�ͼ��
	 * @return
	 * @throws Exception
	 */
	public String update() throws Exception{
		
		bookDao.update(book);
		request.put("message", "�޸�ͼ��ɹ�!!!");
		
		return "update";
	}
	
	/**
	 * ͨ��ͼ�����Ͳ�ѯͼ��
	 * @return
	 * @throws Exception
	 */
	public String findByCid() throws Exception{
		
		int id = Integer.parseInt(caid);
		List<BooksVo> list = bookDao.findByCid(id);
		request.put("cbooks", list);
		return "findByCid";
	}
	
	/**
	 * ��̨ͼ�����ݵ�����Excel��
	 * @return
	 * @throws Exception
	 */
	public String table2Excel() throws Exception{
		
		//���ñ�ͷ
		String[] headName = {"ͼ������","����","�۸�","����"};
		
		//�������
		int cellNumber = headName.length;
		
		//����һ��Excel
		HSSFWorkbook workBook = new HSSFWorkbook();
		
		//�����
		HSSFCell cell = null;
		
		//�����
		HSSFRow row = null;
		
		//���ñ�ͷ����
		HSSFCellStyle headStyle = workBook.createCellStyle();
		headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		//�������ݾ���
		HSSFCellStyle dataStyle = workBook.createCellStyle();
		dataStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		//��������
		HSSFFont font = workBook.createFont();
		
		//����һ��sheet����ӦExcel��sheet
		HSSFSheet sheet = workBook.createSheet("sheet1");
		
		//�õ�sheet��ͷ
		HSSFHeader header = sheet.getHeader();
		
		//�õ�����ͼ������
		List<BooksVo> books = bookDao.list();
		
		//����õ�������Ϊ��
		if(books.size() < 1 || books == null) {
			header.setCenter("��������");
		}else {
			//��������ݣ�����ͷ������
			header.setCenter("ͼ���");
			//���ñ���1�У�����ͷ
			row = sheet.createRow(0);
			//�����и�
			row.setHeight((short) 400);
			
			//���ñ�ͷֵ
			for(int i = 0; i < cellNumber; i++) {
				//������1�е�i��
				cell = row.createCell(i);
				//���õ�1�е�i�е����ݣ�������ͷ���ý�ȥ
				cell.setCellValue(headName[i]);
				
				sheet.setColumnWidth(i,8000);//�����еĿ��  
			    font.setColor(HSSFFont.COLOR_NORMAL); // ���õ�Ԫ���������ɫ.  
			    font.setFontHeight((short)350); //���õ�Ԫ����߶�  
			    headStyle.setFont(font);//����������  
			    cell.setCellStyle(headStyle);  
			}
			
			//�������
			for(int i = 0; i < books.size();i++) {
				BooksVo book = books.get(i);
				row = sheet.createRow(i + 1);
				row.setHeight((short) 400);
				if(book.getName() != null) {
					cell = row.createCell(0);
					cell.setCellValue(book.getName());
					cell.setCellStyle(dataStyle);
				}
				if(book.getAuthor() != null) {
					cell = row.createCell(1);
					cell.setCellValue(book.getAuthor());
					cell.setCellStyle(dataStyle);
				}
				if(book.getPrice() != 0) {
					cell = row.createCell(2);
					cell.setCellValue(book.getPrice());
					cell.setCellStyle(dataStyle);
				}
				if(book.getDescription() != null) {
					cell = row.createCell(3);
					cell.setCellValue(book.getDescription());
					cell.setCellStyle(dataStyle);
				}
			}
			
			HttpServletResponse response = ServletActionContext.getResponse();
			OutputStream out = response.getOutputStream();
			
			response.setHeader("Content-disposition","attachment; filename=" + "book" + ".xls");//filename�����ص�xls���������������Ӣ��  
            response.setContentType("application/msexcel;charset=UTF-8");//��������  
            response.setHeader("Pragma","No-cache");//����ͷ  
            response.setHeader("Cache-Control","no-cache");//����ͷ  
            response.setDateHeader("Expires", 0);//��������ͷ  
            
            workBook.write(out);
            out.flush();
            
            if(out != null) {
            	out.close();
            }
            
		}
		
		return null;
	}

}
