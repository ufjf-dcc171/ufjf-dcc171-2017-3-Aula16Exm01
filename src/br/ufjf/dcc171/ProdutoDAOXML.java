package br.ufjf.dcc171;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class ProdutoDAOXML implements ProdutoDAO{
    

    @Override
    public void criar(Produto prod) throws Exception {
        JAXBContext ctx = JAXBContext.newInstance(Produto.class);
        StreamResult saida = new StreamResult("saida.xml");
        Marshaller marshaller = ctx.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        JAXBElement<Produto> e1 = new JAXBElement<>(new QName("produto"), Produto.class, prod);
        marshaller.marshal(e1, saida);
    }

    @Override
    public List<Produto> listarTodos() throws Exception {
        JAXBContext ctx = JAXBContext.newInstance(Produto.class);
        StreamSource entrada = new StreamSource("entrada.xml");
        Unmarshaller unmarshaller = ctx.createUnmarshaller();
        JAXBElement<Produto> e1 = unmarshaller.unmarshal(entrada, Produto.class);
        Produto p = e1.getValue();
        System.out.println(p);
        return new ArrayList<>();
    }
    
    
    
}
