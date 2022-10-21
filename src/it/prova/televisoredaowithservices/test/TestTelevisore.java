package it.prova.televisoredaowithservices.test;

import it.prova.televisoredaowithservices.connection.MyConnection;
import it.prova.televisoredaowithservices.service.MyServiceFactory;
import it.prova.televisoredaowithservices.service.televisore.TelevisoreService;

public class TestTelevisore {

	public static void main(String[] args) {
		TelevisoreService televisoreService= MyServiceFactory.getTelevisoreServiceImpl();
	}
	
	public static void testList (TelevisoreService televisoreService) {}

}
