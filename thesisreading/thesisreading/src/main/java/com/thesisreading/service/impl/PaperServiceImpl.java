package com.thesisreading.service.impl;

import com.thesisreading.mapper.TopicpaperMapper;
import com.thesisreading.model.Topicpaper;
import com.thesisreading.utils.RequestUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.thesisreading.model.PaperExample;
import com.thesisreading.model.Paper;
import com.thesisreading.mapper.PaperMapper;
import com.thesisreading.service.PaperService;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PaperServiceImpl implements PaperService{

    @Resource
    HttpServletRequest request;

    @Resource
    private PaperMapper paperMapper;

    @Resource
    private TopicpaperMapper  topicpaperMapper;

    @Override
    public long countByExample(PaperExample example) {
        return paperMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(PaperExample example) {
        return paperMapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Integer paperId) {
        return paperMapper.deleteByPrimaryKey(paperId);
    }

    @Override
    public int insert(Paper record) {
        return paperMapper.insert(record);
    }

    @Override
    public int insertSelective(Paper record) {
        return paperMapper.insertSelective(record);
    }

    @Override
    public List<Paper> selectByExample(PaperExample example) {
        return paperMapper.selectByExample(example);
    }

    @Override
    public Paper selectByPrimaryKey(Integer paperId) {
        return paperMapper.selectByPrimaryKey(paperId);
    }


    @Override
    public int updateByExampleSelective(Paper record,PaperExample example) {
        return paperMapper.updateByExampleSelective(record,example);
    }

    @Override
    public int updateByExample(Paper record,PaperExample example) {
        return paperMapper.updateByExample(record,example);
    }

    @Override
    public int updateByPrimaryKeySelective(Paper record) {
        return paperMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Paper record) {
        return paperMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateBatch(List<Paper> list) {
        return paperMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<Paper> list) {
        return paperMapper.batchInsert(list);
    }

    @Override
    public List<Paper> selectAll(){return paperMapper.selectAll();}

    @Override
    public boolean batchImport(String fileName, MultipartFile file, String topicId) throws Exception {
        boolean notNull = false;
        List<Paper> paperList = new ArrayList<>();
        List<Topicpaper> topicpaperList=new ArrayList<>();
        String ext = fileName.substring(fileName.lastIndexOf("."));
        System.out.println(ext);
        if(!ext.equals(".xls")&&!ext.equals(".xlsx"))
        {
            throw new Exception("上传文件格式不正确");
        }
        boolean isExcel2003 = true;
        if(ext.equals(".xlsx"))
        {
            isExcel2003=false;
        }
        InputStream is = file.getInputStream();
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        if(sheet!=null){
            notNull = true;
        }
        else
        {
            System.out.println("is Null");
        }

        // studentId
        String studentId = RequestUtils.getLoginId(request);

        // uploadingTime
        Date uploadingTime = new Date();
        System.out.println(sheet.getLastRowNum());
        // 文件中的每一行
        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null){
                continue;
            }

            // title
            row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
            String title = row.getCell(0).getStringCellValue();
            if(title==null || title.isEmpty()){
                throw new Exception("导入失败(第"+(r+1)+"行,title未填写)");
            }

            // author
            row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
            String author = row.getCell(1).getStringCellValue();
            if(author==null || author.isEmpty()){
                throw new Exception("导入失败(第"+(r+1)+"行,author未填写)");
            }
            // relevancy
            row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
            Double  relevancy= Double.parseDouble(row.getCell(2).getStringCellValue());
            if(relevancy==null){
                throw new Exception("导入失败(第"+(r+1)+"行,relevancy未填写)");
            }
            // source
            row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
            String source = row.getCell(3).getStringCellValue();
            if(source==null || source.isEmpty()){
                throw new Exception("导入失败(第"+(r+1)+"行,source未填写)");
            }
            // keyword
            row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
            String keyword = row.getCell(4).getStringCellValue();
            if(keyword==null || keyword.isEmpty()){
                throw new Exception("导入失败(第"+(r+1)+"行,keyword未填写)");
            }
            // abstractText
            row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
            String abstractText = row.getCell(5).getStringCellValue();
            if(abstractText==null || abstractText.isEmpty()){
                throw new Exception("导入失败(第"+(r+1)+"行,abstractText未填写)");
            }
            // docLocation
            row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
            String docLocation = row.getCell(6).getStringCellValue();
            if(docLocation==null || docLocation.isEmpty()){
                throw new Exception("导入失败(第"+(r+1)+"行,docLocationeyword未填写)");
            }

            /**
             * paperId; String title; String author;String source;String keyword;String abstractText;
             * String docLocation;String studentId;Date uploadingTime;
             *
             */

            Paper paper = Paper.builder().title(title).author(author)
                    .source(source).keyword(keyword).abstractText(abstractText)
                    .docLocation(docLocation).studentId(studentId).uploadingTime(uploadingTime).build();
            paperList.add(paper);

            String stuTopicId = studentId + topicId;
            Topicpaper topicpaper = Topicpaper.builder().relevancy(relevancy)
                    .stutopicId(stuTopicId).build();
            topicpaperList.add(topicpaper);

        }

        // 插入或更新
        for (int i=0;i<paperList.size();i++) {

            Paper paper=paperList.get(i);
            Topicpaper topicpaper=topicpaperList.get(i);
            paperMapper.insert(paper);
            Integer paperId=paper.getPaperId();

            System.out.println(paperId);

            topicpaper.setPaperId(paperId);
            topicpaperMapper.insert(topicpaper);

        }
        return notNull;
    }
    @Override
    public List<Paper> selectByPage( int pageIndex, int pageSize) {
        Integer pageIndexStart = (pageIndex - 1) * pageSize;
        return paperMapper.selectByPage(pageIndexStart, pageSize);
    }

}
