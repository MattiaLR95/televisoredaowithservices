package it.prova.televisoredaowithservices.test;

import java.util.Date;
import java.util.List;

import it.prova.televisoredaowithservices.model.Televisore;
import it.prova.televisoredaowithservices.service.MyServiceFactory;
import it.prova.televisoredaowithservices.service.televisore.TelevisoreService;

public class TestTelevisore {

	public static void main(String[] args) throws Exception {
		TelevisoreService televisoreService = MyServiceFactory.getTelevisoreServiceImpl();

		testInsert(televisoreService);
		System.out.println();

		//testDelete(televisoreService);
		System.out.println();
		
		testUpdate(televisoreService);
		System.out.println();
	}

	public static void testInsert(TelevisoreService televisoreService) throws Exception {
		System.out.println("Inizio test insert");

		Televisore televisoreDaAggiungere = new Televisore("Samsung", "Smart TV", 50, new Date());
		if (televisoreService.insert(televisoreDaAggiungere) != 1)
			throw new RuntimeException("Test insert: FAILED");
		System.out.println("Test Completato.");
	}

	public static void testDelete(TelevisoreService televisoreService) throws Exception {
		System.out.println("Inizio test delete");

		List<Televisore> totaleItem = televisoreService.list();
		if (totaleItem.isEmpty() || totaleItem.get(0) == null)
			throw new Exception("Errore! Nessun elemento nel database");
		
		Televisore primoItem = totaleItem.get(0);
		System.out.println(televisoreService.delete(primoItem) + " row affected");
		
		if (totaleItem.size() == totaleItem.size() - 1)
			throw new Exception("Test delete: FAILED");
		System.out.println("Test completato");
	}
	
	public static void testUpdate (TelevisoreService televisoreService) throws Exception{
		System.out.println("Inizio test update");
		
		List<Televisore> totaleItem=televisoreService.list();
		if (totaleItem.isEmpty() || totaleItem.get(0) == null)
			throw new Exception("Errore! Nessun elemento nel database");
		
		Televisore elementoDaModificare=new Televisore(1l,"LG","Absd",42,new Date());
		System.out.println(televisoreService.update(elementoDaModificare));
		System.out.println(totaleItem.get(0));
	}

}
