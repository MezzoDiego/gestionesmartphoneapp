package it.prova.gestionesmartphoneapp.test;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import it.prova.gestionesmartphoneapp.dao.EntityManagerUtil;
import it.prova.gestionesmartphoneapp.model.App;
import it.prova.gestionesmartphoneapp.model.Smartphone;
import it.prova.gestionesmartphoneapp.service.MyServiceFactory;
import it.prova.gestionesmartphoneapp.service.app.AppService;
import it.prova.gestionesmartphoneapp.service.smartphone.SmartphoneService;

public class TestSmartphoneApp {

	public static void main(String[] args) {

		SmartphoneService smartphoneServiceInstance = MyServiceFactory.getSmartphoneServiceInstance();
		AppService appServiceInstance = MyServiceFactory.getAppServiceInstance();

		try {
			System.out.println("##################################################################################");
			// testInserimentoNuovoSmartphone(smartphoneServiceInstance);
			System.out.println("##################################################################################");
			// testInserimentoNuovaApp(appServiceInstance);
			System.out.println("##################################################################################");
			// testAggiornamentoVersioneOSSmartphone(smartphoneServiceInstance);
			System.out.println("##################################################################################");
			// testAggiornamentoVersioneAppEDataAggiornamento(appServiceInstance);
			System.out.println("##################################################################################");
			// testInstallazioneApp(appServiceInstance, smartphoneServiceInstance);
			System.out.println("##################################################################################");
			// testDisinstallazioneApp(appServiceInstance, smartphoneServiceInstance);
			System.out.println("##################################################################################");
			// testRimozioneSmartphoneAssociatoADueApp(appServiceInstance,
			// smartphoneServiceInstance);
			System.out.println("##################################################################################");

		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			// questa è necessaria per chiudere tutte le connessioni quindi rilasciare il
			// main
			EntityManagerUtil.shutdown();
		}

	}

	private static void testInserimentoNuovoSmartphone(SmartphoneService smartphoneServiceInstance) throws Exception {
		System.out.println(".......testInserimentoNuovoSmartphone inizio.............");

		// creo smartphone e lo inserisco
		Smartphone smartphoneInstance = new Smartphone("Samsung", "Galaxy a5", 700, "Android 2.15.1");
		smartphoneServiceInstance.inserisciNuovo(smartphoneInstance);

		// verifica corretto inserimento
		if (smartphoneInstance.getId() == null)
			throw new RuntimeException("testInserimentoNuovoSmartphone fallito ");

		// reset tabella
		smartphoneServiceInstance.rimuovi(smartphoneInstance.getId());
		System.out.println(".......testInserimentoNuovoSmartphone fine: PASSED.............");
	}

	private static void testInserimentoNuovaApp(AppService appServiceInstance) throws Exception {
		System.out.println(".......testInserimentoNuovaApp inizio.............");

		// creo app e la inserisco
		Date dataInstallazione = new SimpleDateFormat("dd-MM-yyyy").parse("03-01-2022");
		Date dataAggiornamento = new SimpleDateFormat("dd-MM-yyyy").parse("19-05-2022");

		App appInstance = new App("Instagram", dataInstallazione, dataAggiornamento, "6.12.2");
		appServiceInstance.inserisciNuovo(appInstance);

		// verifica corretto inserimento
		if (appInstance.getId() == null)
			throw new RuntimeException("testInserimentoNuovaApp fallito ");

		// reset tabella
		appServiceInstance.rimuovi(appInstance.getId());

		System.out.println(".......testInserimentoNuovaApp fine: PASSED.............");
	}

	private static void testAggiornamentoVersioneOSSmartphone(SmartphoneService smartphoneServiceInstance)
			throws Exception {
		System.out.println(".......testAggiornamentoVersioneOSSmartphone inizio.............");

		// creo smartphone e lo inserisco
		Smartphone smartphoneInstance = new Smartphone("Samsung", "Galaxy a5", 700, "Android 2.15.1");
		smartphoneServiceInstance.inserisciNuovo(smartphoneInstance);

		// verifica corretto inserimento
		if (smartphoneInstance.getId() == null)
			throw new RuntimeException("testAggiornamentoVersioneOSSmartphone fallito: smartphone non inserito. ");

		// esecuzione query aggiornamento
		String vecchiaVersione = smartphoneInstance.getVersioneOS();
		smartphoneInstance.setVersioneOS("Android 2.15.2");
		smartphoneServiceInstance.aggiorna(smartphoneInstance);

		if (smartphoneInstance.getVersioneOS().equals(vecchiaVersione))
			throw new RuntimeException(
					"testAggiornamentoVersioneOSSmartphone FAILED: si e'verificato un errore durante la modifica della versione.");

		// reset tabella
		smartphoneServiceInstance.rimuovi(smartphoneInstance.getId());

		System.out.println(".......testAggiornamentoVersioneOSSmartphone fine: PASSED.............");

	}

