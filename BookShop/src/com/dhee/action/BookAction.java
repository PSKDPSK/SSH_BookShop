package com.dhee.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

}
