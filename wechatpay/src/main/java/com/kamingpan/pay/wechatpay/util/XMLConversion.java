package com.kamingpan.pay.wechatpay.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;

/**
 * xml转换
 *
 * @author kamingpan
 * @since 2016-05-05
 */
public class XMLConversion {

    /**
     * 对象转换成xml(格式化及去除头信息)
     *
     * @param object 转换对象
     * @param <T>    泛型类
     * @return xml字符串
     * @throws JAXBException                jaxb异常
     * @throws UnsupportedEncodingException 不支持编码异常
     */
    public static <T> String convertToXML(T object) throws JAXBException, UnsupportedEncodingException {
        return XMLConversion.convertToXML(object, true, true);
    }

    /**
     * 对象转换成xml
     *
     * @param object         转换对象
     * @param isFormat       是否格式化
     * @param isIgnoreHeader 是否去除头信息
     * @param <T>            泛型类
     * @return xml字符串
     * @throws JAXBException                jaxb异常
     * @throws UnsupportedEncodingException 不支持编码异常
     */
    public static <T> String convertToXML(T object, boolean isFormat, boolean isIgnoreHeader) throws JAXBException, UnsupportedEncodingException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        JAXBContext context = JAXBContext.newInstance(object.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, isFormat); // 是否格式化生成的xml串
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, isIgnoreHeader); // 是否省略xml头信息
        marshaller.marshal(object, byteArrayOutputStream); // 将对象转换为对应的XML文件

        return new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);
    }

    /**
     * xml转换成对象
     *
     * @param xml   xml字符串
     * @param clazz 返回对象
     * @param <T>   类泛型
     * @return 泛型类
     * @throws JAXBException                jaxb异常
     * @throws UnsupportedEncodingException 不支持编码异常
     */
    @SuppressWarnings("unchecked")
    public static <T> T convertToObject(String xml, Class<T> clazz) throws JAXBException, UnsupportedEncodingException {
        JAXBContext context = JAXBContext.newInstance(clazz);
        // 转为系统默认字符编码输入流
        InputStream inputStream = new ByteArrayInputStream(xml.getBytes(System.getProperty("file.encoding")));
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (T) unmarshaller.unmarshal(inputStream);
    }

    /**
     * xml转换成TreeMap
     *
     * @param xml xml字符串
     * @return TreeMap
     * @throws IOException                  IO异常
     * @throws SAXException                 SAX异常
     * @throws ParserConfigurationException 分析器配置异常
     */
    public static Map<String, String> convertToMap(String xml)
            throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        documentBuilderFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        documentBuilderFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        documentBuilderFactory.setXIncludeAware(false);
        documentBuilderFactory.setExpandEntityReferences(false);
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        // TreeMap存储，保证键的自然顺序
        Map<String, String> result = new TreeMap<String, String>();
        InputStream inputStream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
        Document document = documentBuilder.parse(inputStream);
        document.getDocumentElement().normalize();
        NodeList nodeList = document.getDocumentElement().getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                result.put(element.getNodeName(), element.getTextContent());
            }
        }
        return result;
    }

}
