package selab.sogang.bundle;

import java.util.Collection;
import java.util.Properties;

import org.osgi.framework.Constants;
import org.twdata.pkgscanner.ExportPackage;
import org.twdata.pkgscanner.PackageScanner;





/**
 * Because it is somehow hard to use conf/config.properties file in android, I build up properties object hard coded for now
 * @author matthiasneubert
 *
 */
public class FelixConfig {

	private Properties configProps;

	public FelixConfig(String absFilePath){

		// first of all: analyze the classpath / classloader
		analyzeClassPath();



		configProps = new Properties();

		//org.osgi.framework.storage=${felix.cache.rootdir}/felixcache
		configProps.put("org.osgi.framework.storage", absFilePath+"/felix/cache");

		// felix.cache.rootdir=${user.dir}
		configProps.put("felix.cache.rootdir",absFilePath+"/felix");


		// fileinstall watch dir
		configProps.put("felix.fileinstall.dir", absFilePath+"/felix/newbundle"); //"felix.fileinstall.dir";
		configProps.put("felix.fileinstall.debug", "1"); //"felix.fileinstall.debug";

		// instead of exporting concrete packages we export them via boot delegation directly
		// Advantage: wildcards are supported.
		//configProps.put("org.osgi.framework.bootdelegation", BOOT_DELEGATION_PACKAGES);

		// export packeages to provide them for the bundles
		configProps.put("org.osgi.framework.system.packages.extra", ANDROID_FRAMEWORK_PACKAGES_ext);


		// nicht ben�tigt wg. InstallFromRActivator -> w�rde nur vom AutoActivator verarbeitet werden
		// dieser ist aber nicht android Filesystem compatibel
		/*
		 * felix.auto.start.1= \
 file:bundle/shell.jar \
 file:bundle/shelltui.jar \
 file:bundle/bundlerepository.jar \
 file:bundle/ipojo.jar \
 file:bundle/ipojoannotations.jar
		 */

		// felix.log.level=4
		configProps.put("felix.log.level", "4");
		// felix.startlevel.bundle=1
		configProps.put("felix.startlevel.bundle", "1");
		// obr.repository.url=http://felix.apache.org/obr/releases.xml
		configProps.put("obr.repository.url","http://felix.apache.org/obr/releases.xml");
		// osgi.shell.telnet=on
		configProps.put("osgi.shell.telnet", "on");
		// org.osgi.service.http.port=8080
		configProps.put("org.osgi.service.http.port", "8080");
		//
		configProps.put(Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA, "android.content");
		
		configProps.put(Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA, "com.libary.services");
		
		configProps.put(Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA, "maplebundle");
		
		configProps.put(Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA, "mapebundle.ctxmonitor");

		configProps.put("android.content",Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA);
		
		configProps.put("com.libary.services",Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA);
		
		configProps.put("maplebundle",Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA);
		
		configProps.put("mapebundle.ctxmonitor",Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA);
		

	}

	public Properties getConfigProps() {
		return configProps;
	}


	private String analyzedExportString ="";

	// package scanner
	private void analyzeClassPath(){


		PackageScanner pkgScanner = new PackageScanner();

		// set usage of classloader to avoid NPE in internal scanner of PackageScanner
		pkgScanner.useClassLoader(PackageScanner.class.getClassLoader().getParent());
		//FelixConfig.class.getClassLoader()   ClassLoader.getSystemClassLoader()
		//Collection<ExportPackage> exports = pkgScanner.scan();

		Collection<ExportPackage> exports = pkgScanner
				.select
				(

						PackageScanner.jars(
								PackageScanner.include
								(
										"*.jar"),
										PackageScanner.exclude(
												"felix.jar",
												"package*.jar")
								),

								PackageScanner.packages(
										PackageScanner.include
										(
												"org.*",
												"com.*",
												"javax.*",
												"android",
												"android.*",
												"com.android.*",
												"dalvik.*",
												"java.*",
												"junit.*",
												"org.apache.*",
												"org.json",
												"org.xml.*",	
												"org.xmlpull.*",
												"org.w3c.*",
												"textbundle.*",
												"com.libary.services",
												"mapebundle.ctxmonitor",
												"mpaebundle.*",
												"userbehaviormodel.*",
												"selab.dev.uiselfadaptive.*",
												"badsymptomchecker.*",
												"usabilityimprover.*")
										)
						)


						.scan();


		System.out.println("HIER: "+exports.size());
		// now fill analyzedExportString
		while (exports.iterator().hasNext()){
			System.out.println("exports: "+ exports.iterator().next().getPackageName());
		}

	}