	private static void testAggiornamentoVersioneAppEDataAggiornamento(AppService appServiceInstance) throws Exception {

		System.out.println(".......testAggiornamentoVersioneAppEDataAggiornamento inizio.............");

		// creo app e la inserisco
		Date dataInstallazione = new SimpleDateFormat("dd-MM-yyyy").parse("03-01-2022");
		Date dataAggiornamento = new SimpleDateFormat("dd-MM-yyyy").parse("19-05-2022");

		App appInstance = new App("Instagram", dataInstallazione, dataAggiornamento, "6.12.2");
		appServiceInstance.inserisciNuovo(appInstance);

		// verifica corretto inserimento
		if (appInstance.getId() == null)
			throw new RuntimeException("testAggiornamentoVersioneAppEDataAggiornamento FAILED: app non inserita! ");

		// esecuzione update
		String vecchiaVersione = appInstance.getVersione();
		Date vecchiaData = appInstance.getDataUltimoAggiornamento();
		appInstance.setVersione("6.13.0");
		appInstance.setDataUltimoAggiornamento(new Date());
		appServiceInstance.aggiorna(appInstance);

		if (appInstance.getVersione().equals(vecchiaVersione)
				|| appInstance.getDataUltimoAggiornamento().equals(vecchiaData))
			throw new RuntimeException(
					"testAggiornamentoVersioneAppEDataAggiornamento FAILED: update non eseguito correttamente.");

		// reset tabella
		appServiceInstance.rimuovi(appInstance.getId());

		System.out.println(".......testAggiornamentoVersioneAppEDataAggiornamento fine: PASSED.............");

	}

	private static void testInstallazioneApp(AppService appServiceInstance, SmartphoneService smartphoneServiceInstance)
			throws Exception {

		System.out.println(".......testInstallazioneApp inizio.............");

		// creo smartphone e lo inserisco
		Smartphone smartphoneInstance = new Smartphone("Samsung", "Galaxy a5", 700, "Android 2.15.1");
		smartphoneServiceInstance.inserisciNuovo(smartphoneInstance);

		// verifica corretto inserimento
		if (smartphoneInstance.getId() == null)
			throw new RuntimeException("testAggiornamentoVersioneOSSmartphone fallito: smartphone non inserito. ");

		// creo app e la inserisco
		Date dataInstallazione = new SimpleDateFormat("dd-MM-yyyy").parse("03-01-2022");
		Date dataAggiornamento = new SimpleDateFormat("dd-MM-yyyy").parse("19-05-2022");

		App appInstance = new App("Instagram", dataInstallazione, dataAggiornamento, "6.12.2");
		appServiceInstance.inserisciNuovo(appInstance);

		// verifica corretto inserimento
		if (appInstance.getId() == null)
			throw new RuntimeException("testAggiornamentoVersioneAppEDataAggiornamento FAILED: app non inserita! ");

		// installazione della app nello smartphone
		smartphoneServiceInstance.aggiungiApp(smartphoneInstance, appInstance);

		// verifica corretta installazione ricaricando smartphone con strategia eager
		Smartphone smartphoneReloaded = smartphoneServiceInstance
				.caricaSingoloElementoEagerApps(smartphoneInstance.getId());

		if (smartphoneReloaded.getApps().isEmpty())
			throw new RuntimeException("testInstallazioneApp FAILED: installazione non avvenuta correttamente.");

		// reset tabelle
		smartphoneServiceInstance.rimuoviTuttiGliSmartphoneDallaTabellaDiJoin();
		smartphoneServiceInstance.rimuovi(smartphoneInstance.getId());
		appServiceInstance.rimuovi(appInstance.getId());

		System.out.println(".......testInstallazioneApp fine: PASSED.............");

	}

