package br.ufjf.dcc171;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class ProdutoDAOXML implements ProdutoDAO{
    

    @Override
    public void criar(Produto prod) throws Exception {
        JAXBContext ctx = JAXBContext.newInstance(new Class[]{ProdutoWrapper.class, Produto.class});
        StreamResult saida = new StreamResult("dados.xml");
        Marshaller marshaller = ctx.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        ProdutoWrapper pw = new ProdutoWrapper(listarTodos());
        pw.getItems().add(prod);
        JAXBElement<ProdutoWrapper> e1 = new JAXBElement<>(new QName("produtos"), ProdutoWrapper.class, pw);
        marshaller.marshal(e1, saida);
    }

    @Override
    public List<Produto> listarTodos() throws Exception {
        JAXBContext ctx = JAXBContext.newInstance(new Class[]{ProdutoWrapper.class, Produto.class});
        StreamSource entrada = new StreamSource("dados.xml");
        Unmarshaller unmarshaller = ctx.createUnmarshaller();
        JAXBElement<ProdutoWrapper> e1 = unmarshaller.unmarshal(entrada, ProdutoWrapper.class);
        ProdutoWrapper pw = e1.getValue();
        return pw.getItems();
    }
    
        @XmlRootElement
    static class ProdutoWrapper{
        private List<Produto> items;

        public ProdutoWrapper(List<Produto> items) {
            this.items = items;
        }

        public ProdutoWrapper() {
            this(new ArrayList<Produto>());
        }

        @XmlAnyElement(lax = true)
        public List<Produto> getItems() {
            return items;
        }
        
        
        
    }
    
}
