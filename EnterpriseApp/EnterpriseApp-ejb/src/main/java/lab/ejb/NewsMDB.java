package lab.ejb;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@JMSDestinationDefinition(
        name = "java:app/jms/NewsQueue",
        interfaceName = "jakarta.jms.Queue",
        resourceAdapter = "jmsra",
        destinationName = "NewsQueue")
@MessageDriven(
    activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:app/jms/NewsQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue")
})
public class NewsMDB implements jakarta.jms.MessageListener {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void onMessage(Message message) {
        ObjectMessage msg = null;
        TextMessage tmsg = null;
        try {
            if (message instanceof ObjectMessage) {
                msg = (ObjectMessage) message;
                NewsItem e = (NewsItem) msg.getObject();
                em.persist(e);
            } else if(message instanceof TextMessage){
                tmsg = (TextMessage) message;
                NewsItem item = new NewsItem();
                String[] parts = tmsg.getText().split("\\|",2);
                item.setHeading(parts[0]);
                item.setBody(parts[1]);
                em.persist(item);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
