package cn.wengsj.mms.service.impl;

import cn.wengsj.mms.dao.IDrugDao;
import cn.wengsj.mms.dao.impl.DrugDao;
import cn.wengsj.mms.model.Drug;
import cn.wengsj.mms.model.PageBean;
import cn.wengsj.mms.service.IDrugService;

import java.sql.SQLException;
import java.util.List;

public class DrugService implements IDrugService {

    IDrugDao drugDao = new DrugDao();

    @Override
    public PageBean<Drug> selectDrugsByPage(int page, int limit, String condition) throws SQLException {
        PageBean<Drug> pageBean = new PageBean<>();
        // 设置数据的总数
        pageBean.setTotalCount(drugDao.selectCount());
        // 设置需要展示的数据的页码，即第几页的数据
        pageBean.setCurrentPage(page);
        // 设置该页所展示的数据的条数
        pageBean.setPageCount(limit);

        int begin = limit * (page - 1);
        List<Drug> list = null;
        // 根据condition判断是否是模糊条件检索
        // conditon非空则为模糊条件列表检索
        // 反之进行普通检索
        if (condition != null) {
            list = drugDao.listBySearch(begin, limit, condition);
        } else {
            list = drugDao.selectByPage(begin, limit);
        }
        pageBean.setPageData(list);
        return pageBean;
    }

    @Override
    public int save(Drug entity) throws SQLException {
        return drugDao.save(entity);
    }

    @Override
    public int delete(Integer id) throws SQLException {
        return drugDao.delete(id);
    }

    @Override
    public int updateDrug(Drug entity) throws SQLException {
        return drugDao.updateDrug(entity);
    }

}
