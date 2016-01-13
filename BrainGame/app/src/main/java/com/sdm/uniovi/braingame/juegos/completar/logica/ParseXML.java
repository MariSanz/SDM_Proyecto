package com.sdm.uniovi.braingame.juegos.completar.logica;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ParseXML {
    public Document getDom(String xml) {
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is);

        } catch (ParserConfigurationException e) {
            Log.e("Error Parser: ", e.getMessage());
            return null;
        } catch (SAXException e) {
            Log.e("Error SAX: ", e.getMessage());
            return null;
        } catch (IOException e) {
            Log.e("Error IO: ", e.getMessage());
            return null;
        }
        // return DOM
        return doc;
    }

    public String getValor(Element item) {
        Node child;
        if(item != null) {
            child = item.getFirstChild();
            if(child.getNodeType() == Node.TEXT_NODE)
                return child.getNodeValue();
        }
        return "";
    }
}

