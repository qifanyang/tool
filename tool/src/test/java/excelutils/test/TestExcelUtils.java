package excelutils.test;


import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.tobe.excelutils.ExcelRunner;
import com.tobe.excelutils.SelectSQL;
import com.tobe.excelutils.handler.BeanHandler;
import com.tobe.excelutils.handler.BeanListHandler;
import com.tobe.excelutils.test.ActivityVO;

public class TestExcelUtils {
	
	@Test
	public void testIgnore() throws Exception {
		
		SelectSQL sql = new SelectSQL();
		sql.ignore("name").ignore("start");
//		sql.select("singledesc");
//		sql.where("name", "单笔充值");
		
		ExcelRunner runner = new ExcelRunner("/商业活动.xlsx");
		
		ActivityVO vo = runner.query(sql, new BeanHandler<ActivityVO>(ActivityVO.class));
		
		runner = new ExcelRunner("/商业活动.xlsx");
		
		List<ActivityVO> list = runner.query(sql, new BeanListHandler<ActivityVO>(ActivityVO.class));
		
		System.out.println("");
	}
	
	@Test
	public void testSelectField() throws Exception{
		SelectSQL sql = new SelectSQL();
		sql.select("singledesc");
//		sql.where("name", "单笔充值");
		
		ExcelRunner runner = new ExcelRunner("/商业活动.xlsx");
		
		ActivityVO vo = runner.query(sql, new BeanHandler<ActivityVO>(ActivityVO.class));
		
		Assert.assertNull(vo.getEnd());//只查询singledesc字段,end字段应为空
		
		runner = new ExcelRunner("/商业活动.xlsx");
		
		List<ActivityVO> list = runner.query(sql, new BeanListHandler<ActivityVO>(ActivityVO.class));
		
		System.out.println("");
	}
	
	@Test
	public void testWhere() throws Exception{
		SelectSQL sql = new SelectSQL();
		sql.where("name", "单笔充值");//查询条件,name字段值必须是单笔充值,不是的不放入查询结果中
		
		ExcelRunner runner = new ExcelRunner("/商业活动.xlsx");
		
		ActivityVO vo = runner.query(sql, new BeanHandler<ActivityVO>(ActivityVO.class));
		Assert.assertEquals(1, vo.getGroupId());
		
		runner = new ExcelRunner("/商业活动.xlsx");
		
		List<ActivityVO> list = runner.query(sql, new BeanListHandler<ActivityVO>(ActivityVO.class));
		
		for(ActivityVO v : list){
			Assert.assertEquals("单笔充值", v.getName());
		}
		System.out.println("");
	}

}
