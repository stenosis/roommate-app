/*
 *  Roommate
 *  Copyright (C) 2012,2013 Team Roommate (info@roommateapp.info)
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/* package */
package roommateapp.info.io;

/* imports */
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import roommateapp.info.droid.RoommateConfig;
import roommateapp.info.entities.BuildingFile;

/**
 * Content Roommate file Checker,
 * validation of Roommate files.
 */
public class CHandlerRFileCheck implements ContentHandler {

	// Instance variables
	private boolean openInfoTag;
	private static final String ROOMLIST = "roomlist";
	private static final String CONFIG = "config";
	private static final String RELEASE = "release";
	private static final String DATE = "date";
	private static final String SEMESTER = "semester";
	private static final String INFO = "info";
	private static final String GEO = "geo";
	private static final String LAT = "lat";
	private static final String LNG = "lng";
	private static final String ID = "buildingID";
	private static final String BUILDINGNAME = "buildingname";
	private static final String PUBLICSTATUS = "public";
	private static final String STATE = "state";
	private BuildingFile currentFile = new BuildingFile();

	/**
	 * Returns the parsed Roommate file.
	 * 
	 * @return
	 */
	public BuildingFile getBuildingFile() {
		return this.currentFile;
	}

	/**
	 * Start parsing the Roommate file checker.
	 */
	public void startDocument() throws SAXException {
		if (RoommateConfig.VERBOSE) {
			System.out.println("Start checking Roommate file..");
		}
	}

	/**
	 * Stop parsing the Roommate file checker.
	 */
	public void endDocument() throws SAXException {
		if (RoommateConfig.VERBOSE) {
			System.out.println("Stop checking Roommate file..");
		}
	}

	/**
	 * Checks the xml-tag and get the appropriate data.
	 */
	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {

		// Config-Tag
		if (qName.equals(CONFIG)) {
			
			currentFile.setBuildingname(atts.getValue(BUILDINGNAME));
			currentFile.setDate(atts.getValue(DATE));
			currentFile.setRelease(atts.getValue(RELEASE));
			currentFile.setId(atts.getValue(ID));
			currentFile.setState(atts.getValue(STATE));
			String publicStatus = atts.getValue(PUBLICSTATUS);
			if(publicStatus.equals("true")) {
				currentFile.setPublic();
			}
		
		// Geo-Tag
		} else if (qName.equals(GEO)) {
			
			currentFile.setLat(atts.getValue(LAT));
			currentFile.setLng(atts.getValue(LNG));
		
		// Roomlist-Tag
		} else if (qName.equals(ROOMLIST)) {
			
			currentFile.setSemester(atts.getValue(SEMESTER));
		
		// Info-Tag
		} else if (qName.equals(INFO)) {
			
			this.openInfoTag = true;
		}
	}

	/**
	 * Close tag-element.
	 */
	public void endElement(String uri, String localName, String qName) throws SAXException {

		// Closing the info-tag
		if (this.openInfoTag) {
			this.openInfoTag = false;
		}
	}

	/**
	 * Get the content string of the tag.
	 * <tag>content</tag>
	 */
	public void characters(char[] ch, int start, int length) throws SAXException {
		
		// Content of the tag
		String tagString = new String(ch, start, length);
		
		// Info-tag content
		if (this.openInfoTag) {
			currentFile.setInfo(tagString);
		}
	}

	public void endPrefixMapping(String prefix) throws SAXException {
		// TODO Auto-generated method stub
	}

	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
	}

	public void processingInstruction(String target, String data)
			throws SAXException {
		// TODO Auto-generated method stub
	}

	public void setDocumentLocator(Locator locator) {
		// TODO Auto-generated method stub
	}

	public void skippedEntity(String name) throws SAXException {
		// TODO Auto-generated method stub
	}

	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
		// TODO Auto-generated method stub
	}
}