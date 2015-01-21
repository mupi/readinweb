package br.unicamp.iel.dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.transform.ResultTransformer;
import org.sakaiproject.genericdao.hibernate.HibernateCompleteGenericDao;
import org.sakaiproject.site.api.Site;

import br.unicamp.iel.model.reports.UserAccess;

public class ReadInWebDaoImpl extends HibernateCompleteGenericDao 
	implements ReadInWebDao {

	private static Log log = LogFactory.getLog(ReadInWebDaoImpl.class);

	public void init() {
		log.debug("init");
	}

	public List<Site> getReadInWebSites(){
		Site s = null;
		return null;
	}

	public List<UserAccess> getUserAccessesReport(String userId) {
		String sql = ""
				+ "SELECT "
				+ "	min(time) as primeiro, "
				+ "	max(time) as ultimo, "
				+ "	concat(activity_id, '- ', title) as atividade, "
				+ "	count(*) as total "
				+ "FROM "
				+ "	readinweb_accesses left join readinweb_activities "
				+ "		on (activity_id=readinweb_activities.id) "
				+ "WHERE "
				+ "	user_id=? "
				//+ " AND site_id=?"
				+ "GROUP BY activity_id "
				+ "ORDER BY ultimo desc";
		SQLQuery query = this.getSession().createSQLQuery(sql);
		query.setString(0, userId);
		
		query.setResultTransformer(new ResultTransformer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Object transformTuple(Object[] tuple, String[] aliases) {
				UserAccess ua = new UserAccess((Date)tuple[0], (Date)tuple[1], 
						(String)tuple[2], (BigInteger)tuple[3]);
				return ua;
			}
			
			@Override
			public List transformList(List list) {
				return list;
			}
		});
		
		//query.setString(1, "");
		return (List<UserAccess>)query.list();
	}
}
