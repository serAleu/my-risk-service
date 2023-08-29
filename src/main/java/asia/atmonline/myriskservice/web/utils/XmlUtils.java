package asia.atmonline.myriskservice.web.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.apache.commons.lang3.StringUtils;

public class XmlUtils {

  public static byte[] buildBytes(Object object) {
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    build(os, object);
    return os.toByteArray();
  }

  public static String buildString(Object object) {
    StringWriter writer = new StringWriter();
    build(writer, object);
    return writer.toString();

  }

  public static String buildUnformattedString(Object object, String encoding, boolean isFragment) {
    StringWriter writer = new StringWriter();
    buildUnformatted(writer, object, encoding, isFragment);
    return writer.toString();
  }

  public static void build(OutputStream os, Object object) {
    try {
      JAXBContext context = JAXBContext.newInstance(object.getClass());
      Marshaller marshaller = context.createMarshaller();
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
      marshaller.marshal(object, os);
    } catch (JAXBException e) {
      throw new RuntimeException();
    }
  }

  public static void build(Writer writer, Object object) {
    try {
      JAXBContext context = JAXBContext.newInstance(object.getClass());
      Marshaller marshaller = context.createMarshaller();
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
      marshaller.marshal(object, writer);
    } catch (JAXBException e) {
      throw new RuntimeException(e);
    }
  }

  public static void buildUnformatted(Writer writer, Object object, String encoding, boolean isFragment) {
    try {
      JAXBContext context = JAXBContext.newInstance(object.getClass());
      Marshaller marshaller = context.createMarshaller();
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
      if (StringUtils.isNotEmpty(encoding)) {
        marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
      }
      if (isFragment) {
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
      }
      marshaller.marshal(object, writer);
    } catch (JAXBException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T parse(byte[] xml, Class<T> clazz) throws IOException {
    try (InputStream is = new ByteArrayInputStream(xml)) {
      return parse(is, clazz);
    }
  }

  public static <T> T parse(String xml, Class<T> clazz) {
    try (StringReader reader = new StringReader(xml)) {
      return parse(reader, clazz);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T parseSafe(String xml, Class<T> clazz) {
    try {
      return parse(xml, clazz);
    } catch (Throwable ignored) {
    }
    return null;
  }

  @SuppressWarnings("unchecked")
  public static <T> T parse(InputStream is, Class<T> clazz) {
    try {
      return (T) JAXBContext.newInstance(clazz).createUnmarshaller().unmarshal(is);
    } catch (JAXBException e) {
      throw new RuntimeException(e);
    }
  }

  @SuppressWarnings("unchecked")
  public static <T> T parse(Reader reader, Class<T> clazz) {
    try {
      return (T) JAXBContext.newInstance(clazz).createUnmarshaller().unmarshal(reader);
    } catch (JAXBException e) {
      throw new RuntimeException(e);
    }
  }
}
