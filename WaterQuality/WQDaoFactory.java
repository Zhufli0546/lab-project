package WaterQuality;

import java.io.IOException;

public class WQDaoFactory {
	public static WQDao createWQDao() throws IOException {
		WQDao dao = new WQDaoJdbcImpl();
		return dao ;
	}
}