	//org.osgi.framework.bootdelegation=
	private static final String BOOT_DELEGATION_PACKAGES = (
			"org.osgi.*," +
					"android.*," +
					"com.google.android.*," +
					"javax.*," +
					"org.apache.commons.*," +
					"org.bluez," + 
					"org.json," + 
					"org.w3c.dom," +  
					"org.xml.*"
			).intern();


	private static final String ANDROID_FRAMEWORK_PACKAGES = (

			"mapebundle"
			).intern();

	private static final String ANDROID_FRAMEWORK_PACKAGES_ext = (
			"org.osgi.framework; version=1.4.0," +
					"org.osgi.service.packageadmin; version=1.2.0," +
					"org.osgi.service.startlevel; version=1.0.0," +
					"org.osgi.service.url; version=1.0.0," +
					"org.osgi.util.tracker," +
					// ANDROID (here starts semicolon as separator -> Why?
					"android; " + 
					"android.app;" + 
					"android.content;" + 
					"android.database;" + 
					"android.database.sqlite;" + 
					"android.graphics; " + 
					"android.graphics.drawable; " + 
					"android.graphics.glutils; " + 
					"android.hardware; " + 
					"android.location; " + 
					"android.media; " + 
					"android.net; " + 
					"android.opengl; " + 
					"android.os; " + 
					"android.provider; " + 
					"android.sax; " + 
					"android.speech.recognition; " + 
					"android.telephony; " + 
					"android.telephony.gsm; " + 
					"android.text; " + 
					"android.text.method; " + 
					"android.text.style; " + 
					"android.text.util; " + 
					"android.util; " + 
					"android.view; " + 
					"android.view.animation; " + 
					"android.webkit; " + 
					"android.widget; " +
					//MAPS
					"com.google.android.maps; " + 
					"com.google.android.xmppService; " + 
					// JAVAx
					"javax.crypto; " + 
					"javax.crypto.interfaces; " + 
					"javax.crypto.spec; " + 
					"javax.microedition.khronos.opengles; " + 
					"javax.net; " + 
					"javax.net.ssl; " + 
					"javax.security.auth; " + 
					"javax.security.auth.callback; " + 
					"javax.security.auth.login; " + 
					"javax.security.auth.x500; " + 
					"javax.security.cert; " + 
					"javax.sound.midi; " + 
					"javax.sound.midi.spi; " + 
					"javax.sound.sampled; " + 
					"javax.sound.sampled.spi; " + 
					"javax.sql; " + 
					"javax.xml.parsers; " + 
					//JUNIT
					"junit.extensions; " + 
					"junit.framework; " + 
					//APACHE
					"org.apache.commons.codec; " + 
					"org.apache.commons.codec.binary; " + 
					"org.apache.commons.codec.language; " + 
					"org.apache.commons.codec.net; " + 
					"org.apache.commons.httpclient; " + 
					"org.apache.commons.httpclient.auth; " + 
					"org.apache.commons.httpclient.cookie; " + 
					"org.apache.commons.httpclient.methods; " + 
					"org.apache.commons.httpclient.methods.multipart; " + 
					"org.apache.commons.httpclient.params; " + 
					"org.apache.commons.httpclient.protocol; " + 
					"org.apache.commons.httpclient.util; " + 

            //OTHERS
            "org.bluez; " + 
            "org.json; " + 
            "org.w3c.dom; " + 
            "org.xml.sax; " + 
            "org.xml.sax.ext; " + 
            "org.xml.sax.helpers; " + 

            // Android OS Version?? ->her ends semicolon as seperator -> Why?
            "version=1.5.0.r3," +

            // MY OWN
            "mapebundle; " +
            "userbehaviormodel;" +
            "selab.dev.uiselfadaptive; " +
            "badsymptomchecker; " +
            "usabilityimprover; " +
            "com.libary.services;"

			).intern();



}
