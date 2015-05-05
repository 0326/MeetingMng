package com.huiguanjia.authorityvalidate;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

import com.huiguanjia.dao.BaseDAO;
import com.huiguanjia.dao.OrdinaryUser;
import com.huiguanjia.dao.SessionDAO;

public class MeetingParticipatorValidate {

	/**
	 * @info 添加参会人员权限验证
	 * @param cellphone
	 * @param meetingId
	 * @param userList
	 * @return
	 */
	public int addParticipatorValidate(String cellphone,String meetingId,List<String> userList)
	{
		int res = 0;
		
		//判断要被添加的参会人员列表是否为空
		if(0 == userList.size())
		{
			return -2;
		}
		
		BaseDAO aBaseDao = new BaseDAO();
		Session sess = SessionDAO.getSession();
		
		//判断是否为状态是已同意的办会人员
		String hql = "select mo.id.organizerCellphone from MeetingOrganizer as mo " +
				"where mo.id.meetingId = ? and mo.id.organizerCellphone = ? and mo.state = 2";
		Object[] values = new Object[]{meetingId,cellphone};
		List tmpList = aBaseDao.findObjectByHql(hql, values);
		if(0 == tmpList.size())
		{
			return -1;
		}
		
		//验证要被添加为参会人员的信息是否有效
		Iterator it = userList.iterator();
		while(true == it.hasNext())
		{
			String userStr = (String)it.next();
			String hql1 = "select ou.cellphone from OrdinaryUser as ou " +
					"where ou.cellphone = ? and ou.isRegister = true";
			Object[] values1 = new Object[]{userStr};
			List tmpList1 = aBaseDao.findObjectByHql(hql1, values1);
			if(0 == tmpList1.size())
			{
				res = -2;
				break;
			}
		}
		
		SessionDAO.closeSession();
		
		return res;
	}
	
	/**
	 * @info 查找参会人员列表权限验证
	 * @param cellphone
	 * @param meetingId
	 * @return
	 */
	public int findParticipatorValidate(String cellphone,String meetingId)
	{
		int res;
		
		BaseDAO aBaseDao = new BaseDAO();
		Session sess = SessionDAO.getSession();
		
		//判断是否是状态为待定或已同意的办会人员
		String hql1 = "select mo.state from MeetingOrganizer as mo " +
				"where mo.id.meetingId = ? and mo.id.organizerCellphone = ?";
		Object[] values1 = new Object[]{meetingId,cellphone};
		List tmpList1 = aBaseDao.findObjectByHql(hql1, values1);
		if(1 == tmpList1.size())
		{
			int state = (Integer)tmpList1.get(0);
			if((2 == state) || (3 == state))
			{
				return 1;
			}
		}
		
		//判断是否是状态为已同意或待定或已签到的参会人员
		String hql2 = "select mp.state from MeetingParticipator as mp " +
				"where mp.id.meetingId = ? and mp.id.participatorCellphone = ?";
		Object[] values2 = new Object[]{meetingId,cellphone};
		List tmpList2 = aBaseDao.findObjectByHql(hql2, values2);
		if(1 == tmpList2.size())
		{
			int state = (Integer)tmpList2.get(0);
			if((2 == state) || (3 == state) || (4 == state))
			{
				res = 2;
			}
			else
			{
				res = -1;
			}
		}
		else
		{
			res = -1;
		}
		
		SessionDAO.closeSession();
		
		return res;
	}
	
	/**
	 * @info 删除参会人员权限和数据有效性验证
	 * @param cellphone
	 * @param meetingId
	 * @param userList
	 * @return
	 */
	public boolean deleteParticipatorValidate(String cellphone,String meetingId,List<String> userList)
	{
		boolean res = true;
		
		BaseDAO aBaseDao = new BaseDAO();
		Session sess = SessionDAO.getSession();
		
		String hql1 = "select mo.id.organizerCellphone from MeetingOrganizer as mo " +
				"where mo.id.meetingId = ? and mo.id.organizerCellphone = ? and mo.state = 2";
		Object[] values1 = new Object[]{meetingId,cellphone};
		List tmpList1 = aBaseDao.findObjectByHql(hql1, values1);
		if(0 == tmpList1.size())
		{
			SessionDAO.closeSession();
			return false;
		}
		
		Iterator it = userList.iterator();
		while(true == it.hasNext())
		{
			String userStr = (String)it.next();
			String hql2 = "select mp.id.participatorCellphone from MeetingParticipator as mp " +
					"where mp.id.meetingId = ? and mp.id.participatorCellphone = ?";
			Object[] values2 = new Object[]{meetingId,userStr};
			List tmpList2 = aBaseDao.findObjectByHql(hql2, values2);
			if(0 == tmpList2.size())
			{
				res = false;
				break;
			}
		}
			
		SessionDAO.closeSession();
		
		return res;
	}
	
	/**
	 * @info 更新参会人员状态权限验证和数据有效性验证
	 * @param cellphone
	 * @param meetingId
	 * @param operatedCellphone
	 * @param state
	 * @return
	 */
	public boolean updateParticipatorValidate(String cellphone,String meetingId,String operatedCellphone,int state)
	{
		boolean res;
		
		BaseDAO aBaseDao = new BaseDAO();
		Session sess = SessionDAO.getSession();
		
		String hql1 = "select mo.id.organizerCellphone from MeetingOrganizer as mo " +
				"where mo.id.meetingId = ? and mo.id.organizerCellphone = ? and mo.state = 2";
		Object[] values1 = new Object[]{meetingId,cellphone};
		List tmpList1 = aBaseDao.findObjectByHql(hql1, values1);
		if(1 == tmpList1.size())              //如果是状态为已同意的办会人员
		{
			String hql2 = "select mp.state from MeetingParticipator as mp " +
					"where mp.id.meetingId = ? and mp.id.participatorCellphone = ?";
			Object[] values2 = new Object[]{meetingId,operatedCellphone};
			List tmpList2 = aBaseDao.findObjectByHql(hql2, values2);
			if(1 == tmpList2.size())         //如果被修改状态的人是该会议的参会人员
			{
				int tmpInt = (Integer)tmpList2.get(0);
				if(4 == tmpInt)             //如果参会人员状态为已签到，则不可以被修改状态
				{
					res = false;
				}
				else      //判断要修改为的状态是否为已同意、待定或已签到
				{
					if((2 == state) || (3 == state) || (4 == state))
					{
						res = true;
					}
					else
					{
						res = false;
					}
				}
			}
			else
			{
				res = false;
			}
		}
		else                  
		{
			//判断是否是状态为非未发送的参会人员
			String hql3 = "select mp.state from MeetingParticipator as mp " +
					"where mp.id.meetingId = ? and mp.id.participatorCellphone = ?";
			Object[] values3 = new Object[]{meetingId,cellphone};
			List tmpList3 = aBaseDao.findObjectByHql(hql3, values3);
			if(1 == tmpList3.size())           //如果操作者是该会议的参会人员
			{
				int tmpInt = (Integer)tmpList3.get(0);
				if((0 != tmpInt) && (4 != tmpInt))        //如果状态不是未发送或已签到
				{
					if(cellphone == operatedCellphone)
					{
						//判断要修改为的状态是否是同意或待定或退出
						if((2 == state) || (3 == state) || (5 == state))
						{
							res = true;
						}
						else
						{
							res = false;
						}
					}
					else
					{
						res = false;
					}
				}
				else
				{
					res = false;
				}
			}
			else                              //如果操作者不是该会议的参会人员
			{
				res = false;
			}
		}
		
		SessionDAO.closeSession();
		
		return res;
	}
}
