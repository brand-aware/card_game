/**
 * @author mike802
 * @version 1.0 - 2/28/2013
 */
package core;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

import core.doc.IProperties;

public class Properties implements IProperties{
	
	private String coverPath;
	private String emptyPath;
	private ConcurrentHashMap<String, String> suitPaths = 
			new ConcurrentHashMap<String, String>();
	
	private String logo;
	private String company;
	private String company_iframe;
	private String background;

	public Properties(){
		String rootDir = System.getProperty("user.dir");
		coverPath = rootDir + File.separator + "cards" + File.separator + "cover.png";
		emptyPath = rootDir + File.separator + "cards" + File.separator + "empty.png";
		
		String cards = rootDir + File.separator + "cards" + File.separator;
		suitPaths.put("clubs", cards + "clubs");
		suitPaths.put("diamonds", cards + "diamonds");
		suitPaths.put("hearts", cards + "hearts");
		suitPaths.put("spades", cards + "spades");
		
		logo = rootDir + File.separator + "img" + File.separator + "logo.png";
		company = rootDir + File.separator + "img" + File.separator + "company.png";
		company_iframe = rootDir + File.separator + "img" + File.separator + "company_iframe.png";
		background = rootDir + File.separator + "img" + File.separator + "background.png";
	}

	public String suitPath(String suit){
		return suitPaths.get(suit);
	}
	
	public String getCoverPath(){
		return coverPath;
	}
	public String getEmptyPath(){
		return emptyPath;
	}
	public String getLogo(){
		return logo;
	}
	public String getCompany(){
		return company;
	}
	public String getCompanyIframe(){
		return company_iframe;
	}
	public String getBackground(){
		return background;
	}
}