	private static void testDisinstallazioneApp(AppService appServiceInstance,
			SmartphoneService smartphoneServiceInstance) throws Exception {

		System.out.println(".......testDisinstallazioneApp inizio.............");

		// creo smartphone e lo inserisco
		Smartphone smartphoneInstance = new Smartphone("Samsung", "Galaxy a5", 700, "Android 2.15.1");
		smartphoneServiceInstance.inserisciNuovo(smartphoneInstance);

		// verifica corretto inserimento
		if (smartphoneInstance.getId() == null)
			throw new RuntimeException("testDisinstallazioneApp fallito: smartphone non inserito. ");

		// creo app e la inserisco
		Date dataInstallazione = new SimpleDateFormat("dd-MM-yyyy").parse("03-01-2022");
		Date dataAggiornamento = new SimpleDateFormat("dd-MM-yyyy").parse("19-05-2022");

		App appInstance = new App("Instagram", dataInstallazione, dataAggiornamento, "6.12.2");
		appServiceInstance.inserisciNuovo(appInstance);

		// verifica corretto inserimento
		if (appInstance.getId() == null)
			throw new RuntimeException("testDisinstallazioneApp FAILED: app non inserita! ");

		// installazione della app nello smartphone
		smartphoneServiceInstance.aggiungiApp(smartphoneInstance, appInstance);

		// verifica corretta installazione ricaricando smartphone con strategia eager
		Smartphone smartphoneReloaded = smartphoneServiceInstance
				.caricaSingoloElementoEagerApps(smartphoneInstance.getId());

		if (smartphoneReloaded.getApps().isEmpty())
			throw new RuntimeException("testDisinstallazioneApp FAILED: installazione non avvenuta correttamente.");

		// ora la disinstallo e verifico se effettivamente e' stata disinstallata
		appServiceInstance.rimuoviAppDallaTabellaDiJoin(appInstance.getId());

		// lo ricarico eager
		smartphoneReloaded = smartphoneServiceInstance.caricaSingoloElementoEagerApps(smartphoneInstance.getId());

		// verifico
		if (!smartphoneReloaded.getApps().isEmpty())
			throw new RuntimeException("testDisinstallazioneApp FAILED: disinstallazione non avvenuta correttamente.");

		// reset tabelle
		smartphoneServiceInstance.rimuovi(smartphoneInstance.getId());
		appServiceInstance.rimuovi(appInstance.getId());

		System.out.println(".......testDisinstallazioneApp fine: PASSED.............");

	}

	private static void testRimozioneSmartphoneAssociatoADueApp(AppService appServiceInstance,
			SmartphoneService smartphoneServiceInstance) throws Exception {

		System.out.println(".......testRimozioneSmartphoneAssociatoADueApp inizio.............");

		// creo smartphone e lo inserisco
		Smartphone smartphoneInstance = new Smartphone("Samsung", "Galaxy a5", 700, "Android 2.15.1");
		smartphoneServiceInstance.inserisciNuovo(smartphoneInstance);

		// verifica corretto inserimento
		if (smartphoneInstance.getId() == null)
			throw new RuntimeException("testRimozioneSmartphoneAssociatoADueApp fallito: smartphone non inserito. ");

		// creo due app e le inserisco
		Date dataInstallazione = new SimpleDateFormat("dd-MM-yyyy").parse("03-01-2022");
		Date dataAggiornamento = new SimpleDateFormat("dd-MM-yyyy").parse("19-05-2022");

		App appInstance = new App("Instagram", dataInstallazione, dataAggiornamento, "6.12.2");
		appServiceInstance.inserisciNuovo(appInstance);

		// verifica corretto inserimento
		if (appInstance.getId() == null)
			throw new RuntimeException("testRimozioneSmartphoneAssociatoADueApp FAILED: app non inserita! ");

		App appInstance1 = new App("Whatsapp", dataInstallazione, dataAggiornamento, "2.1.1");
		appServiceInstance.inserisciNuovo(appInstance1);

		// verifica corretto inserimento
		if (appInstance1.getId() == null)
			throw new RuntimeException("testRimozioneSmartphoneAssociatoADueApp FAILED: app non inserita! ");

		// installazione delle app nello smartphone
		smartphoneServiceInstance.aggiungiApp(smartphoneInstance, appInstance);
		smartphoneServiceInstance.aggiungiApp(smartphoneInstance, appInstance1);

		// verifica corretta installazione ricaricando smartphone con strategia eager
		Smartphone smartphoneReloaded = smartphoneServiceInstance
				.caricaSingoloElementoEagerApps(smartphoneInstance.getId());

		if (smartphoneReloaded.getApps().isEmpty())
			throw new RuntimeException(
					"testRimozioneSmartphoneAssociatoADueApp FAILED: installazione non avvenuta correttamente.");

		// rimozione smartphone e verifica rimozione
		smartphoneServiceInstance.rimuoviSmartphoneDallaTabellaDiJoin(smartphoneInstance.getId());

		App appReloaded = appServiceInstance.caricaSingoloElementoEagerSmartphones(appInstance.getId());

		if (!appReloaded.getSmartphones().isEmpty())
			throw new RuntimeException(
					"testRimozioneSmartphoneAssociatoADueApp FAILED: si e'verificato un errore durante la rimozione dello smartphone.");

		// reset tabelle
		smartphoneServiceInstance.rimuovi(smartphoneInstance.getId());
		appServiceInstance.rimuovi(appInstance.getId());
		appServiceInstance.rimuovi(appInstance1.getId());

		System.out.println(".......testRimozioneSmartphoneAssociatoADueApp fine: PASSED.............");

	}

}
