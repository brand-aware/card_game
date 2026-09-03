/**
 * @author mike802
 * @version 1.0 - 2/28/2013
 */
package core;

import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

import core.doc.IProperties;

public class Properties implements IProperties{
	
	private URL coverPath;
	private URL emptyPath;
	private ConcurrentHashMap<String, String> suitPaths = 
			new ConcurrentHashMap<String, String>();
	
	private URL logo;
	private URL company;
	private URL company_iframe;
	private URL background;

	public Properties(){
		String rootDir = "/";
		coverPath = getClass().getResource(rootDir + "cards/cover.png");
		emptyPath = getClass().getResource(rootDir + "cards/empty.png");
		
		String cards = rootDir + "cards" + "/";
		suitPaths.put("clubs", cards + "clubs");
		suitPaths.put("diamonds", cards + "diamonds");
		suitPaths.put("hearts", cards + "hearts");
		suitPaths.put("spades", cards + "spades");
		
		logo = getClass().getResource(rootDir + "img/logo.png");
		company = getClass().getResource(rootDir + "img/company.png");
		company_iframe = getClass().getResource(rootDir + "img/company_iframe.png");
		background = getClass().getResource(rootDir + "img/background.png");
	}

	public String suitPath(String suit){
		return suitPaths.get(suit);
	}
	
	public URL getCoverPath(){
		return coverPath;
	}
	public URL getEmptyPath(){
		return emptyPath;
	}
	public URL getLogo(){
		return logo;
	}
	public URL getCompany(){
		return company;
	}
	public URL getCompanyIframe(){
		return company_iframe;
	}
	public URL getBackground(){
		return background;
	}
}
