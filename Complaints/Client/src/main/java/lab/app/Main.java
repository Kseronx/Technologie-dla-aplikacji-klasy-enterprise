package lab.app;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lab.dto.ComplaintDTO;

import java.time.LocalDate;
import java.util.List;

public class Main {

    private static void printStatus301(){
        Client client = ClientBuilder.newClient();
        String status = client.target("http://localhost:8080/Server-1.0-SNAPSHOT/" + "api/complaints/301/status")
                .request(MediaType.TEXT_PLAIN)
                .get(String.class);
        System.out.println("Status complaint#301: " + status);
        client.close();
    }

    private static void printAll(){
        Client client = ClientBuilder.newClient();
        var output = client.target("http://localhost:8080/Server-1.0-SNAPSHOT/" + "api/complaints/")
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<ComplaintDTO>>() {});

        System.out.println("All complaints:");
        System.out.println(output);
        client.close();
    }

    private static void printAllOpened(){
        Client client = ClientBuilder.newClient();
        var output = client.target("http://localhost:8080/Server-1.0-SNAPSHOT/" + "api/complaints?status=open")
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<ComplaintDTO>>() {});

        System.out.println("All complaints:");
        System.out.println(output);
        client.close();
    }

    private static void printOpened303(){
        Client client = ClientBuilder.newClient();
        var output = client.target("http://localhost:8080/Server-1.0-SNAPSHOT/" + "api/complaints/303")
                .request(MediaType.APPLICATION_JSON)
                .get(ComplaintDTO.class);

        System.out.println("Opened complaint: " + output.toString());
        client.close();
    }

    private static void change303Status(){
        ComplaintDTO complaint = new ComplaintDTO();
        complaint.setId(303L);
        complaint.setAuthor("Arthur McCoy");
        complaint.setComplaintDate(LocalDate.parse("2021-04-24"));
        complaint.setComplaintText("Repair fridge in room 311");
        complaint.setStatus("closed");

        Client client = ClientBuilder.newClient();
        var output = client.target("http://localhost:8080/Server-1.0-SNAPSHOT/" + "api/complaints/303")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json(complaint));

        if (output.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
            System.out.println("303 updated");
        } else {
            System.out.println("303 update error: " + output.getStatus());
        }

        client.close();
    }


    public static void main(String[] args){
        printAll();
        change303Status();
        printAllOpened();
    }
}
