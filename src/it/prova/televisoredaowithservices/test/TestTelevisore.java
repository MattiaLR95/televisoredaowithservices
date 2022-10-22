package it.prova.televisoredaowithservices.test;

import java.text.SimpleDateFormat;
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
		
		testFindById(televisoreService);
		System.out.println();

		testDelete(televisoreService);
		System.out.println();

		testUpdate(televisoreService);
		System.out.println();
		
		testTrovaTelevisoreTraDate(televisoreService);
		System.out.println();
		
		testTrovaTelevisorePiuGrande(televisoreService);
		System.out.println();
		
		testNomeMarcheUltimiSeiMesi(televisoreService);
		System.out.println();
	}

	public static void testInsert(TelevisoreService televisoreService) throws Exception {
		System.out.println("Inizio test insert");

		Televisore televisoreDaAggiungere = new Televisore("Samsung", "Smart TV", 50, new Date());
		if (televisoreService.insert(televisoreDaAggiungere) != 1)
			throw new RuntimeException("Test insert: FAILED");
		System.out.println("Test Completato.");
	}
	
	private static void testFindById(TelevisoreService televisoreService) throws Exception {
		System.out.println("\n___inizio testFindById...");
		if (televisoreService.list().isEmpty())
			throw new RuntimeException("testFindById : FAILED, il DB e' vuoto.");
		Long idCercato = televisoreService.list().get(0).getId();
		Televisore result = televisoreService.findById(idCercato);
		if (!result.getId().equals(televisoreService.list().get(0).getId()))
			throw new RuntimeException("testFindById : FAILED, gli id non coincidono.");
		System.out.println("___fine testFindById : PASSED");
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

	public static void testUpdate(TelevisoreService televisoreService) throws Exception {
		System.out.println(".......testUpdateUser inizio.............");

		// inserisco i dati che poi modifico
		if (televisoreService.insert(new Televisore("Sony", "Afdf", 45,new Date())) != 1)
			throw new RuntimeException("testUpdateTelevisore: inserimento preliminare FAILED ");

		// recupero col findbyexample e mi aspetto di trovarla
		List<Televisore> risultatifindByExample = televisoreService.findByExample(new Televisore("Samsung", "Smart TV"));
		if (risultatifindByExample.size() == 1)
			throw new RuntimeException("testUpdateTelevisore: testFindByExample FAILED ");

		// mi metto da parte l'id su cui lavorare per il test
		Long idSamsung = risultatifindByExample.get(0).getId();

		// ricarico per sicurezza con l'id individuato e gli modifico un campo
		String nuovoModello = "4k";
		Televisore toBeUpdated = televisoreService.findById(idSamsung);
		toBeUpdated.setModello(nuovoModello);
		if (televisoreService.update(toBeUpdated) != 1)
			throw new RuntimeException("testUpdateUser FAILED ");
	}

	public static void testTrovaTelevisoreTraDate(TelevisoreService televisoreService) throws Exception {
		System.out.println("Inizio test trovaTelevisoreTraDate");
		List<Televisore> totaleItem = televisoreService.list();
		if (totaleItem.isEmpty() || totaleItem.get(0) == null)
			throw new Exception("Errore! Nessun elemento nel database");
		java.util.Date inizio = new Date(21 - 01 - 2021);
		java.util.Date fine = new Date(21 - 01 - 2023);

		System.out.println(televisoreService.trovaTelevisioniTraDate(inizio, fine));

		System.out.println("Test trovaTelevisoreTraDate: FINE");
	}
	
	private static void testTrovaTelevisorePiuGrande(TelevisoreService televisoreService)throws Exception{
		System.out.println("\n___inizio testFindTelevisorePiuGrande...");
		if (televisoreService.list().isEmpty())
			throw new RuntimeException("Errore! Nessun elemento nel database");
		Televisore result = televisoreService.trovaTelevisionePiuGrande();
		if (!result.getId().equals(televisoreService.list().get(0).getId()))
			throw new RuntimeException("testFindTelevisorePiuGrande : FAILED, la ricerca non ha prodotto i risultati desiderati.");
		System.out.println("___fine testFindTelevisorePiuGrande : PASSED");
	}
	
	private static void testNomeMarcheUltimiSeiMesi(TelevisoreService televisoreService)throws Exception{
		System.out.println("\n___inizio testListaMarcheTelevisoriProdottiUltimiSeiMesi...");
		Date negliUltimiSeiMesi = new SimpleDateFormat("dd-MM-yyyy").parse("01-09-2022");
		Televisore prodottoNegliUltimiSeiMesi = new Televisore(null, "Sony", "KD43X72K", 43,
				negliUltimiSeiMesi);
		if (televisoreService.insert(prodottoNegliUltimiSeiMesi) < 1)
			throw new RuntimeException("testListaMarcheTelevisoriProdottiUltimiSeiMesi : FAILED, inserimento preliminare non avvenuto.");
		java.util.Date data6Mesi = new Date(21 - 04 - 2022);
		List<String> result = televisoreService.nomeMarcheUltimiSeiMesi(data6Mesi);
		if (result.isEmpty())
			throw new RuntimeException("testListaMarcheTelevisoriProdottiUltimiSeiMesi : FAILED, la ricerca non ha prodotto i risultati desiderati.");
		televisoreService.delete(televisoreService.list().get(0));
		System.out.println("___fine testListaMarcheTelevisoriProdottiUltimiSeiMesi : PASSED");
	}
}
