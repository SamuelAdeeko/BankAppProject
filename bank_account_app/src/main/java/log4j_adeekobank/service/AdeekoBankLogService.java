package log4j_adeekobank.service;

import org.apache.log4j.Logger;

public class AdeekoBankLogService {

	

	private static Logger log = Logger.getLogger(AdeekoBankLogService.class);
//	
//	public void hellolog() {
//		log.info("Inval");
//		log.info("bye bye from log service helloLog()");
//	}
	public void servicelog(String message) {
		log.info(message);

	}
//	public void invalidLog() {
//		log.info();
//
//	}
}
